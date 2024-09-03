package kg.test.bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import kg.test.bank.dtos.TaskDto;
import kg.test.bank.exceptions.InvalidTaskDataException;
import kg.test.bank.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Get All tasks",
            description = "Retrieve all tasks available on database")
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasks() {
        return new ResponseEntity<>(taskService.getTasks(), HttpStatus.OK);
    }

    @Operation(summary = "Get tasks by id",
            description = "Get one task by task id available on database")
    @GetMapping(value = "/task/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTask(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a task",
            description = "Create a task and save it to database")
    @PostMapping(value = "/task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        if (taskDto.getDescription() == null || taskDto.getDescription().isEmpty() ||
            taskDto.getStatus() == null) {
            throw new InvalidTaskDataException("Task field cannot be empty");
        }
        return new ResponseEntity<>(taskService.createTask(taskDto), HttpStatus.OK);
    }

    @Operation(summary = "Change task by id",
            description = "Retrieve one task by Id, update it and save to database")
    @PutMapping(value = "/task/{id}")
    public ResponseEntity<TaskDto> changeTask(@RequestBody TaskDto taskDto, @PathVariable Long id)  {
        if (taskDto.getDescription() == null || taskDto.getDescription().isEmpty() ||
                taskDto.getStatus() == null) {
            throw new InvalidTaskDataException("Task field cannot be empty");
        }
        return new ResponseEntity<>(taskService.changeTask(taskDto, id), HttpStatus.OK);
    }

    @Operation(summary = "Delete task by id",
            description = "Retrieve one task by Id and delete it from database")
    @DeleteMapping(value = "/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(String.format("Successfully deleted %d", id), HttpStatus.OK);
    }

}
