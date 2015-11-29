package serie03;


/**
 * @inv <pre>
 *     1 <= getIndex()
 *     canDo()
 *         ==> getIndex() <= getText().getLinesNb()
 *     canUndo()
 *         ==> getLine() != null
 *             getIndex() <= getText().getLinesNb() + 1 </pre>
 */
class DeleteLine extends AbstractCommand {

    // ATTRIBUTS
    
    private final int index;
    private String line;

    // CONSTRUCTEURS
    
    /**
     * Une commande de suppression de la ligne de rang <code>numLine</code>
     *  dans <code>text</code>.
     * @pre <pre>
     *     text != null
     *     1 <= numLine </pre>
     * @post <pre>
     *     getText() == text
     *     getIndex() == numLine
     *     getLine() == null </pre>
     */
    DeleteLine(Text text, int numLine) {
        super(text);
        if (numLine < 1) {
            throw new IllegalArgumentException(
                    "Le num�ro de ligne n'est pas valide (" + numLine + ")."
            );
        }
        index = numLine;
    }
    
    // REQUETES
    
    @Override
    public boolean canDo() {
        return
            super.canDo()
            && index <= getText().getLinesNb();
    }
    @Override
    public boolean canUndo() {
        return
            super.canUndo()
            && line != null
            && index <= getText().getLinesNb() + 1;
    }
    /**
     * Le rang o� l'on doit supprimer la ligne dans le texte.
     */
    int getIndex() {
        return index;
    }
    /**
     * La ligne � supprimer.
     */
    String getLine() {
        return line;
    }
    
    // COMMANDES
    
    /**
     * Ex�cute la suppression d'une ligne dans le texte.
     * @post <pre>
     *     getText().getLinesNb() == old getText().getLinesNb() - 1
     *     getLine().equals(old getText().getLine(getIndex()))
     *     forall i:[getIndex()..getText().getLinesNb()] :
     *         getText().getLine(i).equals(old getText().getLine(i + 1)) </pre>
     */
    @Override
    void doIt() {
        assert canDo();
        line = getText().getLine(index);
        getText().deleteLine(index);
    }
    /**
     * Annule la suppression d'une ligne dans le texte.
     * @post <pre>
     *     getText().getLinesNb() == old getText().getLinesNb() + 1
     *     getText().getLine(getIndex()).equals(getLine())
     *     forall i:[getIndex() + 1..getText().getLinesNb()] :
     *         getText().getLine(i).equals(old getText().getLine(i - 1)) </pre>
     */
    @Override
    void undoIt() {
        assert canUndo();
        getText().insertLine(index, line);
    }
}
