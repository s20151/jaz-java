package pl.edu.pjwstk.jaz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.given;
@RunWith(SpringRunner.class)
@IntegrationTest
public class ExampleTest {
    @Test
    public void should_respond_to_readiness_request() {
        var response = given()
                .when()
                .get("/api/is-ready")
                .thenReturn();
        var statusCode = response.getStatusCode();
    }
}

