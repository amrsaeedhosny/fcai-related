package BankersAlgorithm;

public class Banker {
	private int numOfResources;
	private int numOfProcesses;
	private int[] available;
	private int[][] maximum;
	private int[][] allocation;
	private int[][] need;
	private RequestAlgorithm requestAlgorithm;
	private ReleaseAlgorithm releaseAlgorithm;

	public Banker(int numOfResources, int numOfProcesses, int[] available, int[][] maximum, int[][] allocation,
			int[][] need) {
		this.numOfResources = numOfResources;
		this.numOfProcesses = numOfProcesses;
		this.available = available;
		this.maximum = maximum;
		this.allocation = allocation;
		this.need = need;
		this.requestAlgorithm = new RequestAlgorithm();
		this.releaseAlgorithm = new ReleaseAlgorithm();
	}

	public void request(int processNumber, int[] request) {
		requestAlgorithm.requestResources(numOfProcesses, numOfResources, available, allocation, need, processNumber,
				request);
	}

	public void release(int processNumber, int[] release) {
		releaseAlgorithm.releaseResources(available, allocation, need, processNumber, release);
	}

	public void status() {

		// Available resources

		System.out.println("Available:");
		for (char c = 'A'; c < 'A' + numOfResources; c++) {
			System.out.print(c + "\t");
		}

		System.out.println();

		for (int i = 0; i < numOfResources; i++) {
			System.out.print(available[i] + "\t");
		}

		System.out.println();

		// Allocation resources

		System.out.println("Allocation:");
		for (int i = 0; i < numOfProcesses; i++) {
			for (int j = 0; j < numOfResources; j++) {
				System.out.print(allocation[i][j] + "\t");
			}
			System.out.println();
		}

		// Need

		System.out.println("Need:");
		for (int i = 0; i < numOfProcesses; i++) {
			for (int j = 0; j < numOfResources; j++) {
				System.out.print(need[i][j] + "\t");
			}
			System.out.println();
		}

		// Maximum Need

		System.out.println("Maximum Need:");
		for (int i = 0; i < numOfProcesses; i++) {
			for (int j = 0; j < numOfResources; j++) {
				System.out.print(maximum[i][j] + "\t");
			}
			System.out.println();
		}
	}

}
