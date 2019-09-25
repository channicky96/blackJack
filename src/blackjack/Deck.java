package blackjack;

import java.io.*;
import java.util.*;
import java.io.Serializable;

public class Deck implements Iterable<Card>, Serializable {

    // Serialisation ID
    static final long SERIALISATIONID = 112;
    // A deck ArrayList variable 
    ArrayList<Card> deck;

    public Deck() {
        // Create and initialise the deck
        deck = new ArrayList<>();
        // For each rank and for each suit, create a card (combination)
        for (Card.Rank r : Card.Rank.values()) {
            for (Card.Suit s : Card.Suit.values()) {
                deck.add(new Card(r, s));
            }
        }
        // Shuffle the deck so it is ready to play
        shuffleDeck();
    }

    private void shuffleDeck() {
        Random r = new Random();
        // Do this operation n times where n = the size of deck
        for (int i = 0; i < deck.size(); i++) {
            // j equals any integer between 0 and 52
            int j = r.nextInt(deck.size());
            // Swap the card with position i and position j
            Card tempCard = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, tempCard);
        }
    }

    public Card deal() {
        // Return top card then remove it from the deck
        return deck.remove(0);
    }

    public int size() {
        // Return the how many cards are left in the deck
        return deck.size();
    }

    public final void newDeck() {
        // Remove all cards from deck
        deck.removeAll(deck);
        // Reinitialise the deck
        for (Card.Rank r : Card.Rank.values()) {
            for (Card.Suit s : Card.Suit.values()) {
                deck.add(new Card(r, s));
            }
        }
        // Shuffle the deck after reinitialising 
        // so it's ready for playing again
        shuffleDeck();
    }

    @Override
    public Iterator<Card> iterator() {
        // Create an iterator object
        Iterator<Card> iterCard = deck.iterator();
        // While there is an element in the next position,
        // iterator object points at that element
        while (iterCard.hasNext()) {
            iterCard.next();
        }
        return iterCard;
    }

    public class SecondCardIterator implements Iterator<Card> {

        // Pointer of card of the deck array list; initialise to 0
        int pointer = 0;

        public Iterator<Card> iterator() {
            return new SecondCardIterator();
        }

        @Override
        public boolean hasNext() {
            // As we are traversing every other card, if pointer is less
            // than length of deck - 2, there is another card to deal out
            if (pointer <= deck.size() - 2) {
                pointer += 2;
                return true;
            }
            return false;
        }

        @Override
        public Card next() {
            Card nextCard = null;
            if (pointer >= deck.size() - 2) {
                // NextCard is actually the next next card
                nextCard = deck.get(pointer);
                // Add 2 to pointer so return every other hand as required
                pointer += 2;
                return nextCard;
            }
            return nextCard;
        }
    }

    @Override
    // A toString method to make a string out of all the cards in the deck
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Card c : deck) {
            str.append(c.toString());
        }
        return str.toString();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Deck deck = new Deck();
        ArrayList<Card> secondHand = new ArrayList<>();
        SecondCardIterator it = deck.new SecondCardIterator();
        while (it.hasNext()) {
            secondHand.add(it.next());
        }
        Deck secondHandDeck = new Deck();
        secondHandDeck.deck.clear();
        secondHandDeck.deck = secondHand;

        try {
            String filename = "secondHand.ser";
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(secondHandDeck);
            out.close();
        } catch (IOException ex) {
            System.out.println("Serialisation error: " + ex);
        }
        System.out.print("\nCreate new deck that's shuffled: \n");
        System.out.println(deck.toString());
        System.out.println("\n---------------------------------------------");
        System.out.print("\nDeck size: ");
        System.out.println(deck.size());
        System.out.print("\nDealing a card (top card): ");
        System.out.println(deck.deal());
        System.out.print("\nDeck size after dealing one: ");
        System.out.println(deck.size());
        System.out.print("\nReinitialising deck........\n");
        deck.newDeck();
        System.out.print("\nDeck size after reinitialising: ");
        System.out.println(deck.size());
        System.out.println("\n---------------------------------------------");
    }
}
