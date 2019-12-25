package BankersAlgorithm;

public class SafetyAlgorithm {
	private boolean[] finish;
	private int[] work;

	boolean checkSafety(int numOfProcesses, int numOfResources, int[] available, int[][] need, int[][] allocation) {
		setFinish(numOfProcesses);
		setWork(numOfResources, available);

		while (notFinish()) {
			boolean deadlock = true;

			for (int i = 0; i < numOfProcesses; i++) {
				if (!finish[i] && lessThanWork(need[i])) {
					addToWork(allocation[i]);
					finish[i] = true;
					deadlock = false;
				}
			}

			if (deadlock) {
				break;
			}
		}

		return !notFinish();
	}

	private void setFinish(int numOfProcesses) {
		finish = new boolean[numOfProcesses];
		for (int i = 0; i < numOfProcesses; i++) {
			finish[i] = false;
		}
	}

	private void setWork(int numOfResources, int[] available) {
		work = new int[numOfResources];
		for (int i = 0; i < numOfResources; i++) {
			work[i] = available[i];
		}
	}

	private boolean notFinish() {
		for (int i = 0; i < finish.length; i++) {
			if (!finish[i]) {
				return true;
			}
		}
		return false;
	}

	private boolean lessThanWork(int[] need) {
		for (int i = 0; i < work.length; i++) {
			if (work[i] < need[i]) {
				return false;
			}
		}
		return true;
	}

	private void addToWork(int[] allocation) {
		for (int i = 0; i < work.length; i++) {
			work[i] += allocation[i];
		}
	}

}
