package com.example.testapp.service.table;

import com.example.testapp.exceptions.MainError;
import com.example.testapp.exceptions.MainException;
import com.example.testapp.model.KanbanTable;
import com.example.testapp.repository.KanbanTableRepository;
import com.example.testapp.service.table.IKanbanTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class KanbanTableService implements IKanbanTableService {
    private final KanbanTableRepository kanbanTableRepository;

    @Autowired
    public KanbanTableService(
            KanbanTableRepository kanbanTableRepository
    ) {
        this.kanbanTableRepository = kanbanTableRepository;
    }
    @Override
    public List<KanbanTable> getAllTables() {
        return kanbanTableRepository.findAll();
    }
    @Override
    public KanbanTable addKanbanTable(KanbanTable kanbanTable) {
        return kanbanTableRepository.save(kanbanTable);
    }
    @Override
    public KanbanTable getKanbanTable(Long kanbanTableId) {
        if (!kanbanTableRepository.existsById(kanbanTableId)) throw new MainException(MainError.NOT_FOUND_ERROR, "No such table!");
        return kanbanTableRepository.getReferenceById(kanbanTableId);
    }

    @Override
    public KanbanTable updateKanbanTable(KanbanTable updateKanbanTable) {
        if (!kanbanTableRepository.existsById(updateKanbanTable.getKanbanTableId())) throw new MainException(MainError.NOT_FOUND_ERROR, "No such table!");

        return kanbanTableRepository.save(updateKanbanTable);
    }

    @Override
    public void deleteKanbanTable(Long kanbanTableId) {
        if (!kanbanTableRepository.existsById(kanbanTableId)) throw new MainException(MainError.NOT_FOUND_ERROR, "No such table!");
        KanbanTable kanbanTable = kanbanTableRepository.getReferenceById(kanbanTableId);

        kanbanTableRepository.delete(kanbanTable);
    }

}
