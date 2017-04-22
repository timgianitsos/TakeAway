class AI {

	static boolean easyMode = false;

	public static int getMove(int currentPoints) {
		int min = TakeAway.takeMin;
		int max = TakeAway.takeMax;
		if (easyMode) {
			return (int)(Math.random() * (max - min + 1)) + min;
		}
		else {
			int position = currentPoints % (min + max);
			double rand = Math.random();
			int pointsTaken;
			if (position < min) {
				pointsTaken = position <= min / 2 ? min: max;
			}
			else {
				pointsTaken = (int)(rand * (Math.min(position, max) - Math.max(position - min + 1, min) + 1)) + Math.max(position - min + 1, min);
			}	
			return pointsTaken;
		}
	}
}
