package com.guen.common.file.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guen.program.todo.model.entity.Todo;
import jakarta.persistence.*;

@Entity
@Table(name = "post_files")
public class File {
    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Todo todo;

    // Getters and setters
}