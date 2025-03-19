package org.example.expert.domain.logging.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "log")
@NoArgsConstructor
public class ManagerLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "todo_id")
    private Long todoId;

    @Column(name = "manager_id")
    private Long managerId;

    @Column(nullable = false)
    private String message;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ManagerLog(String message, LocalDateTime createdAt, Long todoId, Long managerId) {
        this.message = message;
        this.createdAt = createdAt;
        this.todoId = todoId;
        this.managerId = managerId;
    }

    public static ManagerLog of(String message, Long todoId, Long managerId) {
        return new ManagerLog(message, LocalDateTime.now(), todoId, managerId);
    }
}
