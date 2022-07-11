package com.example.assspring.repository;

import com.example.assspring.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepo extends CrudRepository<EmployeeEntity, Long> {
}
