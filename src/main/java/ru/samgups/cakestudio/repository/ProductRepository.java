package ru.samgups.cakestudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.samgups.cakestudio.entity.Product;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductCategory_Id(Long categoryId);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findAll();

    Product findByName(String name);

    List<Product> findByIdIn(@Param("ids") List<Long> ids);

}
