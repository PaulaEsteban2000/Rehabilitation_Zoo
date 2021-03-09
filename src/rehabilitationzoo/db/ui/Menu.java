package rehabilitationzoo.db.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import rehabilitationzoo.db.ifaces.DBManager;
import rehabilitationzoo.db.jdbc.JDBCManager;

public class Menu {
	
	private static DBManager dbman = new JDBCManager();
	private static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		
		dbman.connect();
		
		do {
			System.out.println("Choose an option: ");
			System.out.println("1. ");
			System.out.println("2. ");
			System.out.println("3. Exit the program ");
			int choice = Integer.parseInt(reader.readLine());
			
        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
            	dbman.disconnect();
            	System.exit(0);
            	break;
            default:
                System.out.println("Error, opcion no disponible.");
                ;
        }
        
	}while (true);
	}
	
}
