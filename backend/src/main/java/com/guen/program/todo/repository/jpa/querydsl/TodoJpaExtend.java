package com.guen.program.todo.repository.jpa.querydsl;

import com.guen.program.todo.model.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.guen.program.todo.model.entity.QTodo.todo;

public interface TodoJpaExtend {

    Page<Todo> search(final String subject, final Pageable pageable);


    Optional<Todo> findById(Long id);
}
