package pl.edu.pjwstk.jaz.AllezonTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import pl.edu.pjwstk.jaz.login.LoginRequest;
import pl.edu.pjwstk.jaz.requests.AuctionRequest;
import pl.edu.pjwstk.jaz.requests.ParameterRequest;
import pl.edu.pjwstk.jaz.requests.PhotoRequest;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

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
                .body (new LoginRequest ("user","user"))
                .contentType (ContentType.JSON)
                .post ("/api/login")
                .thenReturn();
    }
    @Test
    public void should_response_201_after_creating_auction(){
        AuctionRequest auction = new AuctionRequest();
        List<ParameterRequest> parameterRequestList = new ArrayList<>();
        parameterRequestList.add(new ParameterRequest("Wysokosc","55555"));
        parameterRequestList.add(new ParameterRequest("Dlugosc","666666"));
        parameterRequestList.add(new ParameterRequest("Szerokosc","777777"));
        List<PhotoRequest> photoList = new ArrayList<>();
        photoList.add(new PhotoRequest("link1FromTest123.pl"));
        photoList.add(new PhotoRequest("link2FromTest123.pl"));
        photoList.add(new PhotoRequest("link3FromTest123.pl"));
        auction.setTitle("Auction from test333");
        auction.setDescription("Description from test333");
        auction.setPrice(2131);
        auction.setCategoryName("PSU");
        auction.setParameters(parameterRequestList);
        auction.setPhotos(photoList);
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(auction)
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
        AuctionRequest auctionRequest = new AuctionRequest();
        auctionRequest.setTitle("Title after edit");
        auctionRequest.setDescription("Description after edit");
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(auctionRequest)
                .contentType (ContentType.JSON)
                .put("/api/auctions/7")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }

    @Test
    public void should_response_401_after_trying_to_edit_other_user_existing_auction(){
        var response = given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest())
                .contentType (ContentType.JSON)
                .put("/api/auctions/4")
                .then()
                .statusCode (HttpStatus.UNAUTHORIZED.value ());
    }
    @Test
    public void should_response_200_after_editing_existing_auction_parameters_and_adding_new_one(){
        AuctionRequest auctionRequest = new AuctionRequest();
        List<ParameterRequest> parameters = new ArrayList<>();
        parameters.add(new ParameterRequest("Wysokosc","afterTest1111"));
        parameters.add(new ParameterRequest("Dlugosc","afterTest22"));
        parameters.add(new ParameterRequest("newTestParameter","3333afterTest"));
        var response = given()
                .cookies(adminResponse.getCookies())
                .body(auctionRequest)
                .contentType (ContentType.JSON)
                .put("/api/auctions/7")
                .then()
                .statusCode (HttpStatus.OK.value ());
    }
}
