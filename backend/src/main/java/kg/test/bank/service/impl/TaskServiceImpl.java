package kg.test.bank.service.impl;

import kg.test.bank.dtos.TaskDto;
import kg.test.bank.exceptions.TaskNotFoundException;
import kg.test.bank.mapper.TaskMapper;
import kg.test.bank.model.Task;
import kg.test.bank.repository.TaskRepository;
import kg.test.bank.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDto> getTasks()  {
        List<Task> lstTasks = taskRepository.findAll();

        return taskMapper.toLstTaskDto(
                lstTasks
        );
    }

    @Override
    public TaskDto getTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        return taskMapper.toTaskDto(
                task
        );
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskRepository.save(
                taskMapper.toTask(taskDto)
        );
        return taskMapper.toTaskDto(
                task
        );
    }

    @Override
    public TaskDto changeTask(TaskDto taskDto, Long Id) {
        Task task = taskRepository.findById(Id).orElseThrow(() -> new TaskNotFoundException(Id));
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());

        return taskMapper.toTaskDto(
                taskRepository.save(task)
        );
    }

    @Override
    public void deleteTask(Long Id) {
        Task task = taskRepository.findById(Id).orElseThrow(() -> new TaskNotFoundException(Id));
        taskRepository.delete(task);
    }
}
