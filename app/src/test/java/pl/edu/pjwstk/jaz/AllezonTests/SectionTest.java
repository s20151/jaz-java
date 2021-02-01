package pl.edu.pjwstk.jaz.AllezonTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.IntegrationTest;
import pl.edu.pjwstk.jaz.login.LoginRequest;
import pl.edu.pjwstk.jaz.register.RegisterRequest;
import pl.edu.pjwstk.jaz.requests.SectionRequest;
import static io.restassured.RestAssured.given;

@RunWith(Suite.class)
@IntegrationTest
public class SectionTest {

    private static Response adminResponse;

    @BeforeClass
    public static void loginAsAdmin(){
        given()
                .when()
                .body(new RegisterRequest("admin","admin"))
                .contentType(ContentType.JSON)
                .post("/api/register")
                .thenReturn();
        adminResponse = given()
                .when()
                .body (new LoginRequest ("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/login")
                .thenReturn();
    }

    @Test
    public void should_response_201_after_creating_section(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("newSectionFromTest2"))
                .contentType (ContentType.JSON)
                .post("/api/section")
                .then()
                .statusCode (HttpStatus.CREATED.value ());
    }

    @Test
    public void should_response_200_after_editing_existing_section(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("sectionAfterEdit"))
                .contentType (ContentType.JSON)
                .put("/api/section/1")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }

    @Test
    public void should_response_404_after_trying_to_edit_not_existing_section(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("sectionAfterEdit"))
                .contentType (ContentType.JSON)
                .put("/api/section/1234")
                .then()
                .statusCode (HttpStatus.NOT_FOUND.value ());
    }

    @Test
    public void should_response_409_after_trying_to_add_already_existing_section(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("newSectionFromTest2"))
                .contentType (ContentType.JSON)
                .post("/api/section")
                .then()
                .statusCode (HttpStatus.CONFLICT.value ());
    }
}


