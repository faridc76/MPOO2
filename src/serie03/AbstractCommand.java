package serie03;

/**
 * Classe fournissant un mécanisme général pour les commandes.
 */
abstract class AbstractCommand implements Command {

    // ATTRIBUTS
    
    private State state;
    private final Text text;

    // CONSTRUCTEURS

    /**
     * @pre <pre>
     *     text != null </pre>
     * @post <pre>
     *     getText() == text
     *     getState() == State.DO </pre>
     */
    AbstractCommand(Text text) {
        if (text == null) {
            throw new IllegalArgumentException("Le texte donné est null.");
        }
        this.text = text;
        state = State.DO;
    }

    // REQUETES

    public boolean canDo() {
        return state == State.DO;
    }
    public boolean canUndo() {
        return state == State.UNDO;
    }
    public Text getText() {
        return text;
    }
    public State getState() {
        return state;
    }

    // COMMANDES
    
    public void act() {
        if (!canDo() && !canUndo()) {
            throw new IllegalStateException();
        }
        if (state == State.DO) {
            doIt();
            state = State.UNDO;
        } else { // nécessairement state == State.UNDO
            undoIt();
            state = State.DO;
        }
    }
    /**
     * Cette méthode doit être redéfinie dans les sous-classes, de sorte
     *  qu'elle implante l'action à réaliser pour exécuter la commande.
     * Elle est appelée par act() et ne doit pas être appelée directement.
     * @pre
     *     canDo()
     * @post
     *     La commande a été exécutée
     */
    abstract void doIt();
    /**
     * Cette méthode doit être redéfinie dans les sous-classes, de sorte
     *  qu'elle implante l'action à réaliser pour annuler la commande.
     * Si l'état du texte correspond à celui dans lequel il était après doIt,
     *  alors undoIt rétablit le texte dans l'état où il était avant
     *  l'exécution de doIt.
     * Elle est appelée par act() et ne doit pas être appelée directement.
     * @pre
     *     canUndo()
     * @post
     *     La commande a été annulée
     */
    abstract void undoIt();
}
