import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import ru.isa.demo.dto.UserDTO;
import ru.isa.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;


import static io.restassured.RestAssured.given;
import static ru.isa.demo.specifications.RespSpec.SIMPLE_OK;
import static ru.isa.demo.specifications.RespSpec.requestSpecification;

public class UserControllerTests {

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Get user test")
    @Test
    void test() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("Test name", "test pass", User.ROLE.ADMIN);
        String jsonString = objectMapper.writeValueAsString(userDTO);

        String response = given()
                .spec(requestSpecification())
                .body(jsonString)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .extract().asString();

        Long createUserId = Long.parseLong(response);

        given()
                .spec(requestSpecification())
                .when().log().all().get("/api/user/getUser/{id}", createUserId)
                .then().log().all()
                .spec(SIMPLE_OK());

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createUserId)
                .then().log().all();

    }

    @DisplayName("Get all users")
    @Test
    void test2() throws JsonProcessingException {
        given()
                .spec(requestSpecification())
                .when().log().all().get("/api/user/getAll")
                .then().log().all()
                .spec(SIMPLE_OK());
    }


    @DisplayName("Post user test")
    @Test
    void test3() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("Test name", "test pass", User.ROLE.ADMIN);
        String jsonString = objectMapper.writeValueAsString(userDTO);

        String response = given()
                .spec(requestSpecification())
                .body(jsonString)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .spec(SIMPLE_OK())
                .extract().asString();

        Long createUserId = Long.parseLong(response);

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createUserId)
                .then().log().all();
    }

    @DisplayName("Update user")
    @Test
    void test4() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("Test name", "test pass", User.ROLE.ADMIN);
        String jsonString = objectMapper.writeValueAsString(userDTO);

        String response = given()
                .spec(requestSpecification())
                .body(jsonString)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .extract().asString();

        Long createUserId = Long.parseLong(response);

        given()
                .spec(requestSpecification())
                .when().log().all().put("api/user/updateUser/{id}?username=ChangedName&password=ChangedPassword&role=USER", createUserId)
                .then().log().all()
                .spec(SIMPLE_OK());

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createUserId)
                .then().log().all();
    }

    @DisplayName("Delete user")
    @Test
    void test5() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("Test name", "test pass", User.ROLE.ADMIN);
        String jsonString = objectMapper.writeValueAsString(userDTO);

        String response = given()
                .spec(requestSpecification())
                .body(jsonString)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .extract().asString();

        Long createUserId = Long.parseLong(response);

        given()
                .spec(requestSpecification())
                .when().log().all().delete("api/user/delete/{id}", createUserId)
                .then().log().all()
                .spec(SIMPLE_OK());
    }

}
