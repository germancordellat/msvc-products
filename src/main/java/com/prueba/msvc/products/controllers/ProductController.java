package com.prueba.msvc.products.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.prueba.msvc.products.entities.Product;
import com.prueba.msvc.products.services.ProductService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> list(){
        return ResponseEntity.ok(this.service.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> details(@PathVariable Long id) throws InterruptedException{
        if (id.equals(10L)) {
            throw new IllegalStateException("Product not found");
        }
        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(3L);
        }
        return this.service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
