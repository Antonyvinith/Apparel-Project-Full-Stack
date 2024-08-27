package com.pivotree.appzone.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


/**
 * Entity class for products
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Product extends BaseEntity {

    /**
     * The primary key of the product
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * A unique reference code for the product
     */
    @Column(name = "product_reference", unique = true)
    private String productReference;

    /**
     * A description of the product
     */
    @Column(name = "description")
    private String description;

    /**
     * The price of the product
     */
    @Column(name = "price")
    private Long price;

    /**
     * The brand of the product
     */
    @Column(name = "brand")
    private String brand;

    /**
     * The manufacturing date of the product
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "manufacture_date")
    private Date manufactureDate;

    /**
     * The expiry date of the product
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "expiry_date")
    private Date expiryDate;

    /**
     * A list of image URLs associated with the product
     */
    @OneToMany(targetEntity = ImageUrls.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_urls")
    private List<ImageUrls> imageUrls;

    /**
     * The dimensions of the product
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_dim")
    private Dimension dimensions;

    /**
     * The category of the product
     */
//    @ManyToOne
//    @JoinColumn(name = "fk_cat")
//    private Category category;

    //shravya
    @JsonManagedReference
    @ManyToMany(targetEntity = Category.class, cascade = CascadeType.ALL)
        @JoinTable(
                name = "product_category",
                joinColumns = {@JoinColumn (name = "product_id",referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "category_id" ,referencedColumnName = "id")}
        )
        private List<Category> categories;

    /**
     * Constructs a new Product with the given properties
     *
     * @param productReference  A unique reference code for the product
     * @param description       A description of the product
     * @param price             The price of the product
     * @param brand             The brand of the product
     * @param manufactureDate   The manufacturing date of the product
     * @param expiryDate        The expiry date of the product
     * @param imageUrls         A list of image URLs associated with the product
     * @param dimensions        The dimensions of the product
     * @param categories         The category of the product
     */
//    public Product(String productReference, String description, Long price, String brand, Date manufactureDate,
//                   Date expiryDate, List<ImageUrls> imageUrls, Dimension dimensions,Category category) {
//
//        this.productReference = productReference;
//        this.description = description;
//        this.price = price;
//        this.brand = brand;
//        this.manufactureDate = manufactureDate;
//        this.expiryDate = expiryDate;
//        this.imageUrls = imageUrls;
//        this.dimensions = dimensions;
//        this.category = category;
//
//    }

    //shravya
     public Product(String productReference, String description, Long price, String brand, Date manufactureDate,
                       Date expiryDate, List<ImageUrls> imageUrls, Dimension dimensions, List<Category> categories) {
            this.productReference = productReference;
            this.description = description;
            this.price = price;
            this.brand = brand;
            this.manufactureDate = manufactureDate;
            this.expiryDate = expiryDate;
            this.imageUrls = imageUrls;
            this.dimensions = dimensions;
            this.categories = categories;
        }

    /**
     * Constructs a new Product with the given properties
     *
     * @param productReference  A unique reference code for the product
     * @param description       A description of the product
     * @param price             The price of the product
     * @param brand             The brand of the product
     * @param manufactureDate   The manufacturing date of the product
     * @param expiryDate        The expiry date of the product
     * @param imageUrls         A list of image URLs associated with the product
     * @param dimensions        The dimensions of the product
     */
    public Product(String productReference, String description, Long price, String brand, Date manufactureDate,
                   Date expiryDate, List<ImageUrls> imageUrls, Dimension dimensions) {
        this.productReference = productReference;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
        this.imageUrls = imageUrls;
        this.dimensions = dimensions;
    }

}