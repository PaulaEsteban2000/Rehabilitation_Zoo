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
	
	public enum typesOfAnimalsInTheZoo {
		ELEPHANT, LION, TIGER, RHINO, HIPO, GIRAFFE, MONKEY, DOLPHIN, WHALE, DEER, REINDEER
	}
	
	
	private Integer id; //No need to be in constructor
	private LocalDate enterDate;
	private Integer habitat_id; //fk to habitat the animal lives in
	private Integer foodPeriod;
	private FeedingType feedingType;
	private Date lastBath;	
	private Date lastFed;
	private Date deathDate;
	private Date freedomDate;
	//private String type; //elephant, giraffe...
	private String name; //for easy access when still do not have a "barcode reader"
		
	private List<Worker> workers; //as there's its brother List on Worker class, this conforms a many-to-many relationship
	private List<Illness> illnesses; 
	private List<Drug> drugs; 
	
	//public List<Animal> storeAnimals;
	public typesOfAnimalsInTheZoo animalType;
	
	
	
	public void Animal(typesOfAnimalsInTheZoo unAnimal ) {
		
		//this.storeAnimals = null;
		this.setAnimalType(animalType);
	}
	
	
	
	/*public Animal(Integer id, typesOfAnimalsInTheZoo name) {
		super();
		this.id = id;
		this.name = name;
	}*/
		
	
	public Animal(typesOfAnimalsInTheZoo animalType,String name, LocalDate enterDate, Integer habitat_id, 
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
		
	}
	
	
	
	
	public Animal(LocalDate enterDate, Integer habitat_id, Integer foodPeriod, FeedingType feedingType,  Date lastBath, Date lastFed, Date deathDate, Date freedomDate,
			/*String type,*/ String name, List<Worker> workers,  List<Illness> illnesses,  List<Drug> drugs) {
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


	

	public Animal(typesOfAnimalsInTheZoo unAnimal, String name2, LocalDate enterDate2, Object habitat_id2,
			Object foodPeriod2, Object feedingType2, Object lastBath2, Object lastFed2, Object deathDate2,
			Object freedomDate2) {
		// TODO Auto-generated constructor stub
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


	public Date getLastBath() {
		return lastBath;
	}


	public void setLastBath(Date lastBath) {
		this.lastBath = lastBath;
	}


	public Date getLastFed() {
		return lastFed;
	}


	public void setLastFed(Date lastFed) {
		this.lastFed = lastFed;
	}


	public Date getDeathDate() {
		return deathDate;
	}


	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}


	public Date getFreedomDate() {
		return freedomDate;
	}


	public void setFreedomDate(Date freedomDate) {
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
		return "Animal [id=" + id + ", enterDate=" + enterDate + ", foodPeriod=" + foodPeriod + ", lastFed=" + lastFed
				+ ", deathDate=" + deathDate + ", freedomDate=" + freedomDate + ", lastBath=" + lastBath
				+ ", feedingType=" + feedingType + "]"; //No workers print so as to avoid stackOverflow Error (loop)
		}


	/*public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}*/


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}



	public typesOfAnimalsInTheZoo getAnimalType() {
		return animalType;
	}



	public void setAnimalType(typesOfAnimalsInTheZoo animalType) {
		this.animalType = animalType;
	}



	
}
