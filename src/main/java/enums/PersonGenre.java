package enums;

public enum PersonGenre {
	MR("سادة"), GN("عامة");

	private String type;

	private PersonGenre(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
