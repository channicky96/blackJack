package blackjack;

public class IntermediatePlayer extends BasicPlayer {

    // Card vraiable to store the dealer's forst card
    // so the player can use it for the hitting stategy
    Card dealerCard;
    String name = "IntermediatePlayer";
    
    @Override
    // A method to return the player's name
    public String getName() {
        return this.name;
    }

    @Override
    // A method to determine if the intermediate player
    // should hit or stick
    public boolean hit() {
        // If player has an ace
        if (playerHand.countRank(Card.Rank.ACE) > 0 && !(this.playerHand.isOver(10))) {
            return true;
        }
        // If player doesn't have an ace, if dealer's card is less than 7, hit
        if (dealerCard.rank.getValue() >= 7) {
            return playerHand.totalValue.get(0) <= 17;
            // if not greater or equal to 7, must be less than or equal to 6
        } else if (dealerCard.rank.getValue() <= 6) {
            // hit if soft total is less than 12
            return playerHand.totalValue.get(0) <= 12;
        }
        return false;
    }

    // Allow dealer to show the player their first card
    @Override
    public void viewDealerCard(Card c) {
        dealerCard = c;
    }

}
