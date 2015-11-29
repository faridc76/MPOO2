package projet;

public interface Graphe {
	
	// REQUETES
	
	 /**
     * Le premier successeur.
     * @pre <pre>
     *     s != null.
     *     s appartient au graphe.
     *     s a un successeur.</pre>
     */
	Sommet premSucc(Sommet s);
	
	 /**
     * Le successeur qui suit t dans la liste des successeurs de s.
     * @pre <pre>
     *     s != null et t != null.
     *     s appartien au graphe.
     *     t est un successeur de s.
     *     il existe un successeur a s aprés t. </pre>
     */
	Sommet succSuivant(Sommet s, Sommet t);
	
	
	
	// COMMANDES

	/**
     * Ajoute un Sommet au graphe.
     * @pre <pre>
     *     s != null.
     *     Le graphe ne contient pas s.</pre>
     * @post <pre>
     *     Le graphe contient s.</pre>
     */
	void addSommet(Sommet s);
	
	/**
     * Supprime un Sommet.
     * @pre <pre>
     *     s != null
     *     Le graphe contient s.</pre>
     * @post <pre>
     *     Le graphe ne contient plus s.</pre>
     */
	void removeSommet(Sommet s);
	
	/**
     * Ajoute un t a la liste des successeur de s.
     * @pre <pre>
     *     s != null et t != null.
     *     s appartient au graphe.
     *     t n'est pas un successeur de s.
     * @post <pre>
     *     t est un successeur de s</pre>
     */
	void addSucc(Sommet s, Sommet t);
	
	/**
     * Supprime t de la liste des successeurs de s.
     * @pre <pre>
     *     s != null et t != null.
     *     s appartient au graphe.
     *     t n'est pas un successeur de s.</pre>
     * @post <pre>
     *     t n'est plus un successeur de s.</pre>
     */
	void removeSucc(Sommet s, Sommet t);

}
