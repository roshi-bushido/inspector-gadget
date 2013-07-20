<%@ page import="java.text.SimpleDateFormat" %>
<div class="span12">
    <div id="chartContainer" style="min-width: 200px; height: 400px;"></div>
    <script>
        $(function () {
            $('#chartContainer').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Evolucion mensual'
                },
                xAxis: {
                    categories: ${days}
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Total events'
                    },
                    stackLabels: {
                        enabled: true,
                        style: {
                            fontWeight: 'bold',
                            color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                        }
                    }
                },
                legend: {
                    align: 'right',
                    x: -100,
                    verticalAlign: 'top',
                    y: 20,
                    floating: true,
                    backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                    borderColor: '#CCC',
                    borderWidth: 1,
                    shadow: false
                },
                colors: ['#3366cc', '#109618', '#ff9900', '#dc3912', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.x + '</b><br/>' +
                                this.series.name + ': ' + this.y + '<br/>' +
                                'Total: ' + this.point.stackTotal;
                    }
                },
                plotOptions: {
                    column: {
                        stacking: 'normal',
                        dataLabels: {
                            enabled: true,
                            color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
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