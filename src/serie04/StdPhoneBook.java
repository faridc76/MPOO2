package serie04;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;

public class StdPhoneBook implements PhoneBook {
	

	private NavigableMap<Contact, List<String>> mapData;
	
	
	public StdPhoneBook() {
		mapData = new TreeMap<Contact, List<String>>();
	}

	public String firstPhoneNumber(Contact p) {
		if ((p == null) || !contains(p)) {
			throw new IllegalArgumentException();
		}
		return mapData.get(p).get(0);
	}

	public List<String> phoneNumbers(Contact p) {
		if ((p == null) || !contains(p)) {
			throw new IllegalArgumentException();
		}
		List<String> s = Collections.unmodifiableList(mapData.get(p));
		return s;
	}

	public NavigableSet<Contact> contacts() {
		return mapData.navigableKeySet();
	}

	public boolean contains(Contact p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		return mapData.get(p) != null;
	}

	public boolean isEmpty() {
		return mapData.size() == 0;
	}

	public void addEntry(Contact p, List<String> nums) {
		if ((p == null) || contains(p)) {
			throw new IllegalArgumentException();
		}
		if ((nums == null) || (nums.size() <= 0)) {
			throw new IllegalArgumentException();
		}
		List<String> phoneNumbers = new LinkedList<String>();
		for (int i = 0; i < nums.size(); i++) {
			if (!phoneNumbers.contains(nums.get(i))) {
				phoneNumbers.add(nums.get(i));
			}
		}
		mapData.put(p, phoneNumbers);
	}

	public void addPhoneNumber(Contact p, String n) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		if ((n == null) || n.equals("")) {
			throw new IllegalArgumentException();
		}
		if (!contains(p)) {
			List<String> phoneNumbers = new LinkedList<String>();
			phoneNumbers.add(n);
			addEntry(p, phoneNumbers);
		} else {
			if (!mapData.get(p).contains(n)) {
				mapData.get(p).add(n);
			}
		}	
	}

	public void removeEntry(Contact p) {
		if ((p == null) || !contains(p)) {
			throw new IllegalArgumentException();
		}
		mapData.remove(p);
	}

	public void deletePhoneNumber(Contact p, String n) {
		if ((p == null) || !contains(p)) {
			throw new IllegalArgumentException();
		}
		if ((n == null) || n.equals("")) {
			throw new IllegalArgumentException();
		}
		mapData.get(p).remove(n);
		if (mapData.get(p).size() <= 0) {
			removeEntry(p);
		}
	}

	public void clear() {
		mapData.clear();
	}

}
