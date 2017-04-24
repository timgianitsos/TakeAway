import java.util.Scanner;

//Inspired by a Cyberchase episode on PBS
public class TakeAway 
{
	final static Scanner scan = new Scanner(System.in);
	static int startPoints = 20;
	static int takeMin = 1;
	static int takeMax = 3;
	static boolean simulate = false;
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_CYAN = "\u001B[36m";

	public static void main(String[] args) {
		parseArguments(args);
		intro();
		boolean keepPlaying = true;
		while (keepPlaying) {
			play();
			keepPlaying = getDecision("Would you like to play again? (y)es or (n)o");
		}
		scan.close();
	}

	static void parseArguments(String[] args) {
		if (args != null && args.length == 1 && args[0].equals("-h")) {
			display("Usage: java " + TakeAway.class.toString() + " [starting_points] [take_minimum] [take_maximum] [-e | -h]");
			display("\tstarting_points is the number of points the game starts with");
			display("\ttake_minimum is the minimum points that can be taken on a turn");
			display("\ttake_maximum is the maximum points that can be taken on a turn");
			display("\t-e is easy mode, -h is hard mode. The default is hard");
			System.exit(0);
		}
		else if (args != null && args.length != 0) {
			try {
				startPoints = Integer.parseInt(args[0]);
				takeMin = Integer.parseInt(args[1]);
				takeMax = Integer.parseInt(args[2]);
				AI.easyMode = args[3].startsWith("-e");
				if (startPoints < 1) {
					throw new Exception();
				}
				if (takeMin > takeMax || takeMin < 1 || takeMax < 1) {
					throw new Exception();
				}
			}
			catch (Exception e) {
				System.err.println(colorString("Invalid command line arguments. Using defaults...", ANSI_RED));
				startPoints = 20;
				takeMin = 1;
				takeMax = 3;
				AI.easyMode = false;
			}
		}
	}

	static void intro() {
		display(colorString("\nWelcome to Takeaway. Use the -h flag for help", ANSI_YELLOW));
		display("You will play against the computer. The game starts with " + colorString(startPoints + "", ANSI_GREEN) + " points and you will ");
		display("both take turns subtracting between " + colorString(takeMin + " and " + takeMax, ANSI_YELLOW) + " points away from the total. ");
		display("The player who is " + colorString("left with less than " + takeMin, ANSI_YELLOW) + " to take at the beginning of their turn loses.");
	}

	//return true if the player wins, or if its a simulation return true if player 1 wins 
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
			int result = -1;
			boolean invalidNumber;
			do {
				System.out.print(ANSI_CYAN);
				while (!scan.hasNextInt()) {
					System.out.print(ANSI_RESET);
					String notAnInt = scan.nextLine();
					System.out.println(notAnInt + " is not an integer.");
					System.out.print(ANSI_CYAN);
				}
				System.out.print(ANSI_RESET);
				result = scan.nextInt();
				scan.nextLine();
				invalidNumber = result < takeMin || result > takeMax;
				if (invalidNumber) {
					System.out.println(result + " is not between " + takeMin + " and " + takeMax + ".");
				}
				else {
					invalidNumber = result > points;
					if (invalidNumber) {
						System.out.println("You cannot take more than the " + points + " remaining point(s)");
					}
				}
			} while (invalidNumber);
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
