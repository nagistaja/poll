package ee.ut.demo.poll.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidParameterException extends RuntimeException {

    private static final long serialVersionUID = 4203607837227837536L;
    private String parameter;
    private String message;

    public InvalidParameterException(final String parameter, final String message) {
        super();
        this.parameter = parameter;
        this.message = message;
    }

}
