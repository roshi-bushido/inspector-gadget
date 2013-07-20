<%@ page import="java.text.SimpleDateFormat" %>
<div class="span12">
    <div id="evolutionChartContainer" style="min-width: 200px; height: 400px;"></div>
    <script>
    $(function () {
            $('#evolutionChartContainer').highcharts({
                chart: {
                    type: 'area'
                },
                title: {
                    text: 'Evolucion mensual'
                },
                xAxis: {
                    categories: ${days},
                    tickmarkPlacement: 'on',
                    title: {
                        enabled: false
                    }
                },
                yAxis: {
                    labels: {
                        formatter: function() {
                            return this.value;
                        }
                    }
                },
                colors: ['#3366cc', '#109618', '#ff9900', '#dc3912', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
                tooltip: {
                    shared: true
                },
                plotOptions: {
                    area: {
                        stacking: 'normal',
                        lineColor: '#666666',
                        lineWidth: 1,
                        marker: {
                            lineWidth: 1,
                            lineColor: '#666666'
                        }
                    }
                },
                series: [
                    {
                        name: 'Pending',
                        data: ${pending}
                    },
                    {
                        name: 'Success',
                        data: ${success}
                    },
                    {
                        name: 'Warning',
                        data: ${warnings}
                    },
                    {
                        name: 'Errors',
                        data: ${errors}
                    }
                ]
            });
        });
    </script>
</div>