package com.guen.program.todo.model.entity;


import com.guen.program.todo.model.enumclass.Complete;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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

    public Todo(String subject, String body, Complete completed) {
        this.subject = subject;
        this.body = body;
        this.completed = completed;
    }

    public Todo(Long id, String subject, String body, Complete completed) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.completed = completed;
    }
}
