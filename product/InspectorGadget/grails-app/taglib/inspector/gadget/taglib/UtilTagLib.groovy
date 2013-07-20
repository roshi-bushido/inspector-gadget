package inspector.gadget.taglib

class UtilTagLib {
    static namespace = "util"

    def formatString = { attrs, body ->
        def max = Integer.valueOf(attrs?.max)
        def value = attrs?.string

        if (value?.length() > max) {
            out << value?.substring(0, max).concat("...")
        } else {
            out << value
        }
    }
}
