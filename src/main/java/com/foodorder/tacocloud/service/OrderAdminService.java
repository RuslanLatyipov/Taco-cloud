package com.foodorder.tacocloud.service;

import com.foodorder.tacocloud.repository.dataJpa.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderAdminService {
    private final OrderRepository orderRepository;


    public OrderAdminService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void deleteAllOrders(){
        orderRepository.deleteAllBy();
    }
}
