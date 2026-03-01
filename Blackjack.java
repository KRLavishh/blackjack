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
                deck.add(card); // inside inner loop
            }
        }
        return deck; // inside method, outside loops
    }

    public static void shuffleDeck(ArrayList<String> deck) {
        Collections.shuffle(deck);
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

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Type h or s: ");
            String decision = scanner.nextLine().toLowerCase();

                if (decision.equals("h")) {
                    dealCard(gameDeck, playerHand);
                    displayHand(playerHand, "Player");
            int total = calculateHand(playerHand);
                if (total > 21) {
                    System.out.println("Bust! You lose...");
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
    }
    else{
        if (calculateHand(playerHand) > calculateHand(dealerHand)){
            System.out.println("You win!");
        }
       else if (calculateHand(dealerHand) > calculateHand(playerHand)){
            System.out.println("You lose..");
        }
        else{
            System.out.println("It's a tie!");
        }
    }
        scanner.close();
    }
}
