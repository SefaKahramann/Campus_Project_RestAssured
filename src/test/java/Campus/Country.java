package Campus;

import Utility.Campus_ParentPage;
import Utility.ConfigReader;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Country extends Campus_ParentPage {

    @Test
    public void CreateCountry(){
        Map<String,String> countries = new HashMap<>();
        countries.put("id",null);
        countries.put("name", faker.country().name()+faker.number().digits(3));
        countries.put("code", faker.number().digits(5));
        countries.put("translateName", null);

        String countryID=
                given()
                        .spec(reqSpec)
                        .body(countries)

                        .when()
                        .post("/school-service/api/countries")

                        .then()
                        .statusCode(201)
                        .log().body()
                        .extract().path("id")
        ;
        ConfigReader.updateProperty("countryID",countryID);
    }
}
