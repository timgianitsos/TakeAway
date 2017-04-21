import java.util.Scanner;
//Inspired by a Cyberchase episode on PBS
//TODO add output coloring

public class TakeAway 
{
	final static Scanner scan = new Scanner(System.in);
	final static int startPoints = 25;
	final static int takeMin = 3; //TODO ensure min is less than max
	final static int takeMax = 7;

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
		System.out.println("\nWelcome to Takeaway.");
		System.out.println("You will play against the computer. The game starts with " + startPoints + " points and you will both ");
		System.out.println("take turns subtracting between " + takeMin + " and " + takeMax + " points away from the total. ");
		System.out.println("The player who is left with too few points to take at the beginning of their turn loses.");
	}

	static void play() {
		System.out.println("Would you like to go first? (y)es or (n)o");
		boolean playerTurn = scan.nextLine().equalsIgnoreCase("y");
		
		int points = startPoints;
		System.out.println("\nThe point total is " + points + ".");
		while (points >= takeMin) {
			if (playerTurn) {
				System.out.println("It's your turn player. How many points will you take away?");
				points -= getInt();
			}
			else {
				int position = points % (takeMin + takeMax);
				double rand = Math.random();
				int pointsTaken = position < takeMin ? (int)(rand * (takeMax - takeMin + 1) + takeMin):
					(int)(rand * (Math.min(position, takeMax) - Math.max(position - takeMin + 1, takeMin) + 1)) + Math.max(position - takeMin + 1, takeMin);
				System.out.println("Your opponent takes " + pointsTaken + " point(s).");
				points -= pointsTaken;
			}
			assert points >= 0;
			System.out.println("\nThe point total is " + points + ".");
			playerTurn = !playerTurn;
		}
		System.out.println("\n" + (playerTurn ? "You lose!": "Impossible! You win...") + "\n");
	}

	static int getInt() {
		//TODO implement fail proof logic to get check input
		int result = scan.nextInt();
		scan.nextLine();
		return result;
	}
}
