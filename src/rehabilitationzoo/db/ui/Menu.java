package rehabilitationzoo.db.ui;

import java.io.*;
import java.time.LocalDate;

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
					System.out.println("");
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
		}while(vetMainChoice != 3); //No deberia haber un case 0???
	}
 	
 	
 	
 	
 	public static void adminOption2() throws NumberFormatException, IOException {

		BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));    
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
						int manageOfAnimals;
						System.out.println("\n"+"1. MANAGE OF ANIMALS"+"\n");
						System.out.println("Select the option that fits you the best"+"\n"
								+	"1.Add animals"+"\n"
					        	+	"2.Return animals to wilderness"+"\n"
					        	+	"3.Mark animals as dead"+"\n"
					        	+ 	"4.Go back"+ "\n");
						
						manageOfAnimals= Utils.readInt();
						
						switch(manageOfAnimals) {
						
						case 1:
							//ADD ANIMAL (INSERT)
			                //ARRAYLIST DONDE VAMOS A IR ALMACENANDO LOS ANIMALES
			                //ADEMÁS LOS ID DE LOS ANIMALES SERÁ IGUAL A LA POSICION EN EL ARRAYLIST
			                
							
							System.out.print("\n"+"Which type of animal would you like to add to the zoo?"+"\n");
							String readAnimal = consola.readLine();
							Animal.typesOfAnimalsInTheZoo unAnimal= null;
							
							
							exito=KeyboardInput.isThisAnAnimal(readAnimal);//metodo donde compare si lo que hemos puesto es un animal o no
							if(exito==true) {
		
								
							    unAnimal= KeyboardInput.whichType(readAnimal);
							    
							    System.out.print("\n"+"Put a name to the animal");
							    String name = consola.readLine();
							    
							    System.out.print("\n"+"Introduce the enter date of the "+ unAnimal +" "+ name +"\n");
							    
							    System.out.print("\n"+"Introduce the day");
							    String undia = consola.readLine();
							    int day= Integer.parseInt(undia);

							    System.out.print("\n"+"Introduce the month");
							    String unmes = consola.readLine();
							    int month= Integer.parseInt(unmes);

							    System.out.print("\n"+"Introduce the year");
							    String unanio = consola.readLine();
							    int year= Integer.parseInt(unanio);
							    
							    LocalDate enterDate= LocalDate.of(year,  month, day ) ;
							    
							    
							    
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
								
								
								System.out.print("\n"+"Congratulations you added a new animal to the zoo"+"\n");
							}
							else {
								System.out.print("\n"+" That´s not a type of animal present on the zoo"+"\n");
							}
							
							
							
							
						
						}
						
		                	
		                //RETURN ANIMAL TO WILDERNESS (UPDATE) 
		               	//When an animal enter we don´t insert any freedomDate
		               	//If the animal enters again the freedomDate will be NULL
		             
		                //MARK ANIMAL AS DEAD (UPDATE)
		                //BOOLEAN
	                	
	                break;
	                
	                case 2:
	                	System.out.println("\n"+"MANAGE OF THE WORKERS"+"\n");
						
	                	//HIRE WORKER (INSERT, UPDATE)
	                	//Add worker to the database
	                	
	                	//FIRE WORKER(DELETE)
	                	//Eliminate worker from the database and the arraylist
	                	//MODIFY WORKER´S SALARY
	                	
	                    break;
	                    
	                case 3:
	                	System.out.println("\n"+"MANAGE OF DRUGS"+"\n");
						
	                	//ADD DRUG
	                	//DELETE DRUG
	                	
	                    break;
	                case 4:
	                	//GO BACK
	                	//exit(0); //EXIT FOR THE WHILE(TRUE) METHOD
	                
	                default:
	                    System.out.print("\nThat´s not an option\n");
	        }
			
		}//while
	
 	
		}while(adminChoice != 4);
	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
	public static void zooKeeperOption3() {
		
		
	}


}
