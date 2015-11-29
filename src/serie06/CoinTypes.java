package serie06;

import java.util.HashMap;
import java.util.Map;

public enum CoinTypes {
	ONE(1, "1 ct"),
	TWO(2, "2 cts"),
	FIVE(5, "5 cts"),
	TEN(10, "10 cts"),
	TWENTY(20, "20 cts"),
	FIFTY(50, "50 cts"),
	ONE_HUNDRED(100, "100 cts"),
	TWO_HUNDRED(200, "200 cts");
	
	private int value;
	private String toString;
	private static final Map<Integer, CoinTypes> COIN;
	static {
		COIN = new HashMap<Integer, CoinTypes>();
		CoinTypes[] coinTypes = CoinTypes.values();
		for (int i = 0; i < coinTypes.length; i++) {
			COIN.put(coinTypes[i].getFaceValue(), coinTypes[i]);
		}
	}
	
	CoinTypes(int i, String j) {
		this.value = i;
		this.toString = j;
	}
	
	public int getFaceValue() {
		return value;
	}
	
	public String toString() {
		return toString;
	}
	
	public static CoinTypes getCoinType(int val) {
		return COIN.get(val);
	}
}
