package inspector.gadget.taglib

class JobInstanceTaglibTagLib {
    static namespace = "jobInstance"

    def description = { attrs, body ->
        def max = Integer.valueOf(attrs?.max)
        def instance = attrs?.instance
        String description = instance?.description

        if (description?.length() > max) {
            out << description?.substring(0, max).concat("...")
        } else {
            out << description
        }
    }

   	def status = { attrs, body ->
        def instance = attrs?.instance
        def status = "btn-warning"

        switch (instance?.status?.value.toLowerCase()) {
            case "error":
                status = "btn-danger"
                break
            case "warning":
                status = "btn-warning"
                break
            case "pending":
                status = "btn-info"
                break
            case "ok":
                status = "btn-success"
                break
        }

        out << "<button class=\"btn disabled ${status}\" >"
        out << instance?.status.value
        out << "</button>"
   	}
}
