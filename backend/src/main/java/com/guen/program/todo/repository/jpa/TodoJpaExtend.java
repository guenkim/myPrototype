package com.guen.program.todo.repository.jpa;

import com.guen.common.model.PageResponse;
import org.springframework.data.domain.Pageable;


public interface TodoJpaExtend {

    PageResponse search(final String subject, final Pageable pageable);
}
