package projet;

/**
 * 
 *Une classe dans la quel se trouve des méthodes
 * et des requetes utiles a la classe MatriceGraphe.
 *
 */

public class Matrice {
	
	/**
	 * 
	 * Requete qui test si un sommet est dans le graphe.
	 * 
	 */
	public boolean sommetDansGraph(Sommet[][] tab, Sommet s) {
		boolean b = false;
		for (int i = 0; i < tab[0].length; i++) {
			if (tab[i][0] == s) {
				b = true;
			}
		}
		return b;
	}
	
	/**
	 * 
	 * Requete qui recherche le rang du sommet est dans le graphe.
	 * 
	 */
	public int rangDuSommet(Sommet[][] tab, Sommet s) {
		int i = 0;
		while (tab[i][0] != s) {
			i = i + 1;
		}
		return i;
	}
	
	/**
	 * 
	 * Requete qui recherche le rang d'un successeur 
	 * pour un sommet dans le graphe.
	 * 
	 */
	public int rangDuSuccesseur(Sommet[][] tab, Sommet s, Sommet t) {
		int i = 0;
		while (tab[rangDuSommet(tab,s)][i] != t) {
			i = i + 1;
		}
		return i;
	}
	
	/**
	 * 
	 * Requete qui test si un successeur a un suivant pour un sommet.
	 * 
	 */
	public boolean successeurSuivantExiste(Sommet[][] tab, Sommet s, Sommet t) {
		return ((tab[rangDuSommet(tab,s)][rangDuSuccesseur(tab,s,t) + 1]) != null); 
	}
	
	/**
	 * 
	 * Requete qui test un sommet est un successeur dans autre sommet.
	 * 
	 */
	public boolean estUnSuccesseur(Sommet[][] tab, Sommet s, Sommet t) {
		boolean b = false;
		for (int i = 1; i < tab[1].length; i++) {
			if (tab[rangDuSommet(tab,s)][i] == t) {
				b = true;
			}
		}
		return b;
	}
	
	/**
	 * 
	 * Requete qui test si un sommet a un successeur.
	 * 
	 */
	public boolean aUnSuccesseur(Sommet[][] tab, Sommet s) {
		return (tab[rangDuSommet(tab,s)][1] != null);
	}
}
