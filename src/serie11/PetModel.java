package serie11;

import java.io.File;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Le mod�le est une entit� qui maintient une r�f�rence vers un fichier
 *  (File getFile()), une autre vers un document contenant le texte
 *  � afficher (Document getDocument()), et qui indique si le document
 *  et le fichier (lorsqu'ils existent) sont en phase
 *  (boolean isSynchronized()).
 *
 * Le mod�le peut se trouver dans l'un des �tats suivants
 *  (renvoy� par getState()) :
 * <dl>
 * <dt> NOF_NOD (NO File, NO Document)
 * <dd>pas de fichier ni de document
 *  (<code>getFile() == null  &&  getDocument() == null</code>)
 * <dt>NOF_DOC (NO File, Document)
 * <dd>pas de fichier et un document
 *  (<code>getFile() == null  &&  getDocument() != null</code>)
 * <dt>FIL_NOD : (FILe, NO Document)
 * <dd>un fichier et pas de document
 *  (<code>getFile() != null  &&  getDocument() == null</code>)
 * <dt>FIL_DOC : (FILe, DOCument)
 * <dd>un fichier et un document
 *  (<code>getFile() != null  &&  getDocument() != null</code>)<br />
 *  Notez que cet �tat est le seul des quatre pour lequel le mod�le
 *   a une chance d'�tre synchronis�.
 * </dl>
 *
 * Voici les diff�rentes actions autoris�es sur le mod�le :
 * <ul>
 * <li>d : createEmptyDoc()</li>
 * <li>f : setFile(File f)</li>
 * <li>l : load()</li>
 * <li>rd : removeDocument()</li>
 * <li>rf : removeFile()</li>
 * <li>s : save()</li>
 * </ul>
 *
 * Voici la table des transitions autoris�es entre les diff�rents �tats du
 *  mod�le :
 * <pre>
 *          |    d    |    f    |    rd   |    rf   |    l    |    s    |
 * ---------+---------+---------+---------+---------+---------+---------+
 *  NOF_NOD | NOF_DOC : FIL_NOD : NOF_NOD : NOF_NOD :    -    :    -    |
 * ---------+---------+---------+---------+---------+---------+---------+
 *  NOF_DOC | NOF_DOC : FIL_DOC : NOF_NOD : NOF_DOC :    -    :    -    |
 * ---------+---------+---------+---------+---------+---------+---------+
 *  FIL_NOD | FIL_DOC : FIL_NOD : FIL_NOD : NOF_NOD :    -    :    -    |
 * ---------+---------+---------+---------+---------+---------+---------+
 *  FIL_DOC | FIL_DOC : FIL_DOC : FIL_NOD : NOF_DOC : FIL_DOC : FIL_DOC |
 * ---------+---------+---------+---------+---------+---------+---------+
 *
 *                   |   d   |   f   |   rd  |   rf  |   l   |   s   |
 * ------------------+-------+-------+-------+-------+-------+-------+
 *  isSynchronized() | false | false | false | false |  true |  true |
 * ------------------+-------+-------+-------+-------+-------+-------+</pre>
 * @inv <pre>
 *     getDocument() == null 
 *         <==> getState() == NOF_NOD || getState() == FIL_NOD
 *     getFile() == null 
 *         <==> getState() == NOF_NOD || getState() == NOF_DOC
 *     isSynchronized() ==> getState() == FIL_DOC </pre>
 */
public interface PetModel extends ObservableModel {
    
    enum State {
        NOF_NOD, NOF_DOC, FIL_NOD, FIL_DOC;
    }
    
    // REQUETES
    
    /**
     * Le document associ� � ce mod�le.
     */
    Document getDocument();
    /**
     * Le fichier associ� � ce mod�le.
     */
    File getFile();
    /**
     * L'�tat du mod�le.
     * Pour plus de d�tails, voir la description pr�liminaire.
     */
    State getState();
    /**
     * Le mod�le est-il synchronis� ?
     * La r�ponse vaut true ssi il existe un document et un fichier
     *  et qu'alors la s�quence de caract�res du fichier a �t� g�n�r�e
     *  � partir de celle du document, ou l'inverse.
     */
    boolean isSynchronized();
    
    // COMMANDES
    
    /**
     * Associer un document vierge au mod�le (action d).
     * Les classes d'impl�mentation devront s'assurer que lorsque ce document
     *  sera modifi�, le mod�le en sera automatiquement notifi�.
     * @post
     *    getDocument() != null
     *    getDocument.getLength() == 0
     *    !isSynchronized()
     */
    void createEmptyDoc();
    /**
     * Ecrire le fichier dans le document (action l).
     * Cr�e un nouveau document pour ce mod�le, refl�tant le contenu du
     *  fichier.
     * @pre
     *    getState() == FIL_DOC
     * @post
     *    isSynchronized()
     * @throws java.io.FileNotFoundException
     *    Si le fichier n'a pas pu �tre ouvert en lecture
     * @throws IOException
     *    S'il s'est produit une erreur lors de la lecture du fichier
     * @throws BadLocationException
     *    S'il s'est produit une erreur interne lors du traitement du document
     */
    void load() throws IOException, BadLocationException;
    /**
     * Enlever le document du mod�le (action rd).
     * @post
     *    getDocument() == null
     *    !isSynchronized()
     */
    void removeDocument();
    /**
     * Enlever le fichier du mod�le (action rf).
     * @post
     *    getFile() == null
     *    !isSynchronized()
     */
    void removeFile();
    /**
     * Ecrire le document dans le fichier (action s).
     * @pre
     *    getState() == FIL_DOC
     * @post
     *    isSynchronized()
     * @throws IOException
     *    S'il s'est produit une erreur lors de l'�criture dans le fichier
     * @throws BadLocationException
     *    S'il s'est produit une erreur interne lors du traitement du document
     */
    void save() throws IOException, BadLocationException;
    /**
     * Fixer le fichier courant (action f).
     * @pre <pre>
     *    f != null
     *    f.isFile() </pre>
     * @post <pre>
     *    getFile() == f
     *    !isSynchronized() </pre>
     */
    void setFile(File f);
}
