package serie08;

import java.util.Observable;

import serie06.CoinTypes;
import serie06.DrinkTypes;
import serie06.MoneyAmount;
import serie06.StdMoneyAmount;
import serie06.StdStock;

public class StdDrinksMachineModel extends Observable implements
		DrinksMachineModel {

	//ATTRIBUTS
	private StdMoneyAmount cashBox;
    private StdMoneyAmount credit;
    private StdMoneyAmount changeBox;
    private StdStock<DrinkTypes> drinksStock;
    private DrinkTypes lastDrink;
    private static final int LIMIT = 199;
    
    //CONSTRUCTEURS
    public StdDrinksMachineModel() {
    	drinksStock = new StdStock<DrinkTypes>(); 
		DrinkTypes[] tabDrink = DrinkTypes.values();
		for (int i = 0; i < tabDrink.length; i++) {		
			drinksStock.addElement(tabDrink[i], MAX_DRINK);
		}
		cashBox = new StdMoneyAmount();
		credit = new StdMoneyAmount();
		changeBox = new StdMoneyAmount();

    }

    //REQUETES
	public int getDrinkNb(DrinkTypes d) {
		if (d == null) {
			throw new IllegalArgumentException();
		}
		return drinksStock.getNumber(d);
	}

	public DrinkTypes getLastDrink() {
		return lastDrink;
	}

	public int getCreditAmount() {
		return credit.getValue();
	}

	public int getCreditNb(CoinTypes c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		return credit.getNumber(c);
	}

	public int getCashAmount() {
		return cashBox.getValue();
	}

	public int getCashNb(CoinTypes c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		return cashBox.getNumber(c);
	}

	public int getChangeAmount() {
		return changeBox.getValue();
	}

	public int getChangeNb(CoinTypes c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		return changeBox.getNumber(c);
	}

	public boolean canGetChange() {
		for (int i = 1; i <= LIMIT; i++) {
			if (cashBox.computeChange(i) == null) {
				return false;
			}	
		}
		return true;
	}

	//COMMANDES
	public void selectDrink(DrinkTypes d) {
		if (d == null) {
			throw new IllegalArgumentException();
		}
		if (getDrinkNb(d) < 1) {
			throw new IllegalArgumentException();
		}
		if (getCreditAmount() < d.getPrice()) {
			throw new IllegalArgumentException();
		}
		if (getLastDrink() != null) {
			throw new IllegalStateException();
		}
		cashBox.addAmount(credit);
		drinksStock.removeElement(d);
		int val = credit.getValue();
		if ((val > d.getPrice()) && canGetChange()) {
			MoneyAmount ma = cashBox.computeChange(val - d.getPrice());
			changeBox.addAmount(ma);
			cashBox.removeAmount(ma);
		}
		credit.reset();
		lastDrink = d;
		setChanged();
	}

	public void fillStock(DrinkTypes d, int q) {
		if (d == null) {
			throw new IllegalArgumentException();
		}
		if ((q <= 0) || (getDrinkNb(d) + q > MAX_DRINK)) {
			throw new IllegalArgumentException();
		}
		drinksStock.addElement(d, q);
		setChanged();
	}

	public void fillCash(CoinTypes c, int q) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		if ((q <= 0) || (getCashNb(c) + q > MAX_COIN)) {
			throw new IllegalArgumentException();
		}
		cashBox.addElement(c, q);
		setChanged();
	}

	public void insertCoin(CoinTypes c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		if (getCashNb(c) == MAX_COIN) {
			changeBox.addElement(c);
		}
		if (getCashNb(c) < MAX_COIN) {
			credit.addElement(c);
		}
		setChanged();
	}

	public void cancelCredit() {
		changeBox.addAmount(credit);
		credit.reset();
		setChanged();
	}

	public void takeDrink() {
		lastDrink = null;
		setChanged();
	}

	public void takeChange() {
		changeBox.reset();
		setChanged();
	}

	public void reset() {
		credit.reset();
		cashBox.reset();
		changeBox.reset();
		drinksStock.reset();
		lastDrink = null;
		setChanged();
	}

}
