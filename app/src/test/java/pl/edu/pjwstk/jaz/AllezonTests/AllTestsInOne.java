package pl.edu.pjwstk.jaz.AllezonTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.IntegrationTest;
import pl.edu.pjwstk.jaz.login.LoginRequest;
import pl.edu.pjwstk.jaz.register.RegisterRequest;
import pl.edu.pjwstk.jaz.requests.*;

import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@IntegrationTest
public class AllTestsInOne {
    private static Response adminResponse;
    private static Response userResponse;

    @BeforeClass
    public static void loginAsAdminAndUser(){
        given()
                .when()
                .body(new RegisterRequest("admin","admin"))
                .contentType(ContentType.JSON)
                .post("/api/register")
                .thenReturn();
        adminResponse = given()
                .when()
                .body (new LoginRequest("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/login")
                .thenReturn();
        given()
                .when()
                .body(new RegisterRequest("user1","user1"))
                .contentType(ContentType.JSON)
                .post("/api/register")
                .thenReturn();
        userResponse = given()
                .when()
                .body (new LoginRequest ("user1","user1"))
                .contentType (ContentType.JSON)
                .post ("/api/login")
                .thenReturn();
    }

    @Test
    @Order(1)
    public void should_response_201_after_creating_section(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("newSectionFromTest"))
                .contentType (ContentType.JSON)
                .post("/api/section")
                .then()
                .statusCode (HttpStatus.CREATED.value ());
    }

    @Test
    @Order(2)
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
    @Order(3)
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
    @Order(4)
    public void should_response_409_after_trying_to_add_already_existing_section(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("newSectionFromTest"))
                .contentType (ContentType.JSON)
                .post("/api/section")
                .then()
                .statusCode (HttpStatus.CONFLICT.value ());
    }

    @Test
    @Order(5)
    public void should_response_201_after_creating_category_with_existing_section(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("newCategoryFromTest",1L))
                .contentType (ContentType.JSON)
                .post("/api/category")
                .then()
                .statusCode (HttpStatus.CREATED.value ());
    }

    @Test
    @Order(6)
    public void should_response_409_after_trying_to_add_already_existing_category(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("testCategory", 1L))
                .contentType (ContentType.JSON)
                .post("/api/category")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("testCategory", 1L))
                .contentType (ContentType.JSON)
                .post("/api/category")
                .then()
                .statusCode (HttpStatus.CONFLICT.value ());
    }

    @Test
    @Order(7)
    public void should_response_400_after_creating_category_with_not_existing_section(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("newCategoryWithNotExistingSection",33L))
                .contentType (ContentType.JSON)
                .post("/api/category")
                .then()
                .statusCode (HttpStatus.BAD_REQUEST.value ());
    }

    @Test
    @Order(8)
    public void should_response_200_after_editing_exisiting_category(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("categoryAfterEdit",1L))
                .contentType (ContentType.JSON)
                .put("/api/category/1")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }
    @Test
    @Order(9)
    public void should_response_404_after_trying_to_edit_not_exisiting_category(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("notExisitng",1L))
                .contentType (ContentType.JSON)
                .put("/api/category/1237")
                .then()
                .statusCode (HttpStatus.NOT_FOUND.value ());
    }

    @Test
    @Order(10)
    public void should_response_201_after_creating_auction(){
        List<ParameterRequest> parameterRequestList = new ArrayList<>();
        parameterRequestList.add(new ParameterRequest("TestParameter1","TestValue1"));
        parameterRequestList.add(new ParameterRequest("TestParameter2","TestValue2"));
        parameterRequestList.add(new ParameterRequest("TestParameter3","TestValue3"));
        List<PhotoRequest> photoList = new ArrayList<>();
        photoList.add(new PhotoRequest("link1"));
        photoList.add(new PhotoRequest("link2"));
        photoList.add(new PhotoRequest("link3"));
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new AuctionRequest("Title from test 1", "Description from test 1", 100, "testCategory", parameterRequestList, photoList ))
                .contentType (ContentType.JSON)
                .post("/api/auctions")
                .then()
                .statusCode (HttpStatus.CREATED.value ());
    }
    @Test
    @Order(11)
    public void should_response_400_after_creating_auction_with_not_existing_category(){
        List<ParameterRequest> parameterRequestList = new ArrayList<>();
        List<PhotoRequest> photoList = new ArrayList<>();
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new AuctionRequest("Auction from test2", "Description from test2", 7777, "notExisting", parameterRequestList, photoList))
                .contentType (ContentType.JSON)
                .post("/api/auctions")
                .then()
                .statusCode (HttpStatus.BAD_REQUEST.value ());
    }

    @Test
    @Order(12)
    public void should_response_404_after_trying_to_edit_not_existing_auction(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new EditRequest())
                .contentType (ContentType.JSON)
                .put("/api/auctions/999999999999999999")
                .then()
                .statusCode (HttpStatus.NOT_FOUND.value ());
    }
    @Test
    @Order(13)
    public void should_response_401_after_trying_to_edit_other_user_existing_auction(){
        var response = given()
                .cookies(userResponse.getCookies())
                .body(new EditRequest())
                .contentType (ContentType.JSON)
                .put("/api/auctions/1")
                .then()
                .statusCode (HttpStatus.UNAUTHORIZED.value ());
    }
    @Test
    @Order(14)
    public void auction_view_should_display_auction_with_parameters_and_minature(){
        Response response = given()
                .cookies(adminResponse.getCookies())
                .contentType (ContentType.JSON)
                .get("/api/auctions")
                .andReturn();
        String expectedResponseBody = "[{\"id\":1,\"title\":\"Test\",\"description\":\"Description from test 1\",\"category_name\":\"categoryAfterEdit\",\"price\":100,\"version\":2,\"miniature\":\"link1\",\"parameters\":{\"TestParameter3\":\"TestValue3\",\"TestParameter2\":\"TestValue2\",\"TestParameter1\":\"TestValue1\"}}]";
        Assert.assertEquals(expectedResponseBody,response.getBody().asString());
    }
    @Test
    @Order(15)
    public void should_response_200_after_title_edit(){
        EditRequest auctionRequest = new EditRequest();
        auctionRequest.setTitle("Test");
        auctionRequest.setVersion(1);
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(auctionRequest)
                .contentType (ContentType.JSON)
                .put("/api/auctions/1")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }
    @Test
    @Order(16)
    public void should_response_409_after_trying_to_edit_outdated_auction(){
        EditRequest auctionRequest = new EditRequest();
        auctionRequest.setTitle("Test");
        auctionRequest.setVersion(0);
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(auctionRequest)
                .contentType (ContentType.JSON)
                .put("/api/auctions/1")
                .then()
                .statusCode (HttpStatus.CONFLICT.value ());
    }
    @Test
    @Order(17)
    public void should_response_200_after_editing_existing_auction_parameters_and_adding_new_one(){
        List<PhotoRequest> photos = new ArrayList<>();
        List<ParameterRequest> parameters = new ArrayList<>();
        parameters.add(new ParameterRequest("TestParameter1","afterTest1"));
        parameters.add(new ParameterRequest("TestParameter2","afterTest2"));
        parameters.add(new ParameterRequest("newTestParameter1","3"));
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new EditRequest("Title after edit", "Description after edit", 100, "testCategory", parameters, photos, 2))
                .contentType (ContentType.JSON)
                .put("/api/auctions/1")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }
    @Test
    @Order(18)
    public void should_response_400_after_trying_to_create_auction_without_title(){
        AuctionRequest auctionRequest = new AuctionRequest();
        List<ParameterRequest> parameterRequestList = new ArrayList<>();
        parameterRequestList.add(new ParameterRequest("TestParameter1","TestValue1"));
        parameterRequestList.add(new ParameterRequest("TestParameter2","TestValue2"));
        parameterRequestList.add(new ParameterRequest("TestParameter3","TestValue3"));
        List<PhotoRequest> photoList = new ArrayList<>();
        photoList.add(new PhotoRequest("link1"));
        photoList.add(new PhotoRequest("link2"));
        photoList.add(new PhotoRequest("link3"));
        auctionRequest.setPhotos(photoList);
        auctionRequest.setParameters(parameterRequestList);
        auctionRequest.setPrice(231);
        auctionRequest.setDescription("TestDesc");
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(auctionRequest)
                .contentType (ContentType.JSON)
                .post("/api/auctions")
                .then()
                .statusCode (HttpStatus.BAD_REQUEST.value ());
    }
    @Test
    @Order(19)
    public void auction_view_should_display_edited_auction_with_one_new_parameter(){
        Response response = given()
                .cookies(adminResponse.getCookies())
                .contentType (ContentType.JSON)
                .get("/api/auctions")
                .andReturn();
        String expectedResponseBody = "[{\"id\":1,\"title\":\"Title after edit\",\"description\":\"Description after edit\",\"category_name\":\"categoryAfterEdit\",\"price\":100,\"version\":3,\"miniature\":\"link1\",\"parameters\":{\"TestParameter3\":\"TestValue3\",\"TestParameter2\":\"afterTest2\",\"TestParameter1\":\"afterTest1\",\"newTestParameter1\":\"3\"}}]";
        Assert.assertEquals(expectedResponseBody,response.getBody().asString());
    }
}
