package pl.edu.pjwstk.jaz.AllezonTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import pl.edu.pjwstk.jaz.login.LoginRequest;
import pl.edu.pjwstk.jaz.requests.CategoryRequest;
import static io.restassured.RestAssured.given;

public class CategoryTest {

    private static Response adminResponse;

    @BeforeClass
    public static void loginAsAdmin(){
        adminResponse = given()
                .when()
                .body (new LoginRequest ("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/login")
                .thenReturn();
    }

    @Test
    public void should_response_200_after_creating_category_with_existing_section(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("newCategoryFromTest2",1L))
                .contentType (ContentType.JSON)
                .post("/api/category")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }

    @Test
    public void should_response_400_after_creating_category_with_not_existing_section(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("newCategory",33L))
                .contentType (ContentType.JSON)
                .post("/api/category")
                .then()
                .statusCode (HttpStatus.BAD_REQUEST.value ());
    }

    @Test
    public void should_response_200_after_editing_exisiting_category(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("newCategory123",2L))
                .contentType (ContentType.JSON)
                .put("/api/category/7")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }

    @Test
    public void should_response_404_after_trying_to_edit_not_exisiting_category(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("newCategory",2L))
                .contentType (ContentType.JSON)
                .put("/api/category/1237")
                .then()
                .statusCode (HttpStatus.NOT_FOUND.value ());
    }
}
