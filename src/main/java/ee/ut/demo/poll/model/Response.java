package ee.ut.demo.poll.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@NoArgsConstructor
public final class Response {

    @NotNull
    @Size(min = 1, max = 40)
    public String owner;

    @NotNull
    @Size(min = 1, max = 10)
    @Autowired
    public List<Boolean> choices;

}
