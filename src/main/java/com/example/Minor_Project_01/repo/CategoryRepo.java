package com.example.Minor_Project_01.repo;

import com.example.Minor_Project_01.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
