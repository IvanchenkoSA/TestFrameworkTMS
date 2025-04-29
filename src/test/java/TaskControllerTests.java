import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.isa.demo.dto.TaskDTO;
import ru.isa.demo.model.Task;
import ru.isa.demo.model.User;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static ru.isa.demo.specifications.RespSpec.SIMPLE_OK;
import static ru.isa.demo.specifications.RespSpec.requestSpecification;

public class TaskControllerTests {

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Get task")
    @Test
    void test() {
        given()
                .spec(requestSpecification())
                .when().log().all().get("/api/task/getById/6")
                .then().log().all()
                .spec(SIMPLE_OK());
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
        TaskDTO taskDTO = new TaskDTO(Task.Priority.HIGH, Task.Status.IN_IDLE, "Rest test description", "Rest test title", 16l);
        String jsonString = objectMapper.writeValueAsString(taskDTO);
        given()
                .spec(requestSpecification())
                .body(jsonString)
                .when().log().all().post("/api/task/create")
                .then().log().all()
                .spec(SIMPLE_OK());
    }


    @DisplayName("Update task")
    @Test
    void test4() throws JsonProcessingException {
        given()
                .param("title", "newtfromrestassured")
                .param("description", "newdfromrestassured")
                .param("status", "COMPLETED")
                .param("priority", "MEDIUM")
                .param("userId", 16)
                .when()
                .put("/api/task/update/6")
                .then().log().all()
                .spec(SIMPLE_OK());
    }

    @DisplayName("Delete task")
    @Test
    void test5() throws JsonProcessingException {
        // Создание объекта TaskDTO
        TaskDTO taskDTO = new TaskDTO(Task.Priority.HIGH, Task.Status.IN_IDLE, "Rest test description", "Rest test title", 16L);
        String jsonString = objectMapper.writeValueAsString(taskDTO);

        // Выполнение POST запроса для создания задачи
        String response = given()
                .spec(requestSpecification())
                .body(jsonString)
                .when().log().all()
                .post("/api/task/create")
                .then().log().all()
                .spec(SIMPLE_OK()) // Убедитесь, что задача успешно создана
                .extract().asString(); // Извлечение ответа как строки

        // Предположим, что ID задачи находится в ответе как текст
        Integer createdTaskId = Integer.parseInt(response); // Преобразование строки в Integer, если ID возвращается как текст

        // Выполнение DELETE запроса для удаления задачи
        given()
                .spec(requestSpecification())
                .when().log().all()
                .delete("/api/task/delete/{id}", createdTaskId)
                .then().log().all()
                .spec(SIMPLE_OK()); // Проверка успешного удаления
    }

}
