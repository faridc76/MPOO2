package serie07;

/**
 * Notre modèle compare un nombre proposé avec une valeur qu'il tire
 *  aléatoirement et mémorise le nombre de fois qu'on lui a demandé
 *  d'effectuer cette action.
 * Si le nombre proposé est égal à la valeur aléatoire, le joueur a perdu et
 *  le nombre de coups est réinitialisé.
 * Sinon, le nombre de coup est incrémenté.
 * @inv <pre>
 *     getDrawsCount() >= 0
 *     getProposalsCount() >= 0
 *     getMaxNumber() >= 2
 *     1 <= getProposalNumber() <= getMaxNumber()
 *     1 <= getDrawNumber() <= getMaxNumber()
 *     sameNumbers() == (getProposalNumber() == getDrawNumber()) </pre>
 * @cons <pre>
 * $DESC$ Un modèle permettant d'obtenir un nombre aléatoire entre 1 et max.
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
     * Le nombre de tirages aléatoires effectués depuis la dernière
     *  réinitialisation.
     */
    int getDrawsCount();
    /**
     * Le nombre de propositions effectuées depuis la dernière réinitialisation.
     */
    int getProposalsCount();
    /**
     * La valeur maximale qu'un joueur puisse proposer ou qui puisse être
     *  aléatoirement tirée.
     */
    int getMaxNumber();
    /**
     * Le nombre aléatoire précédemment tiré par <code>drawNumber</code>.
     * @pre <pre>
     *     getDrawsCount() > 0 </pre>
     */
    int getDrawNumber();
    /**
     * Le nombre proposé par le joueur.
     * @pre <pre>
     *     getProposalsCount() > 0 </pre>
     */
    int getProposalNumber();
    /**
     * Indique si la dernière proposition était perdante.
     * @pre <pre>
     *     getDrawsCount() > 0
     *     getProposalsCount() > 0 </pre>
     */
    boolean sameNumbers();
    
    // COMMANDES
    
    /**
     * Tire uniformément un nombre entre 1 et getMaxNumber().
     * @post <pre>
     *     getDrawsCount() == old getDrawsCount() + 1
     *     un nombre aléatoire a été tiré </pre>
     */
    void drawNumber();
    /**
     * Mémorise un nombre entre 1 et getMaxNumber().
     * @pre <pre>
     *     1 <= proposal <= getMaxNumber() </pre>
     * @post <pre>
     *     getProposalsCount() == old getProposalsCount() + 1
     *     getProposalNumber() == proposal </pre>
     */
    void proposeNumber(int number);
    /**
     * Réinitialise le modèle.
     * @post <pre>
     *     getDrawsCount() == 0
     *     getProposalsCount() == 0 </pre>
     */
    void reinit();
}
