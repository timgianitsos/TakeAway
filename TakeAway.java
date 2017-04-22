import java.util.Scanner;
//Inspired by a Cyberchase episode on PBS

public class TakeAway 
{
	final static Scanner scan = new Scanner(System.in);
	final static int startPoints = 30;
	final static int takeMin = 3; //TODO ensure min is less than max
	final static int takeMax = 7;
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static void main(String[] args) { //TODO get game parameters from arguments
		intro();
		boolean keepPlaying = true;
		while (keepPlaying) {
			play();
			System.out.println("Would you like to play again? (y)es or (n)o");
			keepPlaying = scan.nextLine().equalsIgnoreCase("y");
		}
		scan.close();
	}

	static void intro() {
		System.out.println(colorString("\nWelcome to Takeaway.", ANSI_YELLOW));
		System.out.println("You will play against the computer. The game starts with " + colorString(startPoints + "", ANSI_GREEN) + " points and you will both ");
		System.out.println("take turns subtracting between " + colorString(takeMin + " and " + takeMax, ANSI_YELLOW) + " points away from the total. ");
		System.out.println("The player who is " + colorString("left with less than " + takeMin, ANSI_YELLOW) + " to take at the beginning of their turn loses.");
	}

	static void play() {
		System.out.println("Would you like to go first? " + colorString("(y)", ANSI_CYAN) + "es or " + colorString("(n)", ANSI_RED) + "o");
		boolean playerTurn = scan.nextLine().equalsIgnoreCase("y");
		
		int points = startPoints;
		System.out.println("\nThe point total is " + colorString(points + "", ANSI_GREEN) + ".");
		while (points >= takeMin) {
			if (playerTurn) {
				System.out.println("It's your turn " + colorString("player", ANSI_CYAN) + ". How many points will you take away?");
				points -= getInt();
			}
			else {
				int pointsTaken = AI.getMove(points);
				System.out.println("Your " + colorString("opponent", ANSI_RED) + " takes " + colorString(pointsTaken + "", ANSI_RED) + " point(s).");
				points -= pointsTaken;
			}
			assert points >= 0;
			System.out.println("\nThe point total is " + colorString(points + "", ANSI_GREEN) + ".");
			playerTurn = !playerTurn;
		}
		System.out.println("\n" + (playerTurn ? colorString("You lose!", ANSI_RED): colorString("Impossible! You win...", ANSI_CYAN)) + "\n");
	}

	static int getInt() {
		//TODO implement fail proof logic to get check input
		System.out.print(ANSI_CYAN);
		int result = scan.nextInt();
		System.out.print(ANSI_RESET);
		scan.nextLine();
		return result;
	}

	static String colorString(String message, String color) {
		return color + message + ANSI_RESET;
	}
}
