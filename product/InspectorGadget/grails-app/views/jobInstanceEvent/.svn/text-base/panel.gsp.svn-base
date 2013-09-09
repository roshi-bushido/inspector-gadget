
<%@ page import="inspector.gadget.job.JobInstanceEvent" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'jobInstanceEvent.label', default: 'JobInstanceEvent')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">

            <!-- Panel de acciones -->
			<div class="span3">
                <g:render template="/common/actionMenu" model="[currentFilter: 'jobInstanceEvent']"/>
			</div>


            <!-- Listado -->
			<div class="span9">
				<div class="page-header">
					<h1><g:message code="default.list.label" args="[entityName]" /></h1>
				</div>

                <!-- Filtros -->
                <div id="filterList">
                    <strong><h4 class="muted"><g:message code="jobInstanceEvent.list.filter.label" default="Filter"/></h4></strong>
                    <g:select id="statusCombo" name="jobStatus" from="${jobStatusList}" optionKey="id" optionValue="value" noSelection="['':'All']" value="${params?.jobStatusId}"/>
                    <g:select id="jobCombo" name="job" from="${jobList}" optionKey="id" optionValue="name" noSelection="['':'All']" value="${params?.jobId}"/>
                    <g:if test="${params?.jobId}" >
                      <g:select id="jobInstanceCombo" name="jobInstance" from="${jobInstanceList}" optionKey="id" optionValue="id" noSelection="['':'All']" value="${params?.jobInstanceId}"/>
                    </g:if>
                </div>

				<table class="table table-striped">
					<thead>
						<tr>
							<th class="header"><g:message code="jobInstanceEvent.job.label" default="Job" /></th>
                            <g:sortableColumn property="status" title="${message(code: 'jobInstanceEvent.status.label', default: 'Status')}" />
                            <g:sortableColumn property="description" title="${message(code: 'jobInstanceEvent.description.label', default: 'Description')}" />
                            <g:sortableColumn property="dateCreate" title="${message(code: 'jobInstanceEvent.dateCreated.label', default: 'Date Created')}" />
							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${jobInstanceEventInstanceList}" var="jobInstanceEventInstance">
						<tr>
							<td>${fieldValue(bean: jobInstanceEventInstance, field: "instance")}</td>
                            <td><jobInstance:status instance="${jobInstanceEventInstance}" /> </td>
                            <td><jobInstance:description instance="${jobInstanceEventInstance}" max="40"/> </td>
                            <td><g:formatDate date="${jobInstanceEventInstance.dateCreated}" format="dd-MM-yyyy hh:mm:ss" /></td>
                            <td class="link">
                                <g:link action="show" id="${jobInstanceEventInstance.id}" class="btn btn-small">Show &raquo;</g:link>
                            </td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${jobInstanceEventInstanceTotal}" params="${[applicationId: params?.applicationId, jobId: params?.jobId, jobStatusId: params?.jobStatusId]}" />
				</div>
			</div>
		</div>
    <r:script>
         $(document).ready(function() {
            // agrego el evento para el onChange para todos combos de busqueda
            $("[id$=Combo]").change(function() {
                var jobId = $("#jobCombo option:selected").val();
                var jobInstanceId = $("#jobInstanceCombo option:selected").val();
                var jobStatusId = $("#statusCombo option:selected").val();
                var filter = "";

                if ( jobId != undefined && jobId.trim() != "") {
                    filter += "&jobId=" + jobId;
                }

                if ( jobInstanceId != undefined && jobInstanceId.trim() != "") {
                    filter += "&jobInstanceId=" + jobInstanceId;
                }
                if ( jobStatusId != undefined && jobStatusId.trim() != "") {
                    filter += "&jobStatusId=" + jobStatusId;
                }
                window.location.href = "${createLink(controller:"jobInstanceEvent" ,action: "panel")}?" + filter;
            });
         });
     </r:script>
	</body>
</html>
