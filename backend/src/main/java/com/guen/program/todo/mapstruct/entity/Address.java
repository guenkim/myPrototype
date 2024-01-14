package com.guen.program.todo.mapstruct.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zipcode;
    private String address1;
    private String address2;

    // JPA에서는 기본 생성자가 필요하다.
    public Address() {
    }

    // 생성자, getter, setter 등이 필요할 수 있음
}