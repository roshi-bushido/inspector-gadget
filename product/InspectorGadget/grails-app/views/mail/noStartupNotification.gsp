<%@ page import="inspector.gadget.job.JobInstance" contentType="text/html" %>
<html>
<head>
    <title>Error en la ejecuci&oacute;n del proceso</title>

    <style>
    .container {
        width: 100%;
        height: 100%;
    }

    .status {
        background-color: RED;
    }

    .field {
        width: 200px;
    }

    .value {
        width: 400px;
    }
    </style>
</head>
<body>
<%
    JobInstance jobInstance = JobInstance.findLastFrom(instance)
%>

<div class="container">
    <table border="1">
        <tbody>
        <tr>
            <th class="field">Property</th>
            <th class="value">Valor</th>
        </tr>
        <tr>
            <td>Proceso</td>
            <td>${instance?.name}</td>
        </tr>
        <tr>
            <td>Status</td>
            <td class="status">ERROR AT STARTUP</td>
        </tr>

        <g:if test="${jobInstance != null}" >
            <tr>
                <td>Error</td>
                <td>El proceso no volvi&oacute; a correr desde la &uacute; ejecuci&oacute;n (id: ${jobInstance?.id})</td>
            </tr>
            <tr>
                <td>Link &uacute;ltima instancia ok</td>
                <td><a href='<%= createLink(controller: "jobInstance", action: "show", id: jobInstance?.id, absolute: true)%>'>Instancia</a></td>
            </tr>
        </g:if>

        <g:if test="${jobInstance == null}" >
            <tr>
                <td>Error</td>
                <td>El proceso (id: ${instance?.id}) nunca corri&oacute; por primera vez. </td>
            </tr>
            <tr>
                <td>Link al Procesok</td>
                <td><a href='<%= createLink(controller: "job", action: "show", id: instance?.id, absolute: true)%>'>Proceso</a></td>
            </tr>
        </g:if>
        <tr>
            <td>Entorno</td>
            <td>${org.codehaus.groovy.grails.commons.ConfigurationHolder.config.grails.serverURL}</td>
        </tr>
        </tbody>
    </table>
</div>
<label>Mail autogenerador por <a href='<%= createLink(uri: "/", absolute: true)%>'>Sistema Monitoreo de Procesos</a></label>
</body>
</html>