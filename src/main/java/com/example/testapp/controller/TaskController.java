package com.example.testapp.controller;

import com.example.testapp.exceptions.MainError;
import com.example.testapp.exceptions.MainException;
import com.example.testapp.http.tasks.AddTableTaskRequest;
import com.example.testapp.http.tasks.UpdateTableTaskRequest;
import com.example.testapp.model.TableTask;
import com.example.testapp.model.User;
import com.example.testapp.model.enums.Permission;
import com.example.testapp.service.column.KanbanTableColumnService;
import com.example.testapp.service.security.IAuthenticationFacade;
import com.example.testapp.service.tasks.TableTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/task")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'REGULAR')")
public class TaskController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TableTaskService tableTaskService;
    private final KanbanTableColumnService kanbanTableColumnService;
    private final IAuthenticationFacade facade;

    @Autowired
    public TaskController(
            TableTaskService tableTaskService,
            KanbanTableColumnService kanbanTableColumnService,
            IAuthenticationFacade facade
    ) {
        this.tableTaskService = tableTaskService;
        this.kanbanTableColumnService = kanbanTableColumnService;
        this.facade = facade;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TableTask>> getAllTableTasks() {
        return ResponseEntity.ok(tableTaskService.getAllTasks());
    }

    @GetMapping("/task")
    public ResponseEntity<TableTask> getTableTask(
            @RequestParam Long tableTaskId
    ) {
        return ResponseEntity.ok(tableTaskService.getTableTask(tableTaskId));
    }

    @PostMapping("/add-task")
    public ResponseEntity<TableTask> addTableTask(
            @RequestBody AddTableTaskRequest addTableTaskRequest
    ) {
        User user = facade.getCurrentUser();
        TableTask tableTask = TableTask.builder()
                .tableTaskName(addTableTaskRequest.getTableTaskName())
                .taskState(addTableTaskRequest.getTableTaskState())
                .user(user)
                .tableColumn(
                        kanbanTableColumnService.getColumn(addTableTaskRequest.getTableColumnId())
                )
                .build();
        return ResponseEntity.ok(tableTaskService.addTableTask(tableTask));
    }

    @PostMapping("/delete-task")
    public ResponseEntity<Void> deleteTableTask(
            @RequestParam Long tableTaskId
    ) {
        User user = facade.getCurrentUser();
        TableTask tableTask = tableTaskService.getTableTask(tableTaskId);

        if (!(user.hasPermissions(Permission.MANAGER_DELETE)) || // или есть права
                tableTask.getUser().getId().equals(user.getId()) // или это сам создалеть
        ) throw new MainException(MainError.ACCESS_ERROR, "Not enough authorities");

        tableTaskService.deleteTableTask(tableTaskId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-task")
    public ResponseEntity<TableTask> updateTableTask(
            @RequestBody UpdateTableTaskRequest updateTableTaskRequest
    ) {
        User user = facade.getCurrentUser();

        TableTask tableTask = tableTaskService.getTableTask(
                updateTableTaskRequest.getTableTaskId()
        );
        tableTask.setTableTaskId(updateTableTaskRequest.getTableTaskId());
        tableTask.setTaskState(updateTableTaskRequest.getTableTaskState());
        tableTask.setTableColumn(kanbanTableColumnService.getColumn(
                updateTableTaskRequest.getTableColumnId())
        );

        if (!(user.hasPermissions(Permission.MANAGER_UPDATE)) || // или есть права
                tableTask.getUser().getId().equals(user.getId()) // или это сам создалеть
        ) throw new MainException(MainError.ACCESS_ERROR, "Not enough authorities");

        return ResponseEntity.ok(tableTaskService.updateTableTask(tableTask));
    }

}
