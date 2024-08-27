package com.pivotree.appzone.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * BaseEntity is a JPA MappedSuperclass that provides basic fields such as version, createdAt, and updatedAt.
 * The version field is used for optimistic locking, the createdAt field is used to track when the entity was created,
 * and the updatedAt field is used to track when the entity was last updated.
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    /**
     * The version field is used for optimistic locking. It is a Long type that is automatically incremented by one
     * every time the entity is updated. This field can be used to detect concurrent modification conflicts during an
     * update operation.
     */
    @Version
    protected Long version;

    /**
     * The createdAt field is used to track when the entity was created. It is a Date type that is automatically set
     * by the database when the entity is created.
     */
    @CreationTimestamp
    protected Date createdAt;

    /**
     * The updatedAt field is used to track when the entity was last updated. It is a Date type that is automatically set
     * by the database when the entity is updated.
     */
    @UpdateTimestamp
    protected Date updatedAt;

}