import java.util.Scanner;
//Inspired by a Cyberchase episode on PBS
//TODO add output coloring

public class TakeAway 
{
	final static Scanner scan = new Scanner(System.in);
	final static int startPoints = 1000;
	final static int takeMin = 8; //TODO ensure min is less than max
	final static int takeMax = 30;
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
		boolean play = true;
		while (play) {
			play();
			System.out.println("Would you like to play again? (y)es or (n)o");
			play = scan.nextLine().equalsIgnoreCase("y");
		}
	}

	static void intro() {
		System.out.println(ANSI_YELLOW + "\nWelcome to Takeaway." + ANSI_RESET);
		System.out.println("You will play against the computer. The game starts with " + ANSI_GREEN + startPoints + ANSI_RESET + " points and you will both ");
		System.out.println("take turns subtracting between " + ANSI_YELLOW + takeMin + " and " + takeMax + ANSI_RESET + " points away from the total. ");
		System.out.println("The player who is " + ANSI_YELLOW + "left with too few points" + ANSI_RESET + " to take at the beginning of their turn loses.");
	}

	static void play() {
		System.out.println("Would you like to go first? " + ANSI_CYAN + "(y)" + ANSI_RESET + "es or " + ANSI_RED + "(n)" + ANSI_RESET + "o");
		boolean playerTurn = scan.nextLine().equalsIgnoreCase("y");
		
		int points = startPoints;
		System.out.println("\nThe point total is " + ANSI_GREEN +  points + ANSI_RESET + ".");
		while (points >= takeMin) {
			if (playerTurn) {
				System.out.println("It's your turn " + ANSI_CYAN + "player" + ANSI_RESET + ". How many points will you take away?");
				System.out.print(ANSI_CYAN);
				points -= getInt();
				System.out.print(ANSI_RESET);
			}
			else {
				int position = points % (takeMin + takeMax);
				double rand = Math.random();
				int pointsTaken = position < takeMin ? (int)(rand * (takeMax - takeMin + 1) + takeMin): //Improve random behavior
					(int)(rand * (Math.min(position, takeMax) - Math.max(position - takeMin + 1, takeMin) + 1)) + Math.max(position - takeMin + 1, takeMin);
				System.out.println("Your " + ANSI_RED + "opponent" + ANSI_RESET + " takes " + ANSI_RED + pointsTaken + ANSI_RESET + " point(s).");
				points -= pointsTaken;
			}
			assert points >= 0;
			System.out.println("\nThe point total is " + ANSI_GREEN +  points + ANSI_RESET + ".");
			playerTurn = !playerTurn;
		}
		System.out.println("\n" + (playerTurn ? ANSI_RED + "You lose!" + ANSI_RESET: ANSI_CYAN + "Impossible! You win..." + ANSI_RESET) + "\n");
	}

	static int getInt() {
		//TODO implement fail proof logic to get check input
		int result = scan.nextInt();
		scan.nextLine();
		return result;
	}
}
