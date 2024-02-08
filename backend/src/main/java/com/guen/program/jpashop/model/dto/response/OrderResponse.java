package com.guen.program.jpashop.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guen.program.jpashop.model.entity.*;
import com.guen.program.jpashop.model.entity.item.Item;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
public class OrderResponse {

    private Long id;

    private Crew crew;

    private LocalDateTime orderDate; //주문시간

    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    private Delivery delivery;

    private List<OrderItem> orderItems = new ArrayList<>();

    public OrderResponse(Long id, Crew crew, LocalDateTime orderDate, OrderStatus status, Delivery delivery,List<OrderItem> orderItems) {
        this.id = id;
        this.crew = crew;
        this.orderDate = orderDate;
        this.status = status;
        this.delivery = delivery;
        this.orderItems = orderItems;
    }

    @Getter
    public static class Crew{
        private String name;
        private Address address;

        public Crew(String name, Address address) {
            this.name = name;
            this.address = address;
        }
    }

    @Getter
    public static class Delivery{
        private Long id;
        private Address address;
        private DeliveryStatus status; //READY, COMP

        public Delivery(Long id, Address address, DeliveryStatus status) {
            this.id = id;
            this.address = address;
            this.status = status;
        }
    }

    @Getter
    public static class OrderItem{

        private Long id;
        private Item item;
        private int orderPrice; //주문 가격
        private int count; //주문 수량

        public OrderItem(Long id, Item item, int orderPrice, int count) {
            this.id = id;
            this.item = item;
            this.orderPrice = orderPrice;
            this.count = count;
        }

        @Getter
        public static class  Item{
            private Long id;
            private String name;
            private int price;
            private int stockQuantity;

            public Item(Long id, String name, int price, int stockQuantity) {
                this.id = id;
                this.name = name;
                this.price = price;
                this.stockQuantity = stockQuantity;
            }
        }
    }

}
