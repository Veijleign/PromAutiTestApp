package com.example.testapp.service.tasks;

import com.example.testapp.model.TableTask;

import java.util.List;

public interface ITableTaskService {
    List<TableTask> getAllTasks();
    TableTask addTableTask(TableTask tableTask);
    TableTask getTableTask(Long tableTaskId);
    void deleteTableTask(Long tableTaskId);
    TableTask updateTableTask(TableTask updateTableTask);
}
