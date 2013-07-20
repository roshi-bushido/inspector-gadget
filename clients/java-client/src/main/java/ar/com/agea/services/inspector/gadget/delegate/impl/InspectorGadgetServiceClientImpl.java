package ar.com.agea.services.inspector.gadget.delegate.impl;

import ar.com.agea.services.inspector.gadget.delegate.InspectorGadgetServiceClient;
import ar.com.agea.services.inspector.gadget.dto.JobDTO;
import ar.com.agea.services.inspector.gadget.dto.JobInstanceDTO;
import ar.com.agea.services.inspector.gadget.dto.JobInstanceEventDTO;
import ar.com.agea.services.inspector.gadget.exception.JobNotEnabledException;
import ar.com.agea.services.inspector.gadget.exception.ServerErrorException;
import ar.com.agea.services.inspector.gadget.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Implementacion HTTP de {@link InspectorGadgetServiceClient}
 *
 * @author Matias Suarez
 * @author AGEA 2013
 */
public class InspectorGadgetServiceClientImpl implements InspectorGadgetServiceClient {
    private static final String ARCHIVO_PROPIEDADES_EXTERNO = "inspector-gadget.properties";
    private static final String ARCHIVO_PROPIEDADES_POR_DEFECTO = "inspector-gadget-default.properties";

    private static Log logger = LogFactory.getLog(InspectorGadgetServiceClient.class);
    private HttpClient client;
    private Gson jsonParser;
    private Properties configurationKeys;

    private final String HOST;

    public InspectorGadgetServiceClientImpl() {
        this.jsonParser = new GsonBuilder().create();
        this.configurationKeys = new java.util.Properties();

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(ARCHIVO_PROPIEDADES_POR_DEFECTO);
        InputStream extInputStream = this.getClass().getClassLoader().getResourceAsStream(ARCHIVO_PROPIEDADES_EXTERNO);

        try {
            logger.info("Setting default properties found in " + ARCHIVO_PROPIEDADES_POR_DEFECTO);
            configurationKeys.load(inputStream);

            if (extInputStream != null) {
                logger.info("Overriding properties with the ones found in " + ARCHIVO_PROPIEDADES_EXTERNO);
                configurationKeys.load(extInputStream);
            }
            HOST = configurationKeys.getProperty(Services.HOST);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error loading configuration file. Cause is %s", e));
        }
        this.client = getHTTPClient();
    }

    private HttpClient getHTTPClient() {
        Boolean isProxyEnabled = Boolean.parseBoolean(configurationKeys.getProperty("proxy.enabled", "false"));
        PoolingClientConnectionManager manager = new PoolingClientConnectionManager(SchemeRegistryFactory.createDefault());
        manager.setMaxTotal(200);
        manager.setDefaultMaxPerRoute(20);
        DefaultHttpClient client = new DefaultHttpClient(manager);

        if (isProxyEnabled) {
            String proxyHost = configurationKeys.getProperty("proxy.host");
            int proxyPort = Integer.parseInt(configurationKeys.getProperty("proxy.port"));
            String proxyUser = configurationKeys.getProperty("proxy.user");
            String proxyPass = configurationKeys.getProperty("proxy.pass");
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(proxyHost, proxyPort));
            client.getCredentialsProvider().setCredentials(
                    new AuthScope(proxyHost, proxyPort),
                    new UsernamePasswordCredentials(proxyUser, proxyPass)
            );
            // This will exclude the NTLM authentication scheme
            client.getParams().setParameter(AuthPNames.PROXY_AUTH_PREF, Arrays.asList(AuthPolicy.DIGEST, AuthPolicy.BASIC));
        }
        return client;
    }

    public Boolean isRunning() {
        String service = configurationKeys.getProperty(Services.PING);
        String requestURL = HOST.concat(service);
        try {
            HttpJsonResponse response = this.getReponseFrom(requestURL, false, null);
            String json = StringUtil.removeEmptySpaces(response.jsonReponse());
            return json.contains("\"server-status\":\"ok\"");
        } catch (Exception e) {
            logger.error(String.format("Error executing %s. Root cause is %s.", requestURL, e.getMessage()));
            return false;
        }
    }

    public Collection<JobDTO> getJobList() {
        if (!isRunning()) { return new ArrayList<JobDTO>(); }

        Type collectionType = new TypeToken<Collection<JobDTO>>() {}.getType();
        String service = configurationKeys.getProperty(Services.JOB_LIST);
        String requestURL = HOST.concat(service);

        logger.info(String.format("Calling getJobList making request: %s", requestURL));
        return executeRequest(collectionType, requestURL);
    }

    public JobDTO findJobDTOByName(String jobName) {
        if (!isRunning()) { return null; }

        Type collectionType = new TypeToken<JobDTO>() {}.getType();
        String service = String.format(configurationKeys.getProperty(Services.JOB_BY_NAME), jobName);
        String requestURL = HOST.concat(service);

        logger.info(String.format("Calling findJobDTOByName making request: %s", requestURL));

        JobDTO jobDTO = executeRequest(collectionType, requestURL);
        if (jobDTO.getId() != null) {
            return jobDTO;
        } else {
            return null;
        }
    }

    public Collection<JobInstanceDTO> getJobInstancesFrom(JobDTO job) {
        if (!isRunning()) { return new ArrayList<JobInstanceDTO>(); }

        Type collectionType = new TypeToken<Collection<JobInstanceDTO>>() {}.getType();
        String service = String.format(configurationKeys.getProperty(Services.JOB_INSTANCE_LIST), job.getId());
        String requestURL = HOST.concat(service);
        logger.info(String.format("Calling getJobInstancesFrom making request: %s", requestURL));
        return executeRequest(collectionType, requestURL);
    }

    public JobInstanceDTO startInstanceFor(JobDTO job) {
        if (!isRunning()) { return null; }

        if ( job.getEnabled() ) {
            Type collectionType = new TypeToken<JobInstanceDTO>() {}.getType();
            String service = String.format(configurationKeys.getProperty(Services.JOB_INSTANCE_START), job.getId());
            String requestURL = HOST.concat(service);
            logger.info(String.format("Calling startInstanceFor making request: %s", requestURL));
            return executeRequest(collectionType, requestURL, true);
        } else {
            throw new JobNotEnabledException(String.format("Job (id=%s) is not enabled", job.getId()));
        }
    }

    public JobInstanceDTO finishInstance(JobInstanceDTO instance) {
        if (!isRunning()) { return null; }

        Type collectionType = new TypeToken<JobInstanceDTO>() {}.getType();
        String service = String.format(configurationKeys.getProperty(Services.JOB_INSTANCE_FINISH), instance.getJobId(), instance.getId());
        String requestURL = HOST.concat(service);
        logger.info(String.format("Calling finishInstance making request: %s", requestURL));
        return executeRequest(collectionType, requestURL, true);
    }

    public JobInstanceDTO crashInstance(JobInstanceDTO instance, String errorDescription) {
        if (!isRunning()) { return null; }

        Type collectionType = new TypeToken<JobInstanceDTO>() {}.getType();
        String service = String.format(configurationKeys.getProperty(Services.JOB_INSTANCE_CRASHED), instance.getJobId(), instance.getId());
        String requestURL = HOST.concat(service);
        logger.info(String.format("Calling crashInstance making request: %s", requestURL));
        try {
            Map<String,String> params = new HashMap<String,String>();
            params.put("trace", errorDescription);
            return executeRequest(collectionType, requestURL, true, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public JobInstanceDTO crashInstance(JobInstanceDTO instance, Throwable throwable) {
        if (!isRunning()) { return null; }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String reason = sw.toString();
        return this.crashInstance(instance, reason);
    }

    public Collection<JobInstanceEventDTO> getEventListFrom(JobInstanceDTO instance) {
        if (!isRunning()) { return null; }
        Type collectionType = new TypeToken<Collection<JobInstanceEventDTO>>() {}.getType();
        String service = String.format(configurationKeys.getProperty(Services.JOB_INSTANCE_EVENTS), instance.getId());
        String requestURL = HOST.concat(service);
        logger.info(String.format("Calling getEventListFrom making request: %s", requestURL));
        return executeRequest(collectionType, requestURL);
    }

    public JobInstanceEventDTO addSuccessEventFor(JobInstanceDTO instance, String message) {
        if (!isRunning()) { return null; }
        Type collectionType = new TypeToken<JobInstanceEventDTO>() {}.getType();
        String service = String.format(configurationKeys.getProperty(Services.JOB_INSTANCE_EVENT_SUCCESS), instance.getId());
        String requestURL = HOST.concat(service);
        logger.info(String.format("Calling addSuccessEventFor making request: %s", requestURL));
        Map<String,String> params = new HashMap<String,String>();
        params.put("message", message);
        return executeRequest(collectionType, requestURL, true, params);
    }

    public JobInstanceEventDTO addErrorEventFor(JobInstanceDTO instance, String message) {
        if (!isRunning()) { return null; }
        Type collectionType = new TypeToken<JobInstanceEventDTO>() {}.getType();
        String service = String.format(configurationKeys.getProperty(Services.JOB_INSTANCE_EVENT_ERROR), instance.getId());
        String requestURL = HOST.concat(service);
        logger.info(String.format("Calling addErrorEventFor making request: %s", requestURL));
        Map<String,String> params = new HashMap<String,String>();
        params.put("message", message);
        return executeRequest(collectionType, requestURL, true, params);
    }

    private <T> T executeRequest(Type elementType, String requestURL) {
        return executeRequest(elementType, requestURL, false);
    }

    private <T> T executeRequest(Type elementType, String requestURL, Boolean isPost) {
        return executeRequest(elementType, requestURL, isPost, null);
    }

    private <T> T executeRequest(Type elementType, String requestURL, Boolean isPost, Map<String, String> params) {
        try {
            HttpJsonResponse response = this.getReponseFrom(requestURL, isPost, params);
            return parseJsonResponse(elementType, response);
        } catch (Exception e) {
            logger.error(String.format("Error executing %s. Root cause is %s.", requestURL, e.getMessage()));
            throw new ServerErrorException(e.getCause()) ;
        }
    }

    private <T> T parseJsonResponse(Type elementType, HttpJsonResponse response) throws Exception {
        String json = response.jsonReponse();
        if (logger.isDebugEnabled()) {
            logger.debug(json);
        }
        return this.jsonParser.fromJson(json, elementType);
    }

    private HttpJsonResponse getReponseFrom(final String url, Boolean isPost, Map<String, String> params) throws IOException {
        HttpUriRequest request = null;

        if (isPost) {
           request = createPostWith(url, params);
        } else {
            request = new HttpGet(url);
        }

        HttpResponse response = client.execute(request);
        client.getConnectionManager().closeExpiredConnections();
        HttpJsonResponse jsonResponse = new HttpJsonResponse(response);
        if (jsonResponse.getCode() < 200 || jsonResponse.getCode() >= 300) {
            logger.warn(String.format("Request URL (%s) returned HTTP CODE %s instead of 200. ", url, jsonResponse.getCode()));
        }

        return jsonResponse;
    }

    private HttpPost createPostWith(String url, Map<String, String> params) throws UnsupportedEncodingException {
        HttpPost request = new HttpPost(url);

        if ( params != null ) {
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();

            for ( Map.Entry<String, String> entry : params.entrySet() ) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            request.setEntity(entity);
        }
        return request;
    }

    private interface Services {
        String HOST =                       "inspector.gadget.api.host";
        String PING =                       "inspector.gadget.api.catalog.status";
        String JOB_LIST =                   "inspector.gadget.api.catalog.job.list";
        String JOB_BY_NAME =                "inspector.gadget.api.catalog.job.byName";
        String JOB_INSTANCE_LIST =          "inspector.gadget.api.catalog.job.instance.list";
        String JOB_INSTANCE_START =         "inspector.gadget.api.catalog.job.instance.start";
        String JOB_INSTANCE_FINISH =        "inspector.gadget.api.catalog.job.instance.finish";
        String JOB_INSTANCE_CRASHED =       "inspector.gadget.api.catalog.job.instance.crashed";
        String JOB_INSTANCE_EVENTS =        "inspector.gadget.api.catalog.job.instance.events";
        String JOB_INSTANCE_EVENT_SUCCESS = "inspector.gadget.api.catalog.job.instance.event.success";
        String JOB_INSTANCE_EVENT_ERROR =   "inspector.gadget.api.catalog.job.instance.event.error";
    }

    private class HttpJsonResponse {
        private HttpResponse originalResponse;

        public HttpJsonResponse(HttpResponse response) {
            this.originalResponse = response;
        }

        public int getCode() {
            return originalResponse.getStatusLine().getStatusCode();
        }

        public String jsonReponse() throws Exception {
            StringWriter writer = new StringWriter();
            IOUtils.copy(originalResponse.getEntity().getContent(), writer, "UTF-8");
            String jsonResponse = writer.toString();
            return jsonResponse;
        }
    }
}