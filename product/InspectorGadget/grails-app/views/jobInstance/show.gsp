<%@ page import="inspector.gadget.job.JobInstance" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="bootstrap">
    <g:set var="entityName" value="${message(code: 'jobInstance.label', default: 'JobInstance')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="row-fluid">

    <!-- Panel de acciones -->
    <div class="span3">
        <g:render template="/common/actionMenu" model="[currentFilter: 'jobInstance']"/>
    </div>

    <div class="span9">

        <div class="page-header">
            <h1><g:message code="default.show.label" args="[entityName]"/></h1>
        </div>

        <g:if test="${flash.message}">
            <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
        </g:if>

        <dl>
            <g:if test="${jobInstanceInstance?.job}">
                <dt><g:message code="jobInstance.job.label" default="Job"/></dt>
                <dd><g:link controller="job" action="show" id="${jobInstanceInstance?.job?.id}">${jobInstanceInstance?.job?.encodeAsHTML()}</g:link></dd>
            </g:if>

            <g:if test="${jobInstanceInstance?.startedAt}">
                <dt><g:message code="jobInstance.startedAt.label" default="Started At"/></dt>
                <dd><g:formatDate date="${jobInstanceInstance?.startedAt}"/></dd>
            </g:if>

            <g:if test="${jobInstanceInstance?.endedAt}">
                <dt><g:message code="jobInstance.endedAt.label" default="Ended At"/></dt>
                <dd><g:formatDate date="${jobInstanceInstance?.endedAt}"/></dd>
            </g:if>

            <g:if test="${jobInstanceInstance?.status}">
                <dt><g:message code="jobInstance.status.label" default="Status"/></dt>
                <dd><jobInstance:status instance="${jobInstanceInstance}" /></dd>
            </g:if>

            <g:if test="${jobInstanceInstance?.description}">
                <dt><g:message code="jobInstance.description.label" default="Description"/></dt>
                <dd><g:fieldValue bean="${jobInstanceInstance}" field="description"/></dd>
            </g:if>
        </dl>
    </div>
</div>
</body>
</html>