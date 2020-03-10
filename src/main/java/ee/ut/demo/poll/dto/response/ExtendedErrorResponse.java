package ee.ut.demo.poll.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class ExtendedErrorResponse<T> extends CommonErrorResponse {
    private List<T> extraData = new ArrayList<>();

    public final void addData(final T item) {
        extraData.add(item);
    }
}
