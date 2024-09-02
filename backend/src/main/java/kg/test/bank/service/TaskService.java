package kg.test.bank.service;

import kg.test.bank.dtos.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getTasks();
    TaskDto getTask(Long id);
    TaskDto createTask(TaskDto taskDto);
    TaskDto changeTask(TaskDto taskDto, Long Id);
    void deleteTask(Long Id);
}
