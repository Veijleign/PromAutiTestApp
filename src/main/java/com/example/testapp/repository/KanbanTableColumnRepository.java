package com.example.testapp.repository;

import com.example.testapp.model.TableColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanbanTableColumnRepository extends JpaRepository<TableColumn, Long> {
}
