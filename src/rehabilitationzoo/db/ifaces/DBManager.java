package rehabilitationzoo.db.ifaces;

import java.sql.SQLException;
import java.util.List;

import rehabilitationzoo.db.pojos.*;

public interface DBManager {
	
	public void connect ();
	public void disconnect ();

	
	//ZOO-KEEPER
	//1. DRUG ADMINISTRATION
		public void drugAdministrationToAnimal(Animal animal);
	//2. CHOOSE HABITAT
		public Integer getHabitatId(String habitatName) throws SQLException;
		public void feedAnimal(); //boolean?
		public void batheAnimal(); //boolean?
		public void cleanHabitat(); //boolean?
		public void fillUpWaterTank(); //boolean?
	
	
	//VET
	public List<Animal> searchAnimalByName (String name);
	public void printAnimalsInHabitat (String name) throws SQLException;
	public void printAnimalTypes ();
	public void printAnimalNamesGivenType(String animalType);
	public void printDrugTypes();
	public void printIllnesses();
	//1. ANIMAL DIAGNOSIS
		public Animal getAnimal(String type, String name); //in the future this will only need an id read by a barcorde reader
		public boolean prothesisInstallation(Boolean bol); 
		//make it true or false
		//changes release date to month after initial diagnosis
		public Illness setIllness(String name); //remember each can have more than one
			public Integer getTypeOfDrugId(String type);
			public void getDrug(String name, Integer typeOfDrug_id);
				//and now setting the rest of the parameters for the drug (that will be empty until now)...
				//set treatmentDuration, periodBetweenDosis, dosis 
			public void drugPrescription(Drug drug); //links drug to animal //animal can be taking many
			public void illnessQuarantine (Boolean bol); //makes it true or not
			public void quarantineDays(); //days to be excluded
	//2. CHECK ANIMAL
		public void reportAnimalState(Integer option); //stays, dies, released
		//setReleaseDate... etc
		
		
	//ADMINISTRATOR
	public List<Worker> searchWorkerByName (String name);
	public List<Drug> searchDrugByName (String name);
	//1. MANAGE ANIMALS
		public void addAnimal(Animal animal) throws SQLException;
		public void returAnimalToTheWilderness(Animal animal);
		//changes releaseDate
		public void markAnimalAsDeceased(Animal animal);
		//changes deathDate
	//2. MANAGE WORKERS
		public void hireWorker(Worker worker); 
		public Worker getWorker(String name, String lastName);
		public void fireWorker(Worker worker);
		public void modifyWorker(Worker worker, Integer salary);
	//3. MANAGE DRUGS
		public void addNewDrug(Drug drug);
		public void deleteDrug(Drug drug);
		// public void modifyDrug(Drug drug); //needed?

}
