package serie06;

public enum DrinkTypes {
	COFFEE(30, "coffee"),
	CHOCOLATE(45, "chocolate"),
	ORANGE_JUICE(110, "orange juice");
	
	private int price;
	private String toString;
	
	DrinkTypes(int i, String j) {
		this.price = i;
		this.toString = j;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String toString() {
		return toString;
	}
}
