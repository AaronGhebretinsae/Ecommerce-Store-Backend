package com.educative.ecommerce.repository;

import com.educative.ecommerce.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
