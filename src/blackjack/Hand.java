package blackjack;

import java.util.*;
import java.io.Serializable;

public class Hand implements Serializable, Iterable<Card> {

    // Serialisation ID
    static final long SERIALISATIONID = 102;

    // A hand ArrayList of typr Card variable 
    ArrayList<Card> hand;
    // A count of the number of each rank that is currently in the hand
    int[] countOfRank;
    // An arraylist holding total integer values of the cards of the hand,
    // the first element will store the lowest possible value and
    // the last element will store the greatest possible value
    ArrayList<Integer> totalValue;
    // Number of ace(s) in the hand
    int aceCount = 0;

    // Default constructor for constructing an empty hand
    public Hand() {
        this.hand = new ArrayList<>();
        this.countOfRank = new int[13];
        this.totalValue = new ArrayList<>();
    }

    // A constructor to create a hand and add an array of cards to it
    public Hand(ArrayList<Card> cardArray) {
        this.hand = new ArrayList<>();
        this.countOfRank = new int[13];
        this.totalValue = new ArrayList<>();
        // if array given contains no cards, do nothing
        if (cardArray == null) {
            throw new IndexOutOfBoundsException("No cards given!");
        }
        // number of ace in the array of cards
        for (Card card : cardArray) {
            if (card.rank == Card.Rank.ACE) {
                aceCount++;
            }
            // method to get all possible values depends on the amount of ace
            switchAce(aceCount, cardArray);
            // increment the count of each card  
            // based on their ordinal position in the enum
            countOfRank[card.rank.ordinal() - 1] += 1;
            // and add the card to the hand
            this.hand.add(card);
        }
    }

    // A constructor to create a hand and add cards in givenHand to it
    public Hand(Hand givenHand) {
        this.hand = new ArrayList<>();
        this.countOfRank = new int[13];
        this.totalValue = new ArrayList<>();
        // If given cards to add to hand is null
        if (givenHand == null) {
            throw new IndexOutOfBoundsException("No cards given!");
        }
        // if not, then for each card
        for (Card card : givenHand) {
            if (card.rank == Card.Rank.ACE) {
                aceCount++;
            }
            // method to get all possible values depends on the amount of ace
            switchAce(aceCount, givenHand.hand);
            this.countOfRank[card.rank.ordinal()] += 1;
            this.hand.add(card);
        }
    }

    // A method to add a single card to the hand
    public void addSingleCard(Card card) {
        // Add the card to the hand
        this.hand.add(card);
        // update values
        aceCount = 0;
        totalValue.clear();
        totalValue.add(0);
        for (Card aCard : this.hand) {
            if (aCard.rank == Card.Rank.ACE) {
                aceCount++;
            }
        }
        // method to get all possible values depends on the amount of ace
        switchAce(aceCount, this.hand);
        this.countOfRank[card.rank.ordinal()] += 1;
    }

    public void addCardCollection(ArrayList<Card> cards) {
        // If no cards are given
        if (cards == null) {
            throw new IndexOutOfBoundsException("No cards given!");
        }
        // For each card in Cards
        for (Card card : cards) {
            this.hand.add(card);
            this.countOfRank[card.rank.ordinal()] += 1;
        }
        // reset totalValue so it can be recalculated again
        this.aceCount = 0;
        totalValue.clear();
        totalValue.add(0);
        for (Card card : this.hand) {
            if (card.rank == Card.Rank.ACE) {
                aceCount++;
            }
        }
        // update total value
        switchAce(aceCount, this.hand);
    }

    public void addHand(Hand hand) {
        // If no cards are given
        if (hand.hand == null) {
            throw new IndexOutOfBoundsException("No cards given!");
        }
        for (Card card : hand.hand) {
            this.hand.add(card);
            this.countOfRank[card.rank.ordinal()] += 1;
        }
        this.aceCount = 0;
        totalValue.clear();
        totalValue.add(0);
        // number of ace in the array of cards
        for (Card card : this.hand) {
            if (card.rank == Card.Rank.ACE) {
                aceCount++;
            }
        }
        // update total value
        switchAce(aceCount, this.hand);
    }

    public boolean removeSingleCard(Card card) {
        // If card to be removed is in the collection
        System.out.println("\n\nCard to be removed"+card);
        if (hand.contains(card)) {
            // remove the card and return true
            this.hand.remove(card);
            // decrement the count of rank
            this.countOfRank[card.rank.ordinal()] -= 1;
            this.aceCount = 0;
            totalValue.clear();
            totalValue.add(0);
            for (Card aCard : this.hand) {
                if (aCard.rank == Card.Rank.ACE) {
                    aceCount++;
                }
            }
            switchAce(aceCount, this.hand);
            return true;
        } else {
            // if card removed unsuccessfully, return false
            return false;
        }
    }

    public boolean removeCards(ArrayList<Card> Cards) {
        // Count for amount of card(s) remove
        int count = 0;
        // If no card(s) given
        if (Cards == null) {
            System.out.println("There are no card given");
            return true;
        }
        // For each card in Cards
        for (Card card : Cards) {
            if (this.hand.contains(card)) {
                // remove the card and return true
                this.hand.remove(card);
                count++;
                // decrement the count of rank
                this.countOfRank[card.rank.ordinal()] -= 1;
            }
        }
        this.aceCount = 0;
        totalValue.clear();
        totalValue.add(0);
        for (Card aCard : this.hand) {
            if (aCard.rank == Card.Rank.ACE) {
                aceCount++;
            }
        }
        switchAce(aceCount, this.hand);
        // return true if count removed equals the amount of card(s) given
        return Cards.size() == count;
    }
    
    // Remove a card from a given position
    public Card removeCardPosition(int position) {
        // throw exception if position value exceeds the size of the hand
        if (position > this.hand.size()) {
            throw new IndexOutOfBoundsException(" Card at position: "
                    + position + " doesn't exist.");
        }
        Card temp = hand.get(position);
        hand.remove(position);
        this.aceCount = 0;
        totalValue.clear();
        totalValue.add(0);
        for (Card aCard : this.hand) {
            if (aCard.rank == Card.Rank.ACE) {
                aceCount++;
            }
        }
        switchAce(aceCount, this.hand);
        return temp;
    }
    
    @Override
    public Iterator<Card> iterator() {
        // Create an iterator object
        Iterator<Card> iterCard = hand.iterator();
        // While there is an element in the next position,
        // print and return
        while (iterCard.hasNext()) {
            iterCard.next();
        }
        return iterCard;
    }

    // Using compareTo method from Card class,
    // sort cards into descending order
    public void sortDescending(Hand hand) {
        Collections.sort(hand.hand);
    }

    // Using CompareAscending method from Card class,
    // sort cards into ascending order
    public void sortAscending(Hand hand) {
        Collections.sort(hand.hand, new Card.CompareAscending());
    }

    // Given a suit, returns the number of cards of that suit
    public int countSuit(Card.Suit suit) {
        int count = 0;
        for (Card card : hand) {
            // increment count by 1 if the suit of the card
            // equals to the given suit
            if (card.suit == suit) {
                count++;
            }
        }
        // return the count
        return count;
    }

    // Given a rank, returns the number of cards of that rank
    public int countRank(Card.Rank rank) {
        int count = 0;
        for (Card card : hand) {
            // increment count by 1 if the rank of the card
            // equals to the given rank
            if (card.rank == rank) {
                count++;
            }
        }
        // return the count
        return count;
    }

    // Return true if lowest possible hand  
    // value is greater than value passsed
    public boolean isOver(int value) {
        return this.totalValue.get(0) > value;
    }

    // Returns a new hand of original hand in reverese order
    public Hand reverseHand(Hand hand) {
        for (int i = 0, j = hand.hand.size() - 1; i < j; i++) {
            hand.hand.add(i, hand.hand.remove(j));
        }
        return hand;
    }

    // A method to update and create an arraylisy with all possible
    // hand value starting from the lowest in the first element
    public final void switchAce(int aceCount, ArrayList<Card> cardArray) {
        switch (aceCount) {
            // If ace count is 1, that means there are 2 possible hand value
            case 1:
                for (int i = 0; i < 1; i++) {
                    totalValue.add(0);
                }
                for (Card card : cardArray) {
                    if (card.rank == Card.Rank.ACE) {
                        // Starts from 1, then 11, and add other cards value in
                        for (int i = 0, j = 1; i < aceCount + 1; i++, j += 10) {
                            int oldVal = this.totalValue.get(i);
                            int newVal = j;
                            this.totalValue.set(i, oldVal + newVal);
                        }
                    } else {
                        for (int i = 0; i < aceCount + 1; i++) {
                            int oldVal = this.totalValue.get(i);
                            int newVal = card.rank.getValue();
                            this.totalValue.set(i, oldVal + newVal);
                        }
                    }
                }
                break;
            // If ace count is 2, that means there are 3 possible hand value
            case 2:
                for (int i = 0; i < 2; i++) {
                    totalValue.add(0);
                }// Starts from 2, then 12, and 22, plus other cards value in
                for (int i = 0, j = 2; i < aceCount + 1; i++, j += 10) {
                    int oldVal = this.totalValue.get(i);
                    int newVal = j;
                    this.totalValue.set(i, oldVal + newVal);
                }
                for (Card card : cardArray) {
                    if (card.rank != Card.Rank.ACE) {
                        for (int i = 0; i < aceCount + 1; i++) {
                            int oldVal = this.totalValue.get(i);
                            int newVal = card.rank.getValue();
                            this.totalValue.set(i, oldVal + newVal);
                        }
                    }
                }
                break;
            // If ace count is 2, that means there are 3 possible hand value
            case 3:                                         // 3, 13, 23, 33
                for (int i = 0; i < 3; i++) {
                    totalValue.add(0);
                }
                for (int i = 0, j = 3; i < aceCount + 1; i++, j += 10) {
                    int oldVal = this.totalValue.get(i);
                    int newVal = j;
                    this.totalValue.set(i, oldVal + newVal);
                }
                for (Card card : cardArray) {
                    if (card.rank != Card.Rank.ACE) {
                        for (int i = 0; i < aceCount + 1; i++) {
                            int oldVal = this.totalValue.get(i);
                            int newVal = card.rank.getValue();
                            this.totalValue.set(i, oldVal + newVal);
                        }
                    }
                }
                break;
            // If ace count is 2, that means there are 3 possible hand value
            case 4:                                     // 4, 14, 24, 34, 44
                for (int i = 0; i < 4; i++) {
                    totalValue.add(0);
                }
                for (int i = 0, j = 4; i < aceCount + 1; i++, j += 10) {
                    int oldVal = this.totalValue.get(i);
                    int newVal = j;
                    this.totalValue.set(i, oldVal + newVal);
                }
                for (Card card : cardArray) {
                    if (card.rank != Card.Rank.ACE) {
                        for (int i = 0; i < aceCount + 1; i++) {
                            int oldVal = this.totalValue.get(i);
                            int newVal = card.rank.getValue();
                            this.totalValue.set(i, oldVal + newVal);
                        }
                    }
                }
                break;
            // If there're no ace, that means there is only possible hand value
            default:
                for (Card card : cardArray) {
                    int oldVal = this.totalValue.get(0);
                    int newVal = card.rank.getValue();
                    this.totalValue.set(0, (oldVal + newVal));
                }
                break;
        }
    }
    
    @Override
    // toString method to return a string with all cards in the hand
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Card c : hand) {
            str.append(c.toString());
        }
        return str.toString();
    }
    
    public static void main(String[] args) {
        Hand hand = new Hand();
        Hand newHand = new Hand();
        Hand newHand2 = new Hand();
        Deck deck = new Deck();
        deck.deck.clear();
        Card c = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card c2 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card c3 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card c4 = new Card(Card.Rank.ACE, Card.Suit.DIAMONDS);
        deck.deck.add(c);
        deck.deck.add(c2);
        deck.deck.add(c3);
        deck.deck.add(c4);
        for (int i = 0; i < 4; i++) {
            hand.addSingleCard(deck.deck.get(i));
        }
        System.out.print("Current hand: ");
        System.out.print(hand.toString());
        System.out.println("\nCard removed: " + hand.removeSingleCard(c3));

        System.out.print("\nHand after remove card: ");
        System.out.print(hand.toString());
        
        System.out.println("\n\nRemoving card from second position");
        System.out.println(hand.removeCardPosition(1));
        System.out.print("\nHand after remove card: ");
        System.out.print(hand.toString());
        
        System.out.println("\n\n-------------------------------------------");
        deck.newDeck();
        for (int i = 0; i < 3; i++) {
            newHand.addSingleCard(deck.deck.get(i));
        }
        System.out.print("\nNew hand: ");
        System.out.print(newHand.toString());

        hand.addHand(newHand);
        System.out.print("\n\nCombined hand: ");
        System.out.print(hand.toString());
        System.out.println("\n\nRemoving newHand from hand....");
        System.out.println("\nAll cards removed: "
                        + hand.removeCards(newHand.hand));
        System.out.print("\nCurrent hand: ");
        System.out.print(hand.toString());

        System.out.println("\n\n-------------------------------------------");
        for (int i = 0; i < 4; i++) {
            newHand2.addSingleCard(deck.deck.get(i));
        }
        System.out.print("\nAnother new hand: ");
        System.out.print(newHand2.toString());
        newHand2.reverseHand(newHand2);
        System.out.print("\n\nReverse hand:");
        System.out.print(newHand2.toString());
        System.out.println("\n\nAce count: " + newHand2.aceCount);
        System.out.println("\nPossible hand values: ");
        System.out.println(newHand2.totalValue);
        System.out.println("\nCount of ranks: ");
        System.out.print(Arrays.toString(newHand2.countOfRank));
        newHand2.sortDescending(newHand2);
        System.out.print("\n\nSorted hand in descending order (by rank):");
        System.out.print(newHand2.toString());
        newHand2.sortAscending(newHand2);
        System.out.print("\n\nSorted hand in ascending order (by rank):");
        System.out.print(newHand2.toString());
        System.out.println("\n\nBusted? (lowest value over 21):");
        System.out.println(newHand2.isOver(21));
    }
}