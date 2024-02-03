package com.guen.program.todo.repository.jpa;

import com.guen.common.file.model.entity.QFiles;
import com.guen.common.model.PageResponse;
import com.guen.program.todo.model.entity.QTodo;
import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.response.TodoRes;
import com.guen.util.QueryDslUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.guen.program.todo.model.entity.QTodo.todo;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public class TodoJpaExtendImpl extends QuerydslRepositorySupport  implements TodoJpaExtend {

    private JPAQueryFactory jpaQueryFactory;

    public TodoJpaExtendImpl() {
        super(Todo.class);
    }

    @Override
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public PageResponse search(final String subject, final Pageable pageable) {

        List<OrderSpecifier> ORDERS = QueryDslUtil.getDBAllOrderSpecifiers(pageable);

        List<Todo> todos = jpaQueryFactory
                .selectFrom(todo)
                .leftJoin(todo.files, QFiles.files)
                .fetchJoin()
                .where(subjectLike(subject))
                .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = jpaQueryFactory
                .selectFrom(todo)
                .where(subjectLike(subject))
                .fetchCount();

        long total = totalCount;
        List<TodoRes> todoRes = todos.stream().map(todo -> todo.toTodoRes()).collect(Collectors.toList());
        long page = pageable.getPageNumber();
        long size = pageable.getPageSize();

        return PageResponse.builder()
                .page((int)page)
                .size((int)size)
                .totalCount((int)total)
                .content(todoRes)
                .build();
    }


    private BooleanExpression subjectLike(final String subject){
        return StringUtils.isEmpty(subject) ? null : todo.subject.contains(subject);
    }


}
