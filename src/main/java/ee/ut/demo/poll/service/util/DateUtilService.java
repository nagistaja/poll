package ee.ut.demo.poll.service.util;

import java.sql.Timestamp;
import java.time.Instant;

public interface DateUtilService {

    java.util.Date getDate();

    Timestamp getCurrentTimeAsTimestamp();

    Instant getInstantNow();

}
