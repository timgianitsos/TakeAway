class AI {

	static boolean easyMode = false;

	public static int getMove(int currentPoints) {
		int min = TakeAway.takeMin;
		int max = TakeAway.takeMax;
		if (easyMode) {
			int pointsTaken = (int)(Math.random() * (max - min + 1)) + min;
			if (pointsTaken > currentPoints) {
				pointsTaken = currentPoints;
			}
			return pointsTaken;
		}
		else {
			int position = currentPoints % (min + max);
			double rand = Math.random();
			int pointsTaken;
			if (position < min) {
				pointsTaken = min % 2 == 1 && position == min / 2 ? min: position < min / 2 ? max: min;
			}
			else {
				pointsTaken = (int)(rand * (Math.min(position, max) - Math.max(position - min + 1, min) + 1)) + Math.max(position - min + 1, min);
			}	
			return pointsTaken;
		}
	}

	//For testing the AI
	//The AI should lose when starting on a poison number and playing against itself
	public static void main(String[] args) {
		System.out.print("\nTesting AI");
		TakeAway.simulate = true;
		int limit = 100;
		int simulations = 40;
		for (int startPoints = 1; startPoints <= limit; startPoints++) {
			for (int max = 1; max <= startPoints; max++) {
				for (int min = 1; min <= max; min++) {
					for (int i = 0; i < simulations; i++) {
						TakeAway.startPoints = startPoints;
						TakeAway.takeMax = max;
						TakeAway.takeMin = min;
						boolean firstPlayerWin = TakeAway.play();
						if (firstPlayerWin && startPoints % (max + min) < min) {
							//The first player won despite starting on a poison number
							throw new IllegalStateException("Unexpected result for startPoints=" + startPoints + ", min=" + min + ", max=" + max);
						}
					}
				}
			}
			if (startPoints % (limit / 20) == 0){
				System.out.print(".");
			}
		}
		TakeAway.simulate = false;
		System.out.println("\nSuccessful simulation!");
	}
}
