package com.guen.program.todo.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.guen.common.file.model.entity.Files;
import com.guen.program.todo.model.enumclass.Complete;
import com.guen.program.todo.model.request.TodoReq;
import com.guen.program.todo.model.response.TodoRes;
import com.guen.program.todo.model.response.TodoSingleRes;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Todo {

    private static final long serialVersionUID = -563329217866858622L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="subject", nullable = false, length = 100)
    private String subject;

    @Column(name = "body", nullable = true)
    @Lob
    private String body;

    @Column(name = "completed", nullable = false,length = 5)
    @Enumerated(EnumType.STRING)
    private Complete completed;

    @OneToMany(mappedBy = "todo" ,cascade = CascadeType.PERSIST ,orphanRemoval = true ,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Files> files = new ArrayList<>();

    public Todo(String subject, String body, Complete completed) {
        this.subject = subject;
        this.body = body;
        this.completed = completed;
        this.files = files;
    }
    public Todo(String subject, String body, Complete completed,List<Files> files) {
        this.subject = subject;
        this.body = body;
        this.completed = completed;
        this.files = files;
    }

    public Todo(Long id, String subject, String body, Complete completed) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.completed = completed;
    }

    public void updateFiles(List<Files> files){
        this.files = files;
    }

    public void updateTodo(final TodoReq todoReq){
        this.subject = todoReq.getSubject();
        this.body = todoReq.getBody();
        this.completed = todoReq.getCompleted();
    }

    public void updateStatus(final Complete complete){
        this.completed = complete;
    }

    public TodoRes toTodoRes(){
        return TodoRes.builder()
                .id(this.id)
                .subject(this.subject)
                .body(this.body)
                .completed(this.completed ==Complete.FALSE ? Boolean.FALSE : Boolean.TRUE)
                .build();
    }


    @Builder
    public TodoSingleRes toTodoSingleRes(){
        return TodoSingleRes.builder()
                .id(this.id)
                .subject(this.subject)
                .body(this.body)
                .completed(this.completed==Complete.FALSE ? Boolean.FALSE : Boolean.TRUE)
                .files(this.files.stream().map(file->TodoSingleRes.FileInfo.builder().name(file.getFileName()).id(file.getId()).build()).collect(Collectors.toList())).build();
    }

}
