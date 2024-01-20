package com.guen.program.todo.repository.jpa.querydsl;

import com.guen.program.todo.model.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface TodoJpa extends JpaRepository<Todo,Long> ,TodoJpaExtend, QuerydslPredicateExecutor<Todo>, JpaSpecificationExecutor<Todo> {
    Page<Todo> search(final String subject, final Pageable pageable);

    Optional<Todo> findById(Long id);
}
