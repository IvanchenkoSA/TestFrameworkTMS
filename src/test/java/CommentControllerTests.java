import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.isa.demo.dto.CommentDTO;
import ru.isa.demo.dto.TaskDTO;
import ru.isa.demo.dto.UserDTO;
import ru.isa.demo.model.Task;
import ru.isa.demo.model.User;

import static io.restassured.RestAssured.given;
import static ru.isa.demo.specifications.RespSpec.SIMPLE_OK;
import static ru.isa.demo.specifications.RespSpec.requestSpecification;

public class CommentControllerTests {

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Create comment")
    @Test
    void test() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("Test name GET", "test pass", User.ROLE.ADMIN);
        String jsonStringUser = objectMapper.writeValueAsString(userDTO);

        String userResponse = given()
                .spec(requestSpecification())
                .body(jsonStringUser)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .extract().asString();

        Long createdUserId = Long.parseLong(userResponse);

        CommentDTO commentDTO = new CommentDTO("Test comment GET", createdUserId);
        String jsonStringTask = objectMapper.writeValueAsString(commentDTO);

        given()
                .spec(requestSpecification())
                .body(jsonStringTask)
                .when().log().all().post("/api/comment/create")
                .then().log().all()
                .spec(SIMPLE_OK())
                .extract().asString();

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createdUserId)
                .then().log().all();

    }

    @DisplayName("Get all comments")
    @Test
    void test1() throws JsonProcessingException {
        given()
                .spec(requestSpecification())
                .when().log().all().get("/api/comment/getAll")
                .then().log().all()
                .spec(SIMPLE_OK());

    }

    @DisplayName("Get all user comments By userId")
    @Test
    void test2() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("Test name GET", "test pass", User.ROLE.ADMIN);
        String jsonStringUser = objectMapper.writeValueAsString(userDTO);

        String userResponse = given()
                .spec(requestSpecification())
                .body(jsonStringUser)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .extract().asString();

        Long createdUserId = Long.parseLong(userResponse);

        CommentDTO commentDTO = new CommentDTO("Test comment GET", createdUserId);
        CommentDTO commentDTO2 = new CommentDTO("Test comment GET 2", createdUserId);
        String jsonStringTask = objectMapper.writeValueAsString(commentDTO);
        String jsonStringTask2 = objectMapper.writeValueAsString(commentDTO2);

        given()
                .spec(requestSpecification())
                .body(jsonStringTask)
                .when().log().all().post("/api/comment/create")
                .then().log().all()
                .extract().asString();

        given()
                .spec(requestSpecification())
                .body(jsonStringTask2)
                .when().log().all().post("/api/comment/create")
                .then().log().all()
                .extract().asString();

        given()
                .spec(requestSpecification())
                .when().log().all()
                .get("api/comment/getComment/{userId}", createdUserId)
                .then().log().all()
                .spec(SIMPLE_OK());

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createdUserId)
                .then().log().all();
    }

    @DisplayName("Update comment by id")
    @Test
    void test3() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("Test name GET", "test pass", User.ROLE.ADMIN);
        String jsonStringUser = objectMapper.writeValueAsString(userDTO);

        String userResponse = given()
                .spec(requestSpecification())
                .body(jsonStringUser)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .extract().asString();

        Long createdUserId = Long.parseLong(userResponse);

        CommentDTO commentDTO = new CommentDTO("Test comment GET", createdUserId);
        String jsonStringTask = objectMapper.writeValueAsString(commentDTO);


        given()
                .spec(requestSpecification())
                .body(jsonStringTask)
                .when().log().all().post("/api/comment/create")
                .then().log().all()
                .extract().asString();


        given()
                .spec(requestSpecification())
                .when().log().all().put("api/user/updateUser/{id}?content=ChangedComment", createdUserId)
                .then().log().all()
                .spec(SIMPLE_OK());

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createdUserId)
                .then().log().all();

    }

    @DisplayName("Delete all comments user by userId")
    @Test
    void test4() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("Test name GET", "test pass", User.ROLE.ADMIN);
        String jsonStringUser = objectMapper.writeValueAsString(userDTO);

        String userResponse = given()
                .spec(requestSpecification())
                .body(jsonStringUser)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .extract().asString();

        Long createdUserId = Long.parseLong(userResponse);

        CommentDTO commentDTO = new CommentDTO("Test comment GET", createdUserId);
        String jsonStringTask = objectMapper.writeValueAsString(commentDTO);


        given()
                .spec(requestSpecification())
                .body(jsonStringTask)
                .when().log().all().post("/api/comment/create")
                .then().log().all()
                .extract().asString();


        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("api/comment/deleteByUserId/{userId}", createdUserId)
                .then().log().all();

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createdUserId)
                .then().log().all();

    }

    @DisplayName("Delete comment by Id")
    @Test
    void test5() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("Test name GET", "test pass", User.ROLE.ADMIN);
        String jsonStringUser = objectMapper.writeValueAsString(userDTO);

        String userResponse = given()
                .spec(requestSpecification())
                .body(jsonStringUser)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .extract().asString();

        Long createdUserId = Long.parseLong(userResponse);

        CommentDTO commentDTO = new CommentDTO("Test comment GET", createdUserId);
        String jsonStringTask = objectMapper.writeValueAsString(commentDTO);


        String commentResponse = given()
                .spec(requestSpecification())
                .body(jsonStringTask)
                .when().log().all().post("/api/comment/create")
                .then().log().all()
                .extract().asString();

        Long commentId = Long.parseLong(commentResponse);


        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/comment/deleteById/{Id}", commentId)
                .then().log().all()
                .spec(SIMPLE_OK());

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createdUserId)
                .then().log().all();

    }

}
