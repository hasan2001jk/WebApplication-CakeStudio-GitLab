package ru.samgups.cakestudio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.samgups.cakestudio.entity.ShoppingCart;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
    List<ShoppingCart> findByUserId(Long userId);
    // Add custom query methods if needed
}