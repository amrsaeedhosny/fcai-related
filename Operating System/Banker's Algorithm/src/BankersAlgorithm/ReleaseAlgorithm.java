package BankersAlgorithm;

public class ReleaseAlgorithm {
	private int processNumber;
	private int[] release;

	void releaseResources(int[] available, int[][] allocation, int[][] need, int processNumber, int[] release) {
		this.processNumber = processNumber;
		this.release = release;
		if (!lessThanRelease(allocation[processNumber])) {
			addReleaseTo(available);
			subtractReleaseFrom(allocation[processNumber]);
			addReleaseTo(need[processNumber]);
			System.out.println("SUCCESSFUL RELEASE: Release has been granted!");
		} else {
			System.out.println("EXCEED ALLOCATED RESOURCES: Cannot release resources!");
		}
	}

	private boolean lessThanRelease(int[] allocation) {
		for (int i = 0; i < release.length; i++) {
			if (allocation[i] < release[i]) {
				return true;
			}
		}
		return false;
	}

	private void subtractReleaseFrom(int[] resources) {
		for (int i = 0; i < release.length; i++) {
			resources[i] -= release[i];
		}
	}

	private void addReleaseTo(int[] resources) {
		for (int i = 0; i < release.length; i++) {
			resources[i] += release[i];
		}
	}

}
