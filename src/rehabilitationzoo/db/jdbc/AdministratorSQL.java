package rehabilitationzoo.db.jdbc;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import rehabilitationzoo.db.ifaces.AdministratorManager;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.Worker;

public class AdministratorSQL implements AdministratorManager{

	@Override
	public List<Worker> searchWorkerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Drug> searchDrugByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAnimal(Animal animal) { //do we need a prepared Statement better to avoid injection? I think so bc it is insert
		try {
			//Id is chosen by the database
			Statement stmt = JDBCManager.c.createStatement(); //JDBCManager.c porque asi tenemos una sola conexion abierta en la clase que se encarga de la DB - Paula
			String sql = "INSERT INTO animals (enterDate,habitat_id,foodPeriod,feedingType,lastBath,lastFed,deathDate,freedomDate,type,name)";
			sql+= "VALUES ('" + animal.getEnterDate() + "','" + animal.getHabitat_id() + "','" + animal.getFoodPeriod() + "','" 
			+ animal.getFeedingType() + "','" + animal.getLastBath() + "','" + animal.getLastFed() + "','" + animal.getFreedomDate() + "','"
			+ animal.getType() +  "','" + animal.getName() + ")";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void returAnimalToTheWilderness(Animal animal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void markAnimalAsDeceased(Animal animal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hireWorker(Worker worker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Worker getWorker(String name, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fireWorker(Worker worker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyWorker(Worker worker, Integer salary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewDrug(Drug drug) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDrug(Drug drug) {
		// TODO Auto-generated method stub
		
	}

}
