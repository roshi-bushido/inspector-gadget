class UrlMappings {
	static mappings = {
        "/api/1.0/status"                                           (controller: "information", action: "status")
        "/api/1.0/version"                                          (controller: "information", action: "version")

        "/api/1.0/apps/list"                                        (controller: "applicationApi", action: "list")
        "/api/1.0/apps/show/$id"                                    (controller: "applicationApi", action: "show")
        "/api/1.0/jobs/list"                                        (controller: "jobApi", action: "listJobs")
        "/api/1.0/jobs/$jobName"                                    (controller: "jobApi", action: "findJobByName")
        "/api/1.0/jobs/$jobId/list"                                 (controller: "jobApi", action: "listJobInstances")
        "/api/1.0/jobs/$jobId/start"                                (controller: "jobApi", action: "startJobInstance")
        "/api/1.0/jobs/$jobId/$jobInstanceId/finish"                (controller: "jobApi", action: "finishJobInstance")
        "/api/1.0/jobs/$jobId/$jobInstanceId/crash"                 (controller: "jobApi", action: "crashJobInstance")

        "/api/1.0/jobs/$jobInstanceId/events"                       (controller: "jobApi", action: "listEventsFromInstance")
        "/api/1.0/jobs/$jobInstanceId/events/success"               (controller: "jobApi", action: "addSuccessEvent")
        "/api/1.0/jobs/$jobInstanceId/events/error"                 (controller: "jobApi", action: "addErrorEvent")

        "/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
        "/cms"(view:"/indexCms")
        "400"(view:"/index")
		"500"(view:'/error')
	}
}
