package rehabilitationzoo.db.pojos;

public class DrugType {
	
	//OINTMENT, ANALGESIC, ANTIBIOTIC, ANTI_INFLAMATORY, BANDAGE, ANTIBODIES_SERUM

	private String type;

	public DrugType(String type) {
		super();
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
