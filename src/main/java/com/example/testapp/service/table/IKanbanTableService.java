package com.example.testapp.service.table;

import com.example.testapp.model.KanbanTable;

import java.util.List;

public interface IKanbanTableService {
    List<KanbanTable> getAllTables();
    KanbanTable addKanbanTable(KanbanTable kanbanTable);
    KanbanTable getKanbanTable(Long kanbanTableId);
    KanbanTable updateKanbanTable(KanbanTable updateKanbanTable);
    void deleteKanbanTable(Long kanbanTableId);
}
