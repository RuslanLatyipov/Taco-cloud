package com.foodorder.tacocloud.controller;

import com.foodorder.tacocloud.service.OrderAdminService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

private final OrderAdminService orderAdminService;

    public AdminController(OrderAdminService orderAdminService) {
        this.orderAdminService = orderAdminService;
    }
@PostMapping("/deleteOrders")
@PostAuthorize("hasRole('ADMIN')||returnObject.user.username == authentication.name")
    public String deleteAllOrders(){
        orderAdminService.deleteAllOrders();
        return "redirect:/admin";
    }
}
