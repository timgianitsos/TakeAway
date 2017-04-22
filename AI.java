class AI {

	static boolean easyMode = false;

	public static int getMove(int currentPoints) {
		if (easyMode) {
			return (int)(Math.random() * (TakeAway.takeMax - TakeAway.takeMin + 1)) + TakeAway.takeMin;
		}
		else {
			int position = currentPoints % (TakeAway.takeMin + TakeAway.takeMax);
			double rand = Math.random();
			int pointsTaken;
			if (position < TakeAway.takeMin) {
				pointsTaken = (int)(rand * (TakeAway.takeMax - TakeAway.takeMin + 1) + TakeAway.takeMin); //Improve random behavior
			}
			else {
				pointsTaken = (int)(rand * (Math.min(position, TakeAway.takeMax) - Math.max(position - TakeAway.takeMin + 1, TakeAway.takeMin) + 1)) + Math.max(position - TakeAway.takeMin + 1, TakeAway.takeMin);
			}	
			return pointsTaken;
		}
	}
}
