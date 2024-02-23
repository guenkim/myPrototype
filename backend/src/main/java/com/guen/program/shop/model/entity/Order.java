package com.guen.program.shop.model.entity;

import com.guen.program.qdslstudy.model.entity.Employee;
import com.guen.program.shop.model.enumclass.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_id", referencedColumnName = "id", nullable = true)
    private Crew crew;

    @OneToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Size(max = 8)
    @Column(name = "ORDER_MODE", length = 8)
    private String orderMode;



    @Column(name = "ORDER_STATUS")
    private Short orderStatus;

    @Column(name = "ORDER_TOTAL", precision = 8, scale = 2)
    private BigDecimal orderTotal;

    @Column(name = "PROMOTION_ID")
    private Integer promotionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALES_REP_ID")
    private Employee salesRep;


    public void setSalesRep(Employee salesRep) {
        this.salesRep = salesRep;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderMode(String orderMode) {
        this.orderMode = orderMode;
    }

    @Builder
    public Order(LocalDate orderDate, OrderStatus status) {
        this.orderDate = orderDate;
        this.status = status;
    }

    public void addCrew(Crew crew) {
        this.crew = crew;
    }

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void cancel(){
        this.status = OrderStatus.CANCEL;
    }

    public static Order createOrder(final LocalDate orderDate, final OrderStatus status, final Crew crew, final Delivery delivery) {
        Order order = Order.builder()
                .orderDate(orderDate)
                .status(status)
                .build();
        order.addCrew(crew);
        order.addDelivery(delivery);
        return order;
    }
}
