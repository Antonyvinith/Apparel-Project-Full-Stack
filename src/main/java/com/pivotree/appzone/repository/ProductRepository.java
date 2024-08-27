package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.productReference =:name")
    Product findByProductReference(String name);
    @Query(value = "SELECT * FROM product", nativeQuery = true)
    List<Object[]> findAllCustom();

    @Query("SELECT p FROM Product p WHERE LOWER(p.productReference) LIKE %:Product%")
    List<Product> findByNameContainingIgnoreCase(@Param("Product") String name);

    //get product by category name
    @Query("SELECT p FROM Product p JOIN Category c WHERE LOWER(c.categoryName) = LOWER(:categoryName)")
    List<Product> findProductsByCategoryName(String categoryName);


}
