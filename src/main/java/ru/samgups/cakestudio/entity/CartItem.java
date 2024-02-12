package ru.samgups.cakestudio.entity;

import ru.samgups.cakestudio.entity.enums.Stuffing;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "cart_item")
public class
CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Enumerated(EnumType.ORDINAL)
    private Stuffing stuffing;

    private LocalDateTime createdAt;


    public CartItem() {
        this.createdAt = LocalDateTime.now();
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
    }

    public CartItem(Product product, int quantity,Stuffing stuffing) {
        this.product = product;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
        this.stuffing=stuffing;
    }



    public Stuffing getStuffing() {
        return stuffing;
    }

    public void setStuffing(Stuffing stuffing) {
        this.stuffing = stuffing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CartItem{" +"cartItem.id="+getId()+
                "product=" + product.getName() +
                ", quantity=" + quantity +
                '}';
    }

}
