package rehabilitationzoo.db.ifaces;

import rehabilitationzoo.db.pojos.*;

public interface DBManager {
	
	public void connect ();
	public void disconnect ();

	public void feedAnimal(); //boolean?
	public void batheAnimal(); //boolean?
	public void cleanHabitat(); //boolean?
	public void fillUpWaterTank(); //boolean?
	public void drugAdministrationToAnimal();
	
	public void drugsPrescription();
	public void prothesisInstallation();
	public void checkAnimal(); //or animals?
	
	public void addAnimal(Animal animal);
	public void returAnimalToTheWilderness(Animal animal);
	public void markAnimalAsDeceased(Animal animal);
	
	public void hireWorker(Worker worker);
	public void fireWorker(Worker worker);
	public void modifyWorker(Worker worker);

	public void addNewDrug(Drug drug);
	public void deleteDrug(Drug drug);
	public void modifyDrug(Drug drug);

}
