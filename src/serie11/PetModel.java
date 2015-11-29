package serie11;

import java.io.File;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Le modèle est une entité qui maintient une référence vers un fichier
 *  (File getFile()), une autre vers un document contenant le texte
 *  à afficher (Document getDocument()), et qui indique si le document
 *  et le fichier (lorsqu'ils existent) sont en phase
 *  (boolean isSynchronized()).
 *
 * Le modèle peut se trouver dans l'un des états suivants
 *  (renvoyé par getState()) :
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
 *  Notez que cet état est le seul des quatre pour lequel le modèle
 *   a une chance d'être synchronisé.
 * </dl>
 *
 * Voici les différentes actions autorisées sur le modèle :
 * <ul>
 * <li>d : createEmptyDoc()</li>
 * <li>f : setFile(File f)</li>
 * <li>l : load()</li>
 * <li>rd : removeDocument()</li>
 * <li>rf : removeFile()</li>
 * <li>s : save()</li>
 * </ul>
 *
 * Voici la table des transitions autorisées entre les différents états du
 *  modèle :
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
     * Le document associé à ce modèle.
     */
    Document getDocument();
    /**
     * Le fichier associé à ce modèle.
     */
    File getFile();
    /**
     * L'état du modèle.
     * Pour plus de détails, voir la description préliminaire.
     */
    State getState();
    /**
     * Le modèle est-il synchronisé ?
     * La réponse vaut true ssi il existe un document et un fichier
     *  et qu'alors la séquence de caractères du fichier a été générée
     *  à partir de celle du document, ou l'inverse.
     */
    boolean isSynchronized();
    
    // COMMANDES
    
    /**
     * Associer un document vierge au modèle (action d).
     * Les classes d'implémentation devront s'assurer que lorsque ce document
     *  sera modifié, le modèle en sera automatiquement notifié.
     * @post
     *    getDocument() != null
     *    getDocument.getLength() == 0
     *    !isSynchronized()
     */
    void createEmptyDoc();
    /**
     * Ecrire le fichier dans le document (action l).
     * Crée un nouveau document pour ce modèle, reflétant le contenu du
     *  fichier.
     * @pre
     *    getState() == FIL_DOC
     * @post
     *    isSynchronized()
     * @throws java.io.FileNotFoundException
     *    Si le fichier n'a pas pu être ouvert en lecture
     * @throws IOException
     *    S'il s'est produit une erreur lors de la lecture du fichier
     * @throws BadLocationException
     *    S'il s'est produit une erreur interne lors du traitement du document
     */
    void load() throws IOException, BadLocationException;
    /**
     * Enlever le document du modèle (action rd).
     * @post
     *    getDocument() == null
     *    !isSynchronized()
     */
    void removeDocument();
    /**
     * Enlever le fichier du modèle (action rf).
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
     *    S'il s'est produit une erreur lors de l'écriture dans le fichier
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
