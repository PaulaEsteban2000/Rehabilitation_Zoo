package utils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import java.sql.SQLException;
import java.util.*;

import Exceptions.AdminExceptions;
import rehabilitationzoo.db.ifaces.AdministratorManager;
import rehabilitationzoo.db.ifaces.DBManager;
import rehabilitationzoo.db.ifaces.VetManager;

import rehabilitationzoo.db.ifaces.ZooKeeperManager;

import rehabilitationzoo.db.jdbc.AdministratorSQL;

import rehabilitationzoo.db.jdbc.JDBCManager;
import rehabilitationzoo.db.jdbc.VetSQL;
import rehabilitationzoo.db.jdbc.ZooKeeperSQL;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.AnimalType;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.DrugType;
import rehabilitationzoo.db.pojos.FeedingType;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Illness;
import rehabilitationzoo.db.pojos.LightType;
import rehabilitationzoo.db.pojos.Worker;
import rehabilitationzoo.db.pojos.WorkerType;
import rehabilitationzoo.db.ui.Menu;

public class KeyboardInput {
	
	public static AdministratorManager adminMan = new AdministratorSQL();
	
	/////////////////////////METODOS DE PAULA: INICIO////////////////////////////////////////////////////////////////////////////
	//Los meto aqui entre comentarios por separado porque si no se descoloca todo y me agobia tener que ordenarlo cada vez
	//Ademas asi podeis ver lo que he hecho por si alguno os sirve (pero no los toqueis sin preguntarme u os corto las manos:) )
	//
	//
	public static VetManager vetMan = new VetSQL();		

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
		System.out.println("These are the types of animals we take care of in our recovery center. Please choose one:");
		List<String> types = vetMan.getAnimalTypesInZoo();
		System.out.println(types);
		String animalType = Utils.readLine();
		
		System.out.println("These are the names of the animals under the given type. Please choose the one:");
		List<Animal> animalsGivenType = vetMan.getAnimalsGivenType(animalType);
		printAnimalNames(animalsGivenType);
		String animalName = Utils.readLine();
		
		List<Animal> animalToDiagnose = vetMan.getAnimalByNameAndType(animalType, animalName);
		return animalToDiagnose.get(0); //TODO given theres no more animals in the list (there shouldnt be bc of exceptions)
	
	}
	//
	//
	public static Animal askForAnimalFromHabitat(Habitat habitat) throws IOException, SQLException {  //Tambien lo utiliza zoo keeper
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
	public static void printAnimalNames(List<Animal> animalsGivenType) {
		for (int a = 0; a < animalsGivenType.size(); a++) {
			System.out.println(animalsGivenType.get(a).getName());
		}
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

					//TODO al igual deberia meterlo en la db y volverlo a recibir ya con el id relleno
					//NATALIA, MARIA: preguntar juntas what if we add an existent illness?? -> exception?
					
					//TODO Add illness and/or link to patient
					vetMan.addIllness(illness); //esto aqui bien?
					vetMan.setIllnessOnAnimal(illness, animalToDiagnose);
					
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

			System.out.println("This is the illness number " + vetMan.getNumberOfIllnessesAnAnimalHas(animalToDiagnose) + " your animal has.");
			System.out.println("What is the name of the new illness you just diagnosed?: new illness input number" + (a+1));
			nameOfIllness = Utils.readLine(); //TODO check spelling
			
			Illness newIllness = new Illness(nameOfIllness, null);
			vetMan.addIllness(newIllness);
			//TODO setIllnessOnAnimal(String name)
			
			System.out.println("Will it need quarantine?" + "\n"
				+ "a. Yes" + "\n"
				+ "b. No." + "\n");
				String quarantineChoice = Utils.readLine();
					if (quarantineChoice.equals("a")) {
						newIllness.setQuarantine(true);
						vetMan.illnessQuarantine(true, newIllness);
						System.out.println("The animal will be put int quarantine.");
					} else if(quarantineChoice.equals("b")) {
						newIllness.setQuarantine(false);
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
						List<String> drugTypes = vetMan.getDrugTypes();
						System.out.println(drugTypes);
						//TODO cambiar relacion de drug y drugType a 1-n
						
						System.out.println("What type of drug will the animal need to take?");
						String drugType = Utils.readLine();
						//TODO check for correct spelling (in every other case as well)
						
						
						System.out.println("These are the medicines we have for that type: ");
						List<String> drugNames = vetMan.getDrugNamesGivenType(drugType);
						System.out.println(drugNames);
						System.out.println("Please type in the name of the drug to administrate: ");
						String nameOfDrug = Utils.readLine();
						
						System.out.println("How many days will the animal need to take it?");
						Integer treatmentDuration = Utils.readInt();
						
						System.out.println("What will the period between the dosis be? Please type the number of days between each dosis");
						//TODO this should probably be fixed on each drug?
						Integer periodBetweenDosis = Utils.readInt();
						
						System.out.println("How many grams of the medicine will it need to take everyday?");
						Float dosis = Float.parseFloat(Utils.readLine());
						
						drug = new Drug (nameOfDrug, treatmentDuration, periodBetweenDosis, vetMan.getTypeOfDrugId(drugType));
						//save drug bbdd
						
						//asocias drug al animal
						//animalToDiagnose.setDrug(drug);
						
						//guardar drug del animal en la tabla de la relacion
						vetMan.drugPrescription(animalToDiagnose); 
						//TODO drug to illness?? how are they related?
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

	
	public static boolean isThisAnAnimal (String unAnimal) { //Esto no seria una excpecion mejor? - Paula <3
		boolean realAnimal= false;
		
		List <String> typesOfAnimalsInTheZoo=adminMan.getAnimalTypesByName();
		
		
		 for( int i=0; i<typesOfAnimalsInTheZoo.size(); i++) {
			 
			 if(unAnimal.compareTo(typesOfAnimalsInTheZoo.get(i))==0) {
				 realAnimal=true;
			 }
		 }

		return realAnimal;	
	}//Comprobamos que el animal que nos han dicho es realmente un animal existente en el zoo y 
	// ya que estamos, marcamos el tipo de animal que es
	

	
	public static void addAnimalInTheZoo(Animal anAnimal) throws SQLException {
		adminMan.addAnimal(anAnimal);		
	}	 
		 

	public static void addHabitatInTheZoo(Habitat habitat) throws SQLException, AdminExceptions {
		adminMan.addHabitat(habitat);
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
	public static void listAllDrugTypes() {
		adminMan.listDrugTypes(); //MIRAR EN EL MENU POR FAVOR
	}

	public static void addDrugType(String drugName, float dosis) {
		adminMan.addNewDrugType(drugName, dosis);	
	}
	
	public static void addDrug(Drug oneDrug) {
		adminMan.addNewDrug(oneDrug);	
	}
	
	public static void deleteDrug(Drug oneDrug) {
		adminMan.addNewDrug(oneDrug);	
	}

	public void weAddHabitats()throws SQLException, AdminExceptions { //THROW EXCEPTION
		LocalDate localToday = LocalDate.now(); //only way to add dates
		Date newDate =java.sql.Date.valueOf(localToday);
		
		Habitat northPole = new Habitat("Pole zone",newDate, newDate, -19, LightType.LOW );  
		Habitat desert = new Habitat("Desert",newDate, newDate, 50, LightType.HIGH );
		Habitat sabana = new Habitat("Sabana",newDate, newDate, 30, LightType.HIGH );
		Habitat jungle = new Habitat("Jungle",newDate, newDate, 27, LightType.MEDIUM );
		Habitat saltWater = new Habitat("Salt water",newDate, newDate, 15, LightType.MEDIUM );
		Habitat freshWater = new Habitat("Fresh water",newDate, newDate, 13, LightType.MEDIUM );
		Habitat montainous = new Habitat("Montanous",newDate, newDate, 7, LightType.MEDIUM );
		Habitat tropical = new Habitat("Tropical",newDate, newDate, 33, LightType.MEDIUM );
		Habitat forest = new Habitat("Forest",newDate, newDate, 5, LightType.MEDIUM );
		Habitat cliffst = new Habitat("Cliffst",newDate, newDate, 4, LightType.MEDIUM );
		Habitat reef = new Habitat("Reef",newDate, newDate, 15, LightType.MEDIUM );
		Habitat beach = new Habitat("Beach",newDate, newDate, 28, LightType.MEDIUM );
		Habitat steppe = new Habitat("Steppe",newDate, newDate, 22, LightType.HIGH );
		Habitat quarantine = new Habitat("Quarantine",newDate, newDate, 25, LightType.MEDIUM );
		Habitat waitZone = new Habitat("Wait Zone",newDate, newDate, 25, LightType.HIGH );
		
		adminMan.addHabitat(northPole);
		adminMan.addHabitat(desert);
		adminMan.addHabitat(sabana);
		adminMan.addHabitat(jungle);
		adminMan.addHabitat(saltWater);
		adminMan.addHabitat(freshWater);
		adminMan.addHabitat(montainous);
		adminMan.addHabitat(tropical);
		adminMan.addHabitat(forest);
		adminMan.addHabitat(cliffst);
		adminMan.addHabitat(reef);
		adminMan.addHabitat(beach);
		adminMan.addHabitat(steppe);
		adminMan.addHabitat(quarantine);
		adminMan.addHabitat(waitZone);
	}
	
	public void weAddDrugTypes()throws SQLException, AdminExceptions { //THROW EXCEPTION
		
		Float half = (float) 0.5;
		Float one = (float) 1.0;
		
		adminMan.addNewDrugType("Ointment", half);
		adminMan.addNewDrugType("Ointment", one);
		adminMan.addNewDrugType("Analgesic", half);
		adminMan.addNewDrugType("Analgesic", one);
		adminMan.addNewDrugType("Antibiotic", half);
		adminMan.addNewDrugType("Antibiotic", one);
		adminMan.addNewDrugType("Antibody Serum", half);
		adminMan.addNewDrugType("Antibody Serum", one);
		adminMan.addNewDrugType("Bandage", half);
		adminMan.addNewDrugType("Bandage", one);
		
	}
	
	public void weAddAnimalTypes() throws SQLException, AdminExceptions{
		
		AnimalType lion = new AnimalType("Lion", FeedingType.CARNIVORE);
		AnimalType tiger = new AnimalType("Tiger", FeedingType.CARNIVORE);
		AnimalType jaguar = new AnimalType("Jaguar", FeedingType.CARNIVORE);
		AnimalType hiena = new AnimalType("Hiena", FeedingType.CARNIVORE);
		AnimalType giraffe = new AnimalType("Giraffe", FeedingType.HERVIBORE);
		AnimalType elephant = new AnimalType("Elephant", FeedingType.HERVIBORE);
		AnimalType bear = new AnimalType("Bear", FeedingType.OMNIVORE);
		AnimalType panda = new AnimalType("Giant Panda", FeedingType.HERVIBORE);
		AnimalType wolf = new AnimalType("Wolf", FeedingType.CARNIVORE);
		AnimalType cocodrile = new AnimalType("Cocodrile", FeedingType.CARNIVORE);
		AnimalType blueWhale = new AnimalType("Blue Whale", FeedingType.CARNIVORE);
		AnimalType dolphin = new AnimalType("Dolphin", FeedingType.CARNIVORE);
		AnimalType polarBear = new AnimalType("Polar Bear", FeedingType.CARNIVORE);
		AnimalType penguin = new AnimalType ("Penguin", FeedingType.CARNIVORE);
		AnimalType shelby = new AnimalType("Shelby", FeedingType.CARNIVORE);
		
		
		adminMan.introducingAnimalsTypes(lion);
		adminMan.introducingAnimalsTypes(tiger);
		adminMan.introducingAnimalsTypes(jaguar);
		adminMan.introducingAnimalsTypes(hiena);
		adminMan.introducingAnimalsTypes(giraffe);
		adminMan.introducingAnimalsTypes(elephant);
		adminMan.introducingAnimalsTypes(bear);
		adminMan.introducingAnimalsTypes(panda);
		adminMan.introducingAnimalsTypes(wolf);
		adminMan.introducingAnimalsTypes(cocodrile);
		adminMan.introducingAnimalsTypes(blueWhale);
		adminMan.introducingAnimalsTypes(dolphin);
		adminMan.introducingAnimalsTypes(polarBear);
		adminMan.introducingAnimalsTypes(penguin);
		adminMan.introducingAnimalsTypes(shelby);
	}
	
/////////////////////////MARIA////////////////////////////////////////////////////////////////////////////
	
	
	public static ZooKeeperManager zooKMan = new ZooKeeperSQL();	
		
	
	public static Habitat askForHabitat() throws IOException, SQLException {
			System.out.println("These are the habitats existent in our recovery center. Please choose the name of one:");
			List<String> habitatsnames = zooKMan.getHabitats();
			System.out.println(habitatsnames);
			
			String habitatName = Utils.readLine();
			
			Integer habId = zooKMan.getHabitatIdByName(habitatName);
				
			List<Habitat> habitat = zooKMan.getHabitatById(habId);
			
			return habitat.get(0);
			
	}
	

	public static void habitatSubMenu(Habitat habitatToChange, Integer stateOption) throws IOException {
			
			if(stateOption == 4) {
				zooKMan.cleanHabitat(habitatToChange);
			}
			else {
				zooKMan.fillUpWaterTank(habitatToChange);
			}
			
	}
	
	
	public static void AnimalSubMenu(Habitat habitat, Integer stateOption) throws IOException {
	
		switch (stateOption) {
			case 1:
				zooKMan.feedAnimals(habitat);
				break;
				
			case 2:
				zooKMan.batheAnimals(habitat);
				break;
				
			case 3: 
				zooKMan.drugAdministrationToAnimals(habitat);
				break;
				
			default: 
				break;
		}
	
	}
	

}


