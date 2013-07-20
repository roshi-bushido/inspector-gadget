package ar.com.agea.services.inspector.gadget.exception;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Matias Suarez
 * @author AGEA 2013
 */
public class ServerErrorException extends RuntimeException {
	private static final long serialVersionUID = -4368643843500831596L;

	public ServerErrorException() {
    }

    public ServerErrorException(String s) {
        super(s);
    }

    public ServerErrorException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ServerErrorException(Throwable throwable) {
        super(throwable);
    }

}
