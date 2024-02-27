package com.easybytes.cards.service.impl;

import com.easybytes.cards.constants.CardsConstants;
import com.easybytes.cards.dto.CardsDto;
import com.easybytes.cards.entity.Cards;
import com.easybytes.cards.exception.CardAlreadyExistsException;
import com.easybytes.cards.exception.ResourceNotFoundException;
import com.easybytes.cards.mapper.CardsMapper;
import com.easybytes.cards.repository.CardsRepository;
import com.easybytes.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;

    /**
     * Creating a new card
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobile number " + mobileNumber);
        }
        Cards cards = createNewCard(mobileNumber);
        cardsRepository.save(cards);
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Card details based on given mobile number
     */
    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "mobileNumber", mobileNumber)
        );
        CardsDto cardsDto = CardsMapper.mapToCardsDto(new CardsDto(), card);
        return cardsDto;
    }

    /**
     *
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "cardNumber", cardsDto.getCardNumber())
        );
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return true;
    }

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if delete of card details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(card.getId());
        return true;
    }
}
