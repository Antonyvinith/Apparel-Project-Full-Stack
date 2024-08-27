package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.Card;
import com.pivotree.appzone.entity.User;
import com.pivotree.appzone.intemplate.CardInput;
import com.pivotree.appzone.outtemplate.CardOutput;
import com.pivotree.appzone.repository.CardRepository;
import com.pivotree.appzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    public void addCreditCard(CardInput cardInput, String username) {
        User user = getUserByUsername(username);
        Card card = createCreditCard(cardInput, user);
        cardRepository.save(card);
    }

    public void updateCreditCard(CardInput cardInput, Long cardId, String username) {
        Optional<Card> optionalCreditCard = cardRepository.findById(cardId);
        if (optionalCreditCard.isEmpty()) {
            throw new RuntimeException("Credit Card with ID " + cardId + " not found");
        }

        Card card = optionalCreditCard.get();
        updateCreditCardFields(card, cardInput);
        cardRepository.save(card);
    }

    private User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findUser(username);
        return optionalUser.orElseThrow(() -> new RuntimeException("User with username " + username + " not found."));
    }

    private Card createCreditCard(CardInput cardInput, User user) {
        Card card = new Card();
        setCreditCardFields(card, cardInput);
        card.setUser(user);
        return card;
    }

    private void updateCreditCardFields(Card card, CardInput cardInput) {
        card.setCardNumber(cardInput.getCardNumber());
        card.setCvv(cardInput.getCvv());
        card.setCardholderName(cardInput.getCardholderName());
        card.setExpiryDate(cardInput.getExpiryDate());
    }

    private void setCreditCardFields(Card card, CardInput cardInput) {
        card.setCardNumber(cardInput.getCardNumber());
        card.setCvv(cardInput.getCvv());
        card.setCardholderName(cardInput.getCardholderName());
        card.setExpiryDate(cardInput.getExpiryDate());
    }

    public void deleteCreditCard(Long cardId, String username) {
        // Check if the credit card exists
        Optional<Card> optionalCreditCard = cardRepository.findById(cardId);
        if (optionalCreditCard.isEmpty()) {
            throw new RuntimeException("Credit card with ID " + cardId + " not found");
        }

        Card card = optionalCreditCard.get();
        // Check if the credit card belongs to the specified user
        if (!card.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to delete this credit card");
        }

        // Delete the credit card
        cardRepository.deleteById(cardId);
    }

    public List<CardOutput> getAllCreditCardsByUsername(String username) {
        List<Card> cards = cardRepository.findAllByUserUsername(username);
        return cards.stream()
                .map(this::mapToOutput)
                .collect(Collectors.toList());
    }

    private CardOutput mapToOutput(Card card) {
        return new CardOutput(
                card.getCardId(),
                card.getCardNumber(),
                card.getCardholderName(),
                card.getExpiryDate(),
                card.getCvv()
        );
    }




}
