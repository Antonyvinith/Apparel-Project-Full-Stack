package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CategoryManager {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;

    public void save(Category category) {

        entityManager.persist(category);
    }

    public Category findByCategoryName(String name) {

        return categoryRepository.findCategoryByName(name);
    }
}
