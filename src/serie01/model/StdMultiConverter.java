package serie01.model;

import serie01.util.Currency;
import serie01.util.CurrencyId;

public class StdMultiConverter implements MultiConverter {
    
	//ATTRIBUTS
	private int getCurrencyNb;
	private Currency[] getCurrency;
	private double[] getAmount;
	
	//CONSTRUCTEUR
	public StdMultiConverter(int n) {
		if (n < 2) {
			throw new IllegalArgumentException("n doit être >= 2");
		}
		getCurrencyNb = n;
		for (int i = 0; i < n; i++) {
			getCurrency[i] = new Currency(CurrencyId.EUR);
			getAmount[i] = 0.0;
		}		
	}

	//REQUETES
	public double getAmount(int index) {
		if ((index < 0) || (index >= getCurrencyNb)) {
			throw new IllegalArgumentException(" 0 <= index < getCurrencyNb");
		}	
		return getAmount[index];
	}


	public Currency getCurrency(int index) {
		if ((index < 0) || (index >= getCurrencyNb)) {
			throw new IllegalArgumentException(" 0 <= index < getCurrencyNb");
		}
		return getCurrency[index];
	}


	public int getCurrencyNb() {
		return getCurrencyNb;
	}


	public double getExchangeRate(int index1, int index2) {
		if ((index1 < 0) || (index1 >= getCurrencyNb)) {
			throw new IllegalArgumentException(" 0 <= index < getCurrencyNb");
		}
		if ((index2 < 0) || (index2 >= getCurrencyNb)) {
			throw new IllegalArgumentException(" 0 <= index < getCurrencyNb");
		}
		return getCurrency[index2].getExchangeRate() 
		/ getCurrency[index1].getExchangeRate();
	}


	public void setAmount(int index, double amount) {
		if ((index < 0) || (index >= getCurrencyNb)) {
			throw new IllegalArgumentException(" 0 <= index < getCurrencyNb");
		}
		if (amount < 0) {
			throw new IllegalArgumentException(" amount >= 0.0");
		}
		getAmount[index] = amount;
	}


	public void setCurrency(int index, Currency c) {
		if ((index < 0) || (index >= getCurrencyNb)) {
			throw new IllegalArgumentException(" 0 <= index < getCurrencyNb");
		}
		if (c == null) {
			throw new IllegalArgumentException("c != null");
		}
		getCurrency[index] = c;
	}

}
