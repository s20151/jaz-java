package pl.edu.pjwstk.jaz;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.register.RegisterRequest;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@IntegrationTest
public class RegisterTest {
    @Test
    public void status_code_409_when_user_already_exists(){
        given()
                .when ()
                .body (new RegisterRequest("user","user"))
                .contentType (ContentType.JSON)
                .post ("/api/register")
                .thenReturn ();
        given()
                .when ()
                .body (new RegisterRequest ("user","user"))
                .contentType (ContentType.JSON)
                .post ("/api/register")
                .then()
        .statusCode (HttpStatus.CONFLICT.value ());
    }
    @Test
    public void status_code_200_when_new_user_created(){
        given()
                .when ()
                .body (new RegisterRequest ("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/register")
       .then ()
                .statusCode (HttpStatus.OK.value ());
    }

}
