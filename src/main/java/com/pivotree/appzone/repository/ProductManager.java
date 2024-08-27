package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Product;
import com.pivotree.appzone.intemplate.PriceAndCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2

@Component
@Transactional
public class ProductManager {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;

    public void save(Product product) {
        entityManager.persist(product);
    }

    public void addProduct(Product product) {
        entityManager.persist(product);
    }


    public List<Product> searchProductByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> findProductsByCategoryName(String categoryName) {
        return productRepository.findProductsByCategoryName(categoryName);
    }

    public Page<Product> searchProductsByPriceRange(PriceAndCategory apiInput, Pageable pageable) {
        return priceRepository.findProductsByPriceAndCategory(apiInput, pageable);
    }



}
