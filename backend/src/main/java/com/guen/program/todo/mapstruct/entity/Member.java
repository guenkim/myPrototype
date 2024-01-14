package com.guen.program.todo.mapstruct.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberId;
    private String password;

    // JPA에서는 기본 생성자가 필요하다.
    public Member() {
    }

    // 생성자, getter, setter 등이 필요할 수 있음

}