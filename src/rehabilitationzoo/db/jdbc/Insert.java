package rehabilitationzoo.db.jdbc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;

import rehabilitationzoo.db.pojos.FeedingType;
import rehabilitationzoo.db.pojos.FeedingType.*;

public class Insert {

	public static void main(String args[]) {
		try {
			// Open database connection
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:./db/management.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");

			// Get the employee info from the command prompt
			System.out.println("Please, input the department info:");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("ID of the worker: ");
			String workerID = reader.readLine();
			System.out.print("Name of the worker: ");
			String name = reader.readLine();
			/*System.out.print("Last Name: ");
			String lastName = reader.readLine();*/
			
			System.out.print("Hire Date"+"\n"+" Day: ");
			String a1 = reader.readLine();
			int day = Integer.parseInt(a1);
			System.out.print("Month: ");
			String a2 = reader.readLine();
			int month = Integer.parseInt(a2);
			System.out.print("Year");
			String a3 = reader.readLine();
			int year = Integer.parseInt(a3);
			
			LocalDate hireDate = LocalDate.of(year, month, day);
			/*
			System.out.print("Salary of the worker: ");
			String salary = reader.readLine();
			
			System.out.print("Put the type of worker that you are: ");
			String workerType = reader.readLine(); */
			
			
			// Insert new record: begin
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO workers (id, name, hireDate) "
					+ "VALUES ( '"+ workerID + "','" + name + "','"+ hireDate +"');";         
				
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("Workers info saved");
			System.out.println("Records inserted in the database.");
			// Insert new record: done

			
			
			System.out.println("Please, input the animals info:");
			BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("ID of theanimal: ");
			String animalID = reader.readLine();
			System.out.print("Put the feeding type of the animal: CARNIVORE, HERVIBORE, OMNIVORE "+"\n");
			String food = reader.readLine();
			FeedingType.valueOf(food);
			
			Statement stmt1 = c.createStatement();
			String sql1 = "INSERT INTO animals (name, food) "
					+ "VALUES ( '"+ name + "','"+ food +"');";        
			
			
			
			
			
			
			
			// Close database connection
			c.close();
			System.out.println("Database connection closed.");
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}