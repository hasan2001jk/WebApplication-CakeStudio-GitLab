package ru.samgups.cakestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.samgups.cakestudio.dto.OrderDto;
import ru.samgups.cakestudio.entity.Order;
import ru.samgups.cakestudio.service.OrderService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public String showOrderForm(Model model) {
        // Prepare data for the order form, if needed
        // For example, you can retrieve a list of available products and pass it to the model

        return "orderForm";
    }



    @GetMapping("/order/confirmation/{orderId}")
    public String showOrderConfirmation(@PathVariable Long orderId, Model model) {
        // Retrieve the order details using the orderId
        Order order = orderService.getOrderById(orderId);

        // Pass the order details to the model for displaying in the confirmation page
        model.addAttribute("order", order);

        return "orderConfirmation";
    }
}
