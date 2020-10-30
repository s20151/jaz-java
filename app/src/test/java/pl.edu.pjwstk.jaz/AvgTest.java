package pl.edu.pjwstk.jaz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@IntegrationTest
public class AvgTest {

    @Test
    public void test1() {
        var response = given()
                .param("numbers", "4,3,1,7,5")
                .when()
                .get("/api/average")
                .then()
                .statusCode (200)
                .body (equalTo("Average equals: 4"));
    }
    @Test
    public void test2() {
        var response = given()
                .param("numbers", "2,1")
                .when()
                .get("/api/average")
                .then()
                .statusCode (200)
                .body (equalTo("Average equals: 1.5"));
    }
    @Test
    public void test3() {
        var response = given()
                .param("numbers", "2,1,1")
                .when()
                .get("/api/average")
                .then()
                .statusCode (200)
                .body (equalTo("Average equals: 1.33"));
    }
    @Test
    public void test4() {
        var response = given()
               // .param("numbers", "")
                .when()
                .get("/api/average")
                .then()
                .statusCode (200)
                .body (equalTo("Please put parameters."));
    }
}
