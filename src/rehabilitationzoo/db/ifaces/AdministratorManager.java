package rehabilitationzoo.db.ifaces;

import java.sql.SQLException;
import java.util.List;

import Exceptions.AdminExceptions;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.AnimalType;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Worker;

public interface AdministratorManager {
	//PARTE DE NATI
	
	//ADMINISTRATOR
		
		//public List<Drug> searchDrugsByName (String name); //cast??
		
	
		//ANIMALS
		public void listAnimals ();
		public void updateAnimal(Animal animal);
		public void addAnimal(Animal animal) throws SQLException;
		
		
		//ANIMALS TYPES
		public void introducingAnimalsTypes(AnimalType animalType) throws SQLException;
		public List<String> getAnimalTypesByName();
		public List<Integer> getAnimalTypesById(AnimalType type);
		
		//WORKERS
		public List<Worker> searchWorkerByName (String name);
		public void introducingWorkers(Worker aWorker);
		public List<String> getAllWorkersNamesAndLastNames();
		public void deleteThisWorker(String name,String lastname);
		public void modifyWorker(String name, String lastname, Integer salary);
	
		
	    //DRUGS TYPES
		public void addNewDrugType(String drugType, float dosis);
		public void listDrugTypes() ;
		public List <String> getDrugTypes();
		
		//DRUG
		public void addNewDrug(Drug drug);
		public Integer getIdsFromDrugs(String drugName);
		public Drug searchDrugByName(String name);  
		public void deleteDrug(Drug drug);
		// public void modifyDrug(Drug drug); //needed?
		
        //HABITATS
		public List<String> weListHabitats();
		public void addHabitat(Habitat habitat)throws AdminExceptions;
		


		
		
		
}
