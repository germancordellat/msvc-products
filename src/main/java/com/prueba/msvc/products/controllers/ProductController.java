package com.prueba.msvc.products.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.prueba.libs.mscv.commons.entities.Product;
import com.prueba.msvc.products.services.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> list(@RequestHeader(name = "message-request", required = false) String message) {
        logger.info("Fetching product list ProductController");
        logger.info("Header message-request: {}", message);
        return ResponseEntity.ok(this.service.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> details(@PathVariable Long id) throws InterruptedException{
        logger.info("Fetching product details ProductController");
        if (id.equals(10L)) {
            //throw new IllegalStateException("Product not found");
        }
        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(3L);
        }
        return this.service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Product> create(@RequestBody Product product){ 
        logger.info("Creating product ProductController");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product product){
        logger.info("Updating product ProductController");
        Optional<Product> productOptional = this.service.findById(id);
        if (productOptional.isPresent()) {
            Product productDB = productOptional.get();
            productDB.setName(product.getName());
            productDB.setPrice(product.getPrice());
            productDB.setCreateAt(product.getCreateAt());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(productDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        logger.info("Deleting product ProductController");
        Optional<Product> productOptional = this.service.findById(id);
        if (productOptional.isPresent()) {
            this.service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
}
