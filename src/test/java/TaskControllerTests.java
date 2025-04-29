import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.isa.demo.dto.TaskDTO;
import ru.isa.demo.dto.UserDTO;
import ru.isa.demo.model.Task;
import ru.isa.demo.model.User;


import static io.restassured.RestAssured.given;
import static ru.isa.demo.specifications.RespSpec.SIMPLE_OK;
import static ru.isa.demo.specifications.RespSpec.requestSpecification;

public class TaskControllerTests {

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Get task")
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

        TaskDTO taskDTO = new TaskDTO(Task.Priority.HIGH, Task.Status.IN_IDLE, "Rest test description", "Rest test title", createdUserId);
        String jsonStringTask = objectMapper.writeValueAsString(taskDTO);


        String taskResponse = given()
                .spec(requestSpecification())
                .body(jsonStringTask)
                .when().log().all().post("/api/task/create")
                .then().log().all()
                .extract().asString();

        Long createdTaskId = Long.parseLong(taskResponse);

        given()
                .spec(requestSpecification())
                .when().log().all().get("/api/task/getById/{id}", createdTaskId)
                .then().log().all()
                .spec(SIMPLE_OK());

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/task/delete/{id}", createdTaskId)
                .then().log().all();

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createdUserId)
                .then().log().all();

    }

    @DisplayName("Get all task")
    @Test
    void test2() {
        given()
                .spec(requestSpecification())
                .when().log().all().get("/api/task/getAll")
                .then().log().all()
                .spec(SIMPLE_OK());
    }


    @DisplayName("Post task")
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


        TaskDTO taskDTO = new TaskDTO(Task.Priority.HIGH, Task.Status.IN_IDLE, "Rest test description", "Rest test title", createdUserId);
        String jsonString = objectMapper.writeValueAsString(taskDTO);
        given()
                .spec(requestSpecification())
                .body(jsonString)
                .when().log().all().post("/api/task/create")
                .then().log().all()
                .spec(SIMPLE_OK());


        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createdUserId)
                .then().log().all();
    }


    @DisplayName("Update task")
    @Test
    void test4() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("Test name UPD", "test pass", User.ROLE.ADMIN);
        String jsonStringUser = objectMapper.writeValueAsString(userDTO);

        String userResponse = given()
                .spec(requestSpecification())
                .body(jsonStringUser)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .extract().asString();

        Long createdUserId = Long.parseLong(userResponse);

        TaskDTO taskDTO = new TaskDTO(Task.Priority.HIGH, Task.Status.IN_IDLE, "Rest test description", "Rest test title", createdUserId);
        String jsonStringTask = objectMapper.writeValueAsString(taskDTO);


        String taskResponse = given()
                .spec(requestSpecification())
                .body(jsonStringTask)
                .when().log().all().post("/api/task/create")
                .then().log().all()
                .extract().asString();

        Long createdTaskId = Long.parseLong(taskResponse);

        given()
                .param("title", "Test title")
                .param("description", "Test description")
                .param("status", "COMPLETED")
                .param("priority", "MEDIUM")
                .param("userId", createdUserId)
                .when()
                .put("/api/task/update/{id}", createdTaskId)
                .then().log().all()
                .spec(SIMPLE_OK());

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/task/delete/{id}", createdTaskId)
                .then().log().all();

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createdUserId)
                .then().log().all();

    }

    @DisplayName("Delete task")
    @Test
    void test5() throws JsonProcessingException {
        UserDTO userDTO = new UserDTO("Test name UPD", "test pass", User.ROLE.ADMIN);
        String jsonStringUser = objectMapper.writeValueAsString(userDTO);

        String userResponse = given()
                .spec(requestSpecification())
                .body(jsonStringUser)
                .when().log().all()
                .post("/api/user/create")
                .then().log().all()
                .extract().asString();

        Long createdUserId = Long.parseLong(userResponse);

        TaskDTO taskDTO = new TaskDTO(Task.Priority.HIGH, Task.Status.IN_IDLE, "Rest test description", "Rest test title", createdUserId);
        String jsonString = objectMapper.writeValueAsString(taskDTO);

        String response = given()
                .spec(requestSpecification())
                .body(jsonString)
                .when().log().all()
                .post("/api/task/create")
                .then().log().all()
                .spec(SIMPLE_OK())
                .extract().asString();

        Integer createdTaskId = Integer.parseInt(response);

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/task/delete/{id}", createdTaskId)
                .then().log().all()
                .spec(SIMPLE_OK()); // Проверка успешного удаления

        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/user/delete/{id}", createdUserId)
                .then().log().all();

    }

}
