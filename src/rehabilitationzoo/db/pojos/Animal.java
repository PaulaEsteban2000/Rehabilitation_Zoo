package rehabilitationzoo.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Animal implements Serializable { //Serializable is used to have things exist outside memory (in the computer)

	/**
	 * 
	 */
	private static final long serialVersionUID = 723080679524606900L; //"signature" for this class, all objects in the class will have the same one
																		// automatically turning "Animal" into a file (when calling a specific method)
	
	public static  Integer id; //No need to be in constructor
	public  Date enterDate;
	private Integer habitat_id; //fk to habitat the animal lives in
	//public static FeedingType feedingType;
	public static Date lastBath;	
	public static Date lastFed;
	public static Date deathDate;
	public static Date freedomDate;
	//public static AnimalType type; //elephant, giraffe...
	public   String name; //for easy access when still do not have a "barcode reader"
	
	private List<Worker> workers; //as there's its brother List on Worker class, this conforms a many-to-many relationship
	private List<Illness> illnesses; 
	private List<Drug> drugs; 
    
	
    public Animal(String name, Date enterDate) {
    	this.name= name;
		this.enterDate = enterDate;
    }
    

	public Animal(Integer id, Date enterDate, Integer habitat_id,/*FeedingType feedingType,*/ Date lastBath, Date lastFed,
			Date deathDate, Date freedomDate, /*AnimalType type,*/ String name) {
		super();
		this.id = id;
		this.enterDate = enterDate;
		this.habitat_id = habitat_id;
		//this.feedingType = feedingType;
		this.lastBath = lastBath;
		this.lastFed = lastFed;
		this.deathDate = deathDate;
		this.freedomDate = freedomDate;
		//this.type = type;
		this.name = name;
	}

	
	public Animal(Date enterDate, /*FeedingType feedingType,*/ Date lastBath, Date lastFed,
			Date deathDate, Date freedomDate, /*AnimalType type,*/ String name) {
		super();
		this.enterDate = enterDate;
		//this.feedingType = feedingType;
		this.lastBath = lastBath;
		this.lastFed = lastFed;
		this.deathDate = deathDate;
		this.freedomDate = freedomDate;
		//this.type = type;
		this.name = name;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	/*public AnimalType getType() {
		return type;
	}


	public void setType(AnimalType type) {
		this.type = type;
	}*/


	public Date getEnterDate() {
		return enterDate;
	}


	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}


	public Integer getHabitat_id() {
		return habitat_id;
	}


	public void setHabitat_id(Integer habitat_id) {
		this.habitat_id = habitat_id;
	}
	

	/*public FeedingType getFeedingType() {
		return feedingType;
	}


	public void setFeedingType(FeedingType feedingType) {
		this.feedingType = feedingType;
	}*/


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
		return "Animal [name=" + name +/* ", type=" + type +*/ ", enterDate=" + enterDate + ", habitat_id=" + habitat_id
				/*+ ", feedingType=" + feedingType*/ + ", lastBath=" + lastBath
				+ ", lastFed=" + lastFed + ", deathDate=" + deathDate + ", freedomDate=" + freedomDate + ", illnesses="
				+ illnesses + ", drugs=" + drugs + "]"; //No workers print so as to avoid stackOverflow Error (loop)
	}

	
}
