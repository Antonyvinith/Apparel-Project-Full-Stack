package com.pivotree.appzone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for storing image URLs.
 *
 * @author <NAME>
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image_url")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "image_type")
public class ImageUrls extends BaseEntity {

    /**
     * The primary key of the image.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    private Long id;

    /**
     * The URL of the image.
     */
    @Column(name = "url", columnDefinition = "text")
    private String url;

    /**
     * Constructs a new ImageUrls object with the given URL.
     *
     * @param url The URL of the image.
     */
    public ImageUrls(String url) {
        this.url = url;
    }

}