package com.educative.ecommerce.repository;

import com.educative.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Categoryrepository extends JpaRepository<Category, Integer> {

    Category findByCategoryName(String categoryName);

}