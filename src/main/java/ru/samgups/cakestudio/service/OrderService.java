package ru.samgups.cakestudio.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.samgups.cakestudio.dto.OrderDto;
import ru.samgups.cakestudio.dto.OrderItemDto;
import ru.samgups.cakestudio.entity.*;
import ru.samgups.cakestudio.repository.CategoryRepository;
import ru.samgups.cakestudio.repository.OrderRepository;
import ru.samgups.cakestudio.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<Order> getOrdersByUserId(Long userId){
        return  orderRepository.findByUserId(userId);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    public Order placeOrder(OrderDto orderDto) {
        Order order = new Order();
        // Set other properties of the Order entity from the orderDto data

        // Set user-related properties
        User user = userRepository.findById(orderDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        order.setUser(user);

        // Set address properties from orderDto.getAddress()
        order.setShippingAddress(orderDto.getShippingAddress());

        // Set payment-related properties
        Payment payment = new Payment();
        // Set payment properties from orderDto.getPayment()
        order.setPaymentMethod(orderDto.getPaymentMethod());

        // Create OrderItems
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem itemDto : orderDto.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(itemDto.getProduct());
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(itemDto.getProduct().getPrice());
            orderItem.setOrder(order);

            // Set properties of the orderItem from itemDto
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        // Calculate total price
        double totalPrice = orderItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
        order.setTotalPrice(totalPrice);

        // Save the order
        Order savedOrder = orderRepository.save(order);

        return savedOrder;
    }

}
