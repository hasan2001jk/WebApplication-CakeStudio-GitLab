package ru.samgups.cakestudio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.samgups.cakestudio.entity.CartItem;

import java.util.List;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    List<CartItem> findByProductId(Long productId);
    List<CartItem> findByProductProductCategoryName(String categoryName);
    List<CartItem> findByQuantityGreaterThan(int quantity);
    // Add custom query methods if needed
}