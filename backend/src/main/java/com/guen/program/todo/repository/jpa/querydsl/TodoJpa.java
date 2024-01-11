package com.guen.program.todo.repository.jpa.querydsl;

import com.guen.program.todo.model.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoJpa extends JpaRepository<Todo,Long> ,TodoJpaExtend {
    List<Todo> findAll();


    Optional<Todo> findById(Long id);
}
