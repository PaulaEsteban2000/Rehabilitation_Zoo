package rehabilitationzoo.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Animal implements Serializable { //Serializable is used to have things exist outside memory (in the computer)

	/**
	 * 
	 */
	private static final long serialVersionUID = 723080679524606900L; //"signature" for this class, all objects in the class will have the same one
																		// automatically turning "Animal" into a file (when calling a specific method)
	
	public  static  Integer id; //No need to be in constructor
	public static  String name;
	public  static LocalDate enterDate;
	private Integer habitat_id; //fk to habitat the animal lives in
	private Integer foodPeriod;
	private FeedingType feedingType;
	private LocalDate lastBath;	
	private LocalDate lastFed;
	private LocalDate deathDate;
	private LocalDate freedomDate;
	public static String type; //elephant, giraffe...
    //for easy access when still do not have a "barcode reader"
	
	
	//public ArrayList<String> typesOfAnimalsInTheZoo;
	//	ELEPHANT, LION, TIGER, RHINO, HIPO, GIRAFFE, MONKEY, DOLPHIN, WHALE, DEER, REINDEER
	
		
	private List<Worker> workers; //as there's its brother List on Worker class, this conforms a many-to-many relationship
	private List<Illness> illnesses; 
	private List<Drug> drugs; 

	
	
	
	public Animal(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	 
    
    
    public Animal(String type, String name, LocalDate enterDate) {
    	Animal.type=type;
    	this.name= name;
		this.enterDate = enterDate;
    }
	
/*	public Animal(typesOfAnimalsInTheZoo animalType,String name, LocalDate enterDate, Integer habitat_id, 
			Integer foodPeriod, FeedingType feedingType,  Date lastBath, Date lastFed, Date deathDate, 
			Date freedomDate) {
		
		this.animalType= animalType;
		this.name= name;
		this.enterDate = enterDate;
		this.habitat_id = habitat_id;
		this.foodPeriod = foodPeriod;
		this.feedingType = feedingType;
		this.lastBath = lastBath;
		this.lastFed = lastFed;
		this.deathDate = deathDate;
		this.freedomDate = freedomDate;
		
	}
	
	
	public void Animal(typesOfAnimalsInTheZoo animalType,String name, LocalDate enterDate) {
		
		this.animalType= animalType;
		this.name= name;
		this.enterDate = enterDate;
		
	}*/
	
	
	
	
	public Animal(LocalDate enterDate, Integer habitat_id, Integer foodPeriod, FeedingType feedingType,  LocalDate lastBath, LocalDate lastFed, LocalDate deathDate, LocalDate freedomDate,
			String type, String name, List<Worker> workers,  List<Illness> illnesses,  List<Drug> drugs) {
		super();
		this.enterDate = enterDate;
		this.habitat_id = habitat_id;
		this.foodPeriod = foodPeriod;
		this.feedingType = feedingType;
		this.lastBath = lastBath;
		this.lastFed = lastFed;
		this.deathDate = deathDate;
		this.freedomDate = freedomDate;
		//this.setType(type);
		this.setName(name);
		
		this.workers = new ArrayList<Worker>();
		this.illnesses = new ArrayList<Illness>();
		this.drugs = new ArrayList<Drug>();

	}




	@Override //To compare ids
	public int hashCode() { //if two objects have the same id, they'll have the same hashCode
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode()); //compact if
		return result;
	}


	@Override //To compare objects (in memory) 
	public boolean equals(Object obj) { //cannot use ==, not the same thing
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) //checking if objects are from same class
			return false;
		Animal other = (Animal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) //compares ids
			return false;
		return true;
	}

	

	public Integer getId() {
		return id;
	}
	
	
	public Integer getHabitat() {
		return habitat_id;
	}


	public void setHabitat(Integer habitat_id) {
		this.habitat_id = habitat_id;
	}


	public LocalDate getEnterDate() {
		return enterDate;
	}


	public void setEnterDate(LocalDate enterDate) {
		this.enterDate = enterDate;
	}


	public Integer getFoodPeriod() {
		return foodPeriod;
	}


	public void setFoodPeriod(Integer foodPeriod) {
		this.foodPeriod = foodPeriod;
	}


	public FeedingType getFeedingType() {
		return feedingType;
	}


	public void setFeedingType(FeedingType feedingType) {
		this.feedingType = feedingType;
	}


	public LocalDate getLastBath() {
		return lastBath;
	}


	public void setLastBath(LocalDate lastBath) {
		this.lastBath = lastBath;
	}


	public LocalDate getLastFed() {
		return lastFed;
	}


	public void setLastFed(LocalDate lastFed) {
		this.lastFed = lastFed;
	}


	public LocalDate getDeathDate() {
		return deathDate;
	}


	public void setDeathDate(LocalDate deathDate) {
		this.deathDate = deathDate;
	}


	public LocalDate getFreedomDate() {
		return freedomDate;
	}


	public void setFreedomDate(LocalDate freedomDate) {
		this.freedomDate = freedomDate;
	}


	public List<Worker> getWorkers() {
		return workers;
	}


	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}


	public List<Illness> getIllnesses() {
		return illnesses;
	}


	public void setIllnesses(List<Illness> illnesses) {
		this.illnesses = illnesses;
	}


	public List<Drug> getDrugs() {
		return drugs;
	}


	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}



	@Override
	public String toString() {
		return "Animal [id=" + id + ", enterDate=" + enterDate + ", name="+ name +", type="+type+",foodPeriod=" + foodPeriod + ", lastFed=" + lastFed
				+ ", deathDate=" + deathDate + ", freedomDate=" + freedomDate + ", lastBath=" + lastBath
				+ ", feedingType=" + feedingType + "]"; //No workers print so as to avoid stackOverflow Error (loop)
		}


	/*public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}*/


	public  String getName() {
		return name;
	}


	public void setName(String name) {
		Animal.name = name;
	}



	
}
