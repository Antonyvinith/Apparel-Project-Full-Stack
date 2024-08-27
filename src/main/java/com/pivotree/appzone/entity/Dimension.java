package com.pivotree.appzone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class for dimensions
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dimension")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Dimension extends BaseEntity {

    /**
     * The unique id of the dimension
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The length of the dimension
     */
    @Column(name = "length")
    private Long length;

    /**
     * The width of the dimension
     */
    @Column(name = "width")
    private Long width;

    /**
     * The height of the dimension
     */
    @Column(name = "height")
    private Long height;

    /**
     * The volume of the dimension
     */
    @Column(name = "volume")
    private Long volume;

    /**
     * Constructor for a 3-dimensional dimension
     *
     * @param length The length of the dimension
     * @param width  The width of the dimension
     * @param height The height of the dimension
     */
    public Dimension(Long length, Long width, Long height) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.volume = length * width * height;
    }

}