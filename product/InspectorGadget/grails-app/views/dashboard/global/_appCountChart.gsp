<%@ page import="dto.view.dashboard.ApplicationJobsDTO" %>
<div class="span5">
    <div id="jobCountByAppContainer" class="jobChart" style="min-width: 200px; height: 300px;"></div>
    <script>
        $(function () {
            $('#jobCountByAppContainer').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '<p class="text-info text-center">Job Count by Application</p>'
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
                                    var url = "${createLink(controller: "dashboard", action: "application", absolute: true)}?applicationId=" + target.id;
                                    window.location.href = url;
                                }
                            }
                        },
                        name: 'Events',
                        data: [
                            <% for (ApplicationJobsDTO appJobs : dashboardInstance.applicationJobs) { %>
                              { name: '${appJobs.application.name}', y: ${appJobs.jobCount} , id: ${appJobs.application.id} },
                            <% } %>
                        ]
                    }
                ]
            });
        });
    </script>
</div>