package ee.ut.demo.poll.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NonUniqueException extends InvalidParameterException {

    private static final long serialVersionUID = 967447438442065372L;
    private String parameter;
    private String message;

    public NonUniqueException(final String parameter, final String message) {
        super(parameter, message);
        this.parameter = parameter;
        this.message = message;
    }

}
