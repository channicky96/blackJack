package blackjack;

import java.util.*;

interface Dealer {

    void assignPlayers(List<Player> p);

    /**
     * takeBets: Take the bets for all the assigned players
     */
    void takeBets();

    /**
     * dealFirstCards: Deal first two cards to each player, and one card to the
     * dealer           
*
     */
    void dealFirstCards();

    /**
     * play: play the hand of player p. Keep asking if the player wants a card
     * until they stick or they bust
     *
     * @return final score of the hand
     *
     */
    int play(Player p);

    /**
     * playDealer: Play the dealer hand The dealer must take cards until their
     * total is 17 or higher.
     *
     * @return dealer score
     */
    public int playDealer();

    /**
     * scoreHand: The dealer should score the player hands, not rely on the
     * player to do it.
     *
     * @param h
     * @return score. Note if there are aces, this should be the highest
     * possible value that is less than 21 ACE, THREE should return 14. ACE,
     * THREE, TEN should return 14. ACE, ACE, TWO, THREE should return 17. ACE,
     * ACE, TEN should return 12.
     */
    public int scoreHand(Hand h);

    /**
     * settleBets: This should settle all the bets at the end of the hand.
     *
     */
    public void settleBets();

}