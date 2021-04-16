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
		
		//necesitamos 2 métodos nuevos en animal que sean: type, name

		do {
			//LOGING IN
			System.out.println("Choose an option: ");
			System.out.println("1. I am a vet ");
			System.out.println("2. I am an administrator ");
			System.out.println("3. I am a zoo-keeper ");
			System.out.println("0. Exit the program ");
			int choice = Integer.parseInt(reader.readLine());
			
        switch (choice) {
            case 1:
            	//a. animal diagnosis
            		//1. insert type of animal
            		//2. insert name of animal
            		//3. animal search by type and name
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
                break;
            case 2:
            	//drug administration
            	//choose a habitat
            	//exit
                break;
            case 3: 
            	//manage drugs
            	//manage workers
            	//manage drugs 
            	//exit
            	break;
            case 0:
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
