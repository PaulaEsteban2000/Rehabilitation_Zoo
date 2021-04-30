package rehabilitationzoo.db.ifaces;

import java.sql.SQLException;
import java.util.List;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.Illness;

public interface VetManager {
	//PARTE DE PAULA - VET
	
	
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
			public Integer getNumberOfIllnessesAnAnimalHas();
			void addIllness(Illness illness);
			public void illnessQuarantine (Boolean bol, Illness illness); 
			public Integer getTypeOfDrugId(String type);
				//public Integer getIllnessIdByName(Illness illness); is used here as well
	
	
	//2. ANIMAL CHECK - daily
			
		//a. animalCheckSubMenu
			public void reportAnimalState(Integer option, Animal animal); //stays, dies, released
	

	
	///////////////////////////////////////////////////////////////////////////////////
			
	public List<String> getDrugTypes();
	public List<Illness> getAnimalIllnesses();
	
	
		
		public Illness setIllnessOnAnimal(String name); //remember each can have more than one
			
			public List<Drug> getDrugByNameAndType(String nameOfDrug, String typeOfDrug);
				//and now setting the rest of the parameters for the drug (that will be empty until now)...
				//set treatmentDuration, periodBetweenDosis, dosis 
			public void drugPrescription(Drug drug); //links drug to animal //animal can be taking many
			
			public void setQuarantineDays(Integer numberOfDays); //days to be excluded
			
	
		
		///METODOS CREADOS DESPUES A LA FUERZA PARA ORDENAR
		
		Integer getHabitatIdByName(String habitatName) throws SQLException;
		
		List<Animal> getAnimalsInHabitat(String habitatNameToSearch) throws SQLException;
		
	
		
	
}
