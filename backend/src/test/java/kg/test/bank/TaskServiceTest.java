package kg.test.bank;

import kg.test.bank.dtos.TaskDto;
import kg.test.bank.exceptions.TaskNotFoundException;
import kg.test.bank.mapper.TaskMapper;
import kg.test.bank.model.Task;
import kg.test.bank.repository.TaskRepository;
import kg.test.bank.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    private Task task1;
    private Task task2;

    private TaskDto taskDto1;
    private TaskDto taskDto2;

    private static final Long ONE = 1L;
    private static final Long THREE = 3L;
    private static final String TASK_1 = "task 1";

    @BeforeEach
    public void setup(){
        //Given
        task1 = new Task(1L, "task 1", Status.IN_PROGRESS);
        task2 = new Task(2L, "task 2", Status.DONE);

        taskDto1 = new TaskDto(1L, "task 1", Status.IN_PROGRESS);
        taskDto2 = new TaskDto(2L, "task 2", Status.DONE);
    }

    @Test
    void testGetById() {
        //When
        given(taskRepository.findById(ONE)).willReturn(Optional.of(task1));
        given(taskMapper.toTaskDto(task1)).willReturn(taskDto1);

        //Then
        assertThat(taskService.getTask(ONE)).isNotNull();
        assertThat(taskService.getTask(ONE).getStatus()).isEqualTo(Status.IN_PROGRESS);
    };

    @Test
    void testGetById_TaskNotFound() {
        //When
        given(taskRepository.findById(ONE)).willReturn(Optional.of(task1));
        given(taskMapper.toTaskDto(task1)).willReturn(taskDto1);

        //Then
        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(THREE));
    };

    @Test
    void testGetTasks() {
        //When
        given(taskRepository.findAll()).willReturn(List.of(task1, task2));
        given(taskMapper.toLstTaskDto(List.of(task1, task2))).willReturn(
                List.of(taskDto1, taskDto2)
        );

        //Then
        assertThat(taskService.getTasks()).isNotNull();
        assertThat(taskService.getTasks().size()).isEqualTo(2);
    }


    @Test
    void testPostTask() {
        //When
        given(taskRepository.save(task1)).willReturn(task1);
        given(taskMapper.toTask(taskDto1)).willReturn(task1);
        given(taskMapper.toTaskDto(task1)).willReturn(taskDto1);

        //Then
        assertThat(taskService.createTask(taskDto1)).isNotNull();
        assertThat(taskService.createTask(taskDto1).getDescription()).isEqualTo(TASK_1);

    }

    @Test
    void testDeleteTask() {
        //When
        given(taskRepository.findById(ONE)).willReturn(Optional.of(task1));
        taskService.deleteTask(ONE);

        //Then
        verify(taskRepository).delete(task1);
    }

    @Test
    public void testDeleteTask_TaskNotFound() {
        //When
        when(taskRepository.findById(ONE)).thenReturn(Optional.of(task1));

        //Then
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(THREE));
    }

    @Test
    void testChangeTask() {
        //When
        when(taskRepository.findById(ONE)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(task1);
        when(taskMapper.toTaskDto(task1)).thenReturn(taskDto1);

        TaskDto updatedTaskDto = taskService.changeTask(taskDto1, ONE);

        //Then
        assertThat(taskDto1.getId()).isEqualTo(updatedTaskDto.getId());
        assertThat(taskDto1.getDescription()).isEqualTo(updatedTaskDto.getDescription());
        assertThat(taskDto1.getStatus()).isEqualTo(updatedTaskDto.getStatus());
    }

    @Test
    public void testChangeTask_TaskNotFoundException() {
        //When
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        //Then
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.changeTask(taskDto1, ONE);
        });
    }
}
