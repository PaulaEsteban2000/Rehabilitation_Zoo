package utils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.DrugType;
import rehabilitationzoo.db.pojos.Illness;
import rehabilitationzoo.db.ui.Menu;

public class KeyboardInput {
	
	public static Animal askForAnimal() throws IOException {
		
		System.out.println("These are the types of animals existent in our recovery center. Please choose one or enter a new one:");
		Menu.dbman.printAnimalTypes();
		String animalType = Utils.readLine();
		
		System.out.println("These are the names of the animals under the given type. Please choose the one:");
		Menu.dbman.printAnimalNamesGivenType(animalType);
		String animalName = Utils.readLine();
		
		Animal animalToDiagnose = Menu.dbman.getAnimal(animalType, animalName);
		return animalToDiagnose;
	}

	
	
	
	public static void diagnosisSubMenu(Animal animalToDiagnose) throws IOException {
		int illnessChoice;
		
		do {
			System.out.println("Once your diagnosis is completed, please choose if the animal has one of these illnesses or more:" + "\n"
					+ "	1. Animal is in need of a prothesis" + "\n"
					+ "	2. Animal has some other illness" + "\n"
					+ "	3. Go back to main menu" + "\n"); 
			illnessChoice = Utils.readInt();
		
			switch(illnessChoice) {
			case 1: 
				System.out.println("Are you sure this animal needs a prothesis?: " + "\n"
						+ "a. Yes" + "\n"
						+ "b. No, go back to illnesses menu." + "\n");
				String prothesisChoice = Utils.readLine();
				
				if (prothesisChoice.equals("a") ) {
					Menu.dbman.prothesisInstallation(true); //here the release date should be changed if parameter true
					System.out.println("Prothesis was installed. The animal will be set free in 30 days.");
					break;
				} else if(prothesisChoice.equals("b")) {
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
				
			case 3:
				break;
				
			default:
		        System.out.println("Error, nonvalid input.");
				break;
				
			}
		}while(illnessChoice != 3);
	}

	
	
	
	
	private static void illnessesSubMenu(int numberOfIllnesses) throws IOException{
		String nameOfIllness = "";
		int quarantineDays = 0; //set to zero if no quarantine is needed
		Boolean prothesis = false;
		Drug drug = null ;
		
		for (int a = 0; a < numberOfIllnesses; a++) {
			System.out.println("These are some of the illnesses that other animals have: ");
			Menu.dbman.printIllnesses();
			//TODO check for correct spelling (in every other case as well)
			System.out.println("What is the name of the illness your animal has?: " + "illness number " + (a+1));
			//TODO the counter of illnesses should be the actual number of illnesses successfully diagnosed on the animal
			nameOfIllness = Utils.readLine();
			
			System.out.println("Will it need quarantine?" + "\n"
				+ "a. Yes" + "\n"
				+ "b. No." + "\n");
				String quarantineChoice = Utils.readLine();
					if (quarantineChoice.equals("a")) {
						Menu.dbman.illnessQuarantine(true);
						System.out.println("How many days should the animal be ecxluded?");
						quarantineDays = Utils.readInt();
						System.out.println("The animal will be put int quarantine.");
					} else if(quarantineChoice.equals("b")) {
						Menu.dbman.illnessQuarantine(false);
						System.out.println("The animal will not be put int quarantine.");
					} else {
						System.out.println("Error, nonvalid input");
					}
				
			System.out.println("Will the animal need any medicines to take for this illness?:" + "\n"
					+ "a. Yes"+ "\n"
					+ "b. No"+ "\n");
				String drugsChoice = Utils.readLine();
					if (drugsChoice.equals("a") ) {
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
					} else if(drugsChoice.equals("b") ) {
						break;	
					} else {
						System.out.println("Error, nonvalid input");
					}
			
			Illness illness = new Illness (nameOfIllness,  quarantineDays,  prothesis,  drug.getId());
			//TODO animal.addIllness();
			
		}
		
	}
	
	
	
	
	
	public static void animalCheckSubMenu(Animal animalToCheck) throws IOException {
		int stateOption = 0;
		
		do {
			System.out.println("Please select if there has been a change on the state of the animal: "+ "\n"
					+ "	1. Declare animal as healed and release to wilderness."+ "\n"
					+ "	2. Declare animal as deceased."+ "\n"
					+ "	3. No state changes, animal stays in zoo."+ "\n"
					+ "	4. Go back to main Vet menu" + "\n");
				stateOption = Utils.readInt();
				
			switch (stateOption) {
			case 1:
				//TODO animal.setReleaseDate();
				break;
			case 2:
				//TODO animal.setDeathDate();
				break;
			case 3: 
				System.out.println("This animal will still stay at the zoo."+ "\n"
						+ " Has there been any diagnosis changes?"+ "\n"
						+ "	a. Yes"+ "\n"
						+ "	b. No"+ "\n");
				String changeChoice = Utils.readLine();
				
				if (changeChoice.equals("a") ) {
					diagnosisSubMenu(animalToCheck);
				} else if(changeChoice.equals("b")) {
					System.out.println("The animal will continue its treatment.");
					break;	
				} else {
					System.out.println("Error, nonvalid input");
				}
				
				
				break;
			case 4:
				break;
			default: 
				break;
			
			}
		} while(stateOption != 4);
		
	}
	
	
	
	public static List<Animal> storeAnimals=new LinkedList<Animal>();
	
	 public static ArrayList<String> typesOfAnimalsInTheZoo = new ArrayList<String>();
	//	ELEPHANT, LION, TIGER, RHINO, HIPO, GIRAFFE, MONKEY, DOLPHIN, WHALE, DEER, REINDEER
	
	
	//No deberia hacer un constructor vacio primero??
	
	//public KeyboardInput() {
	//	KeyboardInput.storeAnimals=new LinkedList<Animal>();
	//}
	
	
	
	
	public static boolean isThisAnAnimal (String unAnimal) {
		boolean realAnimal= false;
		
		
		 for( int i=0; i<typesOfAnimalsInTheZoo.size(); i++) {
			 
			 if(unAnimal.compareTo(typesOfAnimalsInTheZoo.get(i))==0) {
				 realAnimal=true;
				 Animal.type=typesOfAnimalsInTheZoo.get(i);
			 }
		 }
		
		return realAnimal;	
	}//Comprobamos que el animal que nos han dicho es realmente un animal existente en el zoo y 
	// ya que estamos, marcamos el tipo de animal que es
	
	
	
	public static void addAnimal(Animal unAnimal) {
		KeyboardInput.storeAnimals.add(unAnimal);
		
	}
	

	
	public static void puttingIdsAnimals(Animal unAnimal) {
		int unId;
		
		unId= storeAnimals.indexOf(unAnimal);
		Animal.id=unId;
		System.out.print("\n"+"id del animal:"+Animal.id+"\n");
		
		
	}
	
	
	


public static void main(String[] args) {
	
 typesOfAnimalsInTheZoo.add("giraffe");
 typesOfAnimalsInTheZoo.add("elephant");
 
		boolean exito = false;
		exito= isThisAnAnimal("giraffe");
		
		if(exito==true) {
			System.out.print("The animal exits in the database"+"\n");
			 
			
			LocalDate enterDate= LocalDate.of(2020,05,05 ) ;
			
			    Animal infoAnimal= new Animal("giraffe", "Julia", enterDate);
			    KeyboardInput.addAnimal(infoAnimal);
			    System.out.print(Animal.id +"\n");
			    System.out.print("Antes de meter el id"+Animal.id+"\n");
			    KeyboardInput.puttingIdsAnimals(infoAnimal);
			    System.out.print("Despues de meter el id"+Animal.id+"\n\n");
			    
			    
			    Animal infoAnimal1= new Animal("elephant", "dumbo", enterDate);
			    KeyboardInput.addAnimal(infoAnimal1);
			    System.out.print(Animal.type +"\n"+Animal.name+"\n"+Animal.enterDate+"\n");
			    System.out.print("Antes de meter el id "+Animal.id+"\n");
			    KeyboardInput.puttingIdsAnimals(infoAnimal1);
			    System.out.print("Despues de meter el id "+Animal.id+"\n\n");
			    

			    Animal infoAnimal3= new Animal("giraffe", "Paula", enterDate);
			    KeyboardInput.addAnimal(infoAnimal3);
			    System.out.print(Animal.id +"\n");
			    System.out.print("Antes de meter el id"+Animal.id+"\n");
			    KeyboardInput.puttingIdsAnimals(infoAnimal3);
			    System.out.print("Despues de meter el id"+Animal.id+"\n\n");
			    
			    
			    System.out.print("\nListamos los tipos de animales:\n");
	            for (int i = 0; i <typesOfAnimalsInTheZoo.size() ; i++) {
	                System.out.print(typesOfAnimalsInTheZoo.get(i)+"\n");
	            }
	            
	            
	            System.out.print("\nListamos la info de los animales que tenemos:\n");
	            for (int i = 0; i <storeAnimals.size() ; i++) {
	                System.out.print(storeAnimals.get(i)+"\n");
	            }
	            
		}

	
	
}


}
