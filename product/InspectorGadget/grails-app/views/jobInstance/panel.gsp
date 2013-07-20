
<%@ page import="inspector.gadget.job.JobInstance" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'jobInstance.label', default: 'JobInstance')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">

            <!-- Panel de acciones -->
			<div class="span3">
                <g:render template="/common/actionMenu" model="[currentFilter: 'jobInstance']"/>
			</div>


            <!-- Listado -->
			<div class="span9">
				<div class="page-header">
					<h1><g:message code="default.list.label" args="[entityName]" /></h1>
				</div>

                <!-- Filtros -->
                <div id="filterList">
                    <strong><h4 class="muted"><g:message code="jobInstance.list.filter.label" default="Filter"/></h4></strong>
                    <g:select id="statusCombo" name="jobStatus" from="${jobStatusList}" optionKey="id" optionValue="value" noSelection="['':'All']" value="${params?.jobStatusId}"/>
                    <g:select id="applicationCombo" name="application" from="${applicationList}" optionKey="id" optionValue="name" noSelection="['':'All']" value="${params?.applicationId}"/>
                    <g:if test="${params?.applicationId}" >
                      <g:select id="jobCombo" name="job" from="${jobList}" optionKey="id" optionValue="name" noSelection="['':'All']" value="${params?.jobId}"/>
                    </g:if>
                </div>

				<table class="table table-striped">
					<thead>
						<tr>
							<th class="header"><g:message code="jobInstance.job.label" default="Job" /></th>
							<g:sortableColumn property="startedAt" title="${message(code: 'jobInstance.startedAt.label', default: 'Started At')}" />
							<g:sortableColumn property="endedAt" title="${message(code: 'jobInstance.endedAt.label', default: 'Ended At')}" />
							<g:sortableColumn property="status" title="${message(code: 'jobInstance.status.label', default: 'Status')}" />
							<g:sortableColumn property="description" title="${message(code: 'jobInstance.description.label', default: 'Description')}" />
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${jobInstanceInstanceList}" var="jobInstanceInstance">
						<tr>
							<td>${fieldValue(bean: jobInstanceInstance, field: "job")}</td>
							<td><g:formatDate date="${jobInstanceInstance.startedAt}" format="dd-MM-yyyy hh:mm:ss" /></td>
							<td><g:formatDate date="${jobInstanceInstance.endedAt}" format="dd-MM-yyyy hh:mm:ss" /></td>
							<td><jobInstance:status instance="${jobInstanceInstance}" /> </td>
							<td><jobInstance:description instance="${jobInstanceInstance}" max="40"/> </td>
							<td class="link">
								<g:link action="show" id="${jobInstanceInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${jobInstanceInstanceTotal}" params="${[applicationId: params?.applicationId, jobId: params?.jobId, jobStatusId: params?.jobStatusId]}" />
				</div>
			</div>
		</div>
    <r:script>
         $(document).ready(function() {
            // agrego el evento para el onChange para todos combos de busqueda
            $("[id$=Combo]").change(function() {
                var applicationId = $("#applicationCombo option:selected").val()
                var jobId = $("#jobCombo option:selected").val();
                var jobStatusId = $("#statusCombo option:selected").val();
                var filter = "";

                if ( applicationId != undefined && applicationId.trim() != "") {
                    filter += "applicationId=" + applicationId;
                }
                if ( jobId != undefined && jobId.trim() != "") {
                    filter += "&jobId=" + jobId;
                }

                if ( jobStatusId != undefined && jobStatusId.trim() != "") {
                    filter += "&jobStatusId=" + jobStatusId;
                }
                window.location.href = "${createLink(controller:"jobInstance" ,action: "panel")}?" + filter;
            });
         });
     </r:script>
	</body>
</html>
