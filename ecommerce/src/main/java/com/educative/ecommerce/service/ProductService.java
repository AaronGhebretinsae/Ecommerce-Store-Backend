package com.educative.ecommerce.service;


import com.educative.ecommerce.dto.product.ProductDto;
import com.educative.ecommerce.model.Category;
import com.educative.ecommerce.model.Product;
import com.educative.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        return product;
    }

    public ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setImageURL(product.getImageURL());
        productDto.setName(product.getName());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());
        return productDto;
    }

    public void addProduct(ProductDto productDto, Category category){
        Product product = createProduct(productDto,category);
        productRepository.save(product);
    }



    // list of all the products
    public List<ProductDto> listProducts() {
        // first fetch all the products
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product product : products) {
            // for each product change it to DTO
            productDtos.add(new ProductDto(product));
        }
        return productDtos;
    }

    // update a product
    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        // throw an exception if product does not exist

        if (!optionalProduct.isPresent()) {
            throw new Exception("product not present");
        }

    }

    public Optional<Product> readProduct(Integer productId){

        return productRepository.findById(productId);

    }

    //To Do: add delete functionality
    /*public void  deleteProduct(ProductDto productDto, Category category) {

        Product product = createProduct(productDto, category);

        ProductRepository.delete(product);

    }*/




}
