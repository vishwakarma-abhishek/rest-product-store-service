package com.abhi.store_service_spring.repository;

import com.abhi.store_service_spring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
