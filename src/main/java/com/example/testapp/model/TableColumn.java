package com.example.testapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "table_column")
public class TableColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_column_id", columnDefinition = "serial")
    private Long tableColumnId;

    private String tableColumnName;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "table_id")
    @JsonIgnore
    KanbanTable kanbanTable;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "task_id")
    @Builder.Default
    private List<TableTask> tableTasks = new ArrayList<>();
}
