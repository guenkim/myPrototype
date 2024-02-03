package com.guen.program.todo.repository.jpa;

import com.guen.program.todo.model.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TodoJpa extends JpaRepository<Todo,Long> , TodoJpaExtend , JpaSpecificationExecutor<Todo> , QuerydslPredicateExecutor<Todo> {
    @EntityGraph(attributePaths = {"files"})
    Page<Todo> findBySubjectContaining(final String subject, final Pageable pageable);
}
