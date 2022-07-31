package com.foodorder.tacocloud.controller;

import com.foodorder.tacocloud.component.OrderProps;
import com.foodorder.tacocloud.model.TacoOrder;
import com.foodorder.tacocloud.model.User;
import com.foodorder.tacocloud.repository.dataJpa.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@SessionAttributes("tacoOrder")
@RequestMapping("/orders")
@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderProps orderProps;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderProps orderProps) {
        this.orderRepository = orderRepository;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @GetMapping
    public String OrdersForUsers(@AuthenticationPrincipal User user, Model model){
        Pageable pageable = Pageable.ofSize(orderProps.getPageSize());
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";

    }

    @PostMapping
    public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        tacoOrder.setUser(user);
        orderRepository.save(tacoOrder);
        log.info("Order save: [Id ={}]", tacoOrder.getId());
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
