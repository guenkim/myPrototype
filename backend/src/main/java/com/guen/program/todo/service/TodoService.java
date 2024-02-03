package com.guen.program.todo.service;

import com.guen.common.file.model.entity.Files;
import com.guen.common.file.repository.FileJpa;
import com.guen.common.file.service.FileStorageService;
import com.guen.common.model.PageResponse;
import com.guen.program.todo.exception.TodoNotFindException;
import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import com.guen.program.todo.model.request.TodoReq;
import com.guen.program.todo.model.response.TodoRes;
import com.guen.program.todo.model.response.TodoSingleRes;
import com.guen.program.todo.repository.jpa.TodoJpa;
import com.guen.util.QueryDslUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final FileStorageService fileStorageService;
    private final FileJpa fileJpa;
    private final TodoJpa todoJpa;

    /******************************************
     *  mapper가 필요하다..
     *  modelmapper or mapstruct 이용하여
     *  dto <> entity 간 매핑
     ******************************************/


    public PageResponse search(final String subject, final Pageable pageable){
        // querydsl 적용
//        return todoJpa.search(subject,pageable);

        //spring data jpa 적용
        Pageable dbSortPage = QueryDslUtil.getDBSortPage(pageable);
        Page<Todo> todoPage = todoJpa.findBySubjectContaining(subject, dbSortPage);
        long totalCount = todoPage.getTotalElements(); //전체항목수
        int size = todoPage.getSize(); //페이지당 항목수
        List<Todo> todos = todoPage.getContent(); //데이터 목록
        List<TodoRes> todoRes = todos.stream().map(todo -> todo.toTodoRes()).collect(Collectors.toList());
        int page = todoPage.getNumber(); //현재 페이지
        return PageResponse.builder()
                .page(page)
                .size(size)
                .totalCount((int)totalCount)
                .content(todoRes).build();
    }

    public TodoSingleRes findById(final String todoId){
        Todo todo = todoJpa.findById(Long.valueOf(todoId))
                .orElseThrow(() -> new TodoNotFindException("요청하신 todo가 없습니다."));
        return todo.toTodoSingleRes();
    }

    @Transactional
    public Todo save(final TodoReq todoReq, final List<MultipartFile> files){
        Todo newTodo = new Todo(todoReq.getSubject(),todoReq.getBody(),todoReq.getCompleted());
        List<Files> filesList = new ArrayList<>();
        if(files!=null) {
            filesList = files.stream().map(file -> fileStorageService.storeFile(file))
            .map(filename -> Files.builder().fileName(filename).todo(newTodo).build())
            .collect(Collectors.toList());
        }
        newTodo.updateFiles(filesList);
        todoJpa.save(newTodo);

        return newTodo;
    }

    @Transactional
    public void updateById(final String todoId,final TodoReq todoReq,final List<MultipartFile> files){
        Todo todo = todoJpa.findById(Long.valueOf(todoId)).orElseThrow(
                () -> new TodoNotFindException("요청하신 todo가 없습니다.")
        );
        todo.updateTodo(todoReq);

        if(files!=null) {
            files.stream().map(file -> fileStorageService.storeFile(file))
                    .forEach(filename -> fileJpa.save(Files.builder().fileName(filename).todo(todo).build()));
        }
    }

    @Transactional
    public void remove(final String todoId){
        Todo todo = todoJpa.findById(Long.valueOf(todoId)).orElseThrow(
                () -> new TodoNotFindException("요청하신 todo가 없습니다.")
        );
        todoJpa.delete(todo);
    }

    @Transactional
    public void updateCompleteById(final String todoId ,final Complete complete){
        Todo todo = todoJpa.findById(Long.parseLong(todoId)).orElseThrow(
                () -> new TodoNotFindException("요청하신 todo가 없습니다.")
        );
        todo.updateStatus(complete);
    }

}
