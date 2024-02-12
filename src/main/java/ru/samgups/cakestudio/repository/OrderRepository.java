package ru.samgups.cakestudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samgups.cakestudio.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Add any additional custom query methods if needed
    List<Order> findByUserId(Long id);
}
