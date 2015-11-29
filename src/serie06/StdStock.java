package serie06;

import java.util.HashMap;
import java.util.Map;

public class StdStock<E> implements Stock<E> {

	//ATTRIBUTS
	private int totalNumber;
	private Map<E, Integer> stockage;
	
	//CONSTRUCTEUR
	public StdStock() {
		stockage = new HashMap<E, Integer>();
		totalNumber = 0;
	}
	
	//REQUETES
	
	public int getNumber(E e) {
		if (e == null) {
			throw new IllegalArgumentException("e != null");
		}
		if (stockage.containsKey(e)) {
			return stockage.get(e);
		} else {
			return 0;
		}
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	
	//COMMANDE
	
	public void addElement(E e) {
		if (e == null) {
			throw new IllegalArgumentException("e != null");
		}
		if (stockage.containsKey(e)) {
			stockage.put(e, stockage.get(e) + 1);
		} else {
			stockage.put(e, 1);
		}
		totalNumber = totalNumber + 1;
	}

	public void addElement(E e, int qty) {
		if (e == null) {
			throw new IllegalArgumentException("e != null");
		}
		if (qty <= 0) {
			throw new IllegalArgumentException("qty > 0");
		}
		if (stockage.containsKey(e)) {
			stockage.put(e, stockage.get(e) + qty);
		} else {
			stockage.put(e, qty);
		}
		totalNumber = totalNumber + qty;
	}

	public void removeElement(E e) {
		if (e == null) {
			throw new IllegalArgumentException("e != null");
		}
		if (getNumber(e) < 1) {
			throw new IllegalArgumentException("stockage.get(e) > 0");
		}
		stockage.put(e, stockage.get(e) - 1);
		totalNumber = totalNumber - 1;
	}

	public void removeElement(E e, int qty) {
		if (e == null) {
			throw new IllegalArgumentException("e != null");
		}
		if (qty <= 0) {
			throw new IllegalArgumentException("qty > 0");
		}
		if (getNumber(e) < qty) {
			throw new IllegalArgumentException("stockage.get(e) > 0");
		}
		stockage.put(e, stockage.get(e) - qty);
		totalNumber = totalNumber - qty;
	}

	public void reset() {
		stockage.clear();
		totalNumber = 0;
	}

}
