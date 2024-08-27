package com.pivotree.appzone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pivotree.appzone.enums.StatusType;
import com.pivotree.appzone.enums.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "InventoryQuantity")
public class InventoryQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventoryquantity_id")
    private Long inventoryQuantityId;
    @Column(name = "line_id")
    private Integer lineId;

//    @Column(name = "inventory_id")
//    private Long inventoryID;

//    @ManyToOne
//    @JoinColumn(name = "line_id")
//    @JsonBackReference
//    private Inventory lineId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Double quantity;

    @Column(name = "status_type")
    @Enumerated(EnumType.STRING)
    private StatusType statusType;


//    @Column(name = "inventoryposition_id")
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "inventory_id")
//    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    @JsonBackReference
    private Inventory inventory;

    public InventoryQuantity(Integer lineId, Type committed, StatusType active, Inventory inventory, Double quantity) {
        this.lineId = lineId;
        this.type = committed;
        this.statusType = active;
        this.inventory = inventory;
        this.quantity = quantity;
//      this.inventory = inventory;
    }

    public InventoryQuantity(Type onHand, StatusType active, Inventory inventory, Double quantity) {
        this.type = onHand;
        this.statusType = active;
        this.inventory = inventory;
        this.quantity = quantity;
//      this.inventory = inventory;
    }
}
