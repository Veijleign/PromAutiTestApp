package com.example.testapp.model;

import com.example.testapp.model.enums.TaskStateEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "table_task")
public class TableTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_task_id", columnDefinition = "serial")
    private Long tableTaskId;

    private String tableTaskName;

    @Enumerated(EnumType.STRING)
    private TaskStateEnum taskState;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "task_id")
    @JsonIgnore
    TableColumn tableColumn;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user_task_id")
    @JsonIgnore
    User user;
}