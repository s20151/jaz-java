package pl.edu.pjwstk.jaz;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import pl.edu.pjwstk.jaz.login.LoginRequest;

import static io.restassured.RestAssured.given;

public class LoginWithDBTest {

    @Test
    public void should_response_200_after_succesful_login() {
        var response = given()
                .when()
                .body (new LoginRequest("admin","admin"))
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
    @Test
    public void should_response_200_after_accesing_admin_site_with_admin_authority() {
        var response = given()
                .when()
                .body (new LoginRequest ("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/logindb")
                .thenReturn();
        given()
                 .cookies(response.getCookies())
                 .get("/api/testauthority")
                 .then()
                 .statusCode (HttpStatus.OK.value ());
    }
    @Test
    public void should_response_403_after_accesing_admin_site_with_user_authority() {
        var response = given()
                .when()
                .body (new LoginRequest ("user","user"))
                .contentType (ContentType.JSON)
                .post ("/api/logindb")
                .thenReturn();
        given()
                .cookies(response.getCookies())
                .get("/api/testauthority")
                .then()
                .statusCode (HttpStatus.FORBIDDEN.value ());
    }
    @Test
    public void should_response_200_after_accesing_user_site_with_user_authority() {
        var response = given()
                .when()
                .body (new LoginRequest ("user","user"))
                .contentType (ContentType.JSON)
                .post ("/api/logindb")
                .thenReturn();
        given()
                .cookies(response.getCookies())
                .get("/api/userauthority")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }
    @Test
    public void should_response_200_after_accesing_user_site_with_admin_authority() {
        var response = given()
                .when()
                .body (new LoginRequest ("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/logindb")
                .thenReturn();
        given()
                .cookies(response.getCookies())
                .get("/api/userauthority")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }
}
