package com.pivotree.appzone.repository;


import com.pivotree.appzone.entity.Category;
import com.pivotree.appzone.entity.Product;
import com.pivotree.appzone.intemplate.PriceAndCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Log4j2
@Repository
public class PriceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Page<Product> findProductsByPriceAndCategory(PriceAndCategory priceAndCategory, Pageable pageable) {
        double minPrice = priceAndCategory.getPriceInput().getMinPrice();
        double maxPrice = priceAndCategory.getPriceInput().getMaxPrice();
        String categoryName = priceAndCategory.getCategorySearch().getCategoryName();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Join<Product, Category> catJoin = productRoot.join("categories");

        Predicate pricePredicate = criteriaBuilder.conjunction();
        pricePredicate = criteriaBuilder.and(pricePredicate, criteriaBuilder.between(productRoot.get("price"), minPrice, maxPrice));
        pricePredicate = criteriaBuilder.and(pricePredicate, criteriaBuilder.equal(catJoin.get("categoryName"), categoryName));

        criteriaQuery.select(productRoot).where(pricePredicate);

        List<Product> resultList = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Count total records without pagination
        int totalRecords = resultList.size();

        log.info(totalRecords);

        return new PageImpl<>(resultList, pageable, totalRecords);
    }
}
