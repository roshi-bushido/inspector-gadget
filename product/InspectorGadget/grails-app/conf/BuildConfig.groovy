grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        test 'org.hamcrest:hamcrest-all:1.3'
        test 'org.codehaus.groovy.modules.http-builder:http-builder:0.6'

        // Spock + Geb + Functional Testing
        test 'org.spockframework:spock-grails-support:0.7-groovy-2.0'
        test "org.gebish:geb-spock:0.9.0"
        test ('org.seleniumhq.selenium:selenium-htmlunit-driver:2.0rc3') {
            exclude "xml-apis"
        }

        compile 'mysql:mysql-connector-java:5.1.20'
        compile 'com.google.code.gson:gson:2.2.2'
        compile 'joda-time:joda-time:2.0'
    }

    plugins {
        test ":code-coverage:1.2.6"
        test ":geb:0.7.2"
        test(":spock:0.7") {
            exclude "spock-grails-support"
        }

        compile ':cache:1.0.1'
        compile ":twitter-bootstrap:2.3.0"
        compile ":spring-security-core:1.2.7.3"
        compile ":fields:1.3"
        compile ":quartz:1.0-RC9"
        compile ":constraints:0.8.0"
        compile ":mail:1.0.1"

        build ":tomcat:$grailsVersion"

        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.8.3"
        runtime ":resources:1.1.6"
        runtime ":database-migration:1.2.1"
        runtime ":webxml:1.4.1"
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"
    }
}
coverage {
    enabledByDefault = true
	nopost = true
	xml = true
    exclusions = ["**/LoginController*", "**/LogoutController*", "**/cms/**"]
}