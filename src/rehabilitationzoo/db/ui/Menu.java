package rehabilitationzoo.db.ui;

import java.io.IOException;

import rehabilitationzoo.db.ifaces.DBManager;
import rehabilitationzoo.db.jdbc.JDBCManager;
import rehabilitationzoo.db.pojos.Animal;
import utils.KeyboardInput;
import utils.Utils;

public class Menu {
	
	public static DBManager dbman = new JDBCManager();		
	
	public static void main(String[] args) throws Exception, IOException{
		dbman.connect();
	
		do {
			//LOGING IN
			System.out.println("Choose an option: ");
			System.out.println("1. I am a vet ");
			System.out.println("2. I am an administrator ");
			System.out.println("3. I am a zoo-keeper ");
			System.out.println("0. Exit the program ");
			int choice = Utils.readInt();
			
	        switch (choice) {
	            case 1:
	            	vetOption1();
	
	                break;
	            case 2:
	            	adminOption2();
	            	//drug administration
	            	//choose a habitat
	            	//exit
	                break;
	            case 3: 
	            	zooKeeperOption3();
	            	//a. manage animals
	            		//a. add
	            			//newAnimal
	            		//b. return
	            			//1. getAnimal
	            			//2. changeReleaseDate
	            		//c. dead
	            			//1. getAnimal
	            			//2. changeDeathDate
	            	//b. manage workers
	            		//a. hire
	            			//new worker
	            		//b. fire
	            			//1. getWorker
	            			//2. delete worker
	            		//c. modify
	            			//salary
	            	//c. manage drugs 
	            		//a. add
	    					//new drug
	            		//b. delete
	    					//1. getDrug
	    					//2. delete drug
	            		//c. modify
	            			//??
	    			//salary
	            	//exit
	            	break;
	            case 0:
	            	dbman.disconnect();
	            	System.exit(0);
	            	break;
	            default:
	                System.out.println("Error, nonvalid input.");
	                break;
	        }
        
		}while (true);
		
	}

	
 	public static void vetOption1() throws IOException {
 		int vetMainChoice;
 		
		do {
			System.out.println("Please choose the action you want to complete: " + "\n"
				+ "	1. Animal diagnosis (newly entered animal)" + "\n"
				+ "	2. Animal check (daily)" + "\n"
				+ "	3. Go back to users menu."+ "\n") ;
		
			vetMainChoice = Utils.readInt();
		
		
			switch (vetMainChoice) {
				case 1:
					System.out.println("ANIMAL DIAGNOSIS");
					Animal animalToDiagnose = KeyboardInput.askForAnimal();
					KeyboardInput.diagnosisSubMenu(animalToDiagnose);
					break;
				case 2: 
					System.out.println("ANIMAL CHECK");
					Animal animalToCheck = KeyboardInput.askForAnimal();
					KeyboardInput.animalCheckSubMenu(animalToCheck);
					break;
				case 3:
					break;
				default: 
					System.out.println("Error, nonvalid input.");
					break;
			}
		}while(vetMainChoice != 3);
	}
 	
 	public static void adminOption2() {
		
	} 
	
	public static void zooKeeperOption3() {
		
		
	}


}
