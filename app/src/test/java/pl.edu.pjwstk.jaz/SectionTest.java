package pl.edu.pjwstk.jaz;

import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import pl.edu.pjwstk.jaz.login.LoginRequest;
import pl.edu.pjwstk.jaz.requests.SectionRequest;

import static io.restassured.RestAssured.given;

public class SectionTest {

    @Test
    public void should_response_200_after_creating_section(){
        var response = given()
                .when()
                .body (new LoginRequest ("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/login")
                .thenReturn();
        given()
                .when()
                .body(new SectionRequest("newSectionFromTest"))
                .post("/api/section")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }

    @Test
    public void should_response_200_after_editing_existing_section(){
        var response = given()
                .when()
                .body (new LoginRequest ("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/login")
                .thenReturn();
        given()
                .when()
                .body(new SectionRequest("sectionAfterEdit"))
                .put("/api/section/2")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }
    @Test
    public void should_response_404_after_editing_not_existing_section(){
        var response = given()
                .when()
                .body (new LoginRequest ("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/login")
                .thenReturn();
        given()
                .when()
                .body(new SectionRequest("sectionAfterEdit"))
                .put("/api/section/2222")
                .then()
                .statusCode (HttpStatus.NOT_FOUND.value ());
    }
}


