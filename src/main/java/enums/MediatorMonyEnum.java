package enums;

public enum MediatorMonyEnum {
	MONEY("مال عيني"), WARE("سلع / بضاعة");

	private String type;

	private MediatorMonyEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
