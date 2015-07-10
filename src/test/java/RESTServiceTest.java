import TT4J.utils.RESTClient;
import org.junit.Test;

/**
 * Created by stokowiec on 2015-07-10.
 */
public class RESTServiceTest {



    @Test
    public void post_and_get_is_working() throws Exception{

        RESTClient client = new RESTClient("http://localhost", 8080);

         String text = "bla, bla";

        String id = RESTClient.handleResponse(client.postLink(text)).get(0);
        String response = RESTClient.handleResponse(client.getLink(id)).get(0);

        assert(response.equals(text));
    }
}
