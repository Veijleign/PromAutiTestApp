package com.example.testapp.http.columns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTableColumnRequest {
    private Long tableColumnId;

    private String tableColumnName;

    private Long tableId;

}
