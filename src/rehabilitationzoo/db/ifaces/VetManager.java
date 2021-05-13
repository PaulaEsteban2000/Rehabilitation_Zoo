package rehabilitationzoo.db.ifaces;

import java.sql.SQLException;
import java.util.List;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Illness;

public interface VetManager {
	//PARTE DE PAULA - VET
	
	
	//METODOS QUE HAY QUE PONER
	//public void markAnimalAsDeceased(Animal animal);
	//public void FreedomDateOfTheAnimal(Animal animal);
	
	
	//1. ANIMAL DIAGNOSIS - only on arrival
	
		//a. askForAnimal
			public List<String> getAnimalTypesInZoo ();
			public List<Animal> getAnimalsGivenType(String animalType);
			public List<Animal> getAnimalByNameAndType (String nameToSearch, String typeToSearch); //in the future this will only need an id read by a barcorde reader
			
		//b. firstDiagnosisSubMenu
			public void prothesisInstallation(Boolean bol, Illness illness, Animal animal); 
				public Integer getIllnessIdByName(Illness illness) throws SQLException;
				//public Integer getNumberOfIllnessesAnAnimalHas(); is used here as well
				public Integer getAnimalId(Animal animal);
			
		//c. illnessesInputSubMenu
			public Integer getNumberOfIllnessesAnAnimalHas(Animal animal);
			void addIllness(Illness illness);
			public void illnessQuarantine (Boolean bol, Illness illness); 
			public List<String> getDrugTypes();
			public List<String> getDrugNamesGivenType(String drugName);
			public Integer getTypeOfDrugId(String type);
				//public Integer getIllnessIdByName(Illness illness); is used here as well
	
	
	//2. ANIMAL CHECK - daily
			
		//a. askForHabitatToCheckItsAnimals	
			public List<String> getAllHabitatsNames();
			
		//b. animalCheckSubMenu
			public void reportAnimalState(Integer option, Animal animal); //stays, dies, released
				public List<String> getAnimalTypesInAHabitat(Habitat habitat);
				public List<Animal> getAnimalsGivenHabitatAndType(String habitatName, String animalType);
				//public List<Animal> getAnimalByNameAndType (String nameToSearch, String typeToSearch); is used here as well
				//in the future this will only need an id read by a barcorde reader

	
				
	///////////////////////////////////////////////////////////////////////////////////
			
	
	public List<Illness> getAnimalIllnesses(Animal animal);
		
	public void setIllnessOnAnimal(Illness illness, Animal animal); //remember each can have more than one
	public List<Drug> getDrugByNameAndType(String nameOfDrug, String typeOfDrug);
		//and now setting the rest of the parameters for the drug (that will be empty until now)...
		//set treatmentDuration, periodBetweenDosis, dosis 
	public void drugPrescription(Animal animal); //links drug to animal //animal can be taking many
			
	Integer getHabitatIdByName(String habitatName) throws SQLException;
	List<Animal> getAnimalsInHabitat(String habitatNameToSearch) throws SQLException;
		

		
	
		
	
}
