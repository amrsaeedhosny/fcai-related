import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import BankersAlgorithm.Banker;

public class Main {

	static Scanner in;

	public static void main(String[] args) {
		
		in = new Scanner(System.in);
				
		System.out.println("-------------------------");
		System.out.println(" Please Enter File Name: ");
		System.out.println("-------------------------");
		
		String fileName = in.nextLine();
		
		try {
			in = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("INVALID FILE NAME: File name is not valid!");
			return;
		}
		
		int numOfResources = Integer.valueOf(in.next());
		int numOfProcesses = Integer.valueOf(in.next());
		int[] available = new int[numOfResources];
		int[][] maximum = new int[numOfProcesses][numOfResources];
		int[][] allocation = new int[numOfProcesses][numOfResources];
		int[][] need = new int[numOfProcesses][numOfResources];

		for (int i = 0; i < numOfResources; i++) {
			available[i] = Integer.valueOf(in.next());
		}

		for (int i = 0; i < numOfProcesses; i++) {
			for (int j = 0; j < numOfResources; j++) {
				maximum[i][j] = Integer.valueOf(in.next());
				need[i][j] = maximum[i][j];
			}
		}

		Banker banker = new Banker(numOfResources, numOfProcesses, available, maximum, allocation, need);

		System.out.println("----------------------------------");
		System.out.println(" Banker's Algorithm Command List: ");
		System.out.println("----------------------------------");
		System.out.println(" 1- Request PNUM R1 R2 R3... Rn   ");
		System.out.println(" 2- Release PNUM R1 R2 R3... Rn   ");
		System.out.println(" 3- Status                        ");
		System.out.println(" 4- Quit                          ");

		String command;
		int processNumber;
		int[] request;
		int[] release;
		in = new Scanner(System.in);
		
		command = in.next();

		while (!command.equals("Quit")) {
			switch (command) {
			case "Request":
				processNumber = in.nextInt();
				request = new int[numOfResources];
				for (int i = 0; i < numOfResources; i++) {
					request[i] = in.nextInt();
				}
				banker.request(processNumber, request);
				break;
			case "Release":
				processNumber = in.nextInt();
				release = new int[numOfResources];
				for (int i = 0; i < numOfResources; i++) {
					release[i] = in.nextInt();
				}
				banker.release(processNumber, release);
				break;
			case "Status":
				banker.status();
				break;
			default:
				System.out.println("NOT AVAILABLE COMMAND: Please check command list");
			}
			command = in.next();
		}

	}

}
