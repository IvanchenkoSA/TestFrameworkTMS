import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import ru.isa.demo.dto.UserDTO;
import ru.isa.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;



import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static ru.isa.demo.specifications.RespSpec.SIMPLE_OK;
import static ru.isa.demo.specifications.RespSpec.requestSpecification;

public class UserControllerTests {

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Get user test")
    @Test
    void test() {
        given()
                .spec(requestSpecification())
                .when().log().all().get("/api/user/getUser/16")
                .then().log().all()
                .spec(SIMPLE_OK());
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


    @DisplayName("Post and delete user test")
    @Test
    void test3() throws JsonProcessingException {
        // Создание нового пользователя
        UserDTO userDTO = new UserDTO("Test name", "test pass", User.ROLE.ADMIN);
        String jsonString = objectMapper.writeValueAsString(userDTO);

        // Выполнение POST запроса для создания пользователя
        String response = given()
                .spec(requestSpecification())
                .body(jsonString)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .spec(SIMPLE_OK())
                .extract().asString();

        // Извлечение ID созданного пользователя
        Long createUserId = Long.parseLong(response);

        // Выполнение DELETE запроса для удаления пользователя
        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createUserId)
                .then().log().all()
                .spec(SIMPLE_OK());
    }

    @DisplayName("Update user")
    @Test
    void test4() throws JsonProcessingException {
        given()
                .spec(requestSpecification())
                .when().log().all().put("api/user/updateUser/12?username=ChangedName&password=ChangedPassword&role=USER")
                .then().log().all()
                .spec(SIMPLE_OK());
    }

    @DisplayName("Delete user")
    @Test
    void test5() throws JsonProcessingException {
        given()
                .spec(requestSpecification())
                .when().log().all().delete("api/user/delete/15")
                .then().log().all()
                .spec(SIMPLE_OK());
    }

}
