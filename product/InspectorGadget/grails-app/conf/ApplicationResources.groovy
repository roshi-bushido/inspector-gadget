modules = {
    application {
        resource url: 'js/application.js'
    }
    scaffolding {
        dependsOn 'bootstrap'
        resource url: 'css/scaffolding.css'
        resource url: 'css/scaffolding-ext.css'
    }
    charting {
        dependsOn 'scaffolding'
        resource url: 'js/highcharts.js'
    }

}