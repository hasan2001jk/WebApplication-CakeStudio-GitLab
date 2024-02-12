package ru.samgups.cakestudio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.samgups.cakestudio.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findAll();
    User findByEmail(String email);
    User findByName(String name);

    boolean existsByEmail(String email);
}
