package inspector.gadget
import com.google.gson.GsonBuilder

abstract class BaseController {
    private def gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create()

    public void renderJSON(Object o) {
        render gson.toJson(o)
    }
}
