package com.guen.program.jpashop.service;


import com.guen.program.jpashop.model.dto.response.OrderResponse;
import com.guen.program.jpashop.model.entity.*;
import com.guen.program.jpashop.model.entity.item.Item;
import com.guen.program.jpashop.repository.ItemRepository;
import com.guen.program.jpashop.repository.CrewRepository;
import com.guen.program.jpashop.repository.OrderRepository;
import com.guen.program.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guen.program.jpashop.model.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CrewRepository crewRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Crew crew = crewRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(crew.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(crew, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    //검색
    public List<OrderResponse> findOrders(OrderSearch orderSearch) {
        List<OrderResponse> responses = new ArrayList<>();
        orderRepository.findAllByString(orderSearch).ifPresent(
                orders -> orders.forEach(order -> {
                    OrderResponse.Crew crew = new OrderResponse.Crew(order.getCrew().getName(), order.getCrew().getAddress());
                    OrderResponse.Delivery delivery = new OrderResponse.Delivery(order.getDelivery().getId(), order.getDelivery().getAddress(), order.getDelivery().getStatus());

                    List<OrderResponse.OrderItem> orderItems = order.getOrderItems().stream().map(orderItem ->
                            new OrderResponse.OrderItem(
                                    orderItem.getId(),
                                    new OrderResponse.OrderItem.Item(orderItem.getItem().getId(), orderItem.getItem().getName(), orderItem.getItem().getPrice(), orderItem.getItem().getStockQuantity()),
                                    orderItem.getOrderPrice(),
                                    orderItem.getCount())
                    ).collect(Collectors.toList());

                    OrderResponse  orderResponse = new OrderResponse(order.getId(),crew,order.getOrderDate(), order.getStatus(),delivery,orderItems);
                    responses.add(orderResponse);
                })
        );

        return responses;
    }
}
