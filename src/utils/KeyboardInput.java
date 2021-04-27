package utils;

import java.io.IOException;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.DrugType;
import rehabilitationzoo.db.pojos.Illness;
import rehabilitationzoo.db.ui.Menu;

public class KeyboardInput {

	public static Animal askForAnimal() throws IOException {
		
		System.out.println("These are the types of animals existent in our recovery center. Please choose one:");
		Menu.dbman.printAnimalTypes();
		String animalType = Utils.readLine();
		
		System.out.println("These are the names of the animals under the given type. Please choose the one:");
		Menu.dbman.printAnimalNamesGivenType(animalType);
		String animalName = Utils.readLine();
		
		Animal animalToDiagnose = Menu.dbman.getAnimal(animalType, animalName);
		return animalToDiagnose;
	}

	public static void diagnosisSubMenu(Animal animalToDiagnose) throws IOException {
		
		System.out.println("Once your diagnosis is completed, please choose if the animal has one of these illnesses or more:"
				+ "		1. Animal is in need of a prothesis"
				+ "		2. Animal has some other illness");
		int illnessChoice = Utils.readInt();
	
		switch(illnessChoice) {
		case 1: 
			System.out.println("Are you sure this animal needs a prothesis?: "
					+ "a. Yes"
					+ "b. No, go back to illnesses menu.");
			String prothesisChoice = Utils.readLine();
			
			if (prothesisChoice == "a" ) {
				Menu.dbman.prothesisInstallation(true); //here the release date should be changed if parameter true
				System.out.println("Prothesis was installed. The animal will be set free in 30 days.");
			} else if(prothesisChoice == "b" ) {
				break;	
			} else {
				System.out.println("Error, nonvalid input");
			}
	
			break;
			
		case 2:
			System.out.println("How many illnesses other than prothesis does your animal have?:");
			int numberOfIllnesses = Utils.readInt();
			
			KeyboardInput.illnessesSubMenu(numberOfIllnesses);			
			break;
			
		default:
	        System.out.println("Error, nonvalid input.");
			break;
			
		}
	}

	
	public static void illnessesSubMenu(int numberOfIllnesses) throws IOException{
		String nameOfIllness = "";
		int quarantineDays = 0; //set to zero if no quarantine is needed
		Boolean prothesis = false;
		Drug drug = null ;
		
		for (int a = 0; a < numberOfIllnesses; a++) {
			System.out.println("These are some of the illnesses that other animals have: ");
			Menu.dbman.printIllnesses();
			//TODO check for correct spelling (in every other case as well)
			System.out.println("What is the name of the illness your animal has?: " + "illness number " + a);
			nameOfIllness = Utils.readLine();
			
			System.out.println("Will it need quarantine?"
				+ "a. Yes"
				+ "b. No.");
				String quarantineChoice = Utils.readLine();
					if (quarantineChoice == "a" ) {
						Menu.dbman.illnessQuarantine(true);
						System.out.println("How many days should the animal be ecxluded?");
						quarantineDays = Utils.readInt();
						System.out.println("The animal will be put int quarantine.");
					} else if(quarantineChoice == "b" ) {
						Menu.dbman.illnessQuarantine(false);
						System.out.println("The animal will not be put int quarantine.");
						break;	
					} else {
						System.out.println("Error, nonvalid input");
					}
				
			System.out.println("Will the animal need any medicines to take for this illness?:"
					+ "a. Yes"
					+ "b. No");
				String drugsChoice = Utils.readLine();
					if (drugsChoice == "a" ) {
						System.out.println("These are the types of medicines we have in the center at the moment: ");
						Menu.dbman.printDrugTypes();
						System.out.println("What type of drug will the animal need to take?");
						String drugTypeName = Utils.readLine();
						//TODO check for correct spelling (in every other case as well)
						
						DrugType drugType = new DrugType (drugTypeName);
						
						System.out.println("Please type in the name of the drug to administrate: ");
						String nameOfDrug = Utils.readLine();
						
						System.out.println("How many days will the animal need to take it?");
						Integer treatmentDuration = Utils.readInt();
						
						System.out.println("What will the period between the dosis be? Please type the number of days between each dosis");
						//TODO check if this date supports adding and subtracting days/hours
						Integer periodBetweenDosis = Utils.readInt();
						
						System.out.println("How many grams of the medicine will it need to take everyday?");
						Integer dosis = Utils.readInt();
						
						drug = new Drug (nameOfDrug, treatmentDuration, periodBetweenDosis, drugType.getId(), dosis);
						//TODO animal.addDrug();
					} else if(drugsChoice == "b" ) {
						break;	
					} else {
						System.out.println("Error, nonvalid input");
					}
			
			Illness illness = new Illness (nameOfIllness,  quarantineDays,  prothesis,  drug.getId());
			//TODO animal.addIllness();
			
		}
		
	}
	
	
	public static void animalCheck(Animal animalToCheck) throws IOException {
		
		System.out.println("Please select if there has been a change on the state of the animal: "
				+ "		1. Declare animal as healed and release to wilderness."
				+ "		2. Declare animal as deceased."
				+ "		3. No state changes, animal stays in zoo.");
			int stateOption = Utils.readInt();
			
		switch (stateOption) {
		case 1:
			//TODO animal.setReleaseDate();
			break;
		case 2:
			//TODO animal.setDeathDate();
			break;
		case 3: 
			System.out.println("This animal will still stay at the zoo."
					+ " Has there been any diagnosis changes?"
					+ "		1. Yes"
					+ "		2. No");
			String changeChoice = Utils.readLine();
			
			if (changeChoice == "a" ) {
				diagnosisSubMenu(animalToCheck);
			} else if(changeChoice == "b" ) {
				System.out.println("The animal will continue its treatment.");
				break;	
			} else {
				System.out.println("Error, nonvalid input");
			}
			
			
			break;
		case 4:
			//TODO go back to other menu
			break;
		default: 
			break;
		
		}
	
	
		
	}

}
