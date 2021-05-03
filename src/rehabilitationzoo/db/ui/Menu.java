package rehabilitationzoo.db.ui;

import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rehabilitationzoo.db.ifaces.DBManager;
import rehabilitationzoo.db.jdbc.JDBCManager;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.Habitat;
import utils.KeyboardInput;
import utils.Utils;

public class Menu {
	
	public static DBManager dbman = new JDBCManager();	
	
	
	public static void main(String[] args) throws Exception, IOException{
	
		dbman.connect();
	
		do {
			//LOGGING IN
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
	                break;
	                
	            case 3: 
	            	zooKeeperOption3();
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
	
 	
	public static void vetOption1() throws IOException, SQLException {
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
					KeyboardInput.firstDiagnosisSubMenu(animalToDiagnose);
					break;
				case 2: 
					//ME HE QUEDADO ARREGLANDO EL CHECK PARA ACCEDER A LOS ANIMALES Y TIPOS POR HABITAT
					System.out.println("ANIMAL CHECK");
					Habitat habitatToCheck = KeyboardInput.askForHabitatToCheckItsAnimals();
					KeyboardInput.animalCheckSubMenu(habitatToCheck);
					break;
				case 3:
					break;
				default:  
					System.out.println("Error, nonvalid input.");
					break;
			}
			
		}while(vetMainChoice != 3); //No deberia haber un case 0???
	}
 	
 	
 	public static void adminOption2() throws NumberFormatException, IOException {
 
 		int adminChoice;
 		boolean exito=false;
 		
		do {
			System.out.println("Select an option: "+"\n" 
        	+	"1.Manage animals"+"\n"
        	+	"2.Manage workers"+"\n"
        	+	"3.Manage drugs"+"\n"
        	+ 	"4.Go back"+ "\n");
		
			adminChoice = Utils.readInt();
		
			while(true) {
				switch (adminChoice) {
			 
				case 1:
					System.out.println("\n"+"1. MANAGE OF ANIMALS"+"\n");
					System.out.println("Select the option that fits you the best"+"\n"
							+	"1.Add animals"+"\n"
				        	+	"2.Return animals to wilderness"+"\n"
				        	+	"3.Mark animals as dead"+"\n"
				        	+ 	"4.Go back"+ "\n");
					int manageOfAnimals= Utils.readInt();
					
					switch(manageOfAnimals) {
					case 1:
						//ADD ANIMAL (INSERT)
						//ARRAYLIST DONDE VAMOS A IR ALMACENANDO LOS ANIMALES
						//ADEMAS LOS ID DE LOS ANIMALES SERA IGUAL A LA POSICION EN EL ARRAYLIST
			                
							System.out.print("\n"+"Which type of animal would you like to add to the zoo?"+"\n");
							String readAnimal = Utils.readLine();
							
							exito=KeyboardInput.isThisAnAnimal(readAnimal);//metodo donde compare si lo que hemos puesto es un animal o no
							//esto que quieres hacer... deberia ser una expecion, no? que te parece? - Paula
							if(exito==true) {
		
						//	    String unAnimal= KeyboardInput.whichType(readAnimal);
							    
							    System.out.print("\n"+"Put a name to the animal");
							    String name = Utils.readLine();
							    
							    System.out.print("\n"+"Introduce the enter date of the "+ readAnimal +" "+ name +"\n");
							    
							    System.out.print("\n"+"Introduce the day");
							    int day = Utils.readInt();

							    System.out.print("\n"+"Introduce the month");
							    int month = Utils.readInt();

							    System.out.print("\n"+"Introduce the year");
							    int year = Utils.readInt();
							    
							    Date enterDate= new Date (year,  month, day) ;
							    

							    /*this.animalType= animalType;
								this.name= name;
								
								this.enterDate = enterDate;
								this.habitat_id = habitat_id;
								this.foodPeriod = foodPeriod;
								this.feedingType = feedingType;
								this.lastBath = lastBath;
								this.lastFed = lastFed;
								this.deathDate = deathDate;
								this.freedomDate = freedomDate;*/
						
							    Animal infoAnimal= new Animal(unAnimal, name, enterDate);
								KeyboardInput.addAnimal(infoAnimal); 

							    Animal infoAnimal= new Animal(readAnimal, name, enterDate);
							    KeyboardInput.addAnimal(infoAnimal);
							    KeyboardInput.puttingIdsAnimals(infoAnimal);
								
								System.out.print("\n"+"Congratulations you added a new animal to the zoo"+"\n");
							}
							else {
								System.out.print("\n"+" That is not a type of animal present on the zoo"+"\n");
							}
							
							
							
							
						
						}
						
						
						//THIS IS FOR THE VET
		                	
		                //RETURN ANIMAL TO WILDERNESS (UPDATE) 
		               	//When an animal enter we don´t insert any freedomDate
		               	//If the animal enters again the freedomDate will be NULL
		             
		                //MARK ANIMAL AS DEAD (UPDATE)
		                //BOOLEAN
						
						
						
						//TODO PAULA: Put the method for the diagnosis of the new animal
	                	
	                break;
	                
	                case 2:
	                	System.out.println("\n"+"MANAGEMENT OF THE WORKERS"+"\n");
						
	                	//HIRE WORKER (INSERT, UPDATE)
	                	//Add worker to the database
	                	
	                	//FIRE WORKER(DELETE)
	                	//Eliminate worker from the database and the arraylist
	                	//MODIFY WORKER´S SALARY
	                	
	                    break;
	                    
	                case 3:
	                	System.out.println("\n"+"MANAGEMENT OF DRUGS"+"\n");
						
	                	//ADD DRUG
	                	//DELETE DRUG
	                	
	                    break;
	                case 4:
	                	//GO BACK
	                	//exit(0); //EXIT FOR THE WHILE(TRUE) METHOD
	                
	                default:
	                    System.out.print("\nThat is not an option\n");
	        }
			
		}//while
	
 	
		}while(adminChoice != 4);
	}
 	
 	
	public static void zooKeeperOption3() throws IOException, SQLException{
		int zooKeeperChoice;
		Integer habitatId;
 		
		do {
			System.out.println("Please choose the habitat you are in: " + "\n");
					Habitat habitatToSearch = KeyboardInput.askForHabitat();
			
			System.out.println("Please choose the action you want to complete: " + "\n"
				+ "	1. Feed animals" + "\n"
				+ "	2. Bathe animals" + "\n"
				+ "	3. Drugs administration" + "\n"
				+ "	4. Clean habitats" + "\n"
				+ "	5. Fill up water tanks" + "\n"
				+ "	6. Go back to users menu."+ "\n") ;
		
				zooKeeperChoice = Utils.readInt();
			switch (zooKeeperChoice) {
				case 1:
				case 2: 
				case 3:
					KeyboardInput.drugAdminSubMenu(habitatToSearch, zooKeeperChoice);
					break;
					
					
				case 4:
				case 5:
					KeyboardInput.habitatSubMenu(habitatToSearch, zooKeeperChoice);
					break;
					
					
				case 6:
					break;
				default:  
					System.out.println("Error, nonvalid input.");
					break;
			}
		}while(zooKeeperChoice != 6); 
		
		
	}


}
