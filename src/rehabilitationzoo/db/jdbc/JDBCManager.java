package rehabilitationzoo.db.jdbc;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import rehabilitationzoo.db.ifaces.VetManager;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.FeedingType;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.Illness;
import rehabilitationzoo.db.pojos.LightType;
import rehabilitationzoo.db.pojos.Worker;
import utils.KeyboardInput;

public class JDBCManager implements rehabilitationzoo.db.ifaces.DBManager {
	
	public static Connection c; //TODO deberia ser private?


	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/management.db"); //could change this to prettier name
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			
			this.createTables();
			
			VetManager vetMan = new VetSQL();	
			List<String> habitatNames = vetMan.getAllHabitatsNames();


			String name = "";
					
			if (habitatNames.size() >= 5) {
				return;
	
		
			} else {
				KeyboardInput.weAddHabitats();
				KeyboardInput.weAddDrugTypes();
				KeyboardInput.weAddAnimalTypes();


			}
			KeyboardInput.weAddHabitats();
			KeyboardInput.weAddDrugTypes();
			KeyboardInput.weAddAnimalTypes();
		
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
			
	

	public void disconnect(){
		try {
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	private void createTables() { //we shouldn't have a main here -> build an interface
		
		try {			
			//Open database connection
			// Create tables: begin	
			
			Statement stmt1 = c.createStatement();
			String sql1 = "CREATE TABLE habitats "
					   + "(id			INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " name			TEXT	NOT NULL	UNIQUE, " 
					   + " lastCleaned	DATE	NOT NULL, "
					   + " waterTank	DATE	NOT NULL, "
					   + " temperature	FLOAT	NOT NULL, "
					   + " light		ENUM	NOT NULL )";
			stmt1.executeUpdate(sql1);
			stmt1.close();
			
			Statement stmt2 = c.createStatement();
			String sql2 = "CREATE TABLE groundTypes "
					   + "(id			INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " habitat_id 	INTEGER	NOT NULL	REFERENCES habitats(id), "
					   + " type			STRING	NOT NULL )";
			stmt2.executeUpdate(sql2);
			stmt2.close();
			
			Statement stmt8 = c.createStatement();
			String sql8 = "CREATE TABLE animals_characteristics "
					   + "(id			INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " feedingType	ENUM		NOT NULL, "
					   + " type			STRING		NOT NULL	UNIQUE )";
			stmt8.executeUpdate(sql8);
			stmt8.close();
			
			Statement stmt3 = c.createStatement();
			String sql3 = "CREATE TABLE animals "
					   + "(id			INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " enterDate	DATE	NOT NULL, "
					   + " habitat_id 	INTEGER REFERENCES habitats(id), " //can be null when dead or released
					   + " lastBath		DATE	NOT NULL, "
					   + " lastFed		DATE	NOT NULL, "
					   + " lastDrug		DATE	NOT NULL, "
					   + " deathDate	DATE, "
					   + " freedomDate	DATE, "
					   + " type_id		INTEGER	NOT NULL	REFERENCES animals_characteristics(id), "
					   + " name			STRING	NOT NULL	)";
			stmt3.executeUpdate(sql3);
			stmt3.close();
			
			Statement stmt4 = c.createStatement();
			String sql4 = "CREATE TABLE workers "
					   + "(id			INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " name			TEXT	NOT NULL, "
					   + " lastName 	TEXT	NOT NULL, "
					   + " hireDate		DATE	NOT NULL, "
					   + " salary		FLOAT	NOT NULL, "
					   + " workerType	ENUM	NOT NULL, "
					   + " photo 		BLOB, "
					   + " inWhichHabitatDoYouWork TEXT NULL)";
			stmt4.executeUpdate(sql4);
			stmt4.close();
			
			Statement stmt6 = c.createStatement();
			String sql6 = "CREATE TABLE drugTypes "
					   + "(id	INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " type	TEXT	NOT NULL, " 
					   + " dosis FLOAT NOT NULL)";
			
			stmt6.executeUpdate(sql6);
			stmt6.close();
			
			Statement stmt5 = c.createStatement();
			String sql5 = "CREATE TABLE drugs "
					   + "(id					INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " name					TEXT	NOT NULL	UNIQUE,"
					   + " treatmentDuration	INTEGER	NOT NULL, "
					   + " periodBetweenDosis	INTEGER	NOT NULL, "
					   + " drugType_id 			INTEGER NOT NULL	REFERENCES drugTypes(id) )";			   
			stmt5.executeUpdate(sql5);
			stmt5.close();
			
			
			Statement stmt7 = c.createStatement();
			String sql7 = "CREATE TABLE illnesses "
					   + "(id			INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " name			STRING	NOT NULL, " 	//he quitado el unique (name) porque podemos tener 
					   + " quarantine	BOOLEAN	NOT NULL, " 	//varias enfermedades con el mimso nombre pero 
					   + " prothesis	BOOLEAN	NOT NULL, " 	//que unas requieran cuarentena por ejemplo y otras no - Paula
					   + " drug_id 		INTEGER	REFERENCES drugs(id) )";
					   
			stmt7.executeUpdate(sql7);
			stmt7.close();

			Statement stmt9 = c.createStatement();
			String sql9 = "CREATE TABLE animal_drug "
					   + "(drug_id		INTEGER	REFERENCES drugs(id), "
					   + " animal_id	INTEGER	REFERENCES animals(id), "
					   + " PRIMARY KEY (drug_id, animal_id) )";
			stmt9.executeUpdate(sql9);
			stmt9.close();
			
			
			Statement stmt10 = c.createStatement();
			String sql10 = "CREATE TABLE worker_animal "
					   + "(worker_id	INTEGER REFERENCES workers(id), "
					   + " animal_id	INTEGER REFERENCES animals(id), "
					   + " PRIMARY KEY (worker_id, animal_id) )";
			stmt10.executeUpdate(sql10);
			stmt10.close();
			
			Statement stmt11 = c.createStatement(); 
			String sql11 = "CREATE TABLE animal_illness "
					   + "(animal_id	INTEGER REFERENCES animals(id), "
					   + " illness_id	INTEGER REFERENCES illnesses(id), "
					   + " PRIMARY KEY (illness_id, animal_id) )";
			stmt11.executeUpdate(sql11);
			stmt11.close();
			
			
		}  catch (SQLException e) {
		if (!e.getMessage().contains("already exists")) {
				e.printStackTrace();
			}
		}
		
	}

	
}