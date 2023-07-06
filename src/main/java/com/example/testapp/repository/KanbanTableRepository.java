package com.example.testapp.repository;

import com.example.testapp.model.KanbanTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanbanTableRepository extends JpaRepository<KanbanTable, Long> {
}
