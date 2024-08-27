package com.pivotree.appzone.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "card_number", unique = true)
    private Long cardNumber;

    @Column(name = "cvv")
    private Integer cvv;

    @Column(name = "cardholder_name")
    private String cardholderName;

    @Column(name = "expiry_date")
    @JsonProperty("yyyy-mm")
    private Date expiryDate;


    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;


}
