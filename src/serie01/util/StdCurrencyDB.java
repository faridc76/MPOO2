package serie01.util;

public class StdCurrencyDB implements CurrencyDB {

	//ATTRIBUTS
	private double getExchangeRate;
	private String getIsoCode;
	private String getLand;
	private String getName;
	
	//REQUETES
	public double getExchangeRate(CurrencyId id) {
		if (id == null) {
			throw new IllegalArgumentException("i != null");
		}
		return getExchangeRate;
	}


	public String getIsoCode(CurrencyId id) {
		if (id == null) {
			throw new IllegalArgumentException("i != null");
		}
		return getIsoCode;
	}


	public String getLand(CurrencyId id) {
		if (id == null) {
			throw new IllegalArgumentException("i != null");
		}
		return getLand;
	}


	public String getName(CurrencyId id) {
		if (id == null) {
			throw new IllegalArgumentException("i != null");
		}
		return getName;
	}


	public void setExchangeRate(CurrencyId id, double rate) {
		if (id == null) {
			throw new IllegalArgumentException("i != null");
		}
		if (rate <= 0) {
			throw new IllegalArgumentException("rate > 0");
		}
		getExchangeRate = rate;
	}

}
