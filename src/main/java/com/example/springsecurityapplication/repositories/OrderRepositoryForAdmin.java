package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositoryForAdmin extends JpaRepository<Order, Integer> {
    List<Order> findAll();
    @Query(value = "select * from orders where (number LIKE %?1)", nativeQuery = true)
    List<Order> findByForLastStringsIgnorCase(String strings);

//    @Query(value = "update orders set status = ?1 where id = ?2", nativeQuery = true)
//    String updateStatus(String status, int id);
}