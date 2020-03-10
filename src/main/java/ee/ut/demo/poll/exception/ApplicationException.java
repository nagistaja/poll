package ee.ut.demo.poll.exception;

@SuppressWarnings({ "checkstyle:FinalParameters" })
public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 4076119488371982779L;

    public ApplicationException() {
        super();
    }

    public ApplicationException(final String message) {
        super(message);
    }

    public ApplicationException(final String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
}
