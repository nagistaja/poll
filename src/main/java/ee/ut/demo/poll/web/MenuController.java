package ee.ut.demo.poll.web;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    @GetMapping
    public List<String> getMenuItems() {
        return Arrays.asList("messages", "polls", "responses", "anytics", "creator");
    }

}
