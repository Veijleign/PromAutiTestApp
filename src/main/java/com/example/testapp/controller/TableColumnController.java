package com.example.testapp.controller;

import com.example.testapp.exceptions.MainError;
import com.example.testapp.exceptions.MainException;
import com.example.testapp.http.columns.AddTableColumnRequest;
import com.example.testapp.http.columns.UpdateTableColumnRequest;
import com.example.testapp.model.TableColumn;
import com.example.testapp.model.User;
import com.example.testapp.model.enums.Permission;
import com.example.testapp.service.column.KanbanTableColumnService;
import com.example.testapp.service.security.IAuthenticationFacade;
import com.example.testapp.service.table.KanbanTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/column")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'REGULAR')")
public class TableColumnController {

    private final KanbanTableColumnService kanbanTableColumnService;
    private final KanbanTableService kanbanTableService;
    private final IAuthenticationFacade facade;

    @Autowired
    public TableColumnController(
            KanbanTableColumnService kanbanTableColumnService,
            KanbanTableService kanbanTableService,
            IAuthenticationFacade facade
    ) {
        this.kanbanTableColumnService = kanbanTableColumnService;
        this.kanbanTableService = kanbanTableService;
        this.facade = facade;
    }

    @GetMapping("/get-table-columns")
    public ResponseEntity<List<TableColumn>> getAllColumns() {
        return ResponseEntity.ok(kanbanTableColumnService.getAllColumns());
    }

    @GetMapping("/get-table-column")
    public ResponseEntity<TableColumn> getTableColumn(
            @RequestParam Long tableColumnId
    ) {
        return ResponseEntity.ok(kanbanTableColumnService.getColumn(tableColumnId));
    }

    @PostMapping("/add-table-column")
    public ResponseEntity<TableColumn> addTableColumn(
            @RequestBody AddTableColumnRequest addTableColumn
    ) {
        User user = facade.getCurrentUser();

        TableColumn tableColumn = TableColumn.builder()
                .tableColumnName(addTableColumn.getTableColumnName())
                .kanbanTable(
                        kanbanTableService.getKanbanTable(addTableColumn.getTableId())
                )
                .build();

        // проверка на права доступа к функции
        if (!(user.hasPermissions(Permission.MANAGER_CREATE)) || // или есть права
                tableColumn.getKanbanTable().getUser().getId().equals(user.getId()) // или это сам создалеть
        ) throw new MainException(MainError.ACCESS_ERROR, "Not enough authorities");

        return ResponseEntity.ok(kanbanTableColumnService.addColumn(tableColumn));
    }

    @PostMapping("/delete-column-table")
    public ResponseEntity<Void> deleteTableColumn(
            @RequestParam Long tableColumnId
    ) {
        User user = facade.getCurrentUser();
        TableColumn tableColumn = kanbanTableColumnService.getColumn(tableColumnId);

        // проверка на права доступа к функции
        if (!(user.hasPermissions(Permission.MANAGER_DELETE)) || // или есть права
                tableColumn.getKanbanTable().getUser().getId().equals(user.getId()) // или это сам создалеть
        ) throw new MainException(MainError.ACCESS_ERROR, "Not enough authorities");

        kanbanTableColumnService.deleteKanbanTableColumn(tableColumnId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-table-column")
    public ResponseEntity<TableColumn> updateTableColumn(
            @RequestBody UpdateTableColumnRequest updateTableColumnRequest
    ) {
        User user = facade.getCurrentUser();
        TableColumn tableColumn = kanbanTableColumnService.getColumn(
                updateTableColumnRequest.getTableColumnId()
        );

        // проверка на права доступа к функции
        if (!(user.hasPermissions(Permission.MANAGER_UPDATE)) || // или есть права
                tableColumn.getKanbanTable().getUser().getId().equals(user.getId()) // или это сам создалеть
        ) throw new MainException(MainError.ACCESS_ERROR, "Not enough authorities");

        tableColumn.setTableColumnName(updateTableColumnRequest.getTableColumnName());

        return ResponseEntity.ok(kanbanTableColumnService.updateKanbanTableColumn(tableColumn));
    }
}
