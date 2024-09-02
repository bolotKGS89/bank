package kg.test.bank.dtos;

import kg.test.bank.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskDto implements Serializable {
    public Long id;
    public String description;
    public Status status;
}
