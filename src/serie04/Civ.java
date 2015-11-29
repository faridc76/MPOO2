package serie04;

public enum Civ {
	UKN(""),
	MR("M."),
	MRS("Mme."),
	MS("Mlle.");
	
	private String titre;
	
	Civ(String titre) {
		if (titre == null) {
			throw new IllegalArgumentException();
		}
		this.titre = titre;
	}
	
	public String toString() {
		return this.titre;
	}
	
	
	public boolean canEvolveTo(Civ candidate) {
		if (candidate == null) {
			throw new IllegalArgumentException();
		}
		switch(this) {
		case UKN :
			return (candidate == MR || candidate == MRS || candidate == MS);
		case MR :
			return false;
		case MRS :
			return (candidate == MS);
		case MS :
			return (candidate == MRS);
		default:
			return false;
		}
	}
}

