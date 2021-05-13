package rehabilitationzoo.db.pojos;
import java.io.Serializable;
import java.util.List;

public class AnimalType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //Serializable is used to have things exist outside memory (in the computer)

	private  String type;
	private  FeedingType whatDoYouEat;
	
	private List<Animal> animals; 
	
	
	  /* public AnimalType (String type, FeedingType whatDoYouEat) {
		   this.setType(type);
		   this.setWhatDoYouEat(whatDoYouEat);
	   }*/

	  
	   public AnimalType (String type, FeedingType whatDoYouEat) {
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
	
}
