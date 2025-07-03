package za.co.pass.studentservice.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import za.co.pass.studentservice.dto.StudentRequestDTO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class StudentControllerIntegrationTest {

    private static String studentId;

    @Value("${server.port}")
    private int port;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
    }

    private StudentRequestDTO buildStudentRequestDTO(String name, String email, String address, String dateOfBirth, String registeredDate) {
        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName(name);
        dto.setEmail(email);
        dto.setAddress(address);
        dto.setDateOfBirth(dateOfBirth);
        dto.setRegisteredDate(registeredDate);
        return dto;
    }

    @Test
    @Order(1)
    void testCreateStudent() {
        StudentRequestDTO request = buildStudentRequestDTO(
            "John Doe",
            "john.doe@example.com",
            "123 Main St",
            "2000-01-01",
            "2024-01-01"
        );
        Response response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/students")
                .then()
                .statusCode(200)
                .body("name", equalTo("John Doe"))
                .body("email", equalTo("john.doe@example.com"))
                .extract().response();
        studentId = response.jsonPath().getString("id");
    }

    @Test
    @Order(2)
    void testGetStudent() {
        given()
                .port(port)
                .when()
                .get("/students/" + studentId)
                .then()
                .statusCode(200)
                .body("id", equalTo(studentId))
                .body("name", equalTo("John Doe"));
    }

    @Test
    @Order(3)
    void testUpdateStudent() {
        StudentRequestDTO update = buildStudentRequestDTO(
            "Jane Doe",
            "jane.doe@example.com",
            "456 Main St",
            "2000-01-01",
            "2024-01-01"
        );
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(update)
                .when()
                .put("/students/" + studentId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Jane Doe"))
                .body("email", equalTo("jane.doe@example.com"));
    }

    @Test
    @Order(4)
    void testGetAllStudents() {
        given()
                .port(port)
                .when()
                .get("/students")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(4));
    }

    @Test
    @Order(5)
    void testDeleteStudent() {
        given()
                .port(port)
                .when()
                .delete("/students/" + studentId)
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }

    @Test
    @Order(6)
    void testGetStudentNotFound() {
        given()
                .port(port)
                .when()
                .get("/students/" + studentId)
                .then()
                .statusCode(404);
    }

    @Test
    @Order(7)
    void testCreateStudentValidationErrors() {
        StudentRequestDTO invalid = buildStudentRequestDTO(
            "",
            "not-an-email",
            "",
            "invalid-date",
            "2024-01-01"
        );
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(invalid)
                .when()
                .post("/students")
                .then()
                .statusCode(400)
                .body("message", nullValue()); // Should return validation errors, not a message
    }

    @Test
    @Order(8)
    void testCreateStudentDuplicateEmail() {
        StudentRequestDTO first = buildStudentRequestDTO(
            "Duplicate Email 1",
            "duplicate@email.com",
            "789 Main St",
            "2000-01-01",
            "2024-01-01"
        );
        given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(first)
            .when()
            .post("/students")
            .then()
            .statusCode(200);

        StudentRequestDTO second = buildStudentRequestDTO(
            "Duplicate Email 2",
            "duplicate@email.com",
            "790 Main St",
            "2000-01-01",
            "2024-01-01"
        );
        given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(second)
            .when()
            .post("/students")
            .then()
            .statusCode(400)
            .body("message", containsString("already exists"));
    }

    @Test
    @Order(9)
    void testUpdateStudentMissingEmailUponUpdate() {
        StudentRequestDTO create = buildStudentRequestDTO(
            "Susan Smith",
            "susan@email.com",
            "321 Elm St",
            "2000-01-01",
            "2024-01-01"
        );
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(create)
                .when()
                .post("/students")
                .then()
                .statusCode(200);

        // Now try to update without email
        String updateBody = """
                    {
                        "name": "Susan Smith Updated",
                        "address": "321 Elm St Updated",
                        "dateOfBirth": "2000-01-01",
                        "registeredDate": "2024-01-01"
                    }
                """;
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(updateBody)
                .when()
                .put("/students/" + studentId)
                .then()
                .statusCode(400);
    }

    @Test
    @Order(10)
    void testDeleteNonExistentStudent() {
        String randomId = java.util.UUID.randomUUID().toString();
        given()
                .port(port)
                .when()
                .delete("/students/" + randomId)
                .then()
                .statusCode(404)
                .body("message", containsString("not been found"));
    }

    @Test
    @Order(11)
    void testGetStudentInvalidUUID() {
        given()
                .port(port)
                .when()
                .get("/students/invalid-uuid")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(12)
    void testCreateStudentWithLongFields() {
        String longName = "A".repeat(300);
        String longAddress = "B".repeat(500);
        StudentRequestDTO request = buildStudentRequestDTO(
            longName,
            "long.fields@example.com",
            longAddress,
            "2000-01-01",
            "2024-01-01"
        );
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/students")
                .then()
                .statusCode(anyOf(is(200), is(400))); // Accept either, depending on validation
    }

    @Test
    @Order(13)
    void testGetAllStudentsWhenEmpty() {
        // Delete all students
        Response response = given()
                .port(port)
                .when()
                .get("/students");
        var students = response.jsonPath().getList("");
        for (Object student : students) {
            String id = ((java.util.Map<?, ?>) student).get("id").toString();
            given().port(port).when().delete("/students/" + id);
        }
        // Now get all students, should be empty
        given()
                .port(port)
                .when()
                .get("/students")
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    @Order(14)
    void testUpdateStudentDuplicateEmail() {
        // Create a first student
        StudentRequestDTO firstStudent = buildStudentRequestDTO(
                "First Student",
                "first.student@example.com",
                "101 Main St",
                "2001-01-01",
                "2024-01-01"
        );
        Response firstResponse = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(firstStudent)
                .when()
                .post("/students")
                .then()
                .statusCode(200)
                .extract().response();
        String firstStudentId = firstResponse.jsonPath().getString("id");

        // Create a second student
        StudentRequestDTO secondStudent = buildStudentRequestDTO(
            "Second Student",
            "second.student@example.com",
            "101 Main St",
            "2001-01-01",
            "2024-01-01"
        );
        Response secondResponse = given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(secondStudent)
            .when()
            .post("/students")
            .then()
            .statusCode(200)
            .extract().response();
        String secondStudentId = secondResponse.jsonPath().getString("id");

        // Try to update second student using a first student id
        // with a new email of the second student (should fail)
        StudentRequestDTO update = buildStudentRequestDTO(
            "Second Student",
            "second.student@example.com",
            "101 Main St",
            "2001-01-01",
            "2024-01-01"
        );
        given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(update)
            .when()
            .put("/students/" + firstStudentId)
            .then()
            .statusCode(400)
            .body("message", containsString("already exists"));

        // Also test updating a student with their own email (should succeed)
        StudentRequestDTO selfUpdate = buildStudentRequestDTO(
            "Second Student",
            "second.student@example.com",
            "101 Main St",
            "2001-01-01",
            "2024-01-01"
        );
        given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(selfUpdate)
            .when()
            .put("/students/" + secondStudentId)
            .then()
            .statusCode(200)
            .body("email", equalTo("second.student@example.com"));
    }

}
