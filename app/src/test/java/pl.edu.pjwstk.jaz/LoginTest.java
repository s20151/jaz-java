package pl.edu.pjwstk.jaz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.given;


@RunWith(SpringRunner.class)
@IntegrationTest
public class LoginTest {

    @Test
    public void should_response_200_admin_enters_admin_authority() {
        var response = given()
                .when()
                    .body (new LoginRequest ("admin","admin"))
                    .post ("/api/login")
                    .thenReturn();
        given ()
                .cookies(response.getCookies())
                .get ("api/testauthority")
                .then ()
                .statusCode (200);
    }
    @Test
    public void should_response_200_admin_enters_user_authority() {
        var response = given()
                .when()
                .body (new LoginRequest ("admin","admin"))
                .post ("/api/login")
                .thenReturn();
        given ()
                .cookies(response.getCookies())
                .get ("/api/userauthority")
                .then ()
                .statusCode (200);
    }

}
