package rehabilitationzoo.db.ui;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import rehabilitationzoo.db.ifaces.DBManager;
import rehabilitationzoo.db.ifaces.UserManager;
import rehabilitationzoo.db.jdbc.JDBCManager;
import rehabilitationzoo.db.jpa.JPAUserManager;
import rehabilitationzoo.db.pojos.Animal;
import rehabilitationzoo.db.pojos.AnimalType;
import rehabilitationzoo.db.pojos.Drug;
import rehabilitationzoo.db.pojos.DrugType;
import rehabilitationzoo.db.pojos.FeedingType;
import rehabilitationzoo.db.pojos.Habitat;
import rehabilitationzoo.db.pojos.LightType;
import rehabilitationzoo.db.pojos.users.Role;
import rehabilitationzoo.db.pojos.users.User;
import rehabilitationzoo.db.pojos.Worker;
import rehabilitationzoo.db.pojos.WorkerType;
import utils.KeyboardInput;
import utils.Utils;



public class Menu {
	
	private static DBManager dbman = new JDBCManager();			//should be private
	private static UserManager userMan = new JPAUserManager(); 	//should be private
	
	//TODO all methods here should be private if we can
	//TODO ask Rodrigo how to make diagnosis from another user once an animal is added

	
	public static void main(String[] args) throws Exception, IOException{
	
		dbman.connect();
		userMan.Connect();
	
		do {
			//LOGGING IN
			System.out.println("Choose an option: ");
			System.out.println("1. Register ");
			System.out.println("2. Login ");
			System.out.println("0. Exit the program ");
			int choice = Utils.readInt();
			
	        switch (choice) {
	            case 1:
	            	register();
	                break;
	                
	            case 2: 
	            	login();
	            	break;
	            	
	            case 0:
	            	dbman.disconnect();
	            	userMan.Disconnect();
	            	System.exit(0);
	            	break;
	            	
	            default:
	                System.out.println("Error, nonvalid input.");
	                break;
	        }
        
		}while (true);
		
	}
	
	private static void register() throws IOException, Exception {
		System.out.println("Please type in your email address:");
		String email = Utils.readLine();
		//We can ask for it twice, for checking, but Rodrigo does not like it much
		
		System.out.println("Now write your password:");
		String password = Utils.readLine();
		
		System.out.println(userMan.getRoles());
		System.out.println("Type the chosen role ID: ");
		int id = Utils.readInt();
		Role role = userMan.getRole(id);
		
		MessageDigest md = MessageDigest.getInstance("MD5"); //for changing the password into a different language which is MD5
		md.update(password.getBytes());
		byte[] hash = md.digest(); //the hash that is the one that translates
		
		User user = new User (email, hash, role);
		userMan.newUser(user);
	}
	
	private static void login() throws IOException, SQLException {
		System.out.println("Please type in your email address:");
		String email = Utils.readLine();
		
		System.out.println("Now write your password:");
		String password = Utils.readLine();
		
		User user = userMan.checkPassword(email, password); //we do not need a hash bc of the method we are using
		if (user == null) {
			System.out.println("Wrong email of password");
		}else if (user.getRole().getName().equalsIgnoreCase("vet")) {
			vetOption1();
		}else if (user.getRole().getName().equalsIgnoreCase("zookeeper")) {
			zooKeeperOption3();
		}else if (user.getRole().getName().equalsIgnoreCase("admin")) {
			adminOption2();
		}
		
		
	}

 	
	private static void vetOption1() throws IOException, SQLException {
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
			
		}while(vetMainChoice != 3);
	}
 	
	
	//TODO PAULA: Put the method for the diagnosis of the new animal
	
 	
	private static void adminOption2() throws NumberFormatException, IOException, SQLException {
 
 		int adminChoice;
 		boolean exito=false;
 		
		do {
			System.out.println("Select an option: "+"\n" 
        	+	"1.Manage animals"+"\n"
        	+	"2.Manage workers"+"\n"
        	+	"3.Manage drugs"+"\n"
        	+   "4.Manage habitats"+"\n"
        	+ 	"5.Go back"+ "\n");
		
			adminChoice = Utils.readInt();
		
			while(true) {
				switch (adminChoice) {
			 
				case 1:
					System.out.println("\n"+"1. MANAGE OF ANIMALS"+"\n");
					System.out.println("Select the option that fits you the best"+"\n"
							+	"1.Add animals"+"\n"
				        	+	"2.Add new types of animals to the zoo"+"\n"
				        	+	"3.Show all the animals in the zoo"+ "\n"
				        	+ 	"4.Go back"+ "\n");
					
					int manageOfAnimals= Utils.readInt();
					
					switch(manageOfAnimals) {
						case 1://ADD ANIMAL (INSERT)  
							System.out.print("\n"+"Which type of animal would you like to add to the zoo?"+"\n");
							String readAnimal = Utils.readLine();
							
							boolean exito1 = KeyboardInput.isThisAnAnimal(readAnimal);//esto que quieres hacer... deberia ser una expecion, no? que te parece? - Paula
							if(exito1==true) {
							    
							    System.out.print("\n"+"Put a name to the animal");
							    String name = Utils.readLine();
							    System.out.print("\n"+"Introduce the enter date of the "+ readAnimal +" "+ name +"\n");
							    System.out.print("\n"+"Introduce the day");
							    int day = Utils.readInt();
							    System.out.print("\n"+"Introduce the month");
							    int month = Utils.readInt();
							    System.out.print("\n"+"Introduce the year");
							    int year = Utils.readInt();
							    LocalDate enterLocalDate = LocalDate.of(year, month, day);
							    Date enterDate = Date.valueOf(enterLocalDate);
							    LocalDate now = LocalDate.now();
							    Date today = Date.valueOf(now); //lastfed + lastbathe
							    
							    Animal anAnimal = new Animal(enterDate, today, today,null, null, name);
							    KeyboardInput.addAnimalInTheZoo(anAnimal); //DIRECTLY WE HAVE TO ADD IT TO THE TABLES IN SQL
							    System.out.print("\n"+"Congratulations you added a new animal to the zoo"+"\n");
							    
							    KeyboardInput.firstDiagnosisSubMenu(anAnimal); //Paula: first diagnosis of an animal
							    
								}
								else {
									System.out.print("\n"+" That is not a type of animal present on the zoo"+"\n");
								}
							    break;
							
							    
							
						case 2://ADD ANIMALTYPE (INSERT)
					
							System.out.print("\n"+"Which animal would you like to add to the zoo?"+"\n");
							String readAnimal1 = Utils.readLine();//WE CHECK IF THE ANIMAL EXITS OR NOT IN THE ZOO
							boolean weHaveThisType = KeyboardInput.isThisAnAnimal(readAnimal1);
							if(weHaveThisType == true) {
								System.out.print("\n"+"We already have this type of animal in the zoo.");
								break;
							}
							else {
								System.out.print("\n"+ "Introduce the feeding type of the "+readAnimal1 +"\n"+"Remember they could be CARNIVORE, HERVIBORE or OMNIVORE"+"\n");
								String foodType = Utils.readLine();
								FeedingType aType =FeedingType.valueOf(foodType);
									
								AnimalType animalType= new AnimalType (readAnimal1, aType);
								//KeyboardInput.typesOfAnimalsInTheZoo(readAnimal1);
								KeyboardInput.addAnimalTypeInTheZoo(animalType);
								
								System.out.print("\n"+"New animal introduced in the zoo"+"\n");
								}
							break;
							
						case 3: 
							
							List <String>allTheAnimals = KeyboardInput.adminMan.getAnimalTypesByName();
							for(int i=0; i<allTheAnimals.size(); i++) {
								System.out.print((i+1)+allTheAnimals.get(i)+"\n");
							}
							break;
							 
						default :	
							System.out.println("Error, nonvalid input.");
							break;
								
							}
					break;
	                
	  case 2:
	                	System.out.println("\n"+"2.MANAGEMENT OF THE WORKERS"+"\n");
	                	
	                	System.out.println("Select the option that fits you the best"+"\n"
								+	"1.Hire workers"+"\n"
					        	+	"2.Fire workers"+"\n"
					        	+	"3.Modify worker´s salary"+ "\n"
	                			+   "4.Go back"+ "\n");
						
						int manageOfWorkers= Utils.readInt();
						
						switch(manageOfWorkers) {
						case 1:
							
							System.out.print("\n"+ "Introduce the name of the new worker"+"\n");
							String workerName= Utils.readLine();
							System.out.print("\n"+ "Introduce the lastname"+"\n");
							String workerLastName= Utils.readLine();
							System.out.print("\n"+"Select the job that the worker is going to have"+"\n" );
							
							 System.out.print("\n"+"Introduce the date of the worker we just hired"+"\n");
							    
							    System.out.print("\n"+"Introduce the day");
							    int day = Utils.readInt();

							    System.out.print("\n"+"Introduce the month");
							    int month = Utils.readInt();

							    System.out.print("\n"+"Introduce the year");
							    int year = Utils.readInt();
							    
							    Date workerDate= new Date (year,  month, day) ;
							
							
							System.out.print("\n"+ "Introduce the salary of "+workerName+ " " +workerLastName+"\n");
							String salary=  Utils.readLine();
							float workerSalary= Float.parseFloat(salary);
							
							System.out.print("\n"+ "Introduce the job of the new worker"+"\n"+"Remember, the posibilities are:ZOO_KEEPER, VETERINARY ,ADMINISTRATOR "+"\n");
							String aWork= Utils.readLine();
							WorkerType job= WorkerType.valueOf(aWork);
							
							
							
							
							if( WorkerType.ZOO_KEEPER.equals(job)) {
							System.out.print("\n"+ "Introduce the habitat where "+workerName+" is going to work "+"\n");
							String whichType= Utils.readLine();
							
							boolean realHabitat =KeyboardInput.isThisAnHabitat(whichType);
							
								if(realHabitat==true) {
								
								Worker workerInfo = new Worker( workerName, workerLastName, workerDate, workerSalary, job, whichType);
								KeyboardInput.addWorker(workerInfo);
								break;
								//Mostramos todos los animales con los que va a trabajar esta persona
								//no falta ver que habitats tenemos??
									}
								else {
									System.out.print("\n"+"Thats not an existent habitat"+"\n");
									break;
								}
								
							}//if zoo keeper
							
							
							else {
								Worker workerInfo2 = new Worker( workerName, workerLastName, workerDate, workerSalary, job);
								KeyboardInput.addWorker(workerInfo2);
							}
							
									
							break;
						
						case 2:
							System.out.print("\n"+ "This are the workers that we have in the zoo"+"\n");
							List <String> workersInfo = KeyboardInput.adminMan.getAllWorkersNamesAndLastNames();
							
							for(int i = 0; i<workersInfo.size(); i++){
								System.out.print(workersInfo.get(i)+"\n");
									 
								 }
							
							System.out.print("\n"+ "Introduce the name of the worker you want to fire"+"\n");
							String workerNameToDelete= Utils.readLine();
							System.out.print("\n"+ "Introduce the lastname"+"\n");
							String workerLastNameToDelete= Utils.readLine();
							
							boolean deleted= KeyboardInput.firingWorkers(workerNameToDelete, workerLastNameToDelete);
							if( deleted==true) {
								System.out.print("\n"+"Worker deleted"+"\n");
							}
							else {
								System.out.print("\n"+"The worker hasn´t been deleted"+"\n");}
							
							
							break;
							
						case 3: 

							System.out.print("\n"+ "Introduce the name of the worker that you want to change the salary"+"\n");
							String workerNameChanges= Utils.readLine();
							System.out.print("\n"+ "Introduce the lastname"+"\n");
							String workerLastNameChanges= Utils.readLine();
							System.out.print("\n"+"Which salary would you like that "+workerNameChanges+" has now?"+"\n");
							String changeSalary = Utils.readLine();
							Float aSalary = Float.parseFloat(changeSalary);
							
							boolean changes=KeyboardInput.modificationsSalary(workerNameChanges, workerLastNameChanges, aSalary);
							
							if (changes==true){
								System.out.print("\n"+"Salary updated"+"\n");
							}
							else {
								System.out.print("\n"+"The salary hasn´t been changed"+"\n");
							}
							break;
							
							
						case 4: 
							break;
							 
						default :	
							System.out.println("Error, nonvalid input.");
							break;
	                	
						}
						
	                	//HIRE WORKER (INSERT, UPDATE)
	                	//Add worker to the database
	                	
	                	//FIRE WORKER(DELETE)
	                	//Eliminate worker from the database and the arraylist
	                	//MODIFY WORKER´S SALARY
			
			
	  case 3:
	                	System.out.println("\n"+"MANAGEMENT OF DRUGS"+"\n");
	                	System.out.println("Select the option that fits you the best"+"\n"
								+	"1.Add new drugs types"+"\n"
								+	"2.Add new drugs "+"\n"
					        	+	"3.Deletes drugs"+"\n"
					        	+	"4.Go back"+ "\n");
						
						int manageOfDrugs= Utils.readInt();
						
						switch(manageOfDrugs) {
						
						case 1:
							System.out.print("\n"+"Introduce the name of the new drug type"+"\n");
							String drugType = Utils.readLine();
							
							DrugType addDrugType = new DrugType (drugType);
							
		
							break;
							
						case 2:
							System.out.print("\n"+"Introduce the name of the new drug that youre going to introduce in the zoo"+"\n");
							String drugName = Utils.readLine();
							System.out.print("\n"+"Introduce the dosis of drug that the animal is going to take"+"\n");
							String drugType0 = Utils.readLine();
							Float dosis= Float.parseFloat(drugType0);
							
							System.out.print("\n"+"Introduce the treatment duration"+"\n");
							String stringDuration = Utils.readLine();
							int duration= Integer.parseInt(stringDuration);
							
							System.out.print("\n"+"Introduce the period of days between the dosis"+"\n");
							String stringDays = Utils.readLine();
							int days= Integer.parseInt(stringDays);
							
							System.out.print("\n"+"Introduce the type of drug that the new drug is"+"\n");
							String drugType1 = Utils.readLine();
							
							List <String> differentDrugTypes = KeyboardInput.adminMan.getDrugTypes();
							boolean realDrug= false;
							
							for(int i = 0; i<differentDrugTypes.size(); i++){
								 if(drugType1.equals(differentDrugTypes.get(i)) ) {
									 realDrug = true;
									 break;
								 	} 
								 }
							
							if(realDrug==true) {
								int idDrug= KeyboardInput.adminMan.getIdsFromDrugs(drugType1);
								Drug createADrug = new Drug(drugName, duration, days, idDrug, dosis);
								KeyboardInput.addDrug(createADrug); //static
								}
							
							else {
								System.out.print("\n"+"Thats not a valid type of drug"+"\n");
							}
							
							break;
							
						case 3:
							System.out.print("\n"+"Introduce the name of the drug you want to delete"+"\n");
							String drugToDelete = Utils.readLine();
							
							List <String> differentDrugTypes1 = KeyboardInput.adminMan.getDrugTypes();
							boolean realDrug1= false;
							
							for(int i = 0; i<differentDrugTypes1.size(); i++){
								 if(drugToDelete.equals(differentDrugTypes1.get(i)) ) {
									 realDrug1 = true;
									 break;
								 	} 
								 }
							
							if(realDrug1==true) {
								Drug drug2= (Drug) KeyboardInput.adminMan.searchDrugByName(drugToDelete);
								 KeyboardInput.deleteDrug(drug2);//static
								
							}
							
							break;
							
						case 4: break;
							
						default: 
							System.out.print("\nThat is not an  valid option\n");
							break;
						}
			
	                    break;//case 3 del menu principal
	 case 4:

				System.out.println("\n"+"1. MANAGE OF HABITATS"+"\n");
            	System.out.println("Select the option that fits you the best"+"\n"
						+	"1.Add a new Habitat"+"\n"
						+	"2.Add new drugs "+"\n"
			        	+	"3.Deletes drugs"+"\n"
			        	+	"4.Go back"+ "\n");
            	
            	
            	
            	
            	int manageOfHabitats= Utils.readInt();
				
				switch(manageOfHabitats) {
				
				case 1:
					System.out.print("\n"+"Introduce the name of the habitat"+"\n");
					String name = Utils.readLine();
					
					LocalDate lastCleaned = LocalDate.now(); 
					LocalDate waterTank = LocalDate.now(); 
					
					System.out.print("\n"+"Introduce the temperature "+"\n");
					Integer temperature = Utils.readInt();
					
					System.out.print("\n"+"Introduce the period of days between the dosis"+"\n");
					String lightStr = Utils.readLine();
					LightType light= LightType.valueOf(lightStr);
					
					
					Habitat habitat = new Habitat(name, lastCleaned, waterTank, temperature, light);
					
					 System.out.print("\n"+"Congratulations you added a new animal to the zoo"+"\n");
					    
					

					break;
					
				case 2:
					
					
					break;
					
				case 3:
					
					
					break;
					
				case 4: break;
					
				default: 
					System.out.print("\nThat is not an  valid option\n");
					break;
				}
	
                break;//case 3 del menu principal
				
		 
		 
		 				break;
	 case 5:
		 				//GO BACK
	                	//exit(0); //EXIT FOR THE WHILE(TRUE) METHOD
		 				break;
	                
	 default:
	                    System.out.print("\nThat is not an option\n");
	        }
			
			}//while
	
		}while(adminChoice != 4);
	}
 	
 	
	private static void zooKeeperOption3() throws IOException, SQLException{
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
					KeyboardInput.AnimalSubMenu(habitatToSearch, zooKeeperChoice);
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
