package blackjack;

import java.io.Serializable;
import java.util.*;

public class BasicPlayer implements Player, Serializable{

    // Default player balance
    int playerBalance = 200;
    // Varaible to hold a players bet
    int playerBet = 10;
    // Player's hand
    Hand playerHand = new Hand();
    String name = "BasicPlayer";

    // A method to return the player's name
    @Override
    public String getName(){
        return this.name;
    }
    
    // Reset all variables in a hand and construct a new one
    @Override
    public Hand newHand() {
        // Clear all the variables and return the hand
        playerHand.aceCount = 0;
        Arrays.fill(playerHand.countOfRank, 0);
        playerHand.totalValue.clear();
        playerHand.totalValue.add(0);
        playerHand.hand.clear();
        return playerHand;
    }

    // Basic player's bet defaulted to 10
    @Override
    public int makeBet() {
        return this.playerBet;
    }

    // Return the player's bet
    @Override
    public int getBet() {
        return playerBet;
    }
    
    // Return the player's balance
    @Override
    public int getBalance() {
        return playerBalance;
    }

    // Return true if the player's lowest total hand value is less than 17
    @Override
    public boolean hit() {
        return getHandTotal()<=17;
    }

    // Add a single card to the player's hand
    @Override
    public void takeCard(Card c) {
        playerHand.addSingleCard(c);
    }

    // Update players balance after a game
    // return true if the player has funds remaining
    @Override
    public boolean settleBet(int p) {
//        System.out.println("settle bet: "+p);
        playerBalance += p;
        return playerBalance > 0;
    }

    // Iterate through all total value, find the highest hand total
    // that doesn't exceed 21
    @Override
    public int getHandTotal() {
        int maxSoft = 0;
        for (int value : playerHand.totalValue) {
            if (value <= 21) {
                maxSoft = value;
            }
        }
        if(playerHand.totalValue.get(0)>21){
            return playerHand.totalValue.get(0);
        }
//        System.out.println("maxSoft: "+maxSoft);
        return maxSoft;
    }

    // Check if the hand is a black jack, if so,
    // update blackJack variable and return true
    @Override
    public boolean blackjack() {
        if (Card.isBlackJack(playerHand.hand.get(0), playerHand.hand.get(1)) == true) {
            return playerHand.hand.size() < 2;
        }
        return false;
    }

    // Check if the hand is a black jack, if so,
    // update blackJack variable and return true
    @Override
    public boolean isBust() {
        return playerHand.isOver(21);
    }

    // Return the player hand
    @Override
    public Hand getHand() {
        return playerHand;
    }

    // Allow dealer to show the player their first card, not used here
    @Override
    public void viewDealerCard(Card c) {
    }

    // Allow dealer to show all the cards 
    // that were played after a hand is finished
    // commented out as advancedGame() does not need any game play
    @Override
    public void viewCards(ArrayList<Card> cards) {
//        System.out.println("All the cards that were dealt: ");
//        for (Card card : cards) {
//            System.out.println(card.toString());
//        }
//        System.out.println("\n\n--------------------------------");
    }

    // Allow the dealer to tell them the deck has been reshuffled
    @Override
    public void newDeck() {
//        System.out.println("A new deck has been constructed and reshuffled");
    }
}