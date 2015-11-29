package serie06;

public class StdDrinksMachineModel implements DrinksMachineModel {

	//ATTRIBUTS
	private static final int MAX_DRINK = 50;
	private static final int MAX_COIN = 100;
	private static final int MAX_CHANGE = 199;
	private Stock<DrinkTypes> drinkStock;
	private MoneyAmount cashbox;
	private MoneyAmount change;
	private MoneyAmount credit;
	private DrinkTypes lastDrink;
	
	//CONSTRUCTEUR
	public StdDrinksMachineModel() {
		drinkStock = new StdStock<DrinkTypes>();
		DrinkTypes[] d = DrinkTypes.values();
		for (int j = 0; j < d.length; j++) {
			drinkStock.addElement(d[j], MAX_DRINK);
		}
		cashbox = new StdMoneyAmount();
		change = new StdMoneyAmount();
		credit = new StdMoneyAmount();
	}
	
	//REQUETES
	public int getDrinkNb(DrinkTypes d) {
		if (d == null) {
			throw new IllegalArgumentException("d != null");
		}
		return drinkStock.getNumber(d);
	}

	public DrinkTypes getLastDrink() {
		return lastDrink;
	}

	public int getCreditAmount() {
		return credit.getValue();
	}

	public int getCreditNb(CoinTypes c) {
		if (c == null) {
			throw new IllegalArgumentException("c != null");
		}
		return credit.getNumber(c);
	}

	public int getCashAmount() {
		return cashbox.getValue();
	}

	public int getCashNb(CoinTypes c) {
		if (c == null) {
			throw new IllegalArgumentException("c != null");
		}
		return cashbox.getNumber(c);
	}

	public int getChangeAmount() {
		return change.getValue();
	}

	public int getChangeNb(CoinTypes c) {
		if (c == null) {
			throw new IllegalArgumentException("c != null");
		}
		return change.getNumber(c);
	}

	public boolean canGetChange() {
		for (int i = 1; i <= MAX_CHANGE; i++) {
			if (cashbox.computeChange(i) == null) {
				return false;
			}
		}
		return true;
	}

	//COMMANDES
	public void selectDrink(DrinkTypes d) {
		if (d == null) {
			throw new IllegalArgumentException("d != null");
		}
		if (getDrinkNb(d) < 1) {
			throw new IllegalArgumentException("getDrinkNb(d) >= 1");
		}
		if (getCreditAmount() < d.getPrice()) {
			throw new IllegalArgumentException(
					"getCreditAmount() >= d.getPrice()");
		}
		if (getLastDrink() != null) {
			throw new IllegalStateException("getLastDrink() == null");
		}
		boolean changeIsOk;
		int value;
		MoneyAmount ma;
		cashbox.addAmount(credit);
		drinkStock.removeElement(d);
		changeIsOk = canGetChange();
		value = getCreditAmount();
		if (value > d.getPrice() && changeIsOk) {
			ma = cashbox.computeChange(value - d.getPrice());
			change.addAmount(ma);
			cashbox.removeAmount(ma);
		}
		lastDrink = d;
		credit.reset();
	}

	public void fillStock(DrinkTypes d, int q) {
		if (d == null) {
			throw new IllegalArgumentException("d != null");
		}
		if ((q <= 0) || (getDrinkNb(d) + q > MAX_DRINK)) {
			throw new IllegalArgumentException(
					"q > 0 && getDrinkNb(d) + q <= MAX_DRINK");
		}
		drinkStock.addElement(d, q);
	}

	public void fillCash(CoinTypes c, int q) {
		if (c == null) {
			throw new IllegalArgumentException("c != null");
		}
		if ((q <= 0) || (getCashNb(c) + q > MAX_COIN)) {
			throw new IllegalArgumentException(
					"q > 0 && getCashNb(c) + q <= MAX_COIN");
		}
		cashbox.addElement(c, q);
	}

	public void insertCoin(CoinTypes c) {
		if (c == null) {
			throw new IllegalArgumentException("c != null");
		}
		if (getCashNb(c) == MAX_COIN) {
			change.addElement(c);
		} else {
			if (getCashNb(c) < MAX_COIN) {
				credit.addElement(c);
			}
		}
	}

	public void cancelCredit() {
		change.addAmount(credit);
		credit.reset();
	}

	public void takeDrink() {
		lastDrink = null;
	}

	public void takeChange() {
		change.reset();
	}

	public void reset() {
		change.reset();
		credit.reset();
		cashbox.reset();
		drinkStock.reset();
		lastDrink = null;
	}

}
