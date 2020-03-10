package ee.ut.demo.poll.constant;

public final class ErrorCodes {
    public static final int INVALID_DATA = 11;
    public static final int NO_DATA_FOUND = 56;
    public static final int UNKNOWN_EXCEPTION = 100;
    public static final int APPLICATION_EXCEPTION = 101;

    private ErrorCodes() {
        throw new java.lang.UnsupportedOperationException(
                "This is a constants class and cannot be instantiated");
    }
}
