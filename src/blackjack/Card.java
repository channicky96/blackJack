package blackjack;

import java.util.*;
import java.io.Serializable;

public class Card implements Serializable, Comparable<Card> {

    // Serialisation ID
    static final long SERIALISATIONID = 111;

    // Create the enum for rank
    enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
        NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        // A rank variable 
        private final int value;

        // Make it so that "value" equals to the
        // integer value inside the bracket
        Rank(int value) {
            this.value = value;
        }

        // return the integer value of the card
        public int getValue() {
            return value;
        }

        // Return the previous rank given a rank
        public Rank getPrevious(Rank r) {
            if (r == Rank.TWO) {
                return Rank.ACE;
            } else {
                return Rank.values()[r.ordinal() - 1];
            }
        }
    }

    // Create the enum for suit
    enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    // Card variables
    Rank rank;
    Suit suit;

    // Constructor for a card instance
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // Return the rank of the card
    public Rank getRank() {
        return rank;
    }

    // Return the suit if teh card
    public Suit getSuit() {
        return suit;
    }

    // Return the sum of the value of two cards
    public static int sum(Card c1, Card c2) {
        return c1.rank.getValue() + c2.rank.getValue();
    }

    // Return true if two cards makes a black jack
    public static boolean isBlackJack(Card c1, Card c2) {
        if (c1.getRank() == Rank.ACE) {
            if (c2.rank.getValue() == 10) {
                return true;
            }
        } else if (c2.getRank() == Rank.ACE) {
            if (c1.rank.getValue() == 10) {
                return true;
            }
        }
        return false;
    }

    @Override
    // Sort the cards in decending order by rank first, then suit.
    public int compareTo(Card c) {
        if (this.rank.ordinal() > c.rank.ordinal()) {
            return -1;
        } else if (this.rank.ordinal() < c.rank.ordinal()) {
            return 1;
        } else {
            if (this.suit.ordinal() < c.suit.ordinal()) {
                return 1;
            } else if (this.suit.ordinal() > c.suit.ordinal()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static class CompareAscending implements Comparator<Card> {
        @Override
        // Sorts the card by rank
        public int compare(Card c1, Card c2) {
            if (c1.rank.ordinal() > c2.rank.ordinal()) {
                return 1;
            } else if (c1.rank.ordinal() < c2.rank.ordinal()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static class CompareSuit implements Comparator<Card> {
        @Override
        // Sorts the card by suit
        public int compare(Card c1, Card c2) {
            if (c1.suit.ordinal() > c2.suit.ordinal()) {
                return 1;
            } else if (c1.suit.ordinal() < c2.suit.ordinal()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    // A toString method to print out a card
    public String toString() {
        StringBuilder str = new StringBuilder("\nThe card is:\t");
        str.append(rank).append(" of ").append(suit);
        return str.toString();
    }

    public static void main(String[] args) {
        Card c = new Card(Rank.TWO, Suit.CLUBS);
        Card c2 = new Card(Rank.FIVE, Suit.SPADES);
        Card c3 = new Card(Rank.ACE, Suit.DIAMONDS);
        Card c4 = new Card(Rank.QUEEN, Suit.HEARTS);
        System.out.println(c.toString());
        System.out.print("\nPrevous Rank: ");
        System.out.print(c.rank.getPrevious(c.rank));
        System.out.print("\n\nRank value: ");
        System.out.println(c.rank.getValue());
        System.out.print("\nCard Rank : ");
        System.out.println(c.getRank());
        System.out.print("\nCard Suit : ");
        System.out.println(c.getSuit());
        System.out.println("\n---------------------------------------------");
        System.out.println(c.toString());
        System.out.println(c2.toString());
        System.out.print("\nThe sum of the integer of the two cards : ");
        System.out.println(sum(c, c2));
        System.out.print("\nIs two cards passed BlackJack : ");
        System.out.println(isBlackJack(c, c2));
        System.out.println("\n---------------------------------------------");
        System.out.println(c3.toString());
        System.out.println(c4.toString());
        System.out.print("\nIs two cards passed BlackJack : ");
        System.out.println(isBlackJack(c3, c4));
        System.out.println("\n---------------------------------------------");
    }
}