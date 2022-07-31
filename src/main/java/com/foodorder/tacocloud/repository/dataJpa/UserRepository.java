package com.foodorder.tacocloud.repository.dataJpa;

import com.foodorder.tacocloud.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}