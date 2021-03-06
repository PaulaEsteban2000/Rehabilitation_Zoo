package rehabilitationzoo.db.pojos;
import java.io.Serializable;
import java.util.List;

public class AnimalType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5867414594701189954L; //Serializable is used to have things exist outside memory (in the computer)

	
	private Integer id;
	private String type;
	private FeedingType whatDoYouEat;
	
	private List<Animal> animals; 
	
	public void addAnimalWithGivenCharacteristics(Animal animal) {
		animals.add(animal);
	}
	
	
	  /* public AnimalType (String type, FeedingType whatDoYouEat) {
		   this.setType(type);
		   this.setWhatDoYouEat(whatDoYouEat);
	   }*/

	  
	   public AnimalType (String type, FeedingType whatDoYouEat) {
		   this.type= type;
		   this.whatDoYouEat= whatDoYouEat;
	   }

	   public AnimalType (Integer id, String type, FeedingType whatDoYouEat) {
		   super();
		   this.id = id;
		   this.type= type;
		   this.whatDoYouEat= whatDoYouEat;
	   }
	   
	   public AnimalType (String type) {
		   this.setType(type);
	   }

	

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public FeedingType getWhatDoYouEat() {
		return whatDoYouEat;
	}


	public void setWhatDoYouEat(FeedingType whatDoYouEat) {
		this.whatDoYouEat = whatDoYouEat;
	}


	public List<Animal> getAnimals() {
		return animals;
	}


	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	public Integer getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "AnimalType [id=" + id + ", type=" + type + ", feedingType=" + whatDoYouEat+ "]"; 
	}
	
}
