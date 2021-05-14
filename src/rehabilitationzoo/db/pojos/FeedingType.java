package rehabilitationzoo.db.pojos;

public enum FeedingType {
	
	CARNIVORE, HERVIBORE, OMNIVORE;  

	public FeedingType pase(String dato) {
		if(dato.equalsIgnoreCase("carnivore")) {
			return FeedingType.CARNIVORE;
		}else if(dato.contentEquals("hervibore")) {
			return FeedingType.HERVIBORE;
		}else {
			return FeedingType.OMNIVORE;
		}
	}
}

