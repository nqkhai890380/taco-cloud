package com.example.demo.controller;

import com.example.demo.entity.TacoOrder;
import com.example.demo.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@RequestMapping("/order")
@Slf4j
@SessionAttributes("tacoOrder")
public class OrderController {

    private OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        if (!model.containsAttribute("tacoOrder")) {
            return "redirect:/design";
        }
        Object attribute = model.getAttribute("tacoOrder");
        if (!(attribute instanceof TacoOrder)) {
            return "redirect:/design";
        }
        TacoOrder tacoOrder = (TacoOrder) attribute;
        if (tacoOrder.getTacos().isEmpty()) {
            return "redirect:/design";
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus) {
        log.info("errors: {}", errors);
        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("Submit order: {}", tacoOrder);
        orderRepository.save(tacoOrder);
        sessionStatus.setComplete();

        return "redirect:/";
    }


}

