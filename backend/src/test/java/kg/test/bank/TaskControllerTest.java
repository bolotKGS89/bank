package kg.test.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.test.bank.controller.TaskController;
import kg.test.bank.dtos.TaskDto;
import kg.test.bank.exceptions.InvalidTaskDataException;
import kg.test.bank.model.Task;
import kg.test.bank.repository.TaskRepository;
import kg.test.bank.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
@ExtendWith(SpringExtension.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskServiceImpl taskService;

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task1;
    private Task task2;

    private TaskDto taskDto1;
    private TaskDto taskDto2;

    @BeforeEach
    public void setup() {
        task1 = new Task(1L, "task 1", Status.DONE);
        task2 = new Task(2L, "task 2", Status.DONE);

        taskDto1 = new TaskDto(1L, "task 1", null);
        taskDto2 = new TaskDto(2L, "task 2", Status.DONE);

        this.mockMvc = MockMvcBuilders.standaloneSetup(new TaskController(taskService)).build();
    }

    @Test
    public void testRoutePost() throws Exception {

        mockMvc.perform(post("/api/task/")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(objectMapper.writeValueAsString(task1))
                )
                .andExpect(status().isOk());
    }
    
}
