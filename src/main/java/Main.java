import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    static String[] moves;
    static String error;
    static boolean moveIsMade;
    static boolean exit;
    static int userMove;
    static int computerMove;
    static Rules rules;
    static int resultOfGame = -123;
    static String key;
    static String HMAC;
    static final String hmacSHA256Algorithm = "HmacSHA256";
    static Table table;

    public static void main(String[] args) {
        moves = args;
        table = new Table(moves);

        key = Key.hexString(Key.generateKey().getEncoded());

        if(!verifyInput()) {
            showError();
            return;
        }

        rules = new Rules(moves);

        do {
            startGame();
        } while(!exit);
    }

    private static boolean verifyInput() {
        // If number < 3
        if (moves.length < 3) {
            error = "You entered " + moves.length + " turns, " +
                    "but number of turns must be greater than or equal to 3 (for example, 3 or 8).";
            return false;
        }

        // If number is not odd
        if (moves.length % 2 == 0) {
            error = "You entered an even number of turns (" + moves.length + ")" +
                    ", but number of turns must be odd (for example, 5 or 9)";
            return false;
        }

        // If there are same turns
        for (int i = 0; i < moves.length; i++) {
            for (int j = i + 1; j < moves.length; j++) {
                if (i != j && moves[i].equals(moves[j])) {
                    error = "Turns can't be same.";
                    return false;
                }
            }
        }

        return true;
    }

    private static void startGame(){
        // Computer's move
        computerMove = getRandomNumber(1, moves.length + 1);

        // HMAC generation
        try {
            HMAC = Key.generateHMAC(hmacSHA256Algorithm, moves[computerMove - 1], key);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        // Show HMAC
        System.out.println("HMAC: " + HMAC);

        // User's move
        moveIsMade = false;
        exit = false;

        do {
            if (exit) return;

            if (!moveIsMade) {
                showMenu();
                userMakesMove();
                if(moveIsMade) break;
                System.out.println();
            }

        } while (true);


        // Show computer's move
        System.out.println("Computer's move: " + moves[computerMove - 1]);

        // Show result of game
        resultOfGame = rules.rules[computerMove - 1][userMove - 1];

        if (resultOfGame == 1) {
            System.out.println("You win!");
        } else if (resultOfGame == -1) {
            System.out.println("Computer wins!");
        } else if (resultOfGame == 0) {
            System.out.println("Draw");
        }

        // Show key
        System.out.println("HMAC key: " + key + "\n");

        System.out.println("***Next game***\n");
    }

    private static void showMenu() {
        System.out.println("Available moves: ");
        for (int i = 0; i < moves.length; i++) {
            System.out.printf("%2d - %s\n", i + 1, moves[i]);
        }
        System.out.println(" 0 - exit");
        System.out.println(" ? - help");

    }

    private static void showError() {
        System.out.println("Error! " + error);
        System.out.println("Example of correct moves: " +
                "rock paper scissors lizard Spock");
    }

    private static void userMakesMove(){
        System.out.print("Please, enter your move: ");
        String move = "";
        Scanner scanner = new Scanner(System.in);
        move = scanner.nextLine();

        if(move.equals("?")){
            System.out.println("Table: ");
            String t = table.buildTable();
            System.out.println(t);
        } else if(move.equals("0")){
            System.out.println("Exit");
            exit = true;
        } else{
            int mv;

            try {
                mv = Integer.parseInt(move);
            } catch (NumberFormatException e) {
                mv = -1;
            }

            if (mv > moves.length || mv < 1) {
                System.out.println("Please, choose a correct move");
                moveIsMade = false;
            } else {
                System.out.println("Your move: " + moves[mv - 1]);
                userMove = mv;
                moveIsMade = true;
            }
        }
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min); // in range [min, max)
    }
}