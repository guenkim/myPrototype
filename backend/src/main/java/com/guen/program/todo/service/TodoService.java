package com.guen.program.todo.service;

import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import com.guen.program.todo.model.request.TodoReq;
import com.guen.program.todo.model.response.TodoRes;
import com.guen.program.todo.repository.jpa.pure.TodoPureRepository;
import com.guen.program.todo.repository.jpa.querydsl.TodoJpa;
import com.guen.program.todo.repository.jpa.springdata.TodoSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    //순수 jpa repo
    private final TodoPureRepository pureRepo;
    //spring data jpa repo
    private final TodoSpringDataRepository springDataRepo;
    //spring data jpa + queryDsl repo
    private final TodoJpa queryDslRepo;

    /******************************************
     *  mapper가 필요하다..
     *  modelmapper or mapstruct 이용하여
     *  dto <> entity 간 매핑
     ******************************************/


    public Page<Todo> search(final String subject, final Pageable pageable){
        return queryDslRepo.search(subject,pageable);
    }

    public Optional<Todo> findById(String todoId){
        return queryDslRepo.findById(Long.valueOf(todoId));
    }

    @Transactional
    public void save(TodoReq todoReq){
        Todo newTodo = new Todo(todoReq.getSubject(),todoReq.getBody(),todoReq.getCompleted());
        pureRepo.save(newTodo);
    }

    @Transactional
    public void updateById(String todoId,TodoReq todoReq){
        Todo newTodo = new Todo(todoReq.getSubject(),todoReq.getBody(),todoReq.getCompleted());
        newTodo.setId(Long.valueOf(todoId));
        pureRepo.updateById(newTodo);
    }

    @Transactional
    public void remove(String todoId){
        Optional<Todo> todo = queryDslRepo.findById(Long.valueOf(todoId));
        pureRepo.remove(todo.get());
    }

    @Transactional
    public void updateCompleteById(String todoId , boolean reqComplete){
        Complete complete = reqComplete ? Complete.TRUE : Complete.FALSE;
        pureRepo.updateCompleteById(Long.parseLong(todoId),complete);
    }

}
