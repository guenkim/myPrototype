package com.guen.program.todo.repository.jpa.pure;

import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TodoPureRepository {
    @PersistenceContext
    EntityManager em;

    public List<Todo> findAll(){
        return em.createQuery("select t from Todo t",Todo.class)
                .getResultList();
    }

    public Optional<Todo> findById(Long id){
        return Optional.ofNullable(em.find(Todo.class,id));
    }

    public void save(Todo reqTodo){
        em.persist(reqTodo);
    }

    public void updateById(Todo reqTodo){
        Todo todo = em.find(Todo.class, reqTodo.getId());
        todo.setSubject(reqTodo.getSubject());
        todo.setBody(reqTodo.getBody());
        todo.setCompleted(reqTodo.getCompleted());
    }

    public void remove(Todo reqTodo){
        em.remove(reqTodo);
    }

    public void updateCompleteById(long id , Complete reqComplete){
        Todo todo = em.find(Todo.class, id);
        todo.setCompleted(reqComplete);
    }
}
