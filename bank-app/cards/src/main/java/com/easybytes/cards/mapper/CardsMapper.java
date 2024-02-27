package com.easybytes.cards.mapper;

import com.easybytes.cards.dto.CardsDto;
import com.easybytes.cards.entity.Cards;

public class CardsMapper {

    public static Cards mapToCards(CardsDto cardsDto, Cards cards) {
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        return cards;
    }

    public static CardsDto mapToCardsDto(CardsDto cardsDto, Cards cards) {
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        return cardsDto;
    }
}
