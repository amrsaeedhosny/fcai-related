package BankersAlgorithm;

public class RequestAlgorithm {
	private SafetyAlgorithm safetyAlgorithm;
	int processNumber;
	int[] request;

	RequestAlgorithm() {
		safetyAlgorithm = new SafetyAlgorithm();
	}

	void requestResources(int numOfProcesses, int numOfResources, int[] available, int[][] allocation, int[][] need,
			int processNumber, int[] request) {
		this.processNumber = processNumber;
		this.request = request;

		if (!lessThanRequest(need[processNumber])) {
			if (!lessThanRequest(available)) {

				subtractRequestFrom(available);
				addRequestTo(allocation[processNumber]);
				subtractRequestFrom(need[processNumber]);

				boolean safe = safetyAlgorithm.checkSafety(numOfProcesses, numOfResources, available, need, allocation);

				if (safe) {
					System.out.println("SAFE STATE: Request has been granted successfully!");
				} else {
					addRequestTo(available);
					subtractRequestFrom(allocation[processNumber]);
					addRequestTo(need[processNumber]);
					System.out.println("UNSAFE STATE: Cannot request resources!");
				}

			} else {
				System.out.println("NO RESOURCES AVAILABLE: Cannot request resources!");
			}
		} else {
			System.out.println("EXCEED MAXIMUM RESOURCES: Cannot request resources!");
		}
	}

	boolean lessThanRequest(int[] resources) {
		for (int i = 0; i < request.length; i++) {
			if (resources[i] < request[i]) {
				return true;
			}
		}
		return false;
	}

	void addRequestTo(int[] resources) {
		for (int i = 0; i < request.length; i++) {
			resources[i] += request[i];
		}
	}

	void subtractRequestFrom(int[] resources) {
		for (int i = 0; i < request.length; i++) {
			resources[i] -= request[i];
		}
	}
}
