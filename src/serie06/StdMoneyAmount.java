package serie06;

public class StdMoneyAmount extends StdStock<CoinTypes> implements MoneyAmount {

	//ATTRIBUTS
	
	//CONSTRUCTEUR
	public StdMoneyAmount() {
		super();
	}
	
	//REQUETES
	public int getValue(CoinTypes c) {
		if (c == null) {
			throw new IllegalArgumentException("c != null");
		}
		return getNumber(c) * c.getFaceValue();
	}

	public int getValue() {
		int sum = 0;
		CoinTypes[] coinTypes = CoinTypes.values();
		for (int i = 0; i < coinTypes.length; i++) {
			sum = sum + getValue(coinTypes[i]);
		}
		return sum;
	}

	//COMMANDES
	public void addAmount(MoneyAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("amount != null");
		}
		CoinTypes[] c = CoinTypes.values();
		for (int i = 0; i < c.length; i++) {
			if (amount.getNumber(c[i]) > 0) {
				addElement(c[i], amount.getNumber(c[i]));
			}
		}
	}

	public MoneyAmount computeChange(int s) {
		if (s <= 0) {
			throw new IllegalArgumentException("s > 0");
		}
		int s2;
		MoneyAmount ma = new StdMoneyAmount();
		MoneyAmount mb = new StdMoneyAmount();
		MoneyAmount mc = new StdMoneyAmount();
		mc.addAmount(this);
		boolean trouver = false;
		CoinTypes[] c = CoinTypes.values();
		while (mc.getValue() >= s && !trouver) {
			ma.reset();
			mb.reset();
			mb.addAmount(mc);
			s2 = s;
			int i = c.length - 1;
			while (i >= 0 && s2 != 0) {
				if (c[i].getFaceValue() <= s2 && mb.getNumber(c[i]) > 0) {
					mb.removeElement(c[i]);
					ma.addElement(c[i]);
					s2 -= c[i].getFaceValue();
				} else {
					i -= 1;
				}
			}
			if (s2 == 0) {
				trouver = true;
			} else {
				boolean enlever = false;
				int j = c.length - 1;
				while (!enlever) {
					if (mc.getNumber(c[j]) > 0) {
						mc.removeElement(c[j]);
						enlever = true;
					} else {
						j -= 1;
					}
				}
			}
		}
		if (trouver) {
			return ma;
		} else {
			return null;
		}
	}

	public void removeAmount(MoneyAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("amount != null");
		}
		CoinTypes[] c = CoinTypes.values();
		for (int i = 0; i < c.length; i++) {
			if (getNumber(c[i]) < amount.getNumber(c[i])) {
				throw new IllegalArgumentException(
						"getNumber(c[i]) >= amount.getNumber(c[i])");
			} else {
				if (amount.getNumber(c[i]) > 0) {
					removeElement(c[i], amount.getNumber(c[i]));
				}
			}
		}
	}

}
