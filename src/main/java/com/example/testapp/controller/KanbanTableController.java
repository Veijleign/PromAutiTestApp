package com.example.testapp.controller;

import com.example.testapp.exceptions.MainError;
import com.example.testapp.exceptions.MainException;
import com.example.testapp.http.table.AddKanbanTableRequest;
import com.example.testapp.http.table.UpdateKanbanTableRequest;
import com.example.testapp.model.KanbanTable;
import com.example.testapp.model.User;
import com.example.testapp.model.enums.Permission;
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
@RequestMapping("/api/v1/table")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'REGULAR')")
public class KanbanTableController {

    private final KanbanTableService kanbanTableService;
    private final IAuthenticationFacade facade;

    @Autowired
    public KanbanTableController(
            KanbanTableService kanbanTableService,
            IAuthenticationFacade facade
    ) {
        this.kanbanTableService = kanbanTableService;
        this.facade = facade;
    }

    @GetMapping("/tables")
    public ResponseEntity<List<KanbanTable>> getAllColumns() {
        return ResponseEntity.ok(kanbanTableService.getAllTables());
    }

    @PostMapping("/add-table")
    public ResponseEntity<KanbanTable> addTable(
            @RequestBody AddKanbanTableRequest addKanbanTableRequest
    ) {
        KanbanTable kanbanTable = KanbanTable.builder()
                .kanbanTableName(addKanbanTableRequest.getKanbanTableName())
                //.user(userService.getUser(addKanbanTableRequest.getUserId())) // !!!!!!!
                .user(facade.getCurrentUser())
                .build();
        return ResponseEntity.ok(kanbanTableService.addKanbanTable(kanbanTable));
    }

    @GetMapping("/table")
    public ResponseEntity<KanbanTable> getTable(
            @RequestParam Long kanbanTableId
    ) {
        return ResponseEntity.ok(kanbanTableService.getKanbanTable(kanbanTableId));
    }

    @PostMapping("/delete-table")
    public ResponseEntity<Void> deleteTable(
            @RequestParam Long kanbanTableId
    ) {
        User user = facade.getCurrentUser(); // нужен для проеверки может ли человек удалить табоицу

        KanbanTable kanbanTable = kanbanTableService.getKanbanTable(kanbanTableId);

        // проверка на права доступа к функции
        if (!(user.hasPermissions(Permission.MANAGER_DELETE)) || // или есть права
                kanbanTable.getUser().getId().equals(user.getId()) // или это сам создалеть
        ) throw new MainException(MainError.ACCESS_ERROR, "Not enough authorities");

        kanbanTableService.deleteKanbanTable(kanbanTableId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-table")
    public ResponseEntity<KanbanTable> updateKanbanTable(
            @RequestBody UpdateKanbanTableRequest updateKanbanTableRequest
    ) {
        User user = facade.getCurrentUser();
        KanbanTable kanbanTable = kanbanTableService.getKanbanTable(
                updateKanbanTableRequest.getTableId()
        );
        kanbanTable.setKanbanTableName(updateKanbanTableRequest.getKanbanTableName());

        // проверка на права доступа к функции
        if (!(user.hasPermissions(Permission.MANAGER_UPDATE)) || // или есть права
                kanbanTable.getUser().getId().equals(user.getId()) // или это сам создалеть
        ) throw new MainException(MainError.ACCESS_ERROR, "Not enough authorities");

        return ResponseEntity.ok(kanbanTableService.updateKanbanTable(kanbanTable));
    }
}