package com.example.demo.controller;

import com.example.demo.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    private OrderRepository orderRepository;

    public HomeController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/")
    public String home() {
        log.info("Taco: {}", orderRepository.findByDeliveryAddressDeliveryZip("3006"));
        return "home";
    }

}
