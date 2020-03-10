package ee.ut.demo.poll.exception;

@SuppressWarnings({ "checkstyle:FinalParameters" })
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4076119488371982779L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException(final String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
