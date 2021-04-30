package utils;

import java.sql.Statement;

import rehabilitationzoo.db.ifaces.DBManager;
import rehabilitationzoo.db.jdbc.JDBCManager;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.ui.Menu;

public class PAULA_AUIDA {
	
	public static DBManager dbman = new JDBCManager();	

	public void addAnimal(Animal animal) { //do we need a prepared Statement better to avoid injection? I think so bc it is insert
		try {
			//Id is chosen by the database
			Statement stmt = JDBCManager.c.createStatement(); 
			//JDBCManager.c porque asi tenemos una sola conexion abierta en la clase que se encarga de la DB - Paula
			String sql = "INSERT INTO animals (enterDate,habitat_id,foodPeriod,feedingType,lastBath,lastFed,deathDate,freedomDate,type,name)";
			sql+= "VALUES ('" + animal.getEnterDate() + "','" + animal.getHabitat_id() + "','" 
			+ animal.getFeedingType() + "','" + animal.getLastBath() + "','" + animal.getLastFed() + "','" + animal.getFreedomDate() + "','"
			+ animal.getType() +  "','" + animal.getName() + ")";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

//a. animal diagnosis
//1. insert type of animal
//2. insert name of animal
//3. getAnimal: search by type and name
//4. insert illness the animal has
	//1. if prothesis needed:
		//1. make prothesis true
		//2. change releaseDate to a month after diagnosis
		//3. go back
	//2. if other illness:
		//1. insert name of illness
		//2. insert dosis of drug
		//3. insert period between dosis
		//4. insert treatment duration
		//5. make quarantine true/false
		//6. go back
	//3. go back
//5. go back
//b. check
//1. insert  type of animal
//2. insert name of animal
//3. animal search by type and name
//4. Diagnosis changes 
	// if yes (diagnosis method once more)
       	//4. insert illness the animal has
   			//1. if prothesis needed:
   				//1. make prothesis true
   				//2. change releaseDate to a month after diagnosis
				//3. go back
			//2. if other illness:
    			//1. insert name of illness
    			//2. insert dosis of drug
    			//3. insert period between dosis
    			//4. insert treatment duration					    				
				//5. make quarantine true/false
				//6. go back
		//go back.
//5. Report state from the animal
	//a. Stays in zoo (no changes, goes back to menu)
	//b. Release to freedom (changes releaseDate)
	//c. Deceased (changes deathDate)
//6. go back
//c. exit