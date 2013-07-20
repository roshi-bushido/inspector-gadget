<%@ page contentType="text/html" %>
<html>
<head>
    <title>Creaci&oacute;n de un nuevo proceso</title>

    <style>
    .container {
        width: 100%;
        height: 100%;
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
            <td>Description</td>
            <td>${instance?.description}</td>
        </tr>
        <tr>
            <td>Criticidad</td>
            <td>${instance?.criticity}</td>
        </tr>
        <tr>
            <td>Link</td>
            <td><a href='<%= createLink(controller: "job", action: "show", id: instance?.id, absolute: true)%>'>Instancia</a></td>
        </tr>
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
