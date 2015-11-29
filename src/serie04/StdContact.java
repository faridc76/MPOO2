package serie04;

public class StdContact implements Contact {

	
	private String lastName;
	private String firstName;
	private Civ civility;
	
	
	public StdContact(String n, String p) {
		if ((n == null) || (p == null)){
			throw new IllegalArgumentException();
		}
		if ((n.equals("")) && (p.equals(""))) {
			throw new IllegalArgumentException();
		}
		lastName = n;
		firstName = p;
		civility = Civ.UKN;
	}
	
	public StdContact(Civ c, String n, String p) {
		if ((n == null) || (p == null) || (c == null)){
			throw new IllegalArgumentException();
		}
		if ((n.equals("")) && (p.equals(""))) {
			throw new IllegalArgumentException();
		}
		lastName = n;
		firstName = p;
		civility = c;
	}
	
	
	
	
	public int compareTo(Contact c) {
		if (c == null) {
			throw new NullPointerException();
		}
		int i;
		i = lastName.compareTo(c.getLastName());
		if (i == 0) {
			i = firstName.compareTo(c.getFirstName());
			if (i == 0) {
				i = civility.compareTo(c.getCivility());
			}
		}
		return i;
	}
    
    public boolean equals(Object obj) {
		if (obj != null && obj.getClass() == this.getClass()) {
			StdContact c = (StdContact) obj;
			return (civility == c.getCivility()
					&& lastName == c.getLastName()
					&& firstName == c.getFirstName());
		}
		return false;
	}
    
    public Civ getCivility() {
    	return civility;
    }
    
    public String getFirstName() {
    	return firstName;
    }
    
    public String getLastName() {
    	return lastName;
    }
   
    public int hashCode() {
    	return civility.hashCode()
		+ firstName.hashCode()
		+ lastName.hashCode();
    }
    
    public String toString() {
    	return civility.toString() + " " + getFirstName() + " " + getLastName();
    }

	
    public void setCivility(Civ civility) {
		if (civility == null || !getCivility().canEvolveTo(civility)) {
			throw new IllegalArgumentException();
		}
		this.civility = civility;
	}
}