package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.OrderRepositoryForAdmin;
import com.example.springsecurityapplication.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepositoryForAdmin orderRepositoryForAdmin;

    public OrderService(OrderRepositoryForAdmin orderRepositoryForAdmin) {
        this.orderRepositoryForAdmin = orderRepositoryForAdmin;
    }

    @Transactional
    public void updateStatusOrder(int id, Order order){
        order.setId(id);
        orderRepositoryForAdmin.save(order);
    }

//    @Transactional
//    public void updateStatusOrder(String status, int id, Order order){
//        order.setId(id);
//        order.setStatus(Status.valueOf(status));
//        orderRepositoryForAdmin.updateStatus(status, id);
//    }

    public Order getOrderId(int id){
        Optional<Order> optionalOrder = orderRepositoryForAdmin.findById(id);
        return optionalOrder.orElse(null);
    }
}
