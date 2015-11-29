package projet;

public class MatriceGraphe extends Matrice implements Graphe {

//ATTRIBUTS
	
	private Sommet[][] graphe;
	
	//CONSTRUCTEURS
	
	public MatriceGraphe() {
		graphe = new Sommet[100][];
	}
	
	
	public Sommet premSucc(Sommet s) {
		if (s == null) {
			throw new IllegalArgumentException("s est nul");
		}
		if (!sommetDansGraph(graphe,s)) {
			throw new IllegalArgumentException("s n'est pas dans le graphe");
		}
		if (!aUnSuccesseur(graphe,s)) {
			throw new IllegalArgumentException("s n'a pas de sussesseur");
		}
		return graphe[rangDuSommet(graphe,s)][1];
	}

	public Sommet succSuivant(Sommet s, Sommet t) {
		if ((s == null) || (t == null)) {
			throw new IllegalArgumentException("s ou t est nul");
		}
		if (!sommetDansGraph(graphe,s)) {
			throw new IllegalArgumentException("s n'est pas dans le graphe");
		}
		if (!estUnSuccesseur(graphe,s,t)) {
			throw new IllegalArgumentException("t n'est pas un successeur de s");
		}
		if (!successeurSuivantExiste(graphe,s,t)) {
			throw new IllegalArgumentException("s n'a pas du successeur aprés t");
		} 
		return graphe[rangDuSommet(graphe,s)][rangDuSuccesseur(graphe,s,t) + 1];
	}

	

	public void addSommet(Sommet s) {
		if (s == null) {
			throw new IllegalArgumentException("s est nul");
		}
		if (sommetDansGraph(graphe,s)) {
			throw new IllegalArgumentException("s est déjà dans le graphe");
		}
		graphe[graphe[0].length][0] = s;
	}

	@Override
	public void removeSommet(Sommet s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		if (!sommetDansGraph(graphe,s)) {
			throw new IllegalArgumentException("s n'est pas dans le graphe");
		}
		Sommet[][] copyGraphe = new Sommet[100][];
		for (int j = 0; j < rangDuSommet(graphe,s); j++) { 
			for (int i = 0; i < graphe[1].length; i++) {
				copyGraphe [j][i] = graphe [j][i];
			}
		}
		for (int j = rangDuSommet(graphe,s) + 1; j < graphe[0].length; j++) { 
			for (int i = 0; i < graphe[1].length; i++) {
				copyGraphe [j][i] = graphe [j][i];
			}
		}
	}

	
	public void addSucc(Sommet s, Sommet t) {
		if ((s == null) || (t == null)) {
			throw new IllegalArgumentException("s ou t est nul");
		}
		if (!sommetDansGraph(graphe,s)) {
			throw new IllegalArgumentException("s n'est pas dans le graphe");
		}
		if (estUnSuccesseur(graphe,s,t)) {
			throw new IllegalArgumentException("t est déjà un successeur de s");
		}
		graphe[rangDuSommet(graphe,s)][graphe[1].length] = t;

	}

	
	public void removeSucc(Sommet s, Sommet t) {
		if ((s == null) || (t == null)) {
			throw new IllegalArgumentException("s ou t est nul");
		}
		if (!sommetDansGraph(graphe,s)) {
			throw new IllegalArgumentException("s n'est pas dans le graphe");
		}
		if (!estUnSuccesseur(graphe,s,t)) {
			throw new IllegalArgumentException("t n'est pas un successeur de s");
		}
		for (int i = rangDuSuccesseur(graphe,s,t); graphe[rangDuSommet(graphe,s)][i] != null; i++) {
			graphe [i][0] = graphe [i + 1][0];
			i = i + 1;
		}

	}

}
