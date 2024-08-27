package com.pivotree.appzone.repository;

import com.pivotree.appzone.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT C FROM Category C WHERE id=:id")
    Category findByCategoryId(Long id);

    Category findByCategoryName(String name);

    @Query("SELECT C FROM Category C WHERE categoryName=:name")
    Category findCategoryByName(String name);
    @Query("SELECT c FROM Category c WHERE c.categoryName = :Category")
    Category findCategoryName(@Param("Category") String name);

}
