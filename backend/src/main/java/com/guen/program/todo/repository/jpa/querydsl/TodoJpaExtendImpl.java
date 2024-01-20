package com.guen.program.todo.repository.jpa.querydsl;

import com.guen.program.todo.model.entity.QTodo;
import com.guen.program.todo.model.entity.Todo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.guen.program.todo.model.entity.QTodo.todo;

public class TodoJpaExtendImpl extends QuerydslRepositorySupport implements TodoJpaExtend {

    private JPAQueryFactory queryFactory;

    @Override
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
        queryFactory = new JPAQueryFactory(entityManager);
    }


    public TodoJpaExtendImpl() {
        super(Todo.class);
    }

    public Page<Todo> search(final String subject, final Pageable pageable){
        //todo 조회
        List<Todo> todos = queryFactory.selectFrom(todo)
                .where(subjectLike(subject))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //count 쿼리
        JPAQuery<Todo> countQuery = queryFactory.selectFrom(todo).where(subjectLike(subject));
        return PageableExecutionUtils.getPage(todos,pageable,countQuery::fetchCount);
    }

    private BooleanExpression subjectLike(final String subject){
        return StringUtils.isEmpty(subject) ? null : todo.subject.contains(subject);
    }


    public Optional<Todo> findById(Long id){
        return Optional.ofNullable(queryFactory.selectFrom(todo)
                .where(todo.id.eq(id))
                .fetchOne());
    }

    
}
