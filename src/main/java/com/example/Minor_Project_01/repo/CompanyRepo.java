package com.example.Minor_Project_01.repo;

import com.example.Minor_Project_01.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

}
