package ee.ut.demo.poll.service.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DateUtilServiceImpl implements DateUtilService {
    @Override
    public final java.util.Date getDate() {
        return new java.util.Date();
    }

    @Override
    public final Timestamp getCurrentTimeAsTimestamp() {
        LocalDateTime today = LocalDateTime.now();
        return Timestamp.valueOf(today);
    }

    @Override
    public final Instant getInstantNow() {
        return Instant.now();
    }
}
