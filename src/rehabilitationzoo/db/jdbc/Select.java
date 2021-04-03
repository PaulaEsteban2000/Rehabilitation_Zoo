package rehabilitationzoo.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import rehabilitationzoo.db.pojos.*;

public class Select {
	
	public static void main(String args[]) { //deeberia ser un main?
		try {
			// Open database connection
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:./db/management.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			
			// Retrieve data: begin
			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM workers";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String lastname = rs.getString("lastname");
				Date hireDate = rs.getDate("hireDate"); //Deberia ser la fecha un int?
				Integer salary = rs.getInt("salary");
			    WorkerType workertype =rs.getType("workertype");  //WorkerType.valueOf(rs.getString("type")); COMO HARIAMOS ESTO?
				String animals = rs.getString("animals");
				
				
			//	public Worker(Integer id, String name, String lastName, Date hireDate, Integer salary, WorkerType type,
			//			List<Animal> animals) {		
			
				
				Worker selectWorker = new Worker(id, name, lastname, hireDate, salary, type ,animals);
				System.out.println(selectWorker);
			}
			//rs.close();
			//stmt.close();
			//System.out.println("Search finished.");
			// Retrieve data: end
			
			// Close database connection
			c.close();
			System.out.println("Database connection closed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}