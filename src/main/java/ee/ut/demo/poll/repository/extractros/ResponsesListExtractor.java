package ee.ut.demo.poll.repository.extractros;

import ee.ut.demo.poll.model.Response;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Log4j2
public class ResponsesListExtractor implements ResultSetExtractor<List<Response>> {

    @Override
    public List<Response> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Response> responses = new ArrayList<>();
        while (rs.next()) {
            Response response = new Response();
            if (rs.getString("responses") != null) {
                response.setOwner(rs.getString("owner"));
                List<Boolean> choices = new ArrayList<>();
                StringTokenizer st = new StringTokenizer(rs.getString("responses"), " ,[]");
                while (st.hasMoreTokens()) {
                    var token = st.nextToken();
                    choices.add(Boolean.valueOf(token));
                }
                response.setChoices(choices);
            }
            responses.add(response);
        }
        return responses;
    }
}
