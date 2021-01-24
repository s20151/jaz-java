package pl.edu.pjwstk.jaz;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import pl.edu.pjwstk.jaz.register.RegisterRequest;

import static io.restassured.RestAssured.given;

public class RegisterWithDBTest {
    @Test
    public void should_response_200_when_new_user_created(){
        given()
                .when ()
                .body (new RegisterRequest("newtest123","newtest"))
                .contentType (ContentType.JSON)
                .post ("/api/registerdb")
                .then ()
                .statusCode (HttpStatus.OK.value ());
    }
    @Test
    public void should_response_409_when_user_already_exists(){
        given()
                .when ()
                .body (new RegisterRequest ("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/registerdb")
                .then ()
                .statusCode (HttpStatus.CONFLICT.value ());
    }
}
