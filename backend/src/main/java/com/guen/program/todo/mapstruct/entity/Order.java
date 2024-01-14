package com.guen.program.todo.mapstruct.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id") // Item 테이블에 외래키로 order_id를 사용
    private List<Item> items;

    // JPA에서는 기본 생성자가 필요하다.
    public Order() {
    }

    // 생성자, getter, setter 등이 필요할 수 있음
}