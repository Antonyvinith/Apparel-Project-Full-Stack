package com.pivotree.appzone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity class for Category
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Category extends BaseEntity {

    /**
     * The primary key of the Category table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The description of the Category
     */
    @Column(name = "description")
    private String description;

    /**
     * The unique name of the Category
     */
    @Column(name = "category_name", unique = true)
    private String categoryName;

    /**
     * The products of the Category
     */
    @JsonBackReference
    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

    /**
     * The default constructor of the Category class
     *
     * @param categoryName the unique name of the Category
     * @param description  the description of the Category
     */
    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public Category(String categoryName){
        this.categoryName=categoryName;
    }

}