package rehabilitationzoo.db.pojos;
import java.io.Serializable;
import java.util.List;

public class AnimalType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //Serializable is used to have things exist outside memory (in the computer)

	private static String type;
	private static FeedingType whatDoYouEat;
	
	private List<Animal> animals; 
	
	
	   public AnimalType (String type, FeedingType whatDoYouEat) {
		   this.setType(type);
		   this.setWhatDoYouEat(whatDoYouEat);
	   }


	public static String getType() {
		return type;
	}


	public void setType(String type) {
		AnimalType.type = type;
	}


	public static FeedingType getWhatDoYouEat() {
		return whatDoYouEat;
	}


	public void setWhatDoYouEat(FeedingType whatDoYouEat) {
		AnimalType.whatDoYouEat = whatDoYouEat;
	}


	public List<Animal> getAnimals() {
		return animals;
	}


	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}
	
}
