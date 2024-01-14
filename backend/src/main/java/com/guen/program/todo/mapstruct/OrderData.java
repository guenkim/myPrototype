package com.guen.program.todo.mapstruct;

import lombok.Getter;

import java.util.List;

public class OrderData {
    public Member member;
    public Order order;
    public Address address;

    public static class Member {
        public String id;
        public String password;
    }

    public static class Order {
        public List<Item> item;

        public static class Item {
            public String name;
            public String price;
            public String company;
        }
    }

    public static class Address {
        public String zipcode;
        public String address1;
        public String address2;
    }
}