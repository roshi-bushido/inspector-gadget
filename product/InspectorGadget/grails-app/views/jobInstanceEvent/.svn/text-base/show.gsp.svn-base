<%@ page import="inspector.gadget.job.JobInstanceEvent" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="bootstrap">
    <g:set var="entityName" value="${message(code: 'jobInstanceEvent.label', default: 'JobInstance')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="row-fluid">

    <!-- Panel de acciones -->
    <div class="span3">
        <g:render template="/common/actionMenu" model="[currentFilter: 'jobInstanceEvent']"/>
    </div>

    <div class="span9">

        <div class="page-header">
            <h1><g:message code="default.show.label" args="[entityName]"/></h1>
        </div>

        <g:if test="${flash.message}">
            <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
        </g:if>

        <dl>
            <g:if test="${jobInstanceEventInstance?.instance}">
                <dt><g:message code="jobInstanceEvent.job.label" default="Job"/></dt>
                <dd><g:link controller="jobInstance" action="show" id="${jobInstanceEventInstance?.instance?.id}">${jobInstanceEventInstance?.instance?.encodeAsHTML()}</g:link></dd>
            </g:if>

            <g:if test="${jobInstanceEventInstance?.dateCreated}">
                <dt><g:message code="jobInstanceEvent.dateCreated.label" default="Date Created"/></dt>
                <dd><g:formatDate date="${jobInstanceEventInstance?.dateCreated}"/></dd>
            </g:if>

            <g:if test="${jobInstanceEventInstance?.status}">
                <dt><g:message code="jobInstanceEvent.status.label" default="Status"/></dt>
                <dd><jobInstance:status instance="${jobInstanceEventInstance}" /></dd>
            </g:if>

            <g:if test="${jobInstanceEventInstance?.description}">
                <dt><g:message code="jobInstanceEvent.description.label" default="Description"/></dt>
                <dd><g:fieldValue bean="${jobInstanceEventInstance}" field="description"/></dd>
            </g:if>
        </dl>
    </div>
</div>
</body>
</html>