package serie10;

import java.io.File;
import java.util.Observer;

/**
 * Modélise un concasseur en délégant l'essentiel de son fonctionnement à un
 *  SplitManager.
 * Ce type de concasseur permet de gérer les situations où aucun fichier n'est
 *  traité (et où il n'y a donc pas de SplitManager).
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
 *     describeSplit() est une chaîne décrivant le découpage à venir
 *         (voir énoncé)
 * @cons
 *     $POST$
 *         getSplitManager() == null
 *         getSplitManagerObserver() == null
 */
public interface SplitterModel extends ObservableModel {
    /**
     * Nombre maximal de fragments souhaité.
     */
    int MAX_FRAGMENT_NB = 100;
    /**
     * Taille minimale d'un fragment (en octets).
     */
    int MIN_FRAGMENT_SIZE = 1024;

    // REQUETES
    /**
     * Le SplitManager courant.
     * Peut être null.
     */
    SplitManager getSplitManager();
    /**
     * Le nombre de fragments courant.
     */
    int getFragmentNb();
    /**
     * Description du découpage à venir.
     */
    String describeSplit();
    /**
     * Observateur à mettre en place sur chaque SplitManager.
     */
    Observer getSplitManagerObserver();

    // COMMANDES
    /**
     * Création d'un SplitManager pour le fichier représenté par f.
     * @pre
     *     f != null
     * @post
     *     Soit sm ::= getSplitManager()
     *     Soit oldsm ::= old getSplitManager()
     *     Soit obs ::= getSplitManagerObserver()
     *     oldsm != null ==> tous les observateurs de oldsm ont été décrochés
     *     f.exists() && f.canRead()
     *         ==> sm != null
     *             sm.getFileName().equals(f.getAbsolutePath())
     *             sm.getFileSize() == file.length()
     *             sm.getSplitsSizes().length <= 1
     *             obs != null ==> obs a été enregistré auprès de sm
     *     !f.exists() || !f.canRead() ==> sm == null
     */
    void createSplitManager(File f);
    /**
     * Définit l'observateur qui devra être placé sur chaque futur
     *  SplitManager créé.
     * Si obs est null, cela signifie qu'il n'y aura pas d'observateur.
     * @post
     *     getSplitManagerObserver() == obs
     */
    void defineSplitManagerObserver(Observer obs);
}
