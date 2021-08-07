package com.cognixia.jump.intermediatejava.assignments.digitalanimalzoo;

import java.io.EOFException;

/*
 * Create a prompt on your console that allows you to take in information for
 * a ZooAnimal and store it in a file utilizing a character or byte stream.
 * 
 * Should be able to give the user the option to read in all the zoo animals
 * stored in your file
 * 
 * Ensure you don't overwrite any previously stored information
 * 
 * BONUS:
 * When a user tries to add in an animal that is already listed in the file, it tells
 * the user it already exists and asks them to try again
 * 
 * Make sure all animals are listed in alphabetical order in the file
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import com.cognixia.jump.intermediatejava.assignments.employeejavastreams.DataEntryException;

public class DigitalZooAnimal {
	
	public static void main (String[] args) {
		
		boolean done = false;
		Scanner sc = new Scanner(System.in);
		int opt;
		
		Set<ZooAnimal> zooAnimals = new TreeSet<ZooAnimal>();
		
		File zooAnimalFile = new File("resources/assignments/zooAnimals.txt");
		
		System.out.println(zooAnimalFile.length());
		
		// Checks if the file exists 
		try {
			// if the file doesn't exist
			if (!zooAnimalFile.exists()) {
				
				// create the file
				zooAnimalFile.createNewFile();
				
				System.out.println("File created.");
				
			}
		} catch (IOException e) {
			System.out.println("ERR: Unable to load resource.");
		}
		
		// user is using the program
		while (!done) {
			// prompt user for selection
			opt = getValidatedInteger(sc, displayOptions(), 3);
			
			// execute selected option
			switch(opt)  {
				// Display all stored animals
				case 1:
					// Retrieve animal objects from file
					zooAnimals = readFromFile(zooAnimalFile);
					
					// if there are no animals yet
					if (zooAnimals.isEmpty()) {
						System.out.println("There are no animals in the zoo!");
					}
					
					// existing file content
					else {
						// Print out the animals for display
						System.out.println("ZooAnimals");
						System.out.println("============================");
						
						zooAnimals.stream().forEach(System.out::println);
					}
					
					break;
					
				// Add new ZooAnimal
				case 2:
					
					// Read from file the current zoo animals if there are
					// any contents
					if (zooAnimalFile.length() > 0) {
						zooAnimals = readFromFile(zooAnimalFile);
					}
					
					// Retrieve validated zoo animal
					ZooAnimal newAnimal = getValidatedZooAnimal(sc, zooAnimals);
					
					zooAnimals.add(newAnimal);
					
					// Write animal to file
					writeToFile(zooAnimalFile, zooAnimals);
					
					break;
					
				// Done with the program
				case 3:
					done = true;
					System.out.println("Have a great day!");
					break;
				default:
					System.out.println("Invalid input.");
					break;
			}
		}
	}
	
	public static ZooAnimal getValidatedZooAnimal(Scanner sc, Set<ZooAnimal> zooAnimals) {
		boolean valid = false;
		ZooAnimal newAnimal = null;
		
		while (!valid) {
			
			// get type of animal
			String type = getValidatedStringInput(sc, "Enter an animal to add:");
			
			// if animal matches any that already exists
			if (zooAnimals.stream().anyMatch(a -> type.toLowerCase().equals(a.getType().toLowerCase())) ) {
				// validation failed
				valid = false;
				
				// output error message
				System.out.println("This animal already exists; try a different one!");
				
				// go to next iteration of the loop to try again
				continue;
			}
			
			// get animal population
			int population = getValidatedInteger(sc, "Enter animal's population:", Integer.MAX_VALUE);
			
			// get animal's habitat
			String habitat = getValidatedStringInput(sc, "Enter the animal's habitat:");
			
			// create the new zoo animal object
			newAnimal = new ZooAnimal(type, population, habitat);
			
			valid = true;
			
		}
		
		return newAnimal;
	}
	
	public static String getValidatedStringInput(Scanner sc, String instruction) {
		boolean valid = false;
		String input = "";
		
		while (!valid) {
			// Print out instruction
			System.out.println(instruction);
			
			// Retrieve user input
			input = sc.nextLine().trim();
			
			// Assume input is valid
			valid = true;
			
			// If the input is just whitespace
			if (input.isEmpty()) {
				System.out.println("You didn't enter anything; try again!");
				
				// validation failed
				valid = false;
			}
		}
		
		return input;
	}
	
	public static void writeToFile(File file, Set<ZooAnimal> zooAnimals) {
		
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file))) {
			
			// write animal to file in alphabetical order
			for (ZooAnimal animal : zooAnimals) {
				writer.writeObject(animal);
			}
			
		} catch (IOException e) {
			System.out.println("Cannot write to file.");
		}
		
	}
	
	public static Set<ZooAnimal> readFromFile(File file) {
		Set<ZooAnimal> zooAnimals = new TreeSet<ZooAnimal>();
		
		// if there is actually any animal data stored
		if (file.length() > 0) {
			try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
				
				while (true) {
					
					// Read ZooAnimal object from file
					ZooAnimal animal = (ZooAnimal) reader.readObject();
					
					// Add ZooAnimal object to set
					zooAnimals.add(animal);
				}
				
			// Reached end of file
			}catch (EOFException e) {
				System.out.println("Retrieving data...");
				
			// Unable to read file
			} catch (IOException e) {
				System.out.println("Cannot read from file.");
			
			} catch (ClassNotFoundException e) {
				System.out.println("Something is wrong with the file");
			} 
		}
		
		return zooAnimals;
	}
	
	public static String displayOptions() {
		return 
				"\n[ ========== MAIN MENU ========== ]\n" +
				"1. Display all stored ZooAnimals\n" +
				"2. Add new ZooAnimal\n" +
				"3. Quit the Program\n\n" +
				"Please enter one of the above options:";
	}
	
	public static int getValidatedInteger(Scanner sc, String instr, int maxOption) {
		boolean isValid = false;
		int input = 0;
		
		// while choice is not valid
		while (!isValid) {
			
			// print the instruction
			System.out.println(instr);
			
			// attempt to receive valid input
			try {
				input = sc.nextInt();
				
				// Assume that the input is valid at this point
				isValid = true;
				
				// Input is not within the bounds of the options
				if (input < 1 || input > maxOption) {
					isValid = false;
					throw new DataEntryException(1, maxOption);
				}
			
			// integer value not passed
			} catch (InputMismatchException e) {
				System.out.println("ERR: Input must be an integer value.");
				
			// integer input is out of bounds
			} catch (DataEntryException e) {
				System.out.println(e.getMessage());
			} finally {
				// Advance the scanner
				sc.nextLine();
			}
		}
		
		return input;
	}
}
