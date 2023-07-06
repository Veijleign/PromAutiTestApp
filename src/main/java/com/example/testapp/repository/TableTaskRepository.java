package com.example.testapp.repository;

import com.example.testapp.model.TableTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableTaskRepository extends JpaRepository<TableTask, Long> {
}
