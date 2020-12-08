package pl.edu.pjwstk.jaz;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class LoginWithDBTest {

    @Test
    public void should_response_200_after_succesful_login() {
        var response = given()
                .when()
                .body (new LoginRequest ("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/logindb")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }
    @Test
    public void should_response_401_after_logging_in_with_incorrect_password() {
        var response = given()
                .when()
                .body (new LoginRequest ("admin","Incorrect_Password"))
                .contentType (ContentType.JSON)
                .post ("/api/logindb")
                .then()
                .statusCode (HttpStatus.UNAUTHORIZED.value ());
    }
}