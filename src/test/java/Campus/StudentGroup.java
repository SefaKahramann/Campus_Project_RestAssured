package Campus;

import Utility.Campus_ParentPage;
import Utility.ConfigReader;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class StudentGroup extends Campus_ParentPage {

    @Test
    public void getStudent() {
        String student1 = "";
        String student2 = "";
        String student3 = "";
        String student4 = "";

        Response response =
                given()
                        .spec(reqSpec)

                        .when()
                        .get("/school-service/api/incident/school/6576fd8f8af7ce488ac69b89/class/65770d9b8af7ce488ac69bb6/student-points");

        response.then().statusCode(200);
        JsonPath jsonPath = response.jsonPath();

        student1 = jsonPath.getString("studentPoints[0].id");
        student2 = jsonPath.getString("studentPoints[1].id");
        student3 = jsonPath.getString("studentPoints[2].id");
        student4 = jsonPath.getString("studentPoints[3].id");

        ConfigReader.updateProperty("student1", student1);
        ConfigReader.updateProperty("student2", student2);
        ConfigReader.updateProperty("student3", student3);
        ConfigReader.updateProperty("student4", student4);
    }

    @Test
    public void createStudentGroup(){
        Map<String, Object> studentGroup = new LinkedHashMap<>();
        studentGroup.put("id", null);
        studentGroup.put("schoolId",ConfigReader.getProperty("schoolID") );
        studentGroup.put("name", faker.name().fullName());
        studentGroup.put("description",faker.lorem().sentence());
        studentGroup.put("active",true);
        studentGroup.put("publicGroup",true);
        studentGroup.put("showToStudent",false);

        String groupID =
                given()
                        .spec(reqSpec)
                        .body(studentGroup)

                        .when()
                        .post("/school-service/api/student-group")

                        .then()
                        .statusCode(201)

                        .log().body()
                        .extract().path("id");

        ConfigReader.updateProperty("groupID",groupID);
    }

    @Test
    public void updateStudentGroup(){
        Map<String, Object> studentGroup = new LinkedHashMap<>();
        studentGroup.put("id", ConfigReader.getProperty("groupID"));
        studentGroup.put("schoolId",ConfigReader.getProperty("schoolID") );
        studentGroup.put("name", faker.name().fullName());
        studentGroup.put("description",faker.lorem().sentence());
        studentGroup.put("active",true);
        studentGroup.put("publicGroup",true);
        studentGroup.put("showToStudent",false);

                given()
                        .spec(reqSpec)
                        .body(studentGroup)

                        .when()
                        .put("/school-service/api/student-group")

                        .then()
                        .statusCode(200)

                        .log().body();

    }

    @Test
    public void createStudent(){
        given()
                .spec(reqSpec)

                .when()
                .get("/school-service/api/student-group/"+ConfigReader.getProperty("groupID"))

                .then()
                .statusCode(200)

                .log().body();
    }

    @Test
    public void addStudentGroup(){
        List<String>students=new ArrayList<>();
        students.add(ConfigReader.getProperty("student1"));
        students.add(ConfigReader.getProperty("student2"));
        students.add(ConfigReader.getProperty("student3"));
        students.add(ConfigReader.getProperty("student4"));

        given()
                .spec(reqSpec)
                .body(students)

                .when()
                .post("/school-service/api/student-group/"+ConfigReader.getProperty("groupID")+"/add-students?page=0&size=10")

                .then()
                .statusCode(200)

                .log().body();

    }

    @Test
    public void deleteStudent(){
        List<String>students=new ArrayList<>();
        students.add(ConfigReader.getProperty("student1"));

        given()
                .spec(reqSpec)
                .body(students)

                .when()
                .post("/school-service/api/student-group/"+ConfigReader.getProperty("groupID")+"/remove-students?page=0&size=10")

                .then()
                .statusCode(200)

                .log().body();
    }

    @Test
    public void deleteStudentGroup(){
        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/student-group/"+ConfigReader.getProperty("groupID"))

                .then()
                .statusCode(200)

                .log().body();
    }

    @Test
    public void deleteNegativeStudentGroup(){
        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/student-group/"+ConfigReader.getProperty("groupID"))

                .then()
                .statusCode(400)

                .log().body();
    }
}
