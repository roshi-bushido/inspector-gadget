<%@ page import="java.text.SimpleDateFormat" %>
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
            <h1><g:message code="dashboard.job.label" default="Job Dashboard"/></h1>
        </div>

        <div id="filterList">
            <strong><h4 class="muted"><g:message code="job.list.filter.label" default="Filter"/></h4></strong>
            <g:select id="jobCombo" name="job" from="${jobList}" optionKey="id" optionValue="name" value="${params?.jobId}"/>
            <g:select id="monthCombo" name="month" from="${1..12}" optionKey="value" optionValue="value" value="${currentMonth}"/>
        </div>

        <%
            def simpleDateFormatter = new SimpleDateFormat("dd")
            def days = []
            def errors = []
            def warnings = []
            def success = []
            def pending = []
        %>
        <g:each in="${instance.dailyJobSanity.entrySet()}" var="dailyJobSanityEntry">
            <%
                days.push(simpleDateFormatter.format(dailyJobSanityEntry.value?.day))
                errors.push(dailyJobSanityEntry.value?.error)
                warnings.push(dailyJobSanityEntry.value?.warning)
                success.push(dailyJobSanityEntry.value?.success)
                pending.push(dailyJobSanityEntry.value?.pending)
            %>
        </g:each>
        <g:render template="job/resultEvolutionChart" model="[days: days, pending: pending, warnings: warnings, errors: errors, success: success]" />
        <g:render template="job/resultByDayStackedChart" model="[days: days, pending: pending, warnings: warnings, errors: errors, success: success]" />

    </div>
</div>
<r:script>
     $(document).ready(function() {
        // agrego el evento para el onChange para todos combos de busqueda
        $("[id$=Combo]").change(function() {
            var jobId = $("#jobCombo option:selected").val()
            var month = $("#monthCombo option:selected").val()
            var filter = "";

            if ( jobId != undefined && jobId.trim() != "") {
                filter += "jobId=" + jobId;
            }

            if ( month != undefined && month.trim() != "") {
                filter += "&currentMonth=" + month;
            }
            window.location.href = "${createLink(controller: "dashboard", action: "job")}?" + filter;
        });
     });
</r:script>

</body>
</html>
