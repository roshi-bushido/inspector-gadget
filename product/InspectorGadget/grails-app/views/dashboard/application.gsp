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

        <div id="filterList">
            <strong><h4 class="muted"><g:message code="application.list.filter.label" default="Filter"/></h4></strong>
            <g:select id="applicationCombo" name="application" from="${applicationList}" optionKey="id"
                      optionValue="name" value="${params?.applicationId}"/>
        </div>

        <div class="span12">
            <g:each in="${instance.jobSanityCollection}" var="jobSanity">
                <div class="span5">
                    <div id="${jobSanity.jobName}" class="jobChart" style="min-width: 200px; height: 300px;"></div>
                    <script>
                        $(function () {
                            $('#${jobSanity.jobName}').highcharts({
                                chart: {
                                    plotBackgroundColor: null,
                                    plotBorderWidth: null,
                                    plotShadow: false
                                },
                                title: {
                                    text: '<p class="text-info text-center">Job Name: <a class="text-success" href="${createLink(action: "job", params: [jobId: jobSanity.jobId])}">${jobSanity.jobName}</a></p>'
                                },
                                tooltip: {
                                    pointFormat: '{series.name}: <b>{point.percentage}%</b>',
                                    percentageDecimals: 1
                                },
                                colors: ['#3366cc', '#109618', '#ff9900', '#dc3912', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
                                plotOptions: {
                                    pie: {
                                        allowPointSelect: true,
                                        cursor: 'pointer',
                                        dataLabels: {
                                            enabled: false
                                        },
                                        showInLegend: true
                                    }
                                },
                                series: [
                                    {
                                        type: 'pie',
                                        point: {
                                            events: {
                                                click: function (e) {
                                                    var target = e.currentTarget;
                                                    var url = "${createLink(controller: "dashboard", action: "job", absolute: true)}?jobId=" + target.id;
                                                    window.location.href = url;
                                                }
                                            }
                                        },
                                        name: 'Events',
                                        data: [
                                          { name: 'Pending', y: ${jobSanity.pending}, id: ${jobSanity.jobId}},
                                          { name: 'Success', y: ${jobSanity.success}, id: ${jobSanity.jobId}},
                                          { name: 'Warning', y: ${jobSanity.warning}, id: ${jobSanity.jobId}},
                                          { name: 'Error', y: ${jobSanity.error}, id: ${jobSanity.jobId}},
                                        ]
                                    }
                                ]
                            });
                        });
                    </script>
                </div>
            </g:each>
        </div>
    </div>
</div>
<r:script>
     $(document).ready(function() {
        // agrego el evento para el onChange para todos combos de busqueda
        $("[id$=Combo]").change(function() {
            var applicationId = $("#applicationCombo option:selected").val()
            var filter = "";

            if ( applicationId != undefined && applicationId.trim() != "") {
                filter += "applicationId=" + applicationId;
            }

            window.location.href = "${createLink(controller: "dashboard", action: "application")}?" + filter;
        });
     });
</r:script>

</body>
</html>
