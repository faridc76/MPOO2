package projet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ListeGraphe implements Graphe {

	//ATTRIBUTS
	private Map<Sommet, LinkedList<Sommet>> graphe;

	
	//CONSTRUCTEURS
	public ListeGraphe() {
		graphe = new HashMap<Sommet, LinkedList<Sommet>>();
	}
	
	//REQUETES
	public Sommet premSucc(Sommet s) {
		if (!graphe.containsKey(s)) {
			throw new IllegalArgumentException("graphe.containsKey(s)");
		}
		return graphe.get(s).element();
	}
	
	public Sommet succSuivant(Sommet s, Sommet t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException("s != null, t!= null");
		}
		if (!graphe.containsKey(s)) {
			throw new IllegalArgumentException("graphe.containsKey(s)");
		}
		if (!graphe.get(s).contains(t)) {
			throw new IllegalArgumentException("graphe.get(s).contains(t)");
		}
		LinkedList<Sommet> liste = graphe.get(s);
		return s;
	}
	
	//COMMANDES
	public void addSommet(Sommet s) {
		if (s == null) {
			throw new IllegalArgumentException("s != null");
		}
		if (graphe.containsKey(s)) {
			throw new IllegalArgumentException("!graphe.containsKey(s)");
		}
		LinkedList<Sommet> liste = new LinkedList<Sommet>();
		graphe.put(s, liste);
	}
	
	public void removeSommet(Sommet s) {
		if (s == null) {
			throw new IllegalArgumentException("s != null");
		}
		if (!graphe.containsKey(s)) {
			throw new IllegalArgumentException("graphe.containsKey(s)");
		}
		graphe.remove(s);
	}
	
	public void addSucc(Sommet s, Sommet t) {
		if (t == null) {
			throw new IllegalArgumentException("t != null");
		}
		if (!graphe.containsKey(s)) {
			throw new IllegalArgumentException("graphe.containsKey(s)");
		}
		graphe.get(s).add(t);
		s.addDegree();
	}
	
	public void removeSucc(Sommet s, Sommet t) {
		if (t == null) {
			throw new IllegalArgumentException("t != null");
		}
		if (!graphe.containsKey(s)) {
			throw new IllegalArgumentException("graphe.containsKey(s)");
		}
		graphe.get(s).remove(t);
		s.removeDegree();
	}
	
}
