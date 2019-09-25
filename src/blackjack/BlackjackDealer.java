package blackjack;

import static blackjack.Card.isBlackJack;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackjackDealer implements Dealer, Serializable {

    // A deck variable for playing the game
    Deck deck;
    // A list of integers indicating players bet
    // i.e. first player's bet will be on the first element of the list
    ArrayList<Integer> bets;
    // A hand variable for the dealer
    Hand dealerHand;
    // An array list that store all the players
    ArrayList<Player> players;
    int deckCount = 1;

    // A constructor for a BlackjackDealer object, initialise the variables
    public BlackjackDealer() {
        deck = new Deck();
        bets = new ArrayList<>();
        dealerHand = new Hand();
        players = new ArrayList<>();
    }

    // For each player in the list, assign them to play the game
    @Override
    public void assignPlayers(List<Player> p) {
        for (Player player : p) {
            players.add(player);
        }
    }

    // For each player, add their respective bets 
    @Override
    public void takeBets() {
        for (Player player : players) {
            bets.add(player.makeBet());
        }
    }

    // Play the hand of player p. Keep asking if the player wants a card
    // until they stick or they bust
    @Override
    public void dealFirstCards() {     
        // Dealer deals himself a card
        dealerHand.addSingleCard(deck.deal());
        // For each player 
        for (Player p : players) {
            // If deck size is less than 13(52 * 1/4),
            // make a new deck and notify the player
            if (deck.size() < 13) {
                deck.newDeck();
                this.deckCount +=1;
                p.newDeck();
            }
            // Deal the player two cards
            p.takeCard(deck.deal());
            p.takeCard(deck.deal());
            p.viewDealerCard(this.dealerHand.hand.get(0));
        }
    }

    // Play the hand of a player
    @Override
    public int play(Player p) {
        // If deck size is less than 13(52 * 1/4),
        // make a new deck and notify the player
        if (deck.size() < 13) {
            deck.newDeck();
            p.newDeck();
        }
        System.out.print("Player, your highest hand total is: "
                + p.getHandTotal());
        Scanner scan = new Scanner(System.in);
        System.out.println("\nWould you like to hit or stick?");
        String decision = scan.next().toLowerCase();

        while ("hit".equals(decision)) {
            if (p.blackjack() == true) {
                System.out.println("You have a blackJack!");
                System.out.println("You cannot hit anymore");
                decision = "stick";
                break;
            }
            p.getHand().hand.add(deck.deal());
            System.out.print("Your highest hand total is: " + p.getHandTotal());
            if (p.isBust() == true) {
                System.out.println("You are busted!");
                decision = "NA";
                break;
            }
            System.out.println("\n\nPlease enter either hit ot stick!");
            decision = scan.next().toLowerCase();
        }
        return p.getHandTotal();
    }

    // play the dealer's hand
    @Override
    public int playDealer() {
        // If dealer has an ace (all poosible value is stored from low to high)
        if (dealerHand.totalValue.get(0) == 1) {
            // hit when the second element of the possible hand value
            // is less than or equal to 17
            while (dealerHand.totalValue.get(1) <= 17) {
                dealerHand.addSingleCard(deck.deal());
            }
            // if second element is greater than 21
            if (dealerHand.totalValue.get(1) > 21) {
                // and while the first element is less than or equal to 17
                while (dealerHand.totalValue.get(0) <= 17) {
                    // deal a card
                    dealerHand.addSingleCard(deck.deal());
                    // if busted, return it
                    if (dealerHand.totalValue.get(0) > 21) {
                        return dealerHand.totalValue.get(0);
                    }
                }
            } else {
                // if element is 21 or below but greater or equal to 17
                return dealerHand.totalValue.get(1);
            }
        } else {
            // if he doesn't have an ace, that means there are only one element
            // in arrayList, while that is less than 17, get a card
            while (dealerHand.totalValue.get(0) <= 17) {
                dealerHand.addSingleCard(deck.deal());
            }
        }
        return dealerHand.totalValue.get(0);
    }

    // Traverese through all possible values, find and return 
    // the highest possible hand total that doesn't exceed 21
    @Override
    public int scoreHand(Hand h) {
        int maxHard = 0;
        for (int value : h.totalValue) {
            if (value <= 21) {
                maxHard = value;
            }
        }

        if (h.totalValue.get(0) > 21) {
            return h.totalValue.get(0);
        }

        return maxHard;
    }

    // Settle the bets and update the player's balance
    @Override
    public void settleBets() {
        // For each player
        for (Player p : players) {
            // If the player is busted
            if (p.isBust() == true) {
                p.settleBet(p.getBet() * -1);
            } else {
                // If dealer is busted
                if (dealerHand.isOver(21) == true) {
                    if (p.blackjack() == true) {
                        p.settleBet(p.getBet() * 2);
                    } else {
                        p.settleBet(p.getBet());
                    }
                } else {
                    // If the dealer has a black jack
                    if (isBlackJack(dealerHand.hand.get(0), dealerHand.hand.get(1))
                            == true) {
                        if (!p.blackjack()) {
                            p.settleBet(p.getBet() * -1);
                        } else {
                            p.settleBet(p.getBet());
                        }
                        // Dealer isn't busted and doesn't have a black jack
                    } else if (p.getHandTotal() < scoreHand(dealerHand)) {
                        p.settleBet(p.getBet() * -1);
                    } else if (p.getHandTotal() > scoreHand(dealerHand)) {
                        p.settleBet(p.getBet());
                    } else {
                        p.settleBet(0);
                    }
                }
            }
            // clear the hand after settling bet for a new game
            p.getHand().hand.clear();
        }
    }
}
