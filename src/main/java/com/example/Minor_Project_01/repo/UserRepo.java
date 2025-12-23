package com.example.Minor_Project_01.repo;

import com.example.Minor_Project_01.entity.Role;
import com.example.Minor_Project_01.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByRole(Role role);
    User findByEmail(String email);
}
