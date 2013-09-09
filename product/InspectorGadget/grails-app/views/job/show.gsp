<%@ page import="inspector.gadget.job.Job" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="bootstrap">
    <g:set var="entityName" value="${message(code: 'job.label', default: 'Job')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="row-fluid">

    <div class="span3">
        <g:render template="/common/actionMenu" model="[currentFilter: 'job']"/>
    </div>

    <div class="span9">
        <div class="page-header">
            <h1><g:message code="default.show.label" args="[entityName]"/></h1>
        </div>

        <g:if test="${flash.message}">
            <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
        </g:if>

        <dl>
            <g:if test="${jobInstance?.name}">
                <dt><g:message code="job.name.label" default="Name"/></dt>
                <dd><g:fieldValue bean="${jobInstance}" field="name"/></dd>
            </g:if>

            <g:if test="${jobInstance?.description}">
                <dt><g:message code="job.description.label" default="Description"/></dt>
                <dd><g:fieldValue bean="${jobInstance}" field="description"/></dd>
            </g:if>

            <g:if test="${jobInstance?.errorDurationThreshold}">
                <dt><g:message code="job.errorDurationThreshold.label" default="Error Duration Threshold"/></dt>
                <dd><g:fieldValue bean="${jobInstance}" field="errorDurationThreshold"/></dd>
            </g:if>

            <g:if test="${jobInstance?.warningDurationThreshold}">
                <dt><g:message code="job.warningDurationThreshold.label" default="Warning Duration Threshold"/></dt>
                <dd><g:fieldValue bean="${jobInstance}" field="warningDurationThreshold"/></dd>
            </g:if>

            <g:if test="${jobInstance?.executionInterval}">
                <dt><g:message code="job.executionInterval.label" default="Execution Interval"/></dt>
                <dd><g:fieldValue bean="${jobInstance}" field="executionInterval"/></dd>
            </g:if>

            <g:if test="${jobInstance?.enabled}">
                <dt><g:message code="job.enabled.label" default="Enabled"/></dt>
                <dd><g:formatBoolean boolean="${jobInstance?.enabled}"/></dd>
            </g:if>

            <g:if test="${jobInstance?.application?.id}">
                <dt><g:message code="job.application.label" default="Application"/></dt>
                <dd><g:fieldValue bean="${jobInstance.application}" field="name"/></dd>
            </g:if>

            <g:if test="${jobInstance?.configuration}">
                <dt><g:message code="job.configuration.label" default="Configuration"/></dt>
                <dd><g:fieldValue bean="${jobInstance.configuration}" field="name"/></dd>
            </g:if>

            <g:if test="${jobInstance?.criticity}">
                <dt><g:message code="job.criticity.label" default="Criticity"/></dt>
                <dd><g:fieldValue bean="${jobInstance}" field="criticity"/></dd>
            </g:if>

            <g:if test="${jobInstance?.executionInterval}">
                <dt><g:message code="job.executionInterval.label" default="Execution Interval"/></dt>
                <dd><g:fieldValue bean="${jobInstance}" field="executionInterval"/></dd>
            </g:if>

            <g:if test="${jobInstance?.executionRegExp}">
                <dt><g:message code="job.executionRegExp.label" default="Execution Reg Exp"/></dt>
                <dd><g:fieldValue bean="${jobInstance}" field="executionRegExp"/></dd>
            </g:if>

            <g:if test="${jobInstance?.usesRegExp}">
                <dt><g:message code="job.usesRegExp.label" default="Uses Reg Exp"/></dt>
                <dd><g:formatBoolean boolean="${jobInstance?.usesRegExp}"/></dd>
            </g:if>

            <g:if test="${jobInstance?.dateCreated}">
                <dt><g:message code="job.dateCreated.label" default="Date Created"/></dt>
                <dd><g:formatDate date="${jobInstance?.dateCreated}"/></dd>
            </g:if>

            <g:if test="${jobInstance?.lastUpdated}">
                <dt><g:message code="job.lastUpdated.label" default="Last Updated"/></dt>
                <dd><g:formatDate date="${jobInstance?.lastUpdated}"/></dd>
            </g:if>

        </dl>

        <sec:ifAnyGranted roles="${model.Roles.ROLE_ADMIN}">
        <g:form>
            <g:hiddenField name="id" value="${jobInstance?.id}"/>
            <div class="form-actions">
                <g:link class="btn" action="edit" id="${jobInstance?.id}">
                    <i class="icon-pencil"></i>
                    <g:message code="default.button.edit.label" default="Edit"/>
                </g:link>
                <button class="btn btn-danger" type="submit" name="_action_delete">
                    <i class="icon-trash icon-white"></i>
                    <g:message code="default.button.delete.label" default="Delete"/>
                </button>
            </div>
        </g:form>
        </sec:ifAnyGranted>
    </div>

</div>
</body>
</html>
