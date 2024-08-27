package com.pivotree.appzone.controller;

import com.pivotree.appzone.entity.Card;
import com.pivotree.appzone.entity.User;
import com.pivotree.appzone.intemplate.CardInput;
import com.pivotree.appzone.outtemplate.CardOutput;
import com.pivotree.appzone.repository.CardRepository;
import com.pivotree.appzone.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @PostMapping
    public ResponseEntity<String> addCard(@RequestBody CardInput cardInput) {
        // Extract username from the request body
        String username = cardInput.getUsername();

        // Check if username is provided
        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required in the request body");
        }

        // Call the service method to add the credit card
        cardService.addCreditCard(cardInput, username);

        return ResponseEntity.status(HttpStatus.CREATED).body("Credit card added successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateCard(@RequestBody CardInput cardInput) {
        String username = cardInput.getUsername();
        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required in the request body");
        }

        Long cardId = cardInput.getId();
        if (cardId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credit card ID is required in the request body");
        }

        // Check if the provided username matches the username associated with the credit card
        Optional<Card> optionalCreditCard = cardRepository.findById(cardId);
        if (optionalCreditCard.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit card with ID " + cardId + " not found");
        }

        Card card = optionalCreditCard.get();
        if (!card.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this credit card");
        }

        cardService.updateCreditCard(cardInput, cardId, username);
        return ResponseEntity.status(HttpStatus.OK).body("Credit card updated successfully");
    }

    @DeleteMapping("/{username}/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable String username, @PathVariable Long cardId) {


        if (cardId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card ID is required in the request body");
        }
        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required in the request body");
        }

        cardService.deleteCreditCard(cardId, username);
        return ResponseEntity.status(HttpStatus.OK).body("Credit card deleted successfully");
    }
    @GetMapping("/get/{username}")
    public ResponseEntity<List<CardOutput>> getAllCardsByUsername(@PathVariable String username) {
        List<CardOutput> creditCards = cardService.getAllCreditCardsByUsername(username);
        return ResponseEntity.ok(creditCards);
    }



}
