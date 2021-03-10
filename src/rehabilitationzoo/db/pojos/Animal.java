package rehabilitationzoo.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Animal implements Serializable { //Serializable is used to have things exist outside memory (in the computer)

	/**
	 * 
	 */
	private static final long serialVersionUID = 723080679524606900L; //"signature" for this class, all objects in the class will have the same one
																		// automatically turning "Animal" into a file (when calling a specific method)
	
	private Integer id; //No need to be in constructor
	private Date enterDate;
	private Integer foodPeriod;
	private Date lastFed;
	private Date deathDate;
	private Date freedomDate;
	private Date lastBath;
	private FoodType feedingType;
	private List<Worker> workers; //as there's its brother List on Worker class, this conforms a many-to-many relationship
	private List<Illness> illnesses; 
	private List<Drug> drugs; 
	private List<Habitat> habitats; 
	
	
	public Animal(Date enterDate, Integer foodPeriod, Date lastFed, Date deathDate, Date freedomDate, Date lastBath,
			FoodType feedingType) {
		super();
		this.enterDate = enterDate;
		this.foodPeriod = foodPeriod;
		this.lastFed = lastFed;
		this.deathDate = deathDate;
		this.freedomDate = freedomDate;
		this.lastBath = lastBath;
		this.feedingType = feedingType;
	}
	
	
	public Animal(Integer id, Date enterDate, Integer foodPeriod, Date lastFed, Date deathDate, Date freedomDate,
			Date lastBath, FoodType feedingType, List<Worker> workers, List<Illness> illnesses, List<Drug> drugs, List<Habitat> habitats) {
		super();
		this.id = id;
		this.enterDate = enterDate;
		this.foodPeriod = foodPeriod;
		this.lastFed = lastFed;
		this.deathDate = deathDate;
		this.freedomDate = freedomDate;
		this.lastBath = lastBath;
		this.feedingType = feedingType;
		this.workers = new ArrayList<Worker>();
		this.illnesses = new ArrayList<Illness>();
		this.drugs = new ArrayList<Drug>();
		this.habitats = new ArrayList<Habitat>();
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


	@Override
	public String toString() {
		return "Animal [id=" + id + ", enterDate=" + enterDate + ", foodPeriod=" + foodPeriod + ", lastFed=" + lastFed
				+ ", deathDate=" + deathDate + ", freedomDate=" + freedomDate + ", lastBath=" + lastBath
				+ ", feedingType=" + feedingType + "]"; //No workers print so as to avoid stackOverflow Error (loop)
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
	
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getEnterDate() {
		return enterDate;
	}
	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}
	public Integer getFoodPeriod() {
		return foodPeriod;
	}
	public void setFoodPeriod(Integer foodPeriod) {
		this.foodPeriod = foodPeriod;
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
	public Date getLastBath() {
		return lastBath;
	}
	public void setLastBath(Date lastBath) {
		this.lastBath = lastBath;
	}
	public FoodType getFeedingType() {
		return feedingType;
	}
	public void setFeedingType(FoodType feedingType) {
		this.feedingType = feedingType;
	}


	public List<Drug> getDrugs() {
		return drugs;
	}


	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}


	public List<Habitat> getHabitats() {
		return habitats;
	}


	public void setHabitats(List<Habitat> habitats) {
		this.habitats = habitats;
	}
	
	
}
