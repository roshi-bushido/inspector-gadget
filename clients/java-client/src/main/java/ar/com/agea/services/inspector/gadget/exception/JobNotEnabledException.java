package ar.com.agea.services.inspector.gadget.exception;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
public class JobNotEnabledException extends RuntimeException {


    public JobNotEnabledException() {
    }

    public JobNotEnabledException(String s) {
        super(s);
    }

    public JobNotEnabledException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public JobNotEnabledException(Throwable throwable) {
        super(throwable);
    }
}
