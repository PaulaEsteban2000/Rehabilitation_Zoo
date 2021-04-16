package rehabilitationzoo.db.pojos;

import java.io.Serializable;
import java.util.List;

public class FoodType{


public enum FeedingType {
	
	CARNIVORE, HERVIBORE, OMNIVORE  };


private FeedingType name;


public FoodType (FeedingType name) {
		this.name = name;
	}
	
	
public FeedingType geFoodType() {
	return name;
}
public void setName(FeedingType name) {
	this.name = name;
}

}