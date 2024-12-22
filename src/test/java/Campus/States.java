package Campus;

import Utility.Campus_ParentPage;
import Utility.ConfigReader;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class States extends Campus_ParentPage {

    @Test
    public void createState() {
        Map<String, Object> state = new LinkedHashMap<>();
        state.put("id", null);
        state.put("name", "RussiaState");
        state.put("shortName", "GermanState");

        Map<String, String> countryId = new LinkedHashMap<>();
        countryId.put("id", ConfigReader.getProperty("countryID"));

        state.put("country", countryId);
        state.put("translateName", new String[]{});

        String statesID =
                given()
                        .spec(reqSpec)
                        .body(state)

                        .when()
                        .post("/school-service/api/states")

                        .then()
                        .statusCode(201)
                        .assertThat().time(lessThan(1000L))
                        .log().body()
                        .extract().path("id");
        ConfigReader.updateProperty("statesID", statesID);
    }

    @Test
    public void updateState() {
        Map<String, Object> state = new LinkedHashMap<>();
        state.put("id", null);
        state.put("name", faker.address().state());
        state.put("shortName", faker.address().zipCode());

        Map<String, String> countryId = new LinkedHashMap<>();
        countryId.put("id", ConfigReader.getProperty("countryID"));

        state.put("country", countryId);
        state.put("translateName", new String[]{});

        given()
                .spec(reqSpec)
                .body(state)

                .when()
                .put("/school-service/api/states")

                .then()
                .statusCode(200)
                .assertThat().time(lessThan(1000L))
                .log().body()
                .extract().path("id")
        ;
    }

    @Test
    public void deleteState() {
        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/states/" + ConfigReader.getProperty("statesID"))

                .then()
                .statusCode(200)
        ;
    }
}
