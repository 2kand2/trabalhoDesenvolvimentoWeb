package com.example.auth.controllers;

import com.example.auth.domain.product.Product;
import com.example.auth.domain.product.ProductRequestDTO;
import com.example.auth.domain.product.ProductSearchNameRequestDTO;
import com.example.auth.domain.product.ProductUpdateQtdDTO;
import com.example.auth.repositories.ProductRepository;
import com.example.auth.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository repository;

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody @Valid ProductRequestDTO body){
        productService.createProduct(body);
        return new ResponseEntity<>("Produto criado com sucesso", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Optional<Product> getTaskById(@PathVariable String id) {
        return productService.getProductById(id);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Product>> getProductByName(@RequestBody ProductSearchNameRequestDTO name) {
        List<Product> products = productService.getProductByName(name);
        System.out.println(products);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody ProductRequestDTO body){
        productService.updateProduct(id, body);
        return new ResponseEntity<>("produto atualizado com sucesso", HttpStatus.OK);
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<?> updateQtdProduct(@PathVariable String id, @RequestBody ProductUpdateQtdDTO qtd){
        productService.updateProductQtd(id, qtd);
        return new ResponseEntity<>("quantidade atualizada com sucesso", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductByID(@PathVariable String id){
        if (productService.existsById(id)) {
            productService.deleteProductById(id);
            return new ResponseEntity<>("Produto removido com sucesso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
