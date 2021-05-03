package rehabilitationzoo.db.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rehabilitationzoo.db.ifaces.AdministratorManager;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.AnimalType;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.Habitat;
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
			//Ids are chosen by the database
			Statement stmt = JDBCManager.c.createStatement(); //JDBCManager.c porque asi tenemos una sola conexion abierta en la clase que se encarga de la DB - Paula
			String sql = "INSERT INTO animals (enterDate,lastBath,lastFed,deathDate,freedomDate,name)";
			sql+= "VALUES ('" + animal.getEnterDate() + "','"  + animal.getLastBath() + "','" 
							  + animal.getFreedomDate() + "','" + animal.getName() + ")";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void introducingAnimalsTypes (AnimalType animalType) { 
		try {
			//Ids are chosen by the database
			Statement stmt = JDBCManager.c.createStatement(); 
			String sql = "INSERT INTO animals characteristics(id, type,feedingType)";
			sql+= "VALUES ('" + AnimalType.getType() + "','" + AnimalType.getWhatDoYouEat() + ")";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public void introducingWorkers (Worker aWorker) {//Id is chosen by the database

		try {  			
			
			Statement stmt = JDBCManager.c.createStatement(); 
			String sql = "INSERT INTO workers (name, lastname, hireDate, salary, workerType, whichHabitatDoYouWorkOn )";
			sql+= "VALUES ('" + aWorker.getName() + "','" + aWorker.getLastName() + "','" +aWorker.getHireDate()+ "','" +aWorker.getSalary()
			+ aWorker.getType()+ "','" + aWorker.getwhichHabitatDoYouWorkOn() + ")";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<String> getAllWorkersNamesAndLastNames(){
		List<String> workersNamesAndLastNames = new ArrayList<String>();
		
		try {
		String sql = "SELECT names,lastnames FROM workers"; 			    
		PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
		ResultSet rs = prep.executeQuery();
		
		while (rs.next()) { //like hasNext
			String nameAndLastName = rs.getString("name"+" lastname");
			workersNamesAndLastNames.add (nameAndLastName);
		}
		
			prep.close();
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	return workersNamesAndLastNames;
	
	}
	
	public void deleteThisWorker(String nameAndLastName) {
		
		try {
			String sql = "DELETE * FROM workers WHERE name LIKE ? AND WHERE lastname LIKE ?"; 			    
			PreparedStatement prep = JDBCManager.c.prepareStatement(sql);	
			ResultSet rs = prep.executeQuery();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
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
