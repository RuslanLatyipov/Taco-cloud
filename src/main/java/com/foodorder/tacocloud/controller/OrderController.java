package com.foodorder.tacocloud.controller;

import com.foodorder.tacocloud.component.OrderProps;
import com.foodorder.tacocloud.model.TacoOrder;
import com.foodorder.tacocloud.model.User;
import com.foodorder.tacocloud.repository.dataJpa.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
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

    @PatchMapping(path = "/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TacoOrder patchOrder(@PathVariable("orderId") Long orderId, @RequestBody TacoOrder tacoOrderNew){
        TacoOrder tacoOrder = orderRepository.findById(orderId).get();
        if (tacoOrderNew.getDeliveryName() != null) {
            tacoOrder.setDeliveryName(tacoOrderNew.getDeliveryName());
        }
        if (tacoOrderNew.getDeliveryStreet() != null) {
            tacoOrder.setDeliveryStreet(tacoOrderNew.getDeliveryStreet());
        }
        if (tacoOrderNew.getDeliveryCity() != null) {
            tacoOrder.setDeliveryCity(tacoOrderNew.getDeliveryCity());
        }
        if (tacoOrderNew.getDeliveryState() != null) {
            tacoOrder.setDeliveryState(tacoOrderNew.getDeliveryState());
        }
        if (tacoOrderNew.getDeliveryZip() != null) {
            tacoOrder.setDeliveryZip(tacoOrderNew.getDeliveryZip());
        }
        if (tacoOrderNew.getCcNumber() != null) {
            tacoOrder.setCcNumber(tacoOrderNew.getCcNumber());
        }
        if (tacoOrderNew.getCcExpiration() != null) {
            tacoOrder.setCcExpiration(tacoOrderNew.getCcExpiration());
        }
        if (tacoOrderNew.getCcCVV() != null) {
            tacoOrder.setCcCVV(tacoOrderNew.getCcCVV());
        }
        return orderRepository.save(tacoOrder);

    }

    @DeleteMapping(path = "/orderId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId){
        orderRepository.deleteById(orderId);
    }
}
