package com.crimsonlogic.freelancersystem.repository;

import com.crimsonlogic.freelancersystem.entity.ServiceCategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, String> {

}
