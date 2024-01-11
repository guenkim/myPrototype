package com.guen.program.todo.repository.jpa.querydsl;

import com.guen.program.todo.model.entity.QTodo;
import com.guen.program.todo.model.entity.Todo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

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

    public List<Todo> findAll(){
        return queryFactory.selectFrom(todo)
                .fetch();
    }

    public Optional<Todo> findById(Long id){
        return Optional.ofNullable(queryFactory.selectFrom(todo)
                .where(todo.id.eq(id))
                .fetchOne());
    }

    
}
