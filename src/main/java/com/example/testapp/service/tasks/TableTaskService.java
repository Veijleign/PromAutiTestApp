package com.example.testapp.service.tasks;

import com.example.testapp.exceptions.MainError;
import com.example.testapp.exceptions.MainException;
import com.example.testapp.model.TableTask;
import com.example.testapp.repository.TableTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TableTaskService implements ITableTaskService {

    private final TableTaskRepository tableTaskRepository;

    @Autowired
    public TableTaskService(
            TableTaskRepository tableTaskRepository
    ) {
        this.tableTaskRepository = tableTaskRepository;
    }

    @Override
    public List<TableTask> getAllTasks() {
        return tableTaskRepository.findAll();
    }

    @Override
    public TableTask addTableTask(TableTask tableTask) {
        return tableTaskRepository.save(tableTask);
    }

    @Override
    public TableTask getTableTask(Long tableTaskId) {
        if (!tableTaskRepository.existsById(tableTaskId))
            throw new MainException(MainError.NOT_FOUND_ERROR, "No such task!");
        return tableTaskRepository.getReferenceById(tableTaskId);
    }

    @Override
    public void deleteTableTask(Long tableTaskId) {
        if (!tableTaskRepository.existsById(tableTaskId))
            throw new MainException(MainError.NOT_FOUND_ERROR, "No such task!");

        TableTask tableTask = tableTaskRepository.getReferenceById(tableTaskId);
        tableTaskRepository.delete(tableTask);
    }

    @Override
    public TableTask updateTableTask(TableTask updateTableTask) {
        if (!tableTaskRepository.existsById(updateTableTask.getTableTaskId()))
            throw new MainException(MainError.NOT_FOUND_ERROR, "No such task!");

        return tableTaskRepository.save(updateTableTask);
    }
}