
<%@ page import="inspector.gadget.job.Job" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'job.label', default: 'Job')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">
            <!-- Panel de acciones -->
			<div class="span3">
                <g:render template="/common/actionMenu" model="[currentFilter: 'job']"/>
			</div>

			<div class="span9">
				<div class="page-header">
					<h1><g:message code="default.list.label" args="[entityName]" default="Job List" /></h1>
				</div>

				<table class="table table-striped">
					<thead>
						<tr>
							<g:sortableColumn property="name" title="${message(code: 'job.name.label', default: 'Name')}" />
							<g:sortableColumn property="description" title="${message(code: 'job.description.label', default: 'Description')}" />
							<g:sortableColumn property="errorDurationThreshold" title="${message(code: 'job.errorDurationThreshold.label', default: 'Error Threshold')}" />
							<g:sortableColumn property="warningDurationThreshold" title="${message(code: 'job.warningDurationThreshold.label', default: 'Warning Threshold')}" />
							<g:sortableColumn property="executionInterval" title="${message(code: 'job.executionInterval.label', default: 'Execution Interval')}" />
							<g:sortableColumn property="enabled" title="${message(code: 'job.enabled.label', default: 'Enabled')}" />
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${jobInstanceList}" var="jobInstance">
						<tr>
							<td>${fieldValue(bean: jobInstance, field: "name")}</td>
							<td><util:formatString max="40" string="${fieldValue(bean: jobInstance, field: "description")}" /> </td>
							<td>${fieldValue(bean: jobInstance, field: "errorDurationThreshold")}</td>
							<td>${fieldValue(bean: jobInstance, field: "warningDurationThreshold")}</td>
							<td>${fieldValue(bean: jobInstance, field: "executionInterval")}</td>
							<td><g:formatBoolean boolean="${jobInstance.enabled}" /></td>
							<td class="link">
								<g:link action="show" id="${jobInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${jobInstanceTotal}" />
				</div>
			</div>
		</div>
	</body>
</html>
