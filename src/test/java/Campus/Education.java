package Campus;

import Utility.Campus_ParentPage;
import Utility.ConfigReader;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Education extends Campus_ParentPage {
    @Test
    public void getEducation() {
        String education1 = "";
        Map<String, Object> getEducation = new LinkedHashMap<>();
        getEducation.put("schoolId", ConfigReader.getProperty("schoolID"));
        getEducation.put("name", null);
        getEducation.put("gradeLevelId", null);
        getEducation.put("subjectId", null);
        getEducation.put("packageId", null);
        getEducation.put("pageSize", 10);
        getEducation.put("pageIndex", 0);

        Response response =
                given()
                        .spec(reqSpec)
                        .body(getEducation)

                        .when()
                        .get("/school-service/api/subjects/646cb816433c0f46e7d44cb0/tenant/" + ConfigReader.getProperty("schoolID") + "/school/keyvalue");

        response.then().statusCode(200);
        JsonPath jsonPath = response.jsonPath();
        education1 = jsonPath.getString("[0].id");

        ConfigReader.updateProperty("educationID", education1);
    }

    @Test
    public void addEducation() {

        Map<String, Object> addEducation = new LinkedHashMap<>();
        addEducation.put("id", null);
        addEducation.put("name", faker.name().fullName());
        addEducation.put("description", faker.lorem().sentence());
        addEducation.put("schoolId", ConfigReader.getProperty("schoolID"));
        addEducation.put("gradeLevelId", ConfigReader.getProperty("gradeLevelId"));
        addEducation.put("subjectId", ConfigReader.getProperty("subjectId"));
        addEducation.put("gradeCategoriesTemplateId", ConfigReader.getProperty("gradeCategoriesTemplateId"));
        addEducation.put("gradeCategoryId", ConfigReader.getProperty("gradeCategoryId"));

        String javaID =
                given()
                        .spec(reqSpec)
                        .body(addEducation)

                        .when()
                        .post("/school-service/api/education-standard")

                        .then()
                        .statusCode(201)

                        .log().body()
                        .extract().path("id");

        ConfigReader.updateProperty("javaID", javaID);
    }

    @Test
    public void updateEducation() {
        Map<String, Object> updateEducation = new LinkedHashMap<>();
        updateEducation.put("id", ConfigReader.getProperty("javaID"));
        updateEducation.put("name", faker.name().fullName());
        updateEducation.put("description", faker.lorem().sentence());
        updateEducation.put("schoolId", ConfigReader.getProperty("schoolID"));
        updateEducation.put("gradeLevelId", ConfigReader.getProperty("gradeLevelId"));
        updateEducation.put("subjectId", ConfigReader.getProperty("subjectId"));
        updateEducation.put("gradeCategoriesTemplateId", ConfigReader.getProperty("gradeCategoriesTemplateId"));
        updateEducation.put("gradeCategoryId", ConfigReader.getProperty("gradeCategoryId"));

        given()
                .spec(reqSpec)
                .body(updateEducation)

                .when()
                .put("/school-service/api/education-standard")

                .then()
                .statusCode(201)

                .log().body()
                .extract().path("id");
    }

    @Test
    public void deleteEducation() {
        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/education-standard/" + ConfigReader.getProperty("javaID"))

                .then()
                .statusCode(204)

                .log().body();
    }
}