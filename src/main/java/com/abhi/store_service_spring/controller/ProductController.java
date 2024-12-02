package com.abhi.store_service_spring.controller;

import com.abhi.store_service_spring.dto.ProductDto;
import com.abhi.store_service_spring.exception.BeanValidationException;
import com.abhi.store_service_spring.exception.ProductNotFoundException;
import com.abhi.store_service_spring.service.ProductService;
import com.abhi.store_service_spring.util.UriUtil;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final MessageSource messageSource;
    private final ProductService productService;

    public ProductController(MessageSource messageSource, ProductService productService) {
        this.messageSource = messageSource;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        List<ProductDto> products = productService.findAll();
        return ResponseEntity.ok(products);

    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeanValidationException(bindingResult);
        }

        var createdProductDto = this.productService.create(productDto);
        var uri = UriUtil.path("/{id}", createdProductDto.getId());

        return ResponseEntity.created(uri)
                .body(createdProductDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        return this.productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody @Valid ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BeanValidationException(bindingResult);
        }

        return this.productService.findById(id)
                .map(existingProductDto -> {
                    productDto
                            .setId(id)
                            .setCreatedAt(existingProductDto.getCreatedAt());
                    return ResponseEntity.ok(this.productService.update(productDto));
                }).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return this.productService.findById(id)
                .map(productDto -> {
                    this.productService.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
