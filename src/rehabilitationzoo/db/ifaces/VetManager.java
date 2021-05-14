package rehabilitationzoo.db.ifaces;

import java.sql.SQLException;
import java.util.List;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Illness;

public interface VetManager {
	//PARTE DE PAULA - VET
	
	
	//0. SQL HELPER METHODS
	
	public Integer getIllnessIdByName(Illness illness) throws SQLException;
	public Integer getAnimalId(Animal animal);
	public Integer getHabitatIdByName(String habitatName) throws SQLException;
	
	
	//1. ANIMAL DIAGNOSIS - only on arrival
		
		//a. checkIfAnimalsInHabitat
			public List<Animal> getAnimalsInHabitat(String habitatNameToSearch) throws SQLException;
			
		//b. askForAnimalForDiagnosis
			public List<Animal> getAnimalByNameAndType (String nameToSearch, String typeToSearch);// future: "barcorde" reader
			
		//c. firstDiagnosisSubMenu
			
			//I. PROTHESIS
				public List<String> getAllProthesisExistent();
				public Illness addIllness(Illness illness);
				public Illness getIllnessByName(String name);
				public List<Illness> getAnimalIllnesses(Animal animal);
				public void setIllnessOnAnimal(Illness illness, Animal animal); 
				public void prothesisInstallation(Illness illness, Animal animal);
			
			//II. OTHER - illnessesInputSubMenu
				public Integer getNumberOfIllnessesAnAnimalHas(Animal animal);
				public List<String> getDrugTypes();
				public List<Drug> getDrugsGivenType(String drugName);
				public List<Drug> getDrugByNameAndType(String nameOfDrug, String typeOfDrug);
				public Illness getIllness(Illness illness);
				//public Illness addIllness(Illness illness); again
				//public void setIllnessOnAnimal(Illness illness, Animal animal); again
				public void drugPrescription(Animal animal); 

			//II. BACK
				public List<String> getAllHabitatsNames();
				public void changeHabitat(String habitatName, Animal animal);
			
				
	//2. ANIMAL CHECK - daily
			
		//a. askForHabitatToCheckItsAnimals
			//public List<String> getAllHabitatsNames(); again
			public List<Habitat> getHabitatByName(String name);
			
		//b. checkIfAnimalsInHabitat
			//just like on 1.a (with getAnimalsInHabitat)
			
		//c. animalCheckSubMenu
			
			//I. askForAnimalFromHabitat: in the future this will only need an id read by a barcorde reader
				public List<String> getAnimalTypesInAHabitat(Habitat habitat);
				public List<Animal> getAnimalsGivenHabitatAndType(String habitatName, String animalType);
				//public List<Animal> getAnimalByNameAndType (String nameToSearch, String typeToSearch); again
				
			//II. [rest]
				public void reportAnimalState(Integer option, Animal animal); //stays, dies, released
			
	
}
