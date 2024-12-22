package Utility;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Campus_ParentPage {
    Faker faker=new Faker();
    public RequestSpecification reqSpec;

    @BeforeClass
    public void Setup() {
        baseURI = ConfigReader.getProperty("baseURI");

        Map<String, String> userCredential = new HashMap<>();
        userCredential.put("username",ConfigReader.getProperty("username"));
        userCredential.put("password", ConfigReader.getProperty("password"));
        userCredential.put("rememberMe", "true");

        Cookies cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(userCredential)

                        .when()
                        .post("/auth/login")

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response().detailedCookies();
        ;
        reqSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .build()
        ;
    }
}
