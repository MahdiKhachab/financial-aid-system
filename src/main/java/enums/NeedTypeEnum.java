package enums;

public enum NeedTypeEnum {

	MONTHS("أشهر"), YEARS("سنوات"), DAYS("أيام"),NOT_SET("غير محتسب");

	private String type;

	private NeedTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
