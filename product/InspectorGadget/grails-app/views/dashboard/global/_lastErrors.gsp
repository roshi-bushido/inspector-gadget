<table class="table table-striped table-hover">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="${message(code: 'jobFailure.id.label', default: 'Id')}"/>
        <g:sortableColumn property="jobName" title="${message(code: 'jobFailure.name.label', default: 'Job Name')}"/>
        <g:sortableColumn property="applicationName" title="${message(code: 'jobFailure.applicationName.label', default: 'Application Name')}"/>
        <g:sortableColumn property="endDate" title="${message(code: 'jobFailure.endDate.label', default: 'End Date')}"/>
        <g:sortableColumn property="reason" title="${message(code: 'jobFailure.reason.label', default: 'Reason')}"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${dashboardInstance.lastFailures}" var="jobFailure">
        <tr>
            <td><g:link controller="jobInstance" action="show" id="${jobFailure.id}">${fieldValue(bean: jobFailure, field: "id")}</g:link></td>
            <td><g:link controller="dashboard" action="job" params="${[jobId: jobFailure.jobId]}">${fieldValue(bean: jobFailure, field: "jobName")}</g:link></td>
            <td><g:link controller="dashboard" action="application" params="${[applicationId: jobFailure.applicationId]}">${fieldValue(bean: jobFailure, field: "applicationName")}</g:link></td>
            <td><g:formatDate date="${jobFailure.endDate}" format="dd-MM-yyyy mm:ss:" /></td>
            <td><util:formatString max="40" string="${fieldValue(bean: jobFailure, field: "reason")}" /></td>
        </tr>
    </g:each>
    </tbody>
</table>