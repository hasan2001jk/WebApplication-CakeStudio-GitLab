package ru.samgups.cakestudio.dto;

import ru.samgups.cakestudio.entity.CartItem;

import java.util.List;

public class OrderDto {
    private Long id;
    private String customerName;
    private String customerEmail;
    private String shippingAddress;

    private String paymentMethod;

    private List<CartItem> orderItems;


    // Constructor, getters, and setters


    public OrderDto(Long id,String customerName, String customerEmail, String shippingAddress, List<CartItem> orderItems,String paymentMethod) {
        this.id=id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.paymentMethod=paymentMethod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<CartItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getUserId() {
        return id;
    }

    // Add any additional methods or fields as needed
}
