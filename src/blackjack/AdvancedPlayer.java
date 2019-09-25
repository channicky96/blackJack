package blackjack;

import java.util.ArrayList;

public class AdvancedPlayer extends BasicPlayer {

    // A card variable to store the dealer's card
    Card dealerCard;
    // A collection of dealt cards to use as startegy for an advanced player
    ArrayList<Card> dealedCards = new ArrayList<>();
    // Counter for the advanced player's card count betting strategy
    int count = 0;
    String name = "AdvancedPlayer";
  
    // A method to return the advanced player's name
    @Override
    public String getName() {
        return this.name;
    }

    // A method to determine if the advanced player should hit or not
    @Override
    public boolean hit() {
        // If player has an ace
        if (playerHand.countRank(Card.Rank.ACE) > 0 
                && !(this.playerHand.isOver(10))) {
            return true;
        }
        // If player doesn't have an ace, if dealer's card is less than 7, hit
        if (dealerCard.rank.getValue() >= 7) {
            return playerHand.totalValue.get(0) <= 17;
            // if not greater or equal to 7, must be less tha.n or equal to 6
        } else if (dealerCard.rank.getValue() <= 6) {
            // hit if soft total is less than 12
            return playerHand.totalValue.get(0) <= 12;
        }
        return false;
    }

    // A method to determine the bet amount of an advanced player
    @Override
    public int makeBet() {
        int baseUnit = 10;
        this.count = 0;
        // For each count
        for (Card card : dealedCards) {
            // if less than or equal to 6
            if (card.rank.ordinal() <= 4) {
                this.count += 1;
                // if more than or equal to 10
            } else if (card.rank.ordinal() >= 8) {
                this.count -= 1;
            }
        }
//        System.out.println("dealedCards: " + dealedCards.toString());
//        System.out.println("dealedCards length: "+dealedCards.size());
        if (this.count <= 0) {
            playerBet = baseUnit;
//            System.out.println("Bet: " + baseUnit);
            return playerBet;
        } else {
//            System.out.println("Bet: " + count * baseUnit);
            playerBet = this.count * baseUnit;
            return playerBet;
        }
    }

    // A method to clear the dealt card pile,
    // commented out as advancedGame() does not need any game play
    @Override
    public void newDeck() {
        this.dealedCards.clear();
        //System.out.println("A new deck has been constructed and reshuffled");
    }
    
    // Allow the advanced player to count cards that 
    // have already been dealt for betting strategy
    @Override
    public void viewCards(ArrayList<Card> cards) {
        for (Card card : cards) {
            dealedCards.add(card);
        }
    }

    // Allow dealer to show the player their first card
    // add it to the dealedCards list
    @Override
    public void viewDealerCard(Card c) {
        dealerCard = c;
    }

}
