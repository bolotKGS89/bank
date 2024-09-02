package kg.test.bank.mapper;

import kg.test.bank.Status;
import kg.test.bank.dtos.TaskDto;
import kg.test.bank.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "description", source = "description")
    Task toTask(TaskDto taskDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    @Mapping(target = "description", source = "description")
    TaskDto toTaskDto(Task task);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    @Mapping(target = "description", source = "description")
    List<TaskDto> toLstTaskDto(List<Task> tasks);

    @Named("stringToStatus")
    static Status stringToStatus(String status) {
        return Status.valueOf(status);
    }

    @Named("statusToString")
    static String statusToString(Status status) {
        return status.name();
    }
}
