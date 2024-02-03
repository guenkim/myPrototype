package com.guen.program.todo.repository.jpa;

import com.guen.common.model.PageResponse;
import com.guen.program.todo.model.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TodoJpaExtend {

    PageResponse search(final String subject, final Pageable pageable);

}
