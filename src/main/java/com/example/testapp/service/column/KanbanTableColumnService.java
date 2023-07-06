package com.example.testapp.service.column;

import com.example.testapp.exceptions.MainError;
import com.example.testapp.exceptions.MainException;
import com.example.testapp.model.TableColumn;
import com.example.testapp.repository.KanbanTableColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class KanbanTableColumnService implements IKanbanTableColumnService {

    private final KanbanTableColumnRepository kanbanTableColumnRepository;

    @Autowired
    public KanbanTableColumnService(
            KanbanTableColumnRepository kanbanTableColumnRepository
    ) {
        this.kanbanTableColumnRepository = kanbanTableColumnRepository;
    }
    @Override
    public List<TableColumn> getAllColumns() {
        return kanbanTableColumnRepository.findAll();
    }

    @Override
    public TableColumn addColumn(TableColumn kanbanTableColumn) {
        return kanbanTableColumnRepository.save(kanbanTableColumn);
    }

    @Override
    public TableColumn getColumn(Long kanbanTableColumnId) {
        if (!kanbanTableColumnRepository.existsById(kanbanTableColumnId)) throw new MainException(MainError.NOT_FOUND_ERROR, "No such TableColumn!");

        return kanbanTableColumnRepository.getReferenceById(kanbanTableColumnId);
    }

    @Override
    public void deleteKanbanTableColumn(Long kanbanTableColumnId) {
        if (!kanbanTableColumnRepository.existsById(kanbanTableColumnId)) throw new MainException(MainError.NOT_FOUND_ERROR, "No such TableColumn!");

        TableColumn tableColumn = kanbanTableColumnRepository
                .getReferenceById(kanbanTableColumnId);
        kanbanTableColumnRepository.delete(tableColumn);
    }
    @Override
    public TableColumn updateKanbanTableColumn(TableColumn updateKanbanTableColumn) {
        if (!kanbanTableColumnRepository.existsById(updateKanbanTableColumn.getTableColumnId())) throw new MainException(MainError.NOT_FOUND_ERROR, "No such TableColumn!");

        return kanbanTableColumnRepository.save(updateKanbanTableColumn);
    }
}