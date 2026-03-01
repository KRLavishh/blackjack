import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Blackjack {

    public static ArrayList<String> createDeck() {
        ArrayList<String> deck = new ArrayList<String>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        for (String suit : suits) {
            for (String value : values) {
                String card = value + " of " + suit;
                deck.add(card); 
            }
        }
        return deck; 
    }

    public static void shuffleDeck(ArrayList<String> deck) {
        Collections.shuffle(deck);
    }

    public static void playAgain(String playerName){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to play again?");
        System.out.print("Type y or n: ");
        String decision = scanner.nextLine().toLowerCase();
        if (decision.equals("y")){
            main(null);
        }
        else if (decision.equals("n")){
            System.out.println("Play again sometime, " + playerName + "!");
        }
        else{
            System.out.println("Invalid decision");
        }
        scanner.close();
    }

    public static void dealCard(ArrayList<String> deck, ArrayList<String> hand){
        String cardOne = deck.remove(0);
        hand.add(cardOne);
    }

    public static int calculateHand(ArrayList<String> hand){
        int total = 0;
        for (String card: hand){
            String value = card.split(" ")[0];
            if (value.equals("Jack") || value.equals("King") || value.equals("Queen")){
                total += 10;
            }
            else if (value.equals("Ace")){
                if (total + 11 > 21){
                    total += 1;
                }
                else{
                    total += 11;
                }
            }
            else{
                total += Integer.parseInt(value);
            }
        }
        return total;
    }

    public static void displayHand(ArrayList<String> hand, String owner){
        int total = calculateHand(hand);
        System.out.println(owner + "'s hand: " + String.join(", ", hand) + " - Total: " + total);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        ArrayList<String> gameDeck = createDeck();
        shuffleDeck(gameDeck);

        ArrayList<String> playerHand = new ArrayList<String>();
        ArrayList<String> dealerHand = new ArrayList<String>();

        dealCard(gameDeck, playerHand);
        dealCard(gameDeck, playerHand);
        dealCard(gameDeck, dealerHand);
        dealCard(gameDeck, dealerHand);
        displayHand(playerHand, "Player");
        displayHand(dealerHand, "Dealer");


        while (true) {
            System.out.print("Type h or s: ");
            String decision = scanner.nextLine().toLowerCase();

                if (decision.equals("h")) {
                    dealCard(gameDeck, playerHand);
                    displayHand(playerHand, "Player");
            int total = calculateHand(playerHand);
                if (total > 21) {
                    System.out.println("Bust! You lose...");
                    playAgain(playerName);
                    scanner.close();
                    return;
                }
            } else if (decision.equals("s")) {
                break;
            } else {
                System.out.println("Invalid choice, try again.");
            }
        }

    System.out.println("\nDealer's turn!");
    displayHand(dealerHand, "Dealer");

    while (calculateHand(dealerHand) < 17){
        System.out.println("Dealer hits!");
        dealCard(gameDeck, dealerHand);
        displayHand(dealerHand, "Dealer");
    }

    if (calculateHand(dealerHand) > 21){
        System.out.println("Dealer busts! You win!");
        playAgain(playerName);
    }
    else{
        if (calculateHand(playerHand) > calculateHand(dealerHand)){
            System.out.println("You win!");
            playAgain(playerName);
        }
       else if (calculateHand(dealerHand) > calculateHand(playerHand)){
            System.out.println("You lose..");
            playAgain(playerName);
        }
        else{
            System.out.println("It's a tie!");
            playAgain(playerName);
        }
    }
        scanner.close();
    }
}
