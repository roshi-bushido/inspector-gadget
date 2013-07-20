package ar.com.agea.services.inspector.gadget.util;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
public final class StringUtil {


    /**
     * Removes all empty spaces from the a string.
     *
     * @param string the source string
     * @return if the string is <code>null</code> or empty it returns the sample reference, in other cases returns a copy
     * of the string with no empty spaces.
     */
    public static String removeEmptySpaces(String string) {
        if ( string == null || string.isEmpty() ) {
            return string;
        } else {
            return string.replace(" ", "");
        }
    }
}
