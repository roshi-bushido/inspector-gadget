<!doctype html>
<html>
<head>
    <meta name="layout" content="bootstrap">
    <g:set var="entityName" value="${message(code: 'dashboard.label', default: 'Dashboard')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
    <r:require module="charting" />
</head>

<body>
<div class="row-fluid">
    <!-- Panel de acciones -->
    <div class="span3">
        <g:render template="/common/actionMenu" model="[currentFilter: 'dashboard']"/>
    </div>

    <div class="span9">
        <div class="page-header">
            <h1><g:message code="dashboard.global.label" default="Global Dashboard"/></h1>
        </div>
        <g:render template="global/lastErrors" model="[dashboardInstance: dashboardInstance]" />
        <g:render template="global/appCountChart" model="[dashboardInstance: dashboardInstance]" />
        <g:render template="global/lastErrorsChart" model="[dashboardInstance: dashboardInstance]" />
    </div>
</div>
</body>
</html>
