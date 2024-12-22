package Campus;

import Utility.Campus_ParentPage;
import Utility.ConfigReader;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Grading extends Campus_ParentPage {

    @Test
    public void getGrading() {
        given()
                .spec(reqSpec)

                .when()
                .get("/school-service/api/grading-schemes/school/" + ConfigReader.getProperty("schoolID") + "/search")

                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "getGrading")
    public void createScheme() {
        Map<String, Object> createScheme = new LinkedHashMap<>();

        createScheme.put("id", null);
        createScheme.put("name", faker.name().fullName());
        createScheme.put("active", true);
        createScheme.put("type", "POINT");
        createScheme.put("enablePoint", false);
        createScheme.put("minPointToPass", "1");
        createScheme.put("gradeRanges", new String[]{});
        createScheme.put("schoolId", ConfigReader.getProperty("schoolID"));

        String gradingID =
                given()
                        .spec(reqSpec)
                        .body(createScheme)

                        .when()
                        .post("/school-service/api/grading-schemes")

                        .then()
                        .statusCode(201)

                        .log().body()
                        .extract().path("id");

        ConfigReader.updateProperty("gradingID", gradingID);
    }

    @Test(dependsOnMethods = "createScheme")
    public void updateScheme() {
        Map<String, Object> updateScheme = new LinkedHashMap<>();

        updateScheme.put("id", ConfigReader.getProperty("gradingID"));
        updateScheme.put("name", faker.name().fullName());
        updateScheme.put("active", true);
        updateScheme.put("type", "POINT");
        updateScheme.put("enablePoint", true);
        updateScheme.put("minPointToPass", "2");
        updateScheme.put("gradeRanges", new String[]{});
        updateScheme.put("schoolId", ConfigReader.getProperty("schoolID"));

        given()
                .spec(reqSpec)
                .body(updateScheme)

                .when()
                .put("/school-service/api/grading-schemes")

                .then()
                .statusCode(200)

                .log().body();
    }

    @Test(dependsOnMethods = "updateScheme")
    public void deleteGradingScheme() {
        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/grading-schemes/" + ConfigReader.getProperty("gradingID"))

                .then()
                .statusCode(200)

                .log().body();
    }

    @Test(dependsOnMethods = "deleteGradingScheme")
    public void deleteNegativeGradingScheme() {
        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/grading-schemes/" + ConfigReader.getProperty("gradingID"))

                .then()
                .statusCode(400)

                .log().body();
    }
}