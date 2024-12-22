package Campus;

import Utility.Campus_ParentPage;
import Utility.ConfigReader;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class Cities extends Campus_ParentPage {

    @Test
    public void createCities() {
        Map<String, Object> cities = new LinkedHashMap<>();
        cities.put("id", null);
        cities.put("name", "RussiaCity");
        cities.put("shortName", "GermanCity");

        Map<String, String> countryId = new LinkedHashMap<>();
        countryId.put("id", ConfigReader.getProperty("countryID"));
        cities.put("country", countryId);

        Map<String, String> stateId = new LinkedHashMap<>();
        stateId.put("state", ConfigReader.getProperty("statesID"));
        cities.put("state", stateId);

        cities.put("translateName", new String[]{});

        String citiesId =
                given()
                        .spec(reqSpec)
                        .body(cities)

                        .when()
                        .post("/school-service/api/cities")

                        .then()
                        .statusCode(201)
                        .assertThat().time(lessThan(1000L))
                        .log().body()
                        .extract().path("id");
        ConfigReader.updateProperty("citiesID", citiesId);
    }

    @Test
    public void updateCities() {
        Map<String, Object> cities = new LinkedHashMap<>();
        cities.put("id", ConfigReader.getProperty("citiesID"));
        cities.put("name", faker.address().city());
        cities.put("shortName", faker.number().digits(5));

        Map<String, String> countryId = new LinkedHashMap<>();
        countryId.put("id", ConfigReader.getProperty("countryID"));
        cities.put("country", countryId);

        Map<String, String> stateId = new LinkedHashMap<>();
        stateId.put("state", ConfigReader.getProperty("statesID"));
        cities.put("state", stateId);

        cities.put("translateName", new String[]{});

        given()
                .spec(reqSpec)
                .body(cities)

                .when()
                .put("/school-service/api/cities")

                .then()
                .statusCode(200)
                .assertThat().time(lessThan(1000L))
                .log().body();
    }

    @Test
    public void deleteCities() {
        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/cities/" + ConfigReader.getProperty("citiesID"))

                .then()
                .statusCode(200)
        ;
    }
}
