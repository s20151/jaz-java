package pl.edu.pjwstk.jaz.AllezonTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.IntegrationTest;
import pl.edu.pjwstk.jaz.RestAssurePortListener;
import pl.edu.pjwstk.jaz.entities.AuctionView;
import pl.edu.pjwstk.jaz.login.LoginRequest;
import pl.edu.pjwstk.jaz.requests.AuctionRequest;
import pl.edu.pjwstk.jaz.requests.EditRequest;
import pl.edu.pjwstk.jaz.requests.ParameterRequest;
import pl.edu.pjwstk.jaz.requests.PhotoRequest;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@IntegrationTest
public class AuctionTest {

    private static Response adminResponse;
    private static Response userResponse;

    @BeforeClass
    public static void loginAsAdminAndUser(){
        adminResponse = given()
                .when()
                .body (new LoginRequest ("admin","admin"))
                .contentType (ContentType.JSON)
                .post ("/api/login")
                .thenReturn();
        userResponse = given()
                .when()
                .body (new LoginRequest ("user1","user1"))
                .contentType (ContentType.JSON)
                .post ("/api/login")
                .thenReturn();
    }
    @Test
    public void should_response_201_after_creating_auction(){
        List<ParameterRequest> parameterRequestList = new ArrayList<>();
        parameterRequestList.add(new ParameterRequest("Wysokosc","54322"));
        parameterRequestList.add(new ParameterRequest("Dlugosc","123"));
        parameterRequestList.add(new ParameterRequest("Szerokosc","7123"));
        List<PhotoRequest> photoList = new ArrayList<>();
        photoList.add(new PhotoRequest("link1FromTest123.pl33333"));
        photoList.add(new PhotoRequest("link2FromTest123.pl123123"));
        photoList.add(new PhotoRequest("link3FromTest123.p23123l"));
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new AuctionRequest("Title from test 2", "Description from test 2", 22222, "1st Category", parameterRequestList, photoList ))
                .contentType (ContentType.JSON)
                .post("/api/auctions")
                .then()
                .statusCode (HttpStatus.CREATED.value ());
    }
    @Test
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
    public void should_response_404_after_trying_to_edit_not_existing_auction(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(new AuctionRequest())
                .contentType (ContentType.JSON)
                .put("/api/auctions/999999999999999999")
                .then()
                .statusCode (HttpStatus.NOT_FOUND.value ());
    }
    @Test
    public void should_response_200_after_editing_existing_auction(){
        EditRequest auctionRequest = new EditRequest();
        auctionRequest.setTitle("Title after edit");
        auctionRequest.setDescription("Description after edit");
        auctionRequest.setVersion(5);
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(auctionRequest)
                .contentType (ContentType.JSON)
                .put("/api/auctions/4")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }

    @Test
    public void should_response_401_after_trying_to_edit_other_user_existing_auction(){
        var response = given()
                .cookies(userResponse.getCookies())
                .body(new EditRequest())
                .contentType (ContentType.JSON)
                .put("/api/auctions/4")
                .then()
                .statusCode (HttpStatus.UNAUTHORIZED.value ());
    }
    @Test
    public void should_response_200_after_editing_existing_auction_parameters_and_adding_new_one(){
        EditRequest auctionRequest = new EditRequest();
        List<ParameterRequest> parameters = new ArrayList<>();
        parameters.add(new ParameterRequest("Wysokosc","afterTest1111"));
        parameters.add(new ParameterRequest("Dlugosc","afterTest22"));
        parameters.add(new ParameterRequest("newTestParameter333","3333afterTest"));
        auctionRequest.setVersion(7);
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(auctionRequest)
                .contentType (ContentType.JSON)
                .put("/api/auctions/4")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }
    @Test
    public void should_response_409_after_trying_to_edit_outdated_auction(){
        EditRequest auctionRequest = new EditRequest();
        auctionRequest.setTitle("Test");
        auctionRequest.setVersion(555555);
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(auctionRequest)
                .contentType (ContentType.JSON)
                .put("/api/auctions/5")
                .then()
                .statusCode (HttpStatus.CONFLICT.value ());
    }
    public void auction_view_should_display_auction_with_parameters_and_minature(){
        var response = given()
                .cookies(adminResponse.getCookies())
                .contentType (ContentType.JSON)
                .get("/api/auctions/5")
                .thenReturn();
    }
}
