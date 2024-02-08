package com.guen.program.jpashop.controller;

import com.guen.program.jpashop.domain.Crew;
import com.guen.program.jpashop.domain.Order;
import com.guen.program.jpashop.domain.item.Item;
import com.guen.program.jpashop.repository.OrderSearch;
import com.guen.program.jpashop.service.ItemService;
import com.guen.program.jpashop.service.CrewService;
import com.guen.program.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CrewService crewService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<Crew> crews = crewService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", crews);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
