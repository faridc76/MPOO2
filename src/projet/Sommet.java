package projet;


public class Sommet {
	
	//ATTRIBUTS
	
	private int name;
	private int degree;
	
	//CONSTRUCTEURS
	
	public Sommet(int nom) {
		if (name < 0) {
			throw new IllegalArgumentException("name >= 0");
		}
		name = nom;
		degree = 0;
	}
	
	//REQUETES
	
	public int getName() {
		return name;
	}
	
	public int getDegree() {
		return degree;
	}
	
	
	//COMMANDES
	public void addDegree() {
		degree = getDegree() + 1;
	}
	
	public void removeDegree() {
		degree = getDegree() + 1;
	}
	
}
