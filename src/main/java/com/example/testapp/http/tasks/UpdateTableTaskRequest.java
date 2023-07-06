package com.example.testapp.http.tasks;

import com.example.testapp.model.enums.TaskStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTableTaskRequest {
    private Long tableTaskId;
    private String tableTaskName;
    private TaskStateEnum tableTaskState;
    private Long tableColumnId;
}
