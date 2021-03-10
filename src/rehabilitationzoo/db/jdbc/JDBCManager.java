package rehabilitationzoo.db.jdbc;

import java.sql.*;

import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.Worker;

public class JDBCManager implements rehabilitationzoo.db.ifaces.DBManager {
	
	
	private Connection c;

	
	public void connect () {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/company.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			
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
	
	public void create () { //we shouldn't have a main here -> build an interface
		
		try {
			// Open database connection
			Class.forName("org.sqlite.JDBC"); 
			c = DriverManager.getConnection("jdbc:sqlite:./db/management.db"); //creates a new file whose name is management, where the data bases going to be created
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			
			// Create tables: begin
			Statement stmt1 = c.createStatement();
			String sql1 = "CREATE TABLE animal "
					   + "(id	INTEGER	PRIMARY KEY	AUTOINCREMENT,"
					   + " enterDate	DATE	NOT NULL, "
					   + " foodPeriod	INTEGER	NOT NULL, "
					   + " feedingType	ENUM	NOT NULL, "
					   + " lastBath	DATE , "
					   + " lastFed	DATE , "
					   + " deathDate	DATE, "
					   + " freedomDate	DATE, "
					   + " FOREIGN KEY (habitat_id) REFERENCES habitat(id), )";
			stmt1.executeUpdate(sql1);
			stmt1.close();
			
			Statement stmt2 = c.createStatement();
			String sql2 = "CREATE TABLE habitat "
					   + "(id	INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " name	TEXT	NOT NULL	UNQIUE," 
					   + " lastCleaned	DATE , "
					   + " waterLevel	FLOAT	NOT NULL, "
					   + " ground	ENUM	NOT NULL, "//should be unique if it's an enum?
					   + " temperature	ENUM	NOT NULL, "
					   + " light	ENUM	NOT NULL, )";
			stmt2.executeUpdate(sql2);
			stmt2.close();
			
			Statement stmt3 = c.createStatement();
			String sql3 = "CREATE TABLE workers "
					   + "(id	INTEGER	PRIMARY KEY	AUTOINCREMENT, "
					   + " name	TEXT	NOT NULL, "
					   + " lastName TEXT	NOT NULL, "
					   + " hireDate	DATE	NOT NULL, "
					   + " salary	FLOAT	NOT NULL, "
					   + " workerType	ENUM	NOT NULL, )";
			stmt3.executeUpdate(sql3);
			stmt3.close();
			
			
			Statement stmt4 = c.createStatement();
			String sql4 = "CREATE TABLE illness "
					   + "(id	INTEGER	PRIMARY	KEY,"
					   + " name	ENUM	NOT NULL, "
					   + " quarantine	INTEGER  , "
					   + " prothesis	BOOLEAN )";
					   
			stmt4.executeUpdate(sql4);
			stmt4.close();
			
			Statement stmt5 = c.createStatement();
			String sql5 = "CREATE TABLE drug "
					   + "( id	INTEGER	PRIMARY KEY"
					   + " name	TEXT	NOT NULL	UNIQUE,"
					   + " treatmentDuration	INTEGER	NOT NULL, "
					   + " periodBetweenDosis	INTEGER	NOT NULL, "
					   + " drugType	ENUM	NOT NULL,"
					   + " FOREIGN KEY (illness_id) REFERENCES illness(id), )";					   
			stmt5.executeUpdate(sql5);
			stmt5.close();
			
			Statement stmt6 = c.createStatement();
			String sql6 = "CREATE TABLE animal_drug " //is this necessary? bc there already is a connection between these through the other tables
					   + "(drug_id	INTEGER	REFERENCES drug(id), "
					   + " animal_id	INTEGER	REFERENCES animal(id), "
					   + " PRIMARY KEY (drug_id, animal_id )";
			stmt6.executeUpdate(sql6);
			stmt6.close();
			
			Statement stmt7 = c.createStatement();
			String sql7 = "CREATE TABLE worker_animal "
					   + "(worker_id	INTEGER REFERENCES worker(id), "
					   + " animal_id	INTEGER REFERENCES animal(id), "
					   + " PRIMARY KEY (worker_id, animal_id )";
			stmt7.executeUpdate(sql7);
			stmt7.close();
			
			Statement stmt8 = c.createStatement(); 
			String sql8 = "CREATE TABLE animal_illness "
					   + "(drug_id	INTEGER REFERENCES animal_(id), "
					   + " illness_id	INTEGER REFERENCES illness(id), "
					   + " PRIMARY KEY (animal_id, illness_id )";
			stmt8.executeUpdate(sql8);
			stmt8.close();
			// Create table: end
			
			// - Set initial values for the Primary Keys
			// - Don't try to understand this until JPA is explained
			// This is usually not needed, since the initial values
			// are set when the first row is inserted, but since we
			// are using JPA and JDBC in the same project, and JPA
			// needs an initial value, we do this.
			//Statement stmtSeq = c.createStatement();
			//String sqlSeq = "INSERT INTO sqlite_sequence (name, seq) VALUES ('departments', 1)";
			//stmtSeq.executeUpdate(sqlSeq);
			//sqlSeq = "INSERT INTO sqlite_sequence (name, seq) VALUES ('employees', 1)";
			//stmtSeq.executeUpdate(sqlSeq);
			//sqlSeq = "INSERT INTO sqlite_sequence (name, seq) VALUES ('reports', 1)";
			//stmtSeq.executeUpdate(sqlSeq);
			//stmtSeq.close(); 
			
			// Close database connection
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void feedAnimal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batheAnimal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanHabitat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillUpWaterTank() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drugAdministrationToAnimal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drugsPrescription() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prothesisInstallation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkAnimal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAnimal(Animal animal) {
		// TODO Auto-generated method stub
		
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
	public void fireWorker(Worker worker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyWorker(Worker worker) {
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

	@Override
	public void modifyDrug(Drug drug) {
		// TODO Auto-generated method stub
		
	}
}