package enums;

public enum CurrencyEnum {

	DD("USD"), Lira("LBP");

	private String type;

	private CurrencyEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
