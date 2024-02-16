package com.guen.program.shop.service;

import com.guen.program.shop.exception.ItemNotFindException;
import com.guen.program.shop.exception.NotMemberException;
import com.guen.program.shop.exception.OrderNotFindException;
import com.guen.program.shop.model.dto.request.ReqOrderSearchDto;
import com.guen.program.shop.model.dto.response.OrderDto;
import com.guen.program.shop.model.entity.Crew;
import com.guen.program.shop.model.entity.Delivery;
import com.guen.program.shop.model.entity.Order;
import com.guen.program.shop.model.entity.OrderItem;
import com.guen.program.shop.model.entity.inheritance.Item;
import com.guen.program.shop.model.enumclass.DeliveryStatus;
import com.guen.program.shop.model.enumclass.OrderStatus;
import com.guen.program.shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional(readOnly = true)
public class OrderService {


    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CrewRepo crewRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private DeliveryRepo deliveryRepo;


    @Transactional
    public void createOrder(final Long crewId, final Long itemId, final int count) throws Exception {
        Crew crew = crewRepo.findById(crewId).orElseThrow(() -> new Exception("그러한 회원이 없어!"));
        Item item = itemRepo.findById(itemId).orElseThrow(() -> new ItemNotFindException("그러한 아이템이 없어!"));
        item.reduceStock(count);
        itemRepo.save(item);

        Delivery delivery = Delivery.builder()
                .address(crew.getAddress())
                .status(DeliveryStatus.READY)
                .build();
        deliveryRepo.save(delivery);

        Order order = Order.createOrder(LocalDate.now(), OrderStatus.ORDER, crew, delivery);
        orderRepo.save(order);

        OrderItem orderItem = OrderItem.createOrderItem(item.getPrice(), count, order, item);
        orderItemRepo.save(orderItem);

    }

    //검색
    public OrderDto findOrders(final ReqOrderSearchDto orderSearch) throws NotMemberException {
        List<OrderDto.Order> retOrder = new ArrayList<>();
        Crew crew1 = crewRepo.findById(orderSearch.getCrewId()).orElseThrow(() -> new NotMemberException("그러한 회원이 없어!"));
        OrderDto.Crew crew = new OrderDto.Crew(crew1.getId(), crew1.getName());
        AtomicReference<BigInteger> totalOrderPrice = new AtomicReference<>(BigInteger.ZERO);
        orderRepo.findAllByCriteria(orderSearch).forEach(
                order -> {
                    OrderDto.Order.Delivery delivery = new OrderDto.Order.Delivery(order.getDelivery().getAddress(), order.getDelivery().getStatus());
                    List<OrderItem> retOrderItem = orderItemRepo.findByOrderId(order.getId()).orElseThrow(()->new OrderNotFindException("그런 order 없어!"));
                    List<OrderDto.Order.OrderItem> orderItems = new ArrayList<>();
                    retOrderItem.forEach(orderItem -> {
                        OrderDto.Order.OrderItem orderItem1 = new OrderDto.Order.OrderItem(
                                orderItem.getId(),
                                new OrderDto.Order.OrderItem.Item(orderItem.getItem().getId(), orderItem.getItem().getName(), orderItem.getItem().getPrice(), orderItem.getItem().getStockQuanitty()),
                                orderItem.getOrderPrice(),
                                orderItem.getCount(),
                                orderItem.getTotalPrice()
                        );
                        orderItems.add(orderItem1);
                        totalOrderPrice.updateAndGet(currentValue -> currentValue.add(orderItem1.getTotalOrderPrice()));
                    });

                    OrderDto.Order orderResponse = new OrderDto.Order(order.getId(), crew, order.getOrderDate(), order.getStatus(), delivery, orderItems);
                    retOrder.add(orderResponse);
                }
        );
        OrderDto orderDto = new OrderDto(crew, retOrder, totalOrderPrice.get());
        return orderDto;
    }

    @Transactional
    public void cancel(final Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFindException("그런 주문 없어"));
        order.cancel();
        orderRepo.save(order);

        List<OrderItem> orderItems = orderItemRepo.findByOrderId(order.getId()).orElseThrow(() -> new OrderNotFindException("그런 주문 없어"));
        orderItems.forEach(orderItem -> {
            int cancelCnt = orderItem.getCount();
            Item item = orderItem.getItem();
            item.addStock(cancelCnt);
            itemRepo.save(item);
        });
    }
}
