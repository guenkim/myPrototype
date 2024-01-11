package com.guen.program.todo.repository.jpa.querydsl;

import com.guen.program.todo.model.entity.Todo;

import java.util.List;
import java.util.Optional;

import static com.guen.program.todo.model.entity.QTodo.todo;

public interface TodoJpaExtend {

    List<Todo> findAll();


    Optional<Todo> findById(Long id);
}
