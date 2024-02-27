package com.easybytes.cards.service;

import com.easybytes.cards.dto.CardsDto;

public interface ICardsService {
    /**
     *
     * @param mobileNumber - Customer mobile number
     * Create a new Card
     */
    void createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Card Details based on given mobileNumber
     */
    CardsDto fetchCard(String mobileNumber);

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of Card details is successfully or not
     */
    boolean updateCard(CardsDto cardsDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if delete of Card details is successful or not
     */
    boolean deleteAccount(String mobileNumber);
}
