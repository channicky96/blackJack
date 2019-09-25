package blackjack;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class BlackjackTable implements Serializable {

    // A dealer object for the black jack table
    BlackjackDealer dealer = new BlackjackDealer();
    // A list used to asscociate players with the dealer
    List<Player> players = new ArrayList<>();
    int maxNumberOfPlayer = 8;
    int minBet = 1;
    int maxBet = 500;
    
        // A method that simulates a black jack table with four intermediate player
    public static void basicGame() {
        Scanner scan = new Scanner(System.in);
        BlackjackTable bjt = new BlackjackTable();
        List<Player> players = new ArrayList<>();
        // A variable to check if every player is out of funds and
        // an interger value to indicate how many hands the game it should run
        int breakCount = -1, hands = 0; 
        String decision;
        // Create intermediate players and add them to the list object
        BasicPlayer bp1 = new BasicPlayer();
        BasicPlayer bp2 = new BasicPlayer();
        BasicPlayer bp3 = new BasicPlayer();
        BasicPlayer bp4 = new BasicPlayer();
        players.add(bp1);
        players.add(bp2);
        players.add(bp3);
        players.add(bp4);
        // Associating the players to the dealer
        bjt.dealer.assignPlayers(players);
        System.out.println("Game start\n");
        while (true) {
            // check if all players is out of funds
            for (Player p : bjt.dealer.players) {
                if (p.getBalance() <= 0) {
                    breakCount++;
                }
            }
            // if so, they have the option to restart or stop playing
            if (breakCount >= bjt.dealer.players.size()) {
                System.out.println("All players are out of balance");
                System.out.println("enter y is you want to keep playing");
                System.out.println("enter n if not");
                decision = scan.next();
                // restart by creating new players
                if ("y".equals(decision)) {
                    for (int i = 0; i < bjt.dealer.players.size(); i++) {
                        bjt.dealer.players.remove(i);
                    }
                    bp1 = new BasicPlayer();
                    bp2 = new BasicPlayer();
                    bp3 = new BasicPlayer();
                    bp4 = new BasicPlayer();
                    players.add(bp1);
                    players.add(bp2);
                    players.add(bp3);
                    players.add(bp4);
                    // assign players to the dealer object
                    bjt.dealer.assignPlayers(players);
                } else if ("n".equals(decision)) {
                    break;
                }
            }
            // reset counter so it wouldn't retain it's value when count again
            breakCount = 0;
            // player now have an option to save or load game by the use of
            // serialisation, if they do not wish to do this, they can enter
            // 0 to quit or enter an integer to indicate how many hands
            // they would like to play
            System.out.println("Enter s if you'd like to save the game,\n"
                    + "l to load the game from last save or \n"
                    + "enter amount of hands you want to play \n"
                    + "enter 0 if you want to stop playing");
            try {
                decision = scan.next();
                // catch exception so the program doesnt crash
            } catch (InputMismatchException ex) {
                break;
            }
            if ("s".equals(decision)) {
                try { // save serialisable object to a .ser file
                    String filename = "blackJackTableSave.ser";
                    FileOutputStream fos = new FileOutputStream(filename);
                    ObjectOutputStream out = new ObjectOutputStream(fos);
                    out.writeObject(bjt);
                    out.close();
                    // catch exception in case of error
                } catch (IOException ex) {
                    System.out.println("Serialisation error: " + ex);
                    ex.printStackTrace();
                }
            } else if ("l".equals(decision)) {
                try { // load serialisable object from a .ser file
                    String filename = "blackJackTableSave.ser";
                    FileInputStream fis = new FileInputStream(filename);
                    ObjectInputStream in = new ObjectInputStream(fis);
                    bjt = (BlackjackTable) in.readObject();
                    in.close();
                    // catch exceptions in case of error
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Serialisation error: " + ex);
                }
            } else {
                // How many times to iterate through
                try {
                    hands = Integer.parseInt(decision);
                } catch (NumberFormatException ex) {
                    break;
                }
                // If user enter 0, quit the game
                if (hands == 0) {
                    break;
                }
                // Check if all players are out of funds again
                while (hands > 0) {
                    for (Player p : bjt.dealer.players) {
                        if (p.getBalance() <= 0) {
                            breakCount++;
                        }
                    }
                    if (breakCount == bjt.dealer.players.size()) {
                        break;
                    } else {
                        breakCount = 0;
                    }
                    // Clear dealer hand after each hand to play again
                    bjt.dealer.dealerHand.hand.clear();
                    // For each player, if they have sufficient funds
                    // take the bets of each player
                    for (Player p : bjt.dealer.players) {
                        if (p.getBalance() > p.makeBet()) {
                            bjt.dealer.bets.add(p.makeBet());
                        }
                    }
                    // Deal first two hands to each player, and one to himself
                    bjt.dealer.dealFirstCards();
                    // If deck is less than 1/4 full, reinitialise it
                    for (Player p : bjt.dealer.players) {
                        if (bjt.dealer.deck.size() < 13) {
                            bjt.dealer.deck.newDeck();
                            p.newDeck();
                        }
                        // If player has no funds left, 
                        // they are removed from the game
                        if (p.getBalance() <= 0) {
                            bjt.dealer.players.remove(p);
                            break;
                        }
                        // If player decide to hit, deal them a card
                        while (p.hit() == true) {
                            p.getHand().addSingleCard(bjt.dealer.deck.deal());
//                            p.takeCard(bjt.dealer.deck.deal());
                        }
                    }
                    // Play the dealer's hand
                    bjt.dealer.playDealer();
                    // Settle the bets and update all player's balance
                    bjt.dealer.settleBets();
                    // Decrement counter for amount of iteration needed
                    hands -= 1;
                }
            }
            // At the end of iteration, output player's funds
            // if they have any left
            for (Player pl : bjt.dealer.players) {
                System.out.println("\n\nPlayer balance: "
                        + pl.getBalance() + "\n");
            }
        }
    }
    
    // A method that simulates a black jack table with four intermediate player
    public static void IntermediateGame() {
        Scanner scan = new Scanner(System.in);
        BlackjackTable bjt = new BlackjackTable();
        List<Player> players = new ArrayList<>();
        // A variable to check if every player is out of funds and
        // an interger value to indicate how many hands the game it should run
        int breakCount = -1, hands = 0; 
        String decision;
        // Create intermediate players and add them to the list object
        IntermediatePlayer ip1 = new IntermediatePlayer();
        IntermediatePlayer ip2 = new IntermediatePlayer();
        IntermediatePlayer ip3 = new IntermediatePlayer();
        IntermediatePlayer ip4 = new IntermediatePlayer();
        players.add(ip1);
        players.add(ip2);
        players.add(ip3);
        players.add(ip4);
        // Associating the players to the dealer
        bjt.dealer.assignPlayers(players);
        System.out.println("Game start\n");
        while (true) {
            // check if all players is out of funds
            for (Player p : bjt.dealer.players) {
                if (p.getBalance() <= 0) {
                    breakCount++;
                }
            }
            // if so, they have the option to restart or stop playing
            if (breakCount >= bjt.dealer.players.size()) {
                System.out.println("All players are out of balance");
                System.out.println("enter y is you want to keep playing");
                System.out.println("enter n if not");
                decision = scan.next();
                // restart by creating new players
                if ("y".equals(decision)) {
                    for (int i = 0; i < bjt.dealer.players.size(); i++) {
                        bjt.dealer.players.remove(i);
                    }
                    ip1 = new IntermediatePlayer();
                    ip2 = new IntermediatePlayer();
                    ip3 = new IntermediatePlayer();
                    ip4 = new IntermediatePlayer();
                    players.add(ip1);
                    players.add(ip2);
                    players.add(ip3);
                    players.add(ip4);
                    // assign players to the dealer object
                    bjt.dealer.assignPlayers(players);
                } else if ("n".equals(decision)) {
                    break;
                }
            }
            // reset counter so it wouldn't retain it's value when count again
            breakCount = 0;
            // player now have an option to save or load game by the use of
            // serialisation, if they do not wish to do this, they can enter
            // 0 to quit or enter an integer to indicate how many hands
            // they would like to play
            System.out.println("Enter s if you'd like to save the game,\n"
                    + "l to load the game from last save or \n"
                    + "enter amount of hands you want to play \n"
                    + "enter 0 if you want to stop playing");
            try {
                decision = scan.next();
                // catch exception so the program doesnt crash
            } catch (InputMismatchException ex) {
                break;
            }
            if ("s".equals(decision)) {
                try { // save serialisable object to a .ser file
                    String filename = "blackJackTableSave.ser";
                    FileOutputStream fos = new FileOutputStream(filename);
                    ObjectOutputStream out = new ObjectOutputStream(fos);
                    out.writeObject(bjt);
                    out.close();
                    // catch exception in case of error
                } catch (IOException ex) {
                    System.out.println("Serialisation error: " + ex);
                    ex.printStackTrace();
                }
            } else if ("l".equals(decision)) {
                try { // load serialisable object from a .ser file
                    String filename = "blackJackTableSave.ser";
                    FileInputStream fis = new FileInputStream(filename);
                    ObjectInputStream in = new ObjectInputStream(fis);
                    bjt = (BlackjackTable) in.readObject();
                    in.close();
                    // catch exceptions in case of error
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Serialisation error: " + ex);
                }
            } else {
                // How many times to iterate through
                try {
                    hands = Integer.parseInt(decision);
                } catch (NumberFormatException ex) {
                    break;
                }
                // If user enter 0, quit the game
                if (hands == 0) {
                    break;
                }
                // Check if all players are out of funds again
                while (hands > 0) {
                    for (Player p : bjt.dealer.players) {
                        if (p.getBalance() <= 0) {
                            breakCount++;
                        }
                    }
                    if (breakCount == bjt.dealer.players.size()) {
                        break;
                    } else {
                        breakCount = 0;
                    }
                    // Clear dealer hand after each hand to play again
                    bjt.dealer.dealerHand.hand.clear();
                    // For each player, if they have sufficient funds
                    // take the bets of each player
                    for (Player p : bjt.dealer.players) {
                        if (p.getBalance() > p.makeBet()) {
                            bjt.dealer.bets.add(p.makeBet());
                        }
                    }
                    // Deal first two hands to each player, and one to himself
                    bjt.dealer.dealFirstCards();
                    
                    // If deck is less than 1/4 full, reinitialise it
                    for (Player p : bjt.dealer.players) {
                        if (bjt.dealer.deck.size() < 13) {
                            bjt.dealer.deck.newDeck();
                            p.newDeck();
                        }
                        // If player has no funds left, 
                        // they are removed from the game
                        if (p.getBalance() <= 0) {
                            bjt.dealer.players.remove(p);
                            break;
                        }
                        // If player decide to hit, deal them a card
                        while (p.hit() == true) {
                            p.getHand().addSingleCard(bjt.dealer.deck.deal());
                        }
                    }
                    // Play the dealer's hand
                    bjt.dealer.playDealer();
                    // Settle the bets and update all player's balance
                    bjt.dealer.settleBets();
                    bjt.dealer.bets.clear();
                    // Decrement counter for amount of iteration needed
                    hands -= 1;
                }
            }
            // At the end of iteration, output player's funds
            // if they have any left
            for (Player pl : bjt.dealer.players) {
                System.out.println("\n\nPlayer balance: "
                        + pl.getBalance() + "\n");
            }
        }
    }

    // A method that simulates a black jack table with a basic player
    // and a human player
    public static void humanGame() {
        Scanner scan = new Scanner(System.in);
        BlackjackTable bjt = new BlackjackTable();
        List<Player> players = new ArrayList<>();
        int breakCount = -1, hands = 1;
        String decision;
        // A game running with a a basic player and a human player, which
        // make decsion to hit or stick and amount to bet using console input
        BasicPlayer bp1 = new BasicPlayer();
        HumanPlayer hp1 = new HumanPlayer();
        players.add(bp1);
        players.add(hp1);
        bjt.dealer.assignPlayers(players);
        System.out.println("Game start\n");
        while (true) {
            // check how many players are out of funds
            for (Player p : bjt.dealer.players) {
                if (p.getBalance() <= 0) {
                    breakCount++;
                }
            }
            // if all the players are out of funds
            if (breakCount >= bjt.dealer.players.size()) {
                System.out.println("All players are out of balance");
                // gives option to continue or quit the game
                System.out.println("enter y is you want to keep playing");
                System.out.println("enter n if not");
                decision = scan.next();
                if ("y".equals(decision)) {
                    for (int i = 0; i < bjt.dealer.players.size(); i++) {
                        bjt.dealer.players.remove(i);
                    }
                    // create new players, add it to a list 
                    bp1 = new BasicPlayer();
                    hp1 = new HumanPlayer();
                    players.add(bp1);
                    players.add(hp1);
                    // to be assigned to the dealer
                    bjt.dealer.assignPlayers(players);
                } else if ("n".equals(decision)) {
                    break;
                }
            }
            breakCount = 0;
            System.out.println("Enter s if you'd like to save the game,\n"
                    + "l 'L' to load the game from last save or enter 0 if \n"
                    + "you want to stop playing, 1(one) if you want to play");
            try {
                decision = scan.next();
            } catch (InputMismatchException ex) {
                break;
            }
            if ("s".equals(decision)) {
                try {     // save serialisable object to a .ser file
                    String filename = "humanBlackJackTableSave.ser";
                    FileOutputStream fos = new FileOutputStream(filename);
                    ObjectOutputStream out = new ObjectOutputStream(fos);
                    out.writeObject(bjt);
                    out.close();
                    // catch exception in case of error
                } catch (IOException ex) {
                    System.out.println("Serialisation error: " + ex);
                }
            } else if ("l".equals(decision)) {
                try {    // load serialisable object to a .ser file
                    String filename = "humanBlackJackTableSave.ser";
                    FileInputStream fis = new FileInputStream(filename);
                    ObjectInputStream in = new ObjectInputStream(fis);
                    bjt = (BlackjackTable) in.readObject();
                    in.close();
                    // catch exceptions in case of error
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Serialisation error: " + ex);
                }
            } else {
                try {
                    // parse the string to an integer so can be used 
                    hands = Integer.parseInt(decision);
                    // catch exception in case of error
                } catch (NumberFormatException ex) {
                    break;
                }
                // if 0 is entered above, quit the game
                if (hands == 0) {
                    break;
                }
                // check to see if all the players are out of funds
                for (Player p : bjt.dealer.players) {
                    if (p.getBalance() <= 0) {
                        breakCount++;
                    }
                }
                // if all players are out of funds, finish the game
                if (breakCount == bjt.dealer.players.size() - 1) {
                    break;
                } else {
                    // else reset counter so it can be used to check again 
                    breakCount = 0;
                }
                // clear dealer's hand from previous game
                bjt.dealer.dealerHand.hand.clear();
                // for each player
                for (Player p : bjt.dealer.players) {
                    // if they still have some funds
                    if (p.getBalance() > 0) {
                        // set their bets
                        bjt.dealer.bets.add(p.makeBet());
                    }
                }
//                System.out.println(bjt.dealer.bets);
                // deal two cards to each player and one to himself
                bjt.dealer.dealFirstCards();
                // for each player
                for (Player p : bjt.dealer.players) {
                    // check first if there are sufficient
                    // cards left in the deck
                    if (bjt.dealer.deck.size() < 13) {
                        bjt.dealer.deck.newDeck();
                        p.newDeck();
                    }
                    // and if they are out of funds, remove them from game
                    if (p.getBalance() < 0) {
                        bjt.dealer.players.remove(p);
                        break;
                    }
                    // deal player a card if the condition is true
                    while (p.hit() == true) {
                        p.takeCard(bjt.dealer.deck.deal());
                    }
                }
                // play the dealer's hand
                bjt.dealer.playDealer();
                // make all changes on player's balance
                bjt.dealer.settleBets();
                // for each player that still has funds left after the 
                // hands, display their balance
                for (Player pl : bjt.dealer.players) {
                    System.out.println("\n\nPlayer balance: "
                            + pl.getBalance() + "\n");
                }
            }
        }
    }

    // A method that simulates a black jack table with a basic player
    // and a intermediate player and an advanced palyer
    public static void advanceGame() {
        Scanner scan = new Scanner(System.in);
        // Create BlackjackTable object and a list for player object
        // for assigning players to the dealer
        BlackjackTable bjt = new BlackjackTable();
        List<Player> players = new ArrayList<>();
        int breakCount = -1, hands = 1;
        String decision;
        // 
        BasicPlayer bp1 = new BasicPlayer();
        IntermediatePlayer ip1 = new IntermediatePlayer();
        AdvancedPlayer ap1 = new AdvancedPlayer();
        players.add(ap1);
        players.add(ip1);
        players.add(bp1);
        bjt.dealer.assignPlayers(players);
        System.out.println("Game start\n");
        while (true) {

            for (Player p : bjt.dealer.players) {
                if (p.getBalance() <= 0) {
                    breakCount++;
                }
            }
            if (breakCount >= bjt.dealer.players.size()) {
                System.out.println("All players are out of balance");
                System.out.println("enter y is you want to keep playing");
                System.out.println("enter n if not");
                decision = scan.next();
                // if they wish to restart games, restart by removing 
                // old ones creating new players
                if ("y".equals(decision)) {
                    for (int i = 0; i < bjt.dealer.players.size(); i++) {
                        bjt.dealer.players.remove(i);
                    }
                    bp1 = new BasicPlayer();
                    ip1 = new IntermediatePlayer();
                    ap1 = new AdvancedPlayer();
                    // add to the list and assign them
                    players.add(ap1);
                    players.add(ip1);
                    players.add(bp1);
                    bjt.dealer.assignPlayers(players);
                    // if they want to quit then break out of the game
                } else if ("n".equals(decision)) {
                    break;
                }
            }
            // reset counter so it can be used to see if all players 
            // are out of fund next loop
            breakCount = 0;
            // option to save; load; quit; or keep playing game
            System.out.println("Enter s if you'd like to save the game,\n"
                    + "l to load the game from last save or \n"
                    + "enter amount of hands you want to play \n"
                    + "enter 0 if you want to stop playing");
            try {
                decision = scan.next();
                // catch exception in case of error
            } catch (InputMismatchException ex) {
                break;
            }
            if ("s".equals(decision)) {
                try {
                    String filename = "advancedBlackJackTableSave.ser";
                    FileOutputStream fos = new FileOutputStream(filename);
                    ObjectOutputStream out = new ObjectOutputStream(fos);
                    out.writeObject(bjt);
                    out.close();
                } catch (IOException ex) {
                    System.out.println("Serialisation error: " + ex);
                    ex.printStackTrace();
                }
            } else if ("l".equals(decision)) {
                try {
                    String filename = "advancedBlackJackTableSave.ser";
                    FileInputStream fis = new FileInputStream(filename);
                    ObjectInputStream in = new ObjectInputStream(fis);
                    bjt = (BlackjackTable) in.readObject();
                    in.close();
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Serialisation error: " + ex);
                }
            } else {
                // How many times to iterate through
                try {
                    hands = Integer.parseInt(decision);
                } catch (NumberFormatException ex) {
                    break;
                }
                if (hands == 0) {
                    break;
                }
                while (hands > 0) {
                    for (Player p : bjt.dealer.players) {
                        if (p.getBalance() <= 0) {
                            breakCount++;
                        }
                    }
                    if (breakCount == bjt.dealer.players.size()) {
                        break;
                    } else {
                        breakCount = 0;
                    }
                    bjt.dealer.dealerHand.hand.clear();
                    for (Player p : bjt.dealer.players) {
                        if (p.getBalance() > 0) {
                            bjt.dealer.bets.add(p.makeBet());
//                            System.out.println("Bet: "+p.getBet());
                        }
                    }
                    // Clear previous hand dealt cards
                    bjt.dealer.dealFirstCards();
                    for (Player p : bjt.dealer.players) {
                        if (bjt.dealer.deck.size() < 13) {
                            bjt.dealer.deck.newDeck();
                            bjt.dealer.deckCount += 1;
                            p.newDeck();
                        }
                        if (p.getBalance() < 0) {
                            bjt.dealer.players.remove(p);
                            break;
                        }
                        while (p.hit() == true) {
                            if (bjt.dealer.deck.size() < 13) {
                                bjt.dealer.deck.newDeck();
                                bjt.dealer.deckCount += 1;
                                p.newDeck();
                            }
                            p.takeCard(bjt.dealer.deck.deal());
                        }
                    }
                    // Add all dealt cards to an array list for advanced
                    // player to card count to make bet

                    bjt.dealer.playDealer();

                    for (Player p : bjt.dealer.players) {
                        p.viewCards(p.getHand().hand);
                        p.viewCards(bjt.dealer.dealerHand.hand);
//                        System.out.println("players card: "+p.getHand().toString());
                    }
//                    System.out.println("Dealers hand: "+bjt.dealer.dealerHand.hand);
                    bjt.dealer.settleBets();
                    bjt.dealer.bets.clear();
                    hands -= 1;
                }

                String fileName = "AverageProfitLossPerDeck.txt";
                PrintWriter outStream = null;
                try {
                    outStream = new PrintWriter(fileName);
                } catch (FileNotFoundException e) {
                    System.out.println("Error opening file" + fileName);
                    System.exit(0);
                }

                for (Player pl : bjt.dealer.players) {
                    outStream.println("\n\n" + pl.getName() + " balance: "
                            + pl.getBalance() + "\n");
                    outStream.println("Total profit/loss: "
                            + (pl.getBalance() - 200));
                    outStream.println("Total deck used: "
                            + bjt.dealer.deckCount);
                    DecimalFormat df = new DecimalFormat("#0.###");
                    outStream.print("Average profit/loss per deck: "
                            + df.format((double) (pl.getBalance() - 200)
                                    / bjt.dealer.deckCount)+"\n\n");
                    outStream.println("\r\n------------------------------\n\r");
                    
                }
                
                outStream.close();

                bjt.dealer.deckCount = 1;
            }
        }
    }

    public static void main(String[] args) {
//        basicGame();
//        IntermediateGame();
//        humanGame();
        advanceGame();
    }
}
