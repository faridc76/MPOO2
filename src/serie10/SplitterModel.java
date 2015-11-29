package serie10;

import java.io.File;
import java.util.Observer;

/**
 * Mod�lise un concasseur en d�l�gant l'essentiel de son fonctionnement � un
 *  SplitManager.
 * Ce type de concasseur permet de g�rer les situations o� aucun fichier n'est
 *  trait� (et o� il n'y a donc pas de SplitManager).
 * @inv
 *     0 <= getFragmentNb() <= MAX_FRAGMENT_NB
 *     getSplitManager() == null ==> getFragmentNb() == 0
 *     getSplitManager() != null
 *         ==> getFragmentNb() == min(
 *                 MAX_FRAGMENT_NB,
 *                 ceil(getSplitManager().getFileSize() / MIN_FRAGMENT_SIZE)
 *             )
 *     describeSplit() != null
 *     getSplitManager() == null <==> describeSplit().equals("")
 *     describeSplit() est une cha�ne d�crivant le d�coupage � venir
 *         (voir �nonc�)
 * @cons
 *     $POST$
 *         getSplitManager() == null
 *         getSplitManagerObserver() == null
 */
public interface SplitterModel extends ObservableModel {
    /**
     * Nombre maximal de fragments souhait�.
     */
    int MAX_FRAGMENT_NB = 100;
    /**
     * Taille minimale d'un fragment (en octets).
     */
    int MIN_FRAGMENT_SIZE = 1024;

    // REQUETES
    /**
     * Le SplitManager courant.
     * Peut �tre null.
     */
    SplitManager getSplitManager();
    /**
     * Le nombre de fragments courant.
     */
    int getFragmentNb();
    /**
     * Description du d�coupage � venir.
     */
    String describeSplit();
    /**
     * Observateur � mettre en place sur chaque SplitManager.
     */
    Observer getSplitManagerObserver();

    // COMMANDES
    /**
     * Cr�ation d'un SplitManager pour le fichier repr�sent� par f.
     * @pre
     *     f != null
     * @post
     *     Soit sm ::= getSplitManager()
     *     Soit oldsm ::= old getSplitManager()
     *     Soit obs ::= getSplitManagerObserver()
     *     oldsm != null ==> tous les observateurs de oldsm ont �t� d�croch�s
     *     f.exists() && f.canRead()
     *         ==> sm != null
     *             sm.getFileName().equals(f.getAbsolutePath())
     *             sm.getFileSize() == file.length()
     *             sm.getSplitsSizes().length <= 1
     *             obs != null ==> obs a �t� enregistr� aupr�s de sm
     *     !f.exists() || !f.canRead() ==> sm == null
     */
    void createSplitManager(File f);
    /**
     * D�finit l'observateur qui devra �tre plac� sur chaque futur
     *  SplitManager cr��.
     * Si obs est null, cela signifie qu'il n'y aura pas d'observateur.
     * @post
     *     getSplitManagerObserver() == obs
     */
    void defineSplitManagerObserver(Observer obs);
}
