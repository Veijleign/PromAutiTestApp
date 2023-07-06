package com.example.testapp.service.column;

import com.example.testapp.model.TableColumn;

import java.util.List;

public interface IKanbanTableColumnService {
    List<TableColumn> getAllColumns();
    TableColumn addColumn(TableColumn kanbanTableColumn);
    TableColumn getColumn(Long kanbanTableColumnId);
    void deleteKanbanTableColumn(Long kanbanTableColumnId);
    TableColumn updateKanbanTableColumn(TableColumn updateKanbanTableColumn);
}
