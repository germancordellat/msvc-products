package com.prueba.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;

import com.prueba.libs.mscv.commons.entities.Product;



public interface ProductRepository extends CrudRepository<Product, Long> {

}
