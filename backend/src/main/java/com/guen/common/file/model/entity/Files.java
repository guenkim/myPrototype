package com.guen.common.file.model.entity;

import com.guen.program.todo.model.entity.Todo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Files {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="filename", nullable = false)
    private String fileName;


    @ManyToOne(fetch = FetchType.LAZY)
    private Todo todo;

    @Builder
    public Files(String fileName,Todo todo) {
        this.fileName = fileName;
        this.todo = todo;
    }
}