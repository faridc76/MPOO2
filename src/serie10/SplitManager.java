package serie10;

import java.io.IOException;

/**
 * Mod�lise le type abstrait SplitManager.
 * Un split manager est un objet capable de d�couper un gros fichier
 *  en fichiers plus petits.
 * Le principe est le suivant : on donne le nom du fichier � scinder � la
 *  construction du split manager, il est donc fix� une fois pour toutes.
 * Ensuite, on peut configurer le split manager en lui appliquant autant de
 *  fois que l'on souhaite une des m�thodes setSplits* (en changeant la
 *  taille des morceaux � chaque fois).
 * Puis on commande au split manager de casser le fichier selon la derni�re
 *  configuration effectu�e (m�thode split()).
 * Le nom des petits fichiers produits est form� sur la base du nom du
 *  fichier source, augment� d'un num�ro de s�quence.
 * Remarque : on fait l'hypoth�se que le fichier g�r� par le split manager
 *  n'est pas modifi� pendant toute la dur�e de vie du split manager (comme
 *  si le fichier �tait verrouill� durant toute son utilisation).
 * 
 * @inv <pre>
 *     getFileName() != null
 *     getFileSize() >= 0
 *     getSplitsSizes() != null
 *     getSplitsSizes().length == 0 <==> getFileSize() == 0
 *     forall i:[0..getSplitsSizes().length[ : getSplitsSizes()[i] >= 1
 *     getFileSize() == sum(i:[0..getSplitsSizes().length[, getSplitsSizes()[i])
 *     getSplitsNames() != null
 *     getSplitsNames().length == getSplitsSizes().length
 *     forall i:[0..getSplitsSizes().length[ :
 *         getSplitsNames()[i].equals(getFileName() + "." + (i + 1)) </pre>
 * @cons <pre>
 * $DESC$
 *     Un gestionnaire de scission bas� sur le fichier file.
 * $ARGS$
 *     File file
 * $PRE$
 *     file != null
 * $POST$
 *     getFileName().equals(file.getAbsolutePath()
 *     getFileSize() == file.length()
 *     getSplitsSizes().length <= 1 </pre>
 */
public interface SplitManager extends ObservableModel {
   
    // REQUETES
   
    /**
     * Le nom du fichier � scinder.
     */
    String getFileName();
    /**
     * La taille du fichier � scinder.
     * Retourne 0 si le fichier n'existe pas.
     */
    long getFileSize();
    /**
     * Les noms des fragments de fichier.
     */
    String[] getSplitsNames();
    /**
     * Les tailles des fragments de fichiers.
     */
    long[] getSplitsSizes();
   
    // COMMANDES
   
    /**
     * Fixe la taille des fragments de fichier.
     * Seul le dernier fragment peut �tre de taille inf�rieure.
     * @pre <pre>
     *     1 <= newSize
     *     (getFileSize() / newSize) <= Integer.MAX_VALUE </pre>
     * @post <pre>
     *     Soit gSS ::= getSplitsSizes()
     *     forall i:[0..gSS.length - 1[ : gSS[i] == newSize
     *     getFileSize() > 0 ==> 0 < gSS[gSS.length - 1] <= newSize </pre>
     */
    void setSplitsSizes(long newSize);
    /**
     * Fixe la taille des fragments de fichier.
     * Si la somme des tailles pass�es est inf�rieure � la taille
     *  du fichier � fragmenter on rajoute un dernier fragment qui contient
     *  ce qu'il reste.
     * Si la somme des tailles pass�es est sup�rieure � la taille
     *  du fichier � fragmenter on tronque l'argument.
     * @pre <pre>
     *     newSizes != null
     *     newSizes.length >= 1
     *     forall i:[0..newSizes.length[ : newSizes[i] >= 1 </pre>
     * @post <pre>
     *     Soit gSS ::= getSplitsSizes()
     *     getFileSize() <= sum(i:[0..newSizes.length[, newSizes[i])
     *         ==> gSS.length <= newSizes.length
     *             forall i:[0..gSS.length - 1[ : gSS[i] == newSizes[i]
     *             getFileSize() > 0
     *                 ==> 0 < gSS[gSS.length - 1] <= newSizes[gSS.length - 1]
     *     getFileSize() > sum(i:[0..newSizes.length[, newSizes[i])
     *         ==> gSS.length == newSizes.length + 1
     *             forall i:[0..newSizes.length[ : gSS[i] == newSizes[i]
     *             gSS[newSizes.length] ==
     *                 getFileSize()
     *                     - sum(i:[0..newSizes.length[, newSizes[i]) </pre>
     */
    void setSplitsSizes(long[] newSizes);
    /**
     * Fixe le nombre des fragments de fichier, qui sont alors tous � peu
     *  pr�s de la m�me taille (� un octet pr�s).
     * @pre <pre>
     *     number >= 1 </pre>
     * @post <pre>
     *     Soit gSS ::= getSplitsSizes()
     *     gSS.length == min(getFileSize(), number)
     *     forall i:[0..(getFileSize() % gSS.length)[ :
     *         gSS[i] == (getFileSize() / gSS.length) + 1
     *     forall i:[(getFileSize() % gSS.length)..gSS.length[ : 
     *         gSS[i] == getFileSize() / gSS.length </pre>
     */
    void setSplitsNumber(int number);
    /**
     * Effectue sur le disque la scission du gros fichier en plus petits.
     * Si le fichier source est de taille 0, il ne s'est rien pass�.
     * @post <pre>
     *     forall i:[0..getSplitsSizes().length[ :
     *         getSplitsSizes()[i] == La taille du fichier sur disque de nom
     *                                getSplitsNames()[i]
     *     le fichier de nom getFileName() a m�me contenu que la
     *         concat�nation des fichiers de nom getSplitsNames() </pre>
     * @throws java.io.FileNotFoundException
     *     si getFileName() ne repr�sente pas un fichier accessible
     * @throws IOException
     *     s'il s'est produit une erreur d'entr�e/sortie durant la scission
     */
    void split() throws IOException;
}
