package com.guen.program.jpashop.controller;

import com.guen.jwt.security.UserAuthorize;
import com.guen.program.jpashop.model.entity.Crew;
import com.guen.program.jpashop.model.entity.Order;
import com.guen.program.jpashop.model.entity.item.Item;
import com.guen.program.jpashop.repository.OrderSearch;
import com.guen.program.jpashop.service.ItemService;
import com.guen.program.jpashop.service.CrewService;
import com.guen.program.jpashop.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Order API")
@Slf4j
@RestController
@RequestMapping("/api")
@UserAuthorize
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CrewService crewService;
    private final ItemService itemService;

    @PostMapping("/order")
    public ResponseEntity order(@RequestParam("memberId") final Long memberId,
                                @RequestParam("itemId") final Long itemId,
                                @RequestParam("count") final int count) {

        orderService.order(memberId, itemId, count);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders")
    public ResponseEntity orderList(@ParameterObject final OrderSearch orderSearch) {
        List<Order> orders = orderService.findOrders(orderSearch);
        return ResponseEntity.ok().body(orders);
    }

    @PostMapping("/orders/{orderId}/cancel")
    public ResponseEntity cancelOrder(@PathVariable("orderId") final Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
