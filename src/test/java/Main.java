import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        TaskControllerTests taskControllerTests = new TaskControllerTests();
        UserControllerTests userControllerTests = new UserControllerTests();
        taskControllerTests.test();
        taskControllerTests.test2();
        taskControllerTests.test3();
        taskControllerTests.test4();
        taskControllerTests.test5();

        userControllerTests.test();
        userControllerTests.test2();
        userControllerTests.test3();
        userControllerTests.test4();
        userControllerTests.test5();
    }
}
