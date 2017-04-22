import java.util.Scanner;

class TestStatistics {

	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_RESET = "\u001B[0m";

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
		for (int r = 0; r < waysToLose.length; r++) {
			System.out.print(r + " -> ");
			for (int c = 0; c < waysToLose[0].length; c++) {
				assert waysToLose[r][c] >= minPerRow[r];
				if (waysToLose[r][c] == minPerRow[r]) {
					System.out.print(ANSI_YELLOW + (r + min + c) + ":" + waysToLose[r][c] + ANSI_RESET + ", ");
				}
				else {
					System.out.print((r + min + c) + ":" + waysToLose[r][c] + ", ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
