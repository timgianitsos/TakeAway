import java.util.Scanner;
//Inspired by a Cyberchase episode on PBS

public class TakeAway 
{
	final static Scanner scan = new Scanner(System.in);
	static int startPoints = 30;
	static int takeMin = 3; //TODO ensure min is less than max
	static int takeMax = 7;
	static boolean simulate = false;
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_CYAN = "\u001B[36m";

	public static void main(String[] args) { //TODO get game parameters from arguments
		intro();
		boolean keepPlaying = true;
		while (keepPlaying) {
			play();
			keepPlaying = getDecision("Would you like to play again? (y)es or (n)o");
		}
		scan.close();
	}

	static void intro() {
		display(colorString("\nWelcome to Takeaway.", ANSI_YELLOW));
		display("You will play against the computer. The game starts with " + colorString(startPoints + "", ANSI_GREEN) + " points and you will ");
		display("both take turns subtracting between " + colorString(takeMin + " and " + takeMax, ANSI_YELLOW) + " points away from the total. ");
		display("The player who is " + colorString("left with less than " + takeMin, ANSI_YELLOW) + " to take at the beginning of their turn loses.");
	}

	//return true if first player wins
	static boolean play() {
		boolean playerTurn = getDecision("Would you like to go first? " + colorString("(y)es", ANSI_CYAN) + " or " + colorString("(n)o", ANSI_RED));
		
		int points = startPoints;
		display("\nThe point total is " + colorString(points + "", ANSI_GREEN) + ".");
		while (points >= takeMin) {
			if (playerTurn) {
				display("It's your turn " + colorString("player", ANSI_CYAN) + ". How many points will you take away?");
				points -= getInt(points);
			}
			else {
				int pointsTaken = AI.getMove(points);
				display("Your " + colorString("opponent", ANSI_RED) + " takes " + colorString(pointsTaken + "", ANSI_RED) + " point(s).");
				points -= pointsTaken;
			}
			assert points >= 0;
			display("\nThe point total is " + colorString(points + "", ANSI_GREEN) + ".");
			playerTurn = !playerTurn;
		}
		display("\n" + (playerTurn ? colorString("You lose!", ANSI_RED): colorString("Impossible! You win...", ANSI_CYAN)) + "\n");
		return !playerTurn;
	}

	static boolean getDecision(String message) {
		if (simulate) {
			return true;
		}
		else {
			display(message);
			return scan.nextLine().equalsIgnoreCase("y");
		}
	}

	static int getInt(int points) {
		if (simulate) {
			return AI.getMove(points);
		}
		else {
			//TODO implement fail proof logic to get check input
			System.out.print(ANSI_CYAN);
			int result = scan.nextInt();
			System.out.print(ANSI_RESET);
			scan.nextLine();
			return result;
		}
	}

	static void display(String message) {
		if (!simulate) {
			System.out.println(message);
		}
	}

	static String colorString(String message, String color) {
		return color + message + ANSI_RESET;
	}
}


	// prompt the user for an int. The String prompt will
	// be printed out. I expect key is connected to System.in.
	// public static int getInt(Scanner key, String prompt, char[][] b) {
	// 	int result = -1;
	// 	boolean invalidNumber;
	// 	do {
	// 		System.out.print(prompt);
	// 		while (!key.hasNextInt()) {
	// 			String notAnInt = key.nextLine();
	// 			System.out.println("\n" + notAnInt + " is not an integer.");
	// 			System.out.print(prompt);
	// 		}
	// 		result = key.nextInt();
	// 		invalidNumber = result < 1 || result > COLUMNS || b[0][result - 1] != EMPTY;
	// 		if (invalidNumber) {
	// 			System.out.println("\n" + result + " is not a valid column.");
	// 			key.nextLine();
	// 		}
	// 	} while (invalidNumber);
	// 	key.nextLine();
	// 	return result;
	// }


