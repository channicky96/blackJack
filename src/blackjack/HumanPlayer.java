package blackjack;

import java.util.Scanner;

public class HumanPlayer extends BasicPlayer {

    @Override
    // Make bet based on console input
    public int makeBet() {
        Scanner scan = new Scanner(System.in);
        int hands = 0;
        System.out.println("You currently have: " + playerBalance);
        System.out.println("Place your bet:");
        try {
            hands = scan.nextInt();
            // catch the error if invalid input is entered
        } catch (Exception ex) {
            System.out.println("Enter an integer less than 500 and over 10!");
        }
        // bet amount cannot be greater than the funds they have
        if (playerBalance < hands) {
            while (playerBalance < hands) {
                System.out.println("You can't bet more than you have!");
                System.out.print("please enter an integer "+
                        "lower than your balance\n");
                hands = scan.nextInt();
            }
        }
        playerBet = hands;
        return playerBet;
    }
    
    @Override
    // Make decsion to hit or stick based on console input
    public boolean hit(){
        System.out.println("Current hand: "+playerHand.hand);
        System.out.println("Hand value: "+playerHand.totalValue);
        if(playerHand.totalValue.get(0)>21){
            System.out.println("You are busted!");
            return false;
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("\nWould you like to hit or stick?");
        String decision = scan.next().toLowerCase();
        if ("hit".equals(decision)) {
            // cannot hit if player has a black jack
            if (blackjack() == true) {
                System.out.println("You have a blackJack!");
                System.out.println("You cannot hit anymore");
                return false;
            }
            return true;
        }return false;
    }
}
