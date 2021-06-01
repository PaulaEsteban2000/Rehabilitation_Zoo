package rehabilitationzoo.db.ui;
import rehabilitationzoo.db.jdbc.JDBCManager;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import Exceptions.AdminExceptions;
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
	
	private static DBManager dbMan = new JDBCManager();			//should be private
	private static UserManager userMan = new JPAUserManager(); 	//should be private
	
	//TODO all methods here should be private if we can
	//TODO ask Rodrigo how to make diagnosis from another user once an animal is added

	
	public static void main(String[] args) throws Exception, IOException,AdminExceptions {
	
		dbMan.connect();
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
	            	dbMan.disconnect();
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
		
		Boolean bol = KeyboardInput.adminMan.checkForUsers(email);
		System.out.println(bol + " debe ser false");
		
		do {
			if(bol == true) {
				System.out.println("This email is already used. Please pick another one: ");
				email = Utils.readLine();
				
				if(KeyboardInput.adminMan.checkForUsers(email) == false) {
					bol = false;
				}
			}
		}while (bol == true);
		
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
	
	private static void login() throws Exception, AdminExceptions {
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
					
					List<Animal> animalsToBeDiagnosed = KeyboardInput.checkIfAnimalsInHabitat("Wait zone");
					
					if (animalsToBeDiagnosed == null) { //in case no more checking should be done
						break;
					} else {
						Animal animalToDiagnose = KeyboardInput.askForAnimalForDiagnosis(animalsToBeDiagnosed); 
						KeyboardInput.firstDiagnosisSubMenu(animalToDiagnose);
						break;
					}
					
				case 2:
					System.out.println("ANIMAL CHECK");
					Habitat habitatToCheck = KeyboardInput.askForHabitatToCheckItsAnimals();
					List<Animal> animalsToCheck = KeyboardInput.checkIfAnimalsInHabitat(habitatToCheck.getName());
					
					if (animalsToCheck == null) {
						break;
					} else {
						Integer cont = animalsToCheck.size();
						
						do {
							KeyboardInput.animalCheckSubMenu(habitatToCheck);
							cont --;
						} while (cont >0);
						
					break;
					}
					
				case 3:
					break;
				default:  
					System.out.println("Error, nonvalid input.");
					break;
			}
			
		}while(vetMainChoice != 3);
	}
	
 	
	private static void adminOption2() throws Exception,AdminExceptions  {
 
 		int adminChoice;
 		boolean exito=false;
 		

		do {
			
			System.out.println("Select an option: "+"\n" 
        	+	"1.Manage animals"+"\n"
        	+	"2.Manage workers"+"\n"
        	+	"3.Manage drugs"+"\n"
        	+	"4.Manage xml"+"\n"
        	+ 	"5.Create habitats in xml"+ "\n"
        	+ 	"6.Go back"+ "\n");
		
			adminChoice = Utils.readInt();
		
			//while(true) {
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
							    Date enterDate =Date.valueOf (enterLocalDate);
							    
							    LocalDate now = LocalDate.now();
							    Date today = Date.valueOf(now); //lastfed + lastbathe
							    
							    Animal anAnimal = new Animal(enterDate, today, today,null, null, name);
							    KeyboardInput.addAnimalInTheZoo(anAnimal); 
							    System.out.print("\n"+"Congratulations you added a new animal to the zoo"+"\n");
							    
							    KeyboardInput.firstDiagnosisSubMenu(anAnimal); //Paula: first diagnosis of an animal
							    
								}
								else {
									throw new AdminExceptions (AdminExceptions.AdminErrors.NOTANANIMALTYPE);
								}
							    break;
							
							    
							
						case 2://ADD ANIMALTYPE (INSERT)
					
							System.out.print("\n"+"Which animal would you like to add to the zoo?"+"\n");
							String readAnimal1 = Utils.readLine();//WE CHECK IF THE ANIMAL EXITS OR NOT IN THE ZOO
							boolean weHaveThisType = KeyboardInput.isThisAnAnimal(readAnimal1);
							if(weHaveThisType == true) {
								System.out.print("\n"+"We already have this type of animal in the zoo."+"\n");
								break;
							}
							else {
								System.out.print("\n"+ "Introduce the feeding type of the "+readAnimal1 +"\n"+"Remember they could be CARNIVORE, HERVIBORE or OMNIVORE"+"\n");
								String foodType = Utils.readLine();
								FeedingType aType = null;
								
								if ((foodType.compareTo("C")==0) || (foodType.compareTo("c")==0)) {
									aType = FeedingType.CARNIVORE;
								}
								else {
									if ((foodType.compareTo("H")==0) || (foodType.compareTo("h")==0)) {
										aType = FeedingType.HERVIBORE;
									}
									else {
										if ((foodType.compareTo("O")==0) || (foodType.compareTo("o")==0)) {
											aType = FeedingType.OMNIVORE;
										}
										else {
											throw new AdminExceptions (AdminExceptions.AdminErrors.NOTAFEEDINGTYPE);
										}
									}
								}
								
									
								AnimalType animalType= new AnimalType (readAnimal1, aType);
								//KeyboardInput.typesOfAnimalsInTheZoo(readAnimal1);
								KeyboardInput.addAnimalTypeInTheZoo(animalType);
								
								System.out.print("\n"+"New animal introduced in the zoo"+"\n");
								}
							break;
							
						case 3: 
							
							List <String>allTheAnimals = KeyboardInput.adminMan.getAnimalTypesByName();
							if (allTheAnimals ==null) {
								throw new AdminExceptions(AdminExceptions.AdminErrors.NULL);
							}
							for(int i=0; i<allTheAnimals.size(); i++) {
								System.out.print((i+1)+". "+allTheAnimals.get(i)+"\n");
							}
							System.out.print("\n");
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
					        	+	"4.Show all the workers that work in the zoo"+ "\n"
	                			+   "5.Go back"+ "\n");
						
						int manageOfWorkers= Utils.readInt();
						
						switch(manageOfWorkers) {
						case 1:
							
							System.out.print("\n"+ "Introduce the name of the new worker"+"\n");
							String workerName= Utils.readLine();
							System.out.print("\n"+ "Introduce the lastname"+"\n");
							String workerLastName= Utils.readLine();
							
							LocalDate workerLocalDate = LocalDate.now();
							Date workerDate = Date.valueOf(workerLocalDate);
							System.out.print("\n"+"The date that you added the worker is "+workerDate+"\n");
							
							
							System.out.print("\n"+ "Introduce the salary of "+workerName+ " " +workerLastName+"\n");
							String salary=  Utils.readLine();
							float workerSalary= Float.parseFloat(salary);
							
							System.out.print("\n"+ "Introduce the job of the new worker"+"\n"+"Remember, the posibilities are: ZOO_KEEPER, VETERINARY ,ADMINISTRATOR "+"\n");
							String aWork= Utils.readLine();
							WorkerType job = null; //WorkerType.valueOf(aWork);
							
							
							if (aWork.equalsIgnoreCase("Zoo Keeper")) {
								job = WorkerType.ZOO_KEEPER;
							}
							else {
								if (aWork.equalsIgnoreCase("Vet") || aWork.equalsIgnoreCase("Veterinary")) {
									job = WorkerType.VETERINARY;
								}
								else {
									if (aWork.equalsIgnoreCase("Administrator")) {
										job = WorkerType.ADMINISTRATOR;
									}
									else {
										throw new AdminExceptions (AdminExceptions.AdminErrors.NOTAWORKERTYPE);
									}
								}
							}
							
								Worker workerInfo = new Worker( workerName, workerLastName, workerDate, workerSalary, job);
								KeyboardInput.addWorker(workerInfo);
								KeyboardInput.adminMan.getWorkersInfo();
								break;
								
						
						case 2:
							System.out.print("\n"+ "This are the workers that we have in the zoo"+"\n");
							List<Worker> workersInfo = KeyboardInput.adminMan.getWorkersInfo();
							
							for(int i = 0; i<workersInfo.size(); i++){
								System.out.print((i+1)+"."+workersInfo.get(i)+"\n");
								 }
							
							System.out.print("\n"+ "Introduce the name of the worker you want to fire"+"\n");
							String workerNameToDelete= Utils.readLine();
							System.out.print("\n"+ "Introduce the lastname"+"\n");
							String workerLastNameToDelete= Utils.readLine();
							
							Worker workerToDelete=KeyboardInput.adminMan.getAWorkerFromNameAndLastname(workerNameToDelete, workerLastNameToDelete)	;						
							KeyboardInput.adminMan.deleteThisWorker(workerToDelete.getId());
							System.out.print("\n"+"The worker "+workerNameToDelete+" "+workerLastNameToDelete+" has been fired"+"\n");
							
							break;
							
						case 3: 

							System.out.print("\n"+ "Introduce the id of the worker that you want to change the salary"+"\n");
							List<Worker> workersInformation = KeyboardInput.adminMan.getWorkersInfo();
							
							for(int i = 0; i<workersInformation.size(); i++){
								System.out.print((i+1)+"."+workersInformation.get(i)+"\n");
								}
							
							int idWorker= Utils.readInt();
							System.out.println(idWorker);
							
							Worker oneWorker = KeyboardInput.adminMan.getAWorkerFromId(idWorker);
							boolean workerExist;
							
							for(int i = 0; i<workersInformation.size(); i++){
							
									 if(oneWorker.equals(workersInformation)) {
										 System.out.println(oneWorker);
										 System.out.println(workersInformation);
										 workerExist = true;
									 }
									 else {
										 workerExist=false;
									 }
								 }
							
							if(workerExist=true) {
							System.out.print("\n"+"Which salary would you like that the person selected has now?"+"\n");
							String changeSalary = Utils.readLine();
							Float aSalary = Float.parseFloat(changeSalary);
							
							KeyboardInput.adminMan.modifyWorker(idWorker, aSalary);
							System.out.println("Salary changed"+"\n");
							}
							else {
								throw new AdminExceptions(AdminExceptions.AdminErrors.NULL);
							}

							break;
							
						case 4: 
							List<Worker> showAllWorkers = new ArrayList<Worker>();
							showAllWorkers = KeyboardInput.adminMan.getWorkersInfo();
							
							for(int i=0; i< showAllWorkers.size(); i++) {
								System.out.println(showAllWorkers.get(i));
							}
							System.out.println("\n");
							
							
							break;
						
										
						case 5: 
							
							break;
							 
						default :	
							System.out.println("Error, nonvalid input.");
							break;
	                	
						}
						break;
						
	    
	  case 3:
	                	System.out.println("\n"+"MANAGEMENT OF DRUGS"+"\n");
	                	System.out.println("Select the option that fits you the best"+"\n"
								+	"1.Add new drugs types"+"\n"
								+	"2.Add new drugs "+"\n"
					        	+	"3.Deletes drugs"+"\n"
					        	+	"4.Show all the drugs kept in the zoo"+"\n"
					        	+	"5.Go back"+ "\n");
						
						int manageOfDrugs= Utils.readInt();
						
						switch(manageOfDrugs) {
						
						case 1:
							System.out.print("\n"+"Introduce the name of the new drug type"+"\n");
							String drugType = Utils.readLine();
							System.out.print("\n"+"Introduce the dosis"+"\n");
							String unadosis = Utils.readLine();
							Float dosis = Float.parseFloat(unadosis);
							
							DrugType aDrugType = new DrugType (drugType, dosis);
							KeyboardInput.addDrugType(drugType,dosis );
							System.out.println("Drug type added to the data base");
							break;
							
						case 2:
							System.out.print("\n"+"Introduce the name of the new drug that youre going to introduce in the zoo"+"\n");
							String drugName = Utils.readLine();
							
							System.out.print("\n"+"Introduce the treatment duration"+"\n");
							String stringDuration = Utils.readLine();
							int duration= Integer.parseInt(stringDuration);
							
							System.out.print("\n"+"Introduce the period of days between the dosis"+"\n");
							String stringDays = Utils.readLine();
							int days= Integer.parseInt(stringDays);
							
							System.out.print("\n"+"Introduce the id of type of drug that the new drug is"+"\n");
							System.out.println("Remember that we have this types of drugs: ");				
							List<DrugType> drugsInformation = KeyboardInput.adminMan.getDrugTypes();
							
							for(int i = 0; i<drugsInformation.size(); i++){
								System.out.print((i+1)+"."+drugsInformation.get(i)+"\n");
									 
								 }
							Integer drugType1 = Utils.readInt();
							
							System.out.print(drugType1);
							boolean realDrug=false;
							
							for(int i = 0; i<drugsInformation.size(); i++){
								
								 if(drugType1.equals(drugsInformation.get(i).getId()) ) {
									 realDrug = true;
									 break; 
								 }
							}
							if(realDrug==true) {
								Drug createADrug = new Drug(drugName, duration, days, drugType1);
								KeyboardInput.addDrug(createADrug); 
								System.out.println("\n"+"Drug created"+"\n");
								}
							
							else {
								throw new AdminExceptions(AdminExceptions.AdminErrors.NOTADRUGTYPE);
							}
							
							break; 
							
						case 3:
							
							List<Drug> differentDrugs= KeyboardInput.adminMan.getDrugs();
							
							for(int i = 0; i<differentDrugs.size(); i++){
								System.out.print((i+1)+"."+differentDrugs.get(i)+"\n");
							}
								
							System.out.print("\n"+"Introduce the name of the drug you want to delete"+"\n");
							String drugToDelete = Utils.readLine();
							
							
							for(int i =0; i< differentDrugs.size(); i++) {
							if (drugToDelete.equalsIgnoreCase(differentDrugs.get(i).getName())) {
								
								//Integer drugId = KeyboardInput.adminMan.getIdsFromDrugs(differentDrugs.get(i).getId());
								 KeyboardInput.adminMan.deleteDrug(differentDrugs.get(i).getId());
								 System.out.println("Drug deleted with success");
								 
								}
							else {
								throw new AdminExceptions(AdminExceptions.AdminErrors.NOTADRUG);
								}
							}
							
							break;
							
						case 4: 
							List<Drug> differentDrugs1= KeyboardInput.adminMan.getDrugs();
							
							if (differentDrugs1 ==null) {
								throw new AdminExceptions(AdminExceptions.AdminErrors.NULL);
							}
							for(int i = 0; i<differentDrugs1.size(); i++){
								System.out.println(differentDrugs1.get(i)+"\n");
								}
							System.out.println("\n");
							
							break;
							
						case 5: break;
							
						default: 
							System.out.print("\nThat is not an  valid option\n");
							break;
							
						}break;//case 3 del menu principal


	 case 4:       		System.out.println("Search an habitat");
	 					Habitat habitatToSearch = KeyboardInput.askForHabitat();
						KeyboardInput.generateHabitatXML(habitatToSearch);
						
						break;
	 					
		 
		 				
	 case 5:		
		 		KeyboardInput.addHabitatXML();
		 				
		 				
	 case 6: //TODO CAMBIAR
		 //GO BACK
     					//exit(0); //EXIT FOR THE WHILE(TRUE) METHOD
		 				break;
	                
	 default:
	                    System.out.print("\nThat is not an option\n");
	        }
			
			//}while
	
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
