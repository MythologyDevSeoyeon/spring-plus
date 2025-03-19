package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;
import static org.example.expert.domain.comment.entity.QComment.comment;

@RequiredArgsConstructor
public class TodoRepositoryQueryImpl implements TodoRepositoryQuery {

    private final JPAQueryFactory jqf;

    @Override
    public Optional<Todo> findByIdWithUser(long todoId) {
        Todo result = jqf.selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(Pageable pageable, String keyword, String nickname, LocalDateTime startDate, LocalDateTime endDate) {
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(keyword)) {
            builder.and(todo.title.contains(keyword));
        }
        if (StringUtils.hasText(nickname)) {
            builder.and(todo.user.nickname.contains(nickname));
        }
        builder.and(todo.createdAt.between(startDate, endDate));

        List<TodoSearchResponse> todoList = jqf
                .select(
                        Projections.fields(TodoSearchResponse.class,
                                todo.title.as("title"),
                                manager.id.countDistinct().as("managerCount"),
                                comment.id.countDistinct().as("commentCount")
                        )
                )
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(builder)
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jqf
                .select(todo.count())
                .from(todo)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(todoList, pageable, total != null ? total : 0);
    }
}

