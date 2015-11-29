package serie07;

/**
 * Notre mod�le compare un nombre propos� avec une valeur qu'il tire
 *  al�atoirement et m�morise le nombre de fois qu'on lui a demand�
 *  d'effectuer cette action.
 * Si le nombre propos� est �gal � la valeur al�atoire, le joueur a perdu et
 *  le nombre de coups est r�initialis�.
 * Sinon, le nombre de coup est incr�ment�.
 * @inv <pre>
 *     getDrawsCount() >= 0
 *     getProposalsCount() >= 0
 *     getMaxNumber() >= 2
 *     1 <= getProposalNumber() <= getMaxNumber()
 *     1 <= getDrawNumber() <= getMaxNumber()
 *     sameNumbers() == (getProposalNumber() == getDrawNumber()) </pre>
 * @cons <pre>
 * $DESC$ Un mod�le permettant d'obtenir un nombre al�atoire entre 1 et max.
 * $ARGS$ int max
 * $PRE$  max >= 2
 * $POST$
 *     getDrawsCount() == 0
 *     getProposalsCount() == 0
 *     getMaxNumber() == max </pre>
 */
public interface SuspenseModel extends ObservableModel {
    
    // REQUETES
    
    /**
     * Le nombre de tirages al�atoires effectu�s depuis la derni�re
     *  r�initialisation.
     */
    int getDrawsCount();
    /**
     * Le nombre de propositions effectu�es depuis la derni�re r�initialisation.
     */
    int getProposalsCount();
    /**
     * La valeur maximale qu'un joueur puisse proposer ou qui puisse �tre
     *  al�atoirement tir�e.
     */
    int getMaxNumber();
    /**
     * Le nombre al�atoire pr�c�demment tir� par <code>drawNumber</code>.
     * @pre <pre>
     *     getDrawsCount() > 0 </pre>
     */
    int getDrawNumber();
    /**
     * Le nombre propos� par le joueur.
     * @pre <pre>
     *     getProposalsCount() > 0 </pre>
     */
    int getProposalNumber();
    /**
     * Indique si la derni�re proposition �tait perdante.
     * @pre <pre>
     *     getDrawsCount() > 0
     *     getProposalsCount() > 0 </pre>
     */
    boolean sameNumbers();
    
    // COMMANDES
    
    /**
     * Tire uniform�ment un nombre entre 1 et getMaxNumber().
     * @post <pre>
     *     getDrawsCount() == old getDrawsCount() + 1
     *     un nombre al�atoire a �t� tir� </pre>
     */
    void drawNumber();
    /**
     * M�morise un nombre entre 1 et getMaxNumber().
     * @pre <pre>
     *     1 <= proposal <= getMaxNumber() </pre>
     * @post <pre>
     *     getProposalsCount() == old getProposalsCount() + 1
     *     getProposalNumber() == proposal </pre>
     */
    void proposeNumber(int number);
    /**
     * R�initialise le mod�le.
     * @post <pre>
     *     getDrawsCount() == 0
     *     getProposalsCount() == 0 </pre>
     */
    void reinit();
}
