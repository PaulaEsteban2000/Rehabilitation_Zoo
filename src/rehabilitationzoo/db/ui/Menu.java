package rehabilitationzoo.db.ui;
import rehabilitationzoo.db.jdbc.AdministratorSQL;
import rehabilitationzoo.db.jdbc.JDBCManager;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import Exceptions.AdminExceptions;
import Exceptions.ExceptionMethods;
import Exceptions.VetExceptions;
import rehabilitationzoo.db.ifaces.AdministratorManager;
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
	public static AdministratorManager adminMan = new AdministratorSQL();
	
	
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
			System.out.println("3. Change my email address ");
			System.out.println("0. Exit the program ");
			int choice = Utils.readInt();
			
	        switch (choice) {
	            case 1:
	            	register();
	                break;
	                
	            case 2: 
	            	login();
	            	break;
	            	
	            case 3:
	            	changeEmail();
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
	
	private static void changeEmail() throws IOException {
		System.out.println("Please type in your email address:");
		String emailOld = Utils.readLine();
		System.out.println("Now write your password:");
		String password = Utils.readLine();
		User user = userMan.checkPassword(emailOld, password);
		
		if (user == null) {
			System.out.println("Wrong email of password");
		} else {
			System.out.println("Now write your new email: ");
			String emailNew = Utils.readLine();
			
			Boolean bol = KeyboardInput.adminMan.checkForUsers(emailNew);
			
			do {
				if(bol == true) {
					System.out.println("This email is already used. Please pick another one: ");
					emailNew = Utils.readLine();
					
					if(KeyboardInput.adminMan.checkForUsers(emailNew) == false) {
						bol = false;
					}
				}
			}while (bol == true);
			
			userMan.updateEmail(emailNew, emailOld);
			System.out.println("Your email has been changed.");
		}
	}
	
	private static void register() throws IOException, Exception {
		System.out.println("Please type in your email address:");
		String email = Utils.readLine();
		//We can ask for it twice, for checking, but Rodrigo does not like it much
		
		Boolean bol = KeyboardInput.adminMan.checkForUsers(email);
		
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
					
					try {
					
					List<Animal> animalsToBeDiagnosed = KeyboardInput.checkIfAnimalsInHabitat("Wait zone");
					
					Animal animalToDiagnose = KeyboardInput.askForAnimalForDiagnosis(animalsToBeDiagnosed); 
					KeyboardInput.firstDiagnosisSubMenu(animalToDiagnose);
					
					} catch (VetExceptions ve) {
						System.out.println(ve.toString());
					}
					break;
					
				case 2:
					try {
						System.out.println("ANIMAL CHECK");
						Habitat habitatToCheck = KeyboardInput.askForHabitatToCheckItsAnimals();
						List<Animal> animalsToCheck = KeyboardInput.checkIfAnimalsInHabitat(habitatToCheck.getName());
						
						Integer cont = animalsToCheck.size();
							do {
								KeyboardInput.animalCheckSubMenu(habitatToCheck);
								cont --;
							} while (cont >0);
						 
					} catch (VetExceptions ve) {
						System.out.println(ve.toString());
					}
					break;
					
					
				case 3:
					break;
				default:  
					System.out.println("Error, nonvalid input.");
					break;
			}
			
		}while(vetMainChoice != 3);
	}
	
 	
	private static void adminOption2() throws Exception,AdminExceptions  {
 		int adminChoice = 0;

		do {
			System.out.println("Select an option: "+"\n" 
        	+	"1.Manage animals"+"\n"
        	+	"2.Manage workers"+"\n"
        	+	"3.Manage drugs"+"\n"
        	+	"4.Manage xml"+"\n"
        	+ 	"5.Go back"+ "\n");
		
			adminChoice = Utils.readInt();
			
			switch (adminChoice) {
				 
				case 1: //MANAGE ANIMALS
					int manageOfAnimals = 0;
					
					do{
					System.out.println("\n"+"1. MANAGE OF ANIMALS"+"\n");
					System.out.println("Select the option that fits you the best"+"\n"
						+	"1.Add animals"+"\n"
					    +	"2.Add new types of animals to the zoo"+"\n"
					    +	"3.Show all the animals in the zoo"+ "\n"
					    + 	"4.Go back"+ "\n");
					
					 manageOfAnimals= Utils.readInt();
							
						switch(manageOfAnimals) {
							case 1://ADD ANIMAL (INSERT)  
								System.out.print("\n"+"Which type of animal would you like to add to the zoo?"+"\n");
								
								List <AnimalType>allAnimalTypes= KeyboardInput.adminMan.listAnimalTypes() ;
								
								for(int i=0; i<allAnimalTypes.size(); i++) {
									System.out.println(allAnimalTypes.get(i).getType());
								}
								System.out.println("\n");
								String readAnimal = Utils.readLine();
								ExceptionMethods.checkForExistingAnimal(readAnimal);
								
	
								int animalTypeId=0;
								for(int i=0; i<allAnimalTypes.size(); i++) {
									if(readAnimal.toLowerCase().equals(allAnimalTypes.get(i).getType().toLowerCase())) {
										animalTypeId = allAnimalTypes.get(i).getId();
									}
								}
	
										
								System.out.println("\n"+"Put a name to the animal");
								String animal = Utils.readLine();
								LocalDate now1 = LocalDate.now();
								Date today1 = Date.valueOf(now1); //lastfed + lastbathe
									    
								Animal anAnimal = new Animal(today1, today1, today1, today1 , null, null,animalTypeId,animal);
								KeyboardInput.adminMan.addAnimal(anAnimal);
									   
								System.out.print("\n"+"Congratulations you added a new animal to the zoo"+"\n");
								
								break;
									
							case 2: //ADD NEW ANIMAL TYPE
								try {
									FeedingType aType = null;
									
									System.out.print("\n"+"Which animal type would you like to add to the zoo?"+"\n");
									String readAnimal1 = Utils.readLine();//WE CHECK IF THE ANIMAL EXITS OR NOT IN THE ZOO
									boolean weHaveThisType = KeyboardInput.isThisAnAnimal(readAnimal1);
									if(weHaveThisType == true) {
										System.out.print("\n"+"We already have this type of animal in the zoo."+"\n");
										break;
									}
									else {
										System.out.print("\n"+ "Introduce the feeding type of the "+readAnimal1 +"\n"+"Remember they could be CARNIVORE, HERVIBORE or OMNIVORE"+"\n");
										String foodType = Utils.readLine();
											
										ExceptionMethods.checkForExistingFeedingType(foodType);
											
										if (foodType.equalsIgnoreCase("Carnivore")) {
											aType = FeedingType.CARNIVORE;
										} else if (foodType.equalsIgnoreCase("Hervibore")) {
												aType = FeedingType.HERVIBORE;
										} else if (foodType.equalsIgnoreCase("Omnivore")) {
											aType = FeedingType.OMNIVORE;
										}
									}
									
									AnimalType animalType= new AnimalType (readAnimal1, aType);
									//KeyboardInput.typesOfAnimalsInTheZoo(readAnimal1);
									adminMan.introducingAnimalsTypes(animalType);
											
									System.out.print("New animal introduced in the zoo"+"\n");
								} catch (AdminExceptions ae) {
									System.out.println(ae.toString());
								}
								
								break;
									
							case 3: //SHOW ALL ZOO ANIMALS
								try {
									List <Animal>allTheAnimals =new ArrayList<Animal>();
									allTheAnimals= KeyboardInput.adminMan.listAnimals();
								
									ExceptionMethods.checkForEmptyList(allTheAnimals);
									System.out.println(allTheAnimals.size());
									
									for(int i=0; i<allTheAnimals.size(); i++) {
										System.out.print(allTheAnimals.get(i)+"\n");
									}
								} catch (VetExceptions ae) {
									System.out.println(ae.toString());
								}
								break;
								
							case 4: //GO BACK
								break;
								
							default :	
									System.out.println("Error, nonvalid input.");
									break;
						}
						
					} while (manageOfAnimals !=4);	
					
					break;
		                
				case 2://MANAGE WORKERS
					int manageOfWorkers = 0;
					
					do {
						System.out.println("\n"+"2.MANAGEMENT OF THE WORKERS"+"\n");
			                	
			            System.out.println("Select the option that fits you the best"+"\n"
							+	"1.Hire workers"+"\n"
							+	"2.Fire workers"+"\n"
							+	"3.Modify worker's salary"+ "\n"
							+	"4.Show all the workers that work in the zoo"+ "\n"
			                +   "5.Go back"+ "\n");
								
						manageOfWorkers= Utils.readInt();
								
						switch(manageOfWorkers) {
							case 1: //HIRE WORKERS
								try {
																	
									System.out.print("\n"+ "Introduce the name of the new worker"+"\n");
									String workerName= Utils.readLine();
									System.out.print("\n"+ "Introduce the last name"+"\n");
									String workerLastName= Utils.readLine();
									
									LocalDate workerLocalDate = LocalDate.now();
									Date workerDate = Date.valueOf(workerLocalDate);
									System.out.print("\n"+"The date that you added the worker is "+workerDate+"\n");
									
									
									System.out.print("Introduce the salary of "+workerName+ " " +workerLastName+"\n");
									String salary=  Utils.readLine();
									float workerSalary= Float.parseFloat(salary);
									
									System.out.print("\n"+ "Introduce the job of the new worker"+"\n"+"Remember, the posibilities are: ZOO_KEEPER, VETERINARY ,ADMINISTRATOR "+"\n");
									String aWork= Utils.readLine();
									WorkerType job = null; //WorkerType.valueOf(aWork);
									
									if (aWork.equalsIgnoreCase("Zoo_Keeper")|| aWork.equalsIgnoreCase("Zoo Keeper")|| aWork.equalsIgnoreCase("Zoo")) {
										job = WorkerType.ZOO_KEEPER;
										System.out.println("You added a new zoo keeper :)");
									} else if (aWork.equalsIgnoreCase("Vet") || aWork.equalsIgnoreCase("Veterinary")) {
											job = WorkerType.VETERINARY;
											System.out.println("You added a new veterinary :)");
									} else if (aWork.equalsIgnoreCase("Admin") || aWork.equalsIgnoreCase("Administrator")) {
												job = WorkerType.ADMINISTRATOR;
												System.out.println("You added a new administrator :)");
									} else {
									
									ExceptionMethods.checkForExistingWorkerType(aWork);
									}
									
										System.out.println("\n");
										Worker workerInfo = new Worker( workerName, workerLastName, workerDate, workerSalary, job);
										adminMan.introducingWorkers(workerInfo);
										adminMan.getWorkersInfo();
										
								} catch (AdminExceptions ae){
									System.out.println(ae.toString());
								}
									
								break;
										
										
							case 2: //FIRE WORKERS
								
								try {
									
									System.out.print("\n"+ "This are the workers that we have in the zoo"+"\n");
									List<Worker> workersInfo = adminMan.getWorkersInfo();
										
									for(int i = 0; i<workersInfo.size(); i++){
										System.out.print((i+1)+". "+workersInfo.get(i)+"\n");
									}
										
									System.out.print("\n"+ "Introduce the name of the worker you want to fire"+"\n");
									String workerNameToDelete= Utils.readLine();
									System.out.print("\n"+ "Introduce the lastname"+"\n");
									String workerLastNameToDelete= Utils.readLine();
									
									Worker oneWorker = new Worker (workerNameToDelete, workerLastNameToDelete, null, null, null);
									ExceptionMethods.checkForExistingWorker(workersInfo, oneWorker);
										
									Worker workerToDelete = KeyboardInput.adminMan.getAWorkerFromNameAndLastname(workerNameToDelete, workerLastNameToDelete);						
									KeyboardInput.adminMan.deleteThisWorker(workerToDelete.getId());
	
									System.out.print("\n"+"The worker "+workerNameToDelete+" "+workerLastNameToDelete+" has been fired"+"\n");
									
								} catch(AdminExceptions ae) {
									System.out.println(ae.toString());
								}
								
								break;
									
							case 3: //CHANGE SALARY
								try {
										
									System.out.print("\n"+ "Introduce the id of the worker that you want to change the salary"+"\n");
									List<Worker> workersInformation = KeyboardInput.adminMan.getWorkersInfo();
										
									for(int i = 0; i<workersInformation.size(); i++){
										System.out.print((i+1)+"."+workersInformation.get(i)+"\n");
									}
										
									int idWorker= Utils.readInt();
									System.out.println(idWorker);
										
									Worker oneWorker = KeyboardInput.adminMan.getAWorkerFromId(idWorker);
			
									ExceptionMethods.checkForExistingWorker(workersInformation, oneWorker);
									
									System.out.print("\n"+"Which salary would you like that the person selected has now?"+"\n");
									String changeSalary = Utils.readLine();
									Float aSalary = Float.parseFloat(changeSalary);
										
									KeyboardInput.adminMan.modifyWorker(idWorker, aSalary);
									System.out.println("Salary changed"+"\n");
								
								} catch (AdminExceptions ae) {
									System.out.println(ae.toString());
								}
								
								break;
									
							case 4: //SHOW ALL WORKERS IN ZOO
								List<Worker> showAllWorkers = new ArrayList<Worker>();
								showAllWorkers = KeyboardInput.adminMan.getWorkersInfo();
									
								for(int i=0; i< showAllWorkers.size(); i++) {
									System.out.println(showAllWorkers.get(i));
								}
								System.out.println("\n");
								
								break;
								
							case 5: //GO BACK							
								break;
									 
							default :	
								System.out.print("\nThat is not an  valid option\n");
								break;
			                	
						}
					} while(manageOfWorkers!=5);
				
					break;
	    
				case 3: //MANAGEMENT OF DRUGS
					int manageOfDrugs = 0;
					
					do {
						System.out.println("\n"+"MANAGEMENT OF DRUGS"+"\n");
			            System.out.println("Select the option that fits you the best"+"\n"
							+	"1.Add new drugs types"+"\n"
							+	"2.Add new drugs "+"\n"
							+	"3.Deletes drugs"+"\n"
							+	"4.Show all the drugs kept in the zoo"+"\n"
							+	"5.Go back"+ "\n");
								
						 manageOfDrugs= Utils.readInt();
								
						switch(manageOfDrugs) {
							case 1: //ADD NEW DRUG TYPE
								System.out.print("\n"+"Introduce the name of the new drug type"+"\n");
								String drugType = Utils.readLine();
								System.out.print("\n"+"Introduce the dosis"+"\n");
								String unadosis = Utils.readLine();
								Float dosis = Float.parseFloat(unadosis);
									
								DrugType aDrugType = new DrugType (drugType, dosis);
								KeyboardInput.addDrugType(drugType,dosis );
								System.out.println("Drug type added to the data base");
								break;
									
							case 2: //ADD NEW DRUG
								
									try {
									System.out.print("\n"+"Introduce the name of the new drug that youre going to introduce in the zoo"+"\n");
									String drugName = Utils.readLine();
										
									System.out.print("\n"+"Introduce the treatment duration"+"\n");
									int duration = Utils.readInt();
										
									System.out.print("\n"+"Introduce the period of days between the dosis"+"\n");
									int days = Utils.readInt();
										
									System.out.print("\n"+"Introduce the id of type of drug that the new drug is"+"\n");
									System.out.println("Remember that we have these types of drugs: ");				
									List<DrugType> drugsInformation = KeyboardInput.adminMan.getDrugTypes();
										
									for(int i = 0; i<drugsInformation.size(); i++){
										System.out.print((i+1)+"."+drugsInformation.get(i)+"\n");
									}
										
									Integer drugType1 = Utils.readInt();
									
									ExceptionMethods.checkForDrugType(drugType1, drugsInformation);
									
									Drug createADrug = new Drug(drugName, duration, days, drugType1);
									KeyboardInput.addDrug(createADrug); 
									System.out.println("\n"+"Drug created"+"\n");
								
								} catch(AdminExceptions ae) {
									System.out.println(ae.toString());
								}
								
								break; 
									
							case 3: //DELETE DRUG
								try {
									List<Drug> differentDrugs= KeyboardInput.adminMan.getDrugs();
										
									for(int i = 0; i<differentDrugs.size(); i++){
										System.out.print((i+1)+"."+differentDrugs.get(i)+"\n");
									}
											
									System.out.print("\n"+"Introduce the id of the drug you want to delete"+"\n");
									int drugToDeleteId = Utils.readInt();
										
									ExceptionMethods.checkForDrug(drugToDeleteId, differentDrugs);
										
									adminMan.deleteDrug(drugToDeleteId);
									System.out.println("Drug deleted with success");
									
								} catch(AdminExceptions ae) {
									System.out.println(ae.toString());
								}
		
								break;
									
							case 4: //SHOW ALL DRUGS
								try {
								List<Drug> differentDrugs1= KeyboardInput.adminMan.getDrugs();
								
								ExceptionMethods.checkForEmptyList(differentDrugs1);
								
								for(int i = 0; i<differentDrugs1.size(); i++){
									System.out.println(differentDrugs1.get(i)+"\n");
								}
								System.out.println("\n");
								} catch (VetExceptions ve) {
									System.out.println(ve.toString());
								}
								
								break;
									
							case 5: //GO BACK
								break;
									
							default: 
								System.out.print("\nThat is not an  valid option\n");
								break;
									
						}
						
					}while(manageOfDrugs!=5);
					break;
					
				case 4: //MANAGE XML
					int manageOfXML= 0;
					
					do {
						System.out.println("\n"+"MANAGEMENT OF XML"+"\n");
						System.out.println("Select the option that fits you best"+"\n"
		 						+	"1.Generate Habitat xml"+"\n"
		 						+	"2.Generate Ground type xml "+"\n"
		 						+	"3.Add Habitat to the data base with an XML"+"\n"
		 						+	"4.Add Ground type to the data base with an XML"+"\n"
		 						+	"5.Go back"+ "\n");
		 				
		 				manageOfXML= Utils.readInt();
						
						switch(manageOfXML) {
						
							case 1: 
				 				System.out.println("Search an habitat");
								Habitat habitatToSearch1 = KeyboardInput.askForHabitat();
								KeyboardInput.generateHabitatXML(habitatToSearch1);
								break;
							
							case 2:
				 				System.out.println("Search an habitat");
								Habitat habitatToSearch2 = KeyboardInput.askForHabitat();
								KeyboardInput.generateGroundTypeXML(habitatToSearch2);
								break;
								
							case 3:
								KeyboardInput.addHabitatXML();
								break;
								
							case 4:
								KeyboardInput.addGroundTypeXML();
								break;
								
							case 5:
								break;
								
							default:
								System.out.print("\nThat is not an  valid option\n");
								break;
						}
						
					} while(manageOfXML!=5);
					
					break;
					
				case 5: //GO BACK
			 		break;
		                
				default:
		            System.out.print("\nThat is not an option\n");
		            
		        }
	
		} while(adminChoice != 5);
	}
 	
 	
	private static void zooKeeperOption3() throws IOException, SQLException{
		int zooKeeperChoice;
		Habitat habitatToSearch = null;
		String choice = "";
		
		do {			
			System.out.println("Please choose the habitat you are in. " + "\n");
			habitatToSearch = KeyboardInput.askForHabitat();
			
			do {
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
				
			} while(zooKeeperChoice != 6); 
			
			System.out.println("Would you like to eneter a new habitat? Or exit?");
			System.out.println("a. Enter a habitat");
			System.out.println("b. Exit");			
			choice = Utils.readLine();
		
		} while (!choice.equals("b"));
	}
	


}
