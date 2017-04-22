import java.util.Scanner;


/*
Poison numbers are values that, if a player starts their turn on, guarentees them to lose if their opponent plays optimally.

If the player is on a poison number, the AI (in hard mode) will ensure that they keep landing on poison numbers, ultimately 
causing them to lose.

If the AI starts on a poison number, its opponent could theoretically ensure that the AI loses. However, the AI will attempt to 
free itself by making decisions that leave its opponent with the LEAST number of ways to ensure AI failure.

This class is an attempt to gain insight about the best move the AI can make when it finds itself on a poison number. To view stats, 
enter two parameters of the game: the lower bound that can be taken away (we will call "min"), and the upper bound that can be taken 
away (we will call "max"). The starting number of points in the game is an irrelevant parameter to the strategy.

For example, entering: 
7 10
will produce a chart that looks like this: 
0 -> 7:4, 8:3, 9:2, 10:1, 
1 -> 7:4, 8:4, 9:3, 10:2, 
2 -> 7:4, 8:4, 9:4, 10:3, 
3 -> 7:4, 8:4, 9:4, 10:4, 
4 -> 7:3, 8:4, 9:4, 10:4, 
5 -> 7:2, 8:3, 9:4, 10:4, 
6 -> 7:1, 8:2, 9:3, 10:4, 
with some numbers colored green, some colored red, and others not colored

What does this mean? First, a discussion of poison numbers. 

Since min was 7, this means that the numbers 0 through 6 are poison numbers (because if we start 
our turn with them, we automatically lose). However, the numbers 17 through 23 are also poison numbers, as well as 34 through 40.
Assume i is the number a turn is started on. Then i is a poison number if i % 17 < 7. The 17 is simply (min + max), whereas 
the 7 is simply min.

The numbers [0, 17, 34, 51 ... etc] all correspond to each other because they are the first poison numbers out of a consecutive sequence of 6 
other numbers - this means that the strategy used when landing on any of them is identical. The same correspondence applies between 
[1, 18, 35, 52 ... etc], and [2, 19, 36, 53 ... etc] and so on until [6, 23, 40, 57 ... etc].

In the chart, the first row labeled with 0 corresponds to the fact that i % 17 == 0. This corresponds to landing on [0, 17, 34, 51 ... etc].
Obviously starting a turn on 0 causes us to lose immediately. But 17, 34, 51 etc are the poison numbers in the set which we try to 
devise a strategy from. Again, any strategy that applies to 17 also applies to 34 because they are in the same set. If we look right of 
the arrow, the number before each colon is the number we take away, and the number after each colon is the number of ways our opponent 
can put us back on a poison number - we want to minimize this. 

For example, say we start on 34, corresponding to row 0 in the chart. The numbers that the game lets us take away must be between 7 and 10. 

Assume we take 7. Our opponent is left with 27. The opponent can choose 7, 8, 9 or 10.
27 - 7 = 20 (poison), 
27 - 8 = 19 (poison), 
27 - 9 = 18 (poison), 
27 - 10 = 17 (poison) 
There are 4 ways that our opponent can keep us on a poison number.

Assume we take 8. Our opponent is left with 26. The opponent can choose 7, 8, 9 or 10.
26 - 7 = 19 (poison), 
26 - 8 = 18 (poison), 
26 - 9 = 17 (poison) 
26 - 10 = 16 
There are 3 ways that our opponent can keep us on a poison number.

Assume we take 9. Our opponent is left with 25. The opponent can choose 7, 8, 9 or 10.
25 - 7 = 18 (poison), 
25 - 8 = 17 (poison), 
25 - 9 = 16, 
25 - 10 = 15 
There are 2 ways that our opponent can keep us on a poison number.

Assume we take 10. Our opponent is left with 24. The opponent can choose 7, 8, 9 or 10.
25 - 7 = 17 (poison), 
25 - 8 = 16, 
25 - 9 = 15, 
25 - 10 = 14 
There is 1 way that our opponent can keep us on a poison number.

In all cases, if the opponent plays optimally, they can keep us on a poison number. But since there are the fewest ways of being poisoned 
by choosing 10, we should choose 10.

If a number is colored green in the chart, that means it has the minimum number of losses for that row. 
If a number is colored red in the chart, that means EVERY move the opponent makes will keep us poisoned.
When both conditions apply, the values will be green.
*/
class TestStatistics {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int min = scan.nextInt();
		while (min != 0) {
			int max = scan.nextInt();

			int[][] waysToLose = new int[min][max - min + 1];
			int[] minPerRow = new int[waysToLose.length];
			for (int r = 0; r < waysToLose.length; r++) {
				for (int c = 0; c < waysToLose[0].length; c++) {
					int takeFrom = min + max + r;
					int take = min + c;
					takeFrom -= take;

					int losses = 0;
					for (int i = min; i <= max; i++) {
						if (takeFrom - i >= 0 && takeFrom - i < min) {
							losses++;
						}
					}
					waysToLose[r][c] = losses;
					minPerRow[r] = minPerRow[r] == 0 ? losses: Math.min(minPerRow[r], losses);
				}
			}

			printWaysToLose(waysToLose, minPerRow, min, max);

			min = scan.nextInt();
		}
	}

	static void printWaysToLose(int[][] waysToLose, int[] minPerRow, int min, int max) {
		int firstColumnLength = ((min + waysToLose[0][0]) + "").length();
		int secondColumnLength = (min + "").length();
		for (int r = 0; r < waysToLose.length; r++) {
			System.out.print(r + "\t-> ");
			for (int c = 0; c < waysToLose[0].length; c++) {
				assert waysToLose[r][c] >= minPerRow[r];
				String color = waysToLose[r][c] == minPerRow[r] ? ANSI_GREEN: waysToLose[r][c] == max - min + 1 ? ANSI_RED: "";
				String endColor = color.equals("") ? "": ANSI_RESET;
				System.out.printf(color + "%" + firstColumnLength + "s:%-" + (secondColumnLength + 1) + "s" + endColor + " ", (min + c), waysToLose[r][c] + ",");
			}
			System.out.println();
		}
		System.out.println();
	}
}
