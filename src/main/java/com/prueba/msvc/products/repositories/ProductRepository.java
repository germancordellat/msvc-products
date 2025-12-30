package com.prueba.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;

import com.prueba.msvc.products.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
