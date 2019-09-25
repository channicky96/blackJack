package blackjack;

import java.util.ArrayList;

interface Player{
/**
* getName: @return the current player's name
* players name
**/
    String getName();
    
/**
* newHand: this method should clear the previous hand ready for new cards and 
* return the old hand
**/
    Hand newHand();
    
/**
 * makeBet: This method determines what bet the player will make. It should be 
 * called prior to any cards being dealt. 
*/
    int makeBet();
    
/**
 * getBet: @return the bet for the current game. This must not exceed the 
 * players balance
 */    
    int getBet();

/**
 * getBalance: @return the players current total pot. 
 */
    int getBalance();
    
/**
 * hit: this method should determine whether the player wants to take a card. 
 * return true if a card is required, false otherwise. 
**/
    boolean hit();
    
/**
 * takeCard: If a card is requested by hit() it should be added to the players 
 * hand with this method
 * @param c 
 */    
    void takeCard(Card c);

 /**
 * settleBet: The value passed is positive if the player won, negative otherwise. 
 * @return true if the player has funds remaining, false otherwise. 
 */
    boolean settleBet(int p);
    
/**
 * 
 * getHandTotal: @return the hand total score. If the player has one or more 
 * aces, this should return the highest HARD total that is less than 21. 
 * So for example
 * ACE, THREE   should return 14.
 * ACE, THREE, TEN should return 14.
 * ACE, ACE, TWO, THREE should return 17.
 * ACE, ACE, TEN should return 12.
 */
    int getHandTotal();
    
/**
 * 
 * blackjack: @return true if the current hand is a black jack (ACE, TEN or 
 * PICTURE CARD)
 */    
    boolean blackjack();
    
/**
 * 
 * isBust: @return true if the current hand is bust
 */    
    boolean isBust();
    
/**
 * 
 * getHand: @return the current hand
 */    
    Hand getHand();
    
/**
 * viewDealerCard. This method allows the dealer to show the player their card. 
 * @param c Dealers first card
 */
    void viewDealerCard(Card c);

 /**
 * viewCards: This method allows the dealer to show all the cards that were 
 * played after a hand is finished. If the player is card counting, they will 
 * need this info
 */    
    void viewCards(ArrayList<Card> cards);
    
/**
 * newDeck. This method is called by the dealer to tell them the deck has been 
 * reshuffled
 */    
    void newDeck();
    
}