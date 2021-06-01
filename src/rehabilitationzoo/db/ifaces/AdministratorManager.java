package rehabilitationzoo.db.ifaces;

import java.sql.SQLException;
import java.util.List;

import Exceptions.AdminExceptions;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.AnimalType;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.DrugType;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Worker;

public interface AdministratorManager {
	//PARTE DE NATI
	
	//ADMINISTRATOR
		
		//public List<Drug> searchDrugsByName (String name); //cast??
		
	
		//ANIMALS
		public void listAnimals () ;
		public void updateAnimal(Animal animal);
		public void addAnimal(Animal animal) ;
		
		
		//ANIMALS TYPES
		public void introducingAnimalsTypes(AnimalType animalType) ;
		public List<String> getAnimalTypesByName();
		public List<Integer> getAnimalTypesById(AnimalType type);
		
		//WORKERS
		public List<Worker> searchWorkerByName (String name);
		public void introducingWorkers(Worker aWorker);
		public Worker getAWorkerFromNameAndLastname(String name, String lastname) ;
		public Worker getAWorkerFromId(Integer id) ;
		
		public List<String> getAllWorkersNamesAndLastNames() ;
		public void deleteThisWorker(Integer id);
		public void modifyWorker(Integer id , Float salary);
		public List<Worker> getWorkersInfo() ;
		
	    //DRUGS TYPES
		public void addNewDrugType(String drugType, float dosis);
		public void listDrugTypes()  ;
		public List<DrugType> getDrugTypes() ;
		
		//DRUG
		public void addNewDrug(Drug drug);
		//public List <Integer> getIdsFromDrugs(String drugName);throws AdminExceptions;  
		public void deleteDrug(Integer drugId);
		//public String getTypeFromDrugs(Integer id);
		public List <Drug> getDrugs() ;
		public Drug searchDrugByName(String name);
		// public void modifyDrug(Drug drug); //needed?
		
        //HABITATS
		public List<String> weListHabitats() ;
		public void addHabitat(Habitat habitat);
		
		
		
		


		
		
		
}
