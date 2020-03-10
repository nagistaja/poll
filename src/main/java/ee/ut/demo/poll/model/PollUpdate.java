package ee.ut.demo.poll.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@NoArgsConstructor
public final class PollUpdate {

    @NotNull
    @Size(max = 200)
    public String title;

    @Size(max = 2000)
    public String description;

    @NotNull
    @Size(min = 1, max = 10)
    public List<String> options;

    public boolean active;

    public String owner;

}
