package com.guen.program.todo.service;

import com.guen.common.file.model.entity.Files;
import com.guen.common.file.repository.FileJpa;
import com.guen.common.file.service.FileStorageService;
import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import com.guen.program.todo.model.request.TodoReq;
import com.guen.program.todo.repository.jpa.pure.TodoPureRepository;
import com.guen.program.todo.repository.jpa.querydsl.TodoJpa;
import com.guen.program.todo.repository.jpa.springdata.TodoSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


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
    private final FileStorageService fileStorageService;
    private final FileJpa fileJpa;

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
    public Todo save(TodoReq todoReq, List<MultipartFile> files){
        Todo newTodo = new Todo(todoReq.getSubject(),todoReq.getBody(),todoReq.getCompleted());
        pureRepo.save(newTodo);

        if(files!=null) {
            files.stream().map(file -> fileStorageService.storeFile(file))
                    .forEach(filename -> fileJpa.save(Files.builder().fileName(filename).todo(newTodo).build()));
        }

        return newTodo;
    }

    @Transactional
    public void updateById(String todoId,TodoReq todoReq, List<MultipartFile> files){
        Todo newTodo = new Todo(todoReq.getSubject(),todoReq.getBody(),todoReq.getCompleted());
        newTodo.setId(Long.valueOf(todoId));
        pureRepo.updateById(newTodo);

        if(files!=null) {
            files.stream().map(file -> fileStorageService.storeFile(file))
                    .forEach(filename -> fileJpa.save(Files.builder().fileName(filename).todo(newTodo).build()));
        }

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
