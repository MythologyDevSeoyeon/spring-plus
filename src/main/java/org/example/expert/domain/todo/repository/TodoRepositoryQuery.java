package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepositoryQuery {

    Optional<Todo> findByIdWithUser(long todoId);

    Page<TodoSearchResponse> searchTodos(Pageable pageable, String keyword, String nickname, LocalDateTime startDate, LocalDateTime endDate);
}
