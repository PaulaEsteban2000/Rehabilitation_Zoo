package utils;

import java.io.IOException;
import java.time.LocalDate;
import java.sql.SQLException;
//>>>>>>> branch 'main' of https://github.com/PaulaEsteban2000/Rehabilitation_Zoo
import java.util.*;

import rehabilitationzoo.db.ifaces.AdministratorManager;
import rehabilitationzoo.db.ifaces.DBManager;
import rehabilitationzoo.db.ifaces.VetManager;
import rehabilitationzoo.db.jdbc.AdministratorSQL;
import rehabilitationzoo.db.jdbc.JDBCManager;
import rehabilitationzoo.db.jdbc.VetSQL;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.AnimalType;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.DrugType;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Illness;
import rehabilitationzoo.db.pojos.Worker;
import rehabilitationzoo.db.pojos.WorkerType;
import rehabilitationzoo.db.ui.Menu;

public class KeyboardInput {
	
	
	/////////////////////////METODOS DE PAULA: INICIO////////////////////////////////////////////////////////////////////////////
	//Los meto aqui entre comentarios por separado porque si no se descoloca todo y me agobia tener que ordenarlo cada vez
	//Ademas asi podeis ver lo que he hecho por si alguno os sirve (pero no los toqueis sin preguntarme u os corto las manos:) )
	//
	//
	public static VetManager vetMan = new VetSQL();		
	public static AdministratorManager adminMan = new AdministratorSQL();
	//
	//
	public static Habitat askForHabitatToCheckItsAnimals() throws IOException {
		System.out.println("These are our actual habitats.");
		List<String> habitatNames= vetMan.getAllHabitatsNames();
		System.out.println(habitatNames);
		System.out.println("Please input the name of the habitat to check its animals: ");
		String name = Utils.readLine();
		Habitat habitatToCheck = null;
		
		return habitatToCheck;
	}
	
	//
	//
	public static Animal askForAnimal() throws IOException {
		//TODO solo poder acceder a animales con freedom y death date NULL
		
		//TODO o deberia ser mejor sacarle la lista de animales que tratamos en el zoo?
		System.out.println("These are the types of animals existent in our recovery center. Please choose one or enter a new one:");
		List<String> types = vetMan.getAnimalTypesInZoo();
		System.out.println(types);
		String animalType = Utils.readLine();
		
		System.out.println("These are the names of the animals under the given type. Please choose the one:");
		List<Animal> animalsGivenType = vetMan.getAnimalsGivenType(animalType);
		System.out.println(animalsGivenType);
		String animalName = Utils.readLine();
		
		List<Animal> animalToDiagnose = vetMan.getAnimalByNameAndType(animalType, animalName);
		return animalToDiagnose.get(0); //TODO given theres no more animals in the list (there shouldnt be bc of exceptions)
	
	}
	//
	//
	public static Animal askForAnimalFromHabitat(Habitat habitat) throws IOException, SQLException {
		//TODO solo poder acceder a animales con freedom y death date NULL
		
		System.out.println("These are the types of animals existent in the habitat. Please choose one or enter a new one:");
		List<String> animalsTypesInHabitat = vetMan.getAnimalTypesInAHabitat(habitat);
		printAnimalTypesInHabitat(animalsTypesInHabitat);
		String animalType = Utils.readLine();
		
		System.out.println("These are the names of the animals under the given type in the habitat. Please choose the one:");
		List<Animal> animalsGivenType = vetMan.getAnimalsGivenHabitatAndType(habitat.getName(), animalType);
		printAnimalNamesInHabitat(animalsGivenType);
		String animalName = Utils.readLine();
		
		List<Animal> animalToDiagnose = vetMan.getAnimalByNameAndType(animalType, animalName);
		return animalToDiagnose.get(0); //TODO given theres no more animals in the list (there shouldnt be bc of exceptions)
	}
	//
	//
	public static void printAnimalTypesInHabitat(List<String> animalsInHabitat) {
		for (int a = 0; a < animalsInHabitat.size(); a++) {
			System.out.println(animalsInHabitat.get(a));
		}
	}
	//
	//
	public static void printAnimalNamesInHabitat(List<Animal> animalsInHabitat) {
		for (int a = 0; a < animalsInHabitat.size(); a++) {
			System.out.println(animalsInHabitat.get(a).getName());
		}
	}
	//
	//
	public static void firstDiagnosisSubMenu(Animal animalToDiagnose) throws IOException {
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
					
					System.out.println("Where does the animal need a prothesis? "); 
					String bodyPart = Utils.readLine();
					String name = bodyPart + "_prothesis";
					Illness illness = new Illness(name, false, true, null);
					//Add illness and/or link to patient
					//TODO: NATALIA, MERI: preguntar juntas what if we add an existent illness?? -> exception? 
					//Pues que no se almacena como una illness nueva
					
					vetMan.prothesisInstallation(true, illness, animalToDiagnose); //here the release date should be changed if parameter true
					System.out.println("Prothesis was installed. The animal will be released into the wilderness in 30 days.");
					break;
				} else if(prothesisChoice.equals("b")) {
					break;	
				} else {
					System.out.println("Error, nonvalid input");
				}
		
				break;
				
			case 2:
				System.out.println("How many illnesses (other than prothesis) does your animal have?:");
				int numberOfIllnesses = Utils.readInt();
				
				KeyboardInput.illnessesInputSubMenu(numberOfIllnesses, animalToDiagnose);			
				break;
				
			case 3:
				break;
				
			default:
		        System.out.println("Error, nonvalid input.");
				break;
				
			}
		}while(illnessChoice != 3);
	}
	//
	//
	private static void illnessesInputSubMenu(int numberOfIllnesses, Animal animalToDiagnose) throws IOException{
		String nameOfIllness = "";
		int quarantineDays = 0; //set to zero in case no quarantine is needed
		Boolean prothesis = false;
		Drug drug = null ;
		
		for (int a = 0; a < numberOfIllnesses; a++) {
			System.out.println("These are the illnesses we can take care of. Please choose one: ");
			//TODO metodo que imprima las enfermedades que podemos curar de la lista
			//System.out.println(illnessesWeCanCure);
			//TODO check for correct spelling (in every other case as well)
			
			System.out.println("This is the illness number " + vetMan.getNumberOfIllnessesAnAnimalHas() + " your animal has.");
			System.out.println("What is the name of the new illness you just diagnosed?: new illness input number" + (a+1));
			nameOfIllness = Utils.readLine(); //TODO check spelling
			
			Illness newIllness = new Illness(nameOfIllness, null, false, null);
			vetMan.addIllness(newIllness);
			//TODO setIllnessOnAnimal(String name)
			
			System.out.println("Will it need quarantine?" + "\n"
				+ "a. Yes" + "\n"
				+ "b. No." + "\n");
				String quarantineChoice = Utils.readLine();
					if (quarantineChoice.equals("a")) {
						vetMan.illnessQuarantine(true, newIllness);
						//NOT KNOW IF NEEDED ----...
						System.out.println("How many days should the animal be ecxluded?");
						quarantineDays = Utils.readInt();
						//...----
						System.out.println("The animal will be put int quarantine.");
					} else if(quarantineChoice.equals("b")) {
						vetMan.illnessQuarantine(false, newIllness);
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
						//TODO imprimir tipos de medicamentos disponibles de lista o como?
						
						System.out.println("What type of drug will the animal need to take?");
						String drugType = Utils.readLine();
						//TODO check for correct spelling (in every other case as well)
						
						System.out.println("Please type in the name of the drug to administrate: ");
						String nameOfDrug = Utils.readLine();
						
						System.out.println("How many days will the animal need to take it?");
						Integer treatmentDuration = Utils.readInt();
						
						System.out.println("What will the period between the dosis be? Please type the number of days between each dosis");
						//TODO check if this date supports adding and subtracting days/hours
						//this should probably be fixed on each drug?
						Integer periodBetweenDosis = Utils.readInt();
						
						System.out.println("How many grams of the medicine will it need to take everyday?");
						Integer dosis = Utils.readInt();
						
						drug = new Drug (nameOfDrug, treatmentDuration, periodBetweenDosis, vetMan.getTypeOfDrugId(drugType), dosis);
						//TODO animal.addDrug(); 
						//TODO drug to illness??
					} else if(drugsChoice.equals("b") ) {
						break;	
					} else {
						System.out.println("Error, nonvalid input");
					}
			
		}
		
	}
	//
	//
	public static void animalCheckSubMenu(Habitat habitatToCheck) throws IOException, SQLException {
		int stateOption = 0;
		
		Animal animalToCheck = KeyboardInput.askForAnimalFromHabitat(habitatToCheck);
		//TODO should do something to not leave the habitat until all animals are checked
		//attribute for habitat for lastChecked when done?
		
		do {
			System.out.println("Please select if there has been a change on the state of the animal: "+ "\n"
					+ "	1. Declare animal as healed and release to wilderness."+ "\n"
					+ "	2. Declare animal as deceased."+ "\n"
					+ "	3. No state changes, animal stays in zoo."+ "\n"
					+ "	4. Go back to main Vet menu" + "\n");
				stateOption = Utils.readInt();
				
			switch (stateOption) {
			case 1:
				 vetMan.reportAnimalState(stateOption, animalToCheck);
				break;
			case 2:
				vetMan.reportAnimalState(stateOption, animalToCheck);
				break;
			case 3: 
				System.out.println("This animal will still stay at the zoo."+ "\n"
						+ " Has there been any diagnosis changes?"+ "\n"
						+ "	a. Yes"+ "\n"
						+ "	b. No"+ "\n");
				String changeChoice = Utils.readLine();
				
				if (changeChoice.equals("a") ) {
					KeyboardInput.firstDiagnosisSubMenu(animalToCheck);
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
	//
	//
	////////////////////////METODOS DE PAULA: FIN///////////////////////////////////////////////////////////////////////////////

	
	
	
	/*public static ArrayList<String> typesOfAnimalsInTheZoo = new ArrayList<String>();
	//	ELEPHANT, LION, TIGER, RHINO, HIPO, GIRAFFE, MONKEY, DOLPHIN, WHALE, DEER, REINDEER
	
	
	
	
	
	public static boolean isThisAnAnimal (String unAnimal) { //Esto no seria una excpecion mejor? - Paula <3
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
	
		 
	*/
	
	
	
	public static void addAnimalInTheZoo(Animal anAnimal) throws SQLException {
		adminMan.addAnimal(anAnimal);		
	}	 
		 
		 
	
	public static void addAnimalTypeInTheZoo(AnimalType anAnimalType) throws SQLException {
		adminMan.introducingAnimalsTypes(anAnimalType);
	}	 
		
	
	public static boolean isThisAnHabitat(String nameHabitat) {
		boolean success=false;
		 List<String> weSeeTheHabitats= vetMan.getAllHabitatsNames();
		 
			 for(int i = 0; i<weSeeTheHabitats.size(); i++){
				 if( nameHabitat.equals(weSeeTheHabitats.get(i)) ) {
					 success=true;
				 }  
		 }
		return success;		
	}
	
	
	
	public static void addWorker (Worker worker) {
		adminMan.introducingWorkers(worker);
		
	}
	
	public static boolean firingWorkers(String workerName, String workerLastName) {
		
      String totalName= workerName+ " "+ workerLastName;
      boolean deleted= false;
		
		List<String> allWorkersName = adminMan.getAllWorkersNamesAndLastNames();
		
		for(int i = 0; i<allWorkersName.size(); i++){
			 if(totalName.equals(allWorkersName.get(i)) ) {
				 	String[] parts = totalName.split(" ");
					String part1Name = parts[0];
					String part2Lastname = parts[1];
					
				 adminMan.deleteThisWorker(part1Name, part2Lastname);
				 deleted=true;
				 
			 }
				
			 } 
		return deleted;
		
	}

	
	public static boolean modificationsSalary(String name1, String lastname1, Float salary) {
	boolean changes=false;
	String anotherTotalName= name1 +" "+ lastname1;
	List<String> anotherAllWorkersName= adminMan.getAllWorkersNamesAndLastNames();

	
		
		for(int i = 0; i<anotherAllWorkersName.size(); i++){
			 if(anotherTotalName.equals(anotherAllWorkersName.get(i)) ) {
				 
				adminMan.modifyWorker(name1, lastname1, null);
				changes=true;
				break;
			 	} 
			 }
		return changes;
		
	}
	

	public void addDrugType(String drugName) {
		adminMan.addNewDrugType(drugName);	
	}
	
	public void addDrug(Drug oneDrug) {
		adminMan.addNewDrug(oneDrug);	
	}
	
	public void deleteDrug(Drug oneDrug) {
		adminMan.addNewDrug(oneDrug);	
	}
	
	
public static Habitat askForHabitat() throws IOException {
		
		System.out.println("These are the habitats existent in our recovery center. Please choose the Id of one:");
		Menu.dbman.printHabitatsNamesAndId();
		Integer habitatId = Utils.readInt();
		try {
			Menu.dbman.getHabitatId(habitatId);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Habitat habitat = Menu.dbman.getHabitat(habitatId);
		return habitat;
	}
	

/*public static void main(String[] args) {
	
 typesOfAnimalsInTheZoo.add("giraffe");
 typesOfAnimalsInTheZoo.add("elephant");
 
		boolean exito = false;
		exito= isThisAnAnimal("giraffe");
		
		if(exito==true) {
			System.out.print("The animal exits in the database"+"\n");
			 
			
			Date enterDate= LocalDate.of(2020,05,05 ) ; //Ahora esto da errores porque no usamos LocalDate, voy a averiguar si esto es verdad! yo me ocupo - Paula
			
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
		}*/


public static void habitatSubMenu(Habitat habitatToChange, Integer stateOption) throws IOException {

		
		if(stateOption == 4) {
			//TODO habitatToChange.setLastCleaned();
		}
		else {
			//TODO habitatToChange.setWaterLevel();
		}
		
	}


public static void drugAdminSubMenu(Habitat habitat, Integer stateOption) throws IOException {

	
	switch (stateOption) {
	case 1:
		habitat.getAnimals();
		//TODO bucle que le vaya dando de comer a cada animal de la lista .feedAnimal(); //boolean?
		break;
	case 2:
		//TODO bucle que le vaya bañando a cada animal de la lista .batheAnimal(); //boolean?
		break;
	case 3: 
		// TODO bucle que le vaya dando las drugs a cada animal de la lista .drugAdministrationToAnimal(Animal animal); //boolean?
		break;
		
	default: 
		break;
	
		}
	}
}






