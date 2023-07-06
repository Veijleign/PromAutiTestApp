package com.example.testapp.http.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateKanbanTableRequest {
    private Long tableId;

    private String kanbanTableName;
}
