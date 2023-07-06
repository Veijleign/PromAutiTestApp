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
@Table(name = "kanban_tables")
public class KanbanTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kanban_table_id", columnDefinition = "serial")
    private Long kanbanTableId;

    private String kanbanTableName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "table_id")
    @Builder.Default
    private List<TableColumn> tableColumn = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "table_user_id")
    @JsonIgnore
    User user;
}
