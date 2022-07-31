package com.foodorder.tacocloud.controller;

import com.foodorder.tacocloud.model.TacoOrder;
import com.foodorder.tacocloud.model.User;
import com.foodorder.tacocloud.repository.dataJpa.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
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
