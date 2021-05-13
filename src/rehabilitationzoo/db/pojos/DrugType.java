package rehabilitationzoo.db.pojos;

public class DrugType {
	
	//OINTMENT, ANALGESIC, ANTIBIOTIC, ANTI_INFLAMATORY, BANDAGE, ANTIBODIES_SERUM

	private Integer id; 
	private String type;
	private float dosis;
	
	public DrugType(String type) {
		super();
		this.type = type;
		//this.drug = drug;
	}
	
	public DrugType(String type, Float dosis) {
		super();
		this.type = type;
		this.dosis = dosis;
	}
	
	public DrugType(Integer id, String type, Float dosis) {
		super();
		this.id= id;
		this.type = type;
		this.dosis = dosis;
	}

	public Integer getId() {
			return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Float getDosis() {
		return dosis;
	}

	public void setDosis(Float dosis) {
		this.dosis = dosis;
	}
	
	
}
