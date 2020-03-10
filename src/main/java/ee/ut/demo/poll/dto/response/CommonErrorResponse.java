package ee.ut.demo.poll.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class CommonErrorResponse {
    private String timestamp;
    private int code;
    private String message;

    public final void setTimestampAsStr(final Instant time) {
        if (time != null) {
            setTimestamp(time.toString());
        }
    }
}
