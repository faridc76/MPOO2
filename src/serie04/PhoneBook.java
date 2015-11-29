package serie04;

import java.util.List;
import java.util.NavigableSet;

/**
 * Mod�lise un annuaire t�l�phonique permettant d'associer une liste de
 *  num�ros de t�l�phone � un <code>Contact</code>.
 * Une entr�e au plus par contact.
 * @inv <pre>
 *     contacts() != null
 *     contacts() refl�te � chaque instant l'ensemble des contacts qui se
 *       trouvent dans l'annuaire et le retrait d'un �l�ment de cet ensemble
 *       entraine une modification dans l'annuaire, et r�ciproquement toute
 *       modification de l'annuaire est visible sur cet ensemble
 *     isEmpty() == (contacts().size() == 0)
 *     forall p:Contact :
 *         contains(p) == contacts().contains(p)
 *     forall p:contacts() :
 *         (phoneNumbers(p) != null) && (phoneNumbers(p).size() > 0)
 *         firstPhoneNumber(p) != null
 *         firstPhoneNumber(p).equals(phoneNumbers(p).get(0))
 *         phoneNumbers(p) est non modifiable mais refl�te � chaque instant
 *           les num�ros de p dans l'annuaire (m�me lorsque ce dernier �volue)
 *         forall n:phoneNumbers(p) :
 *             phoneNumbers(p).lastIndexOf(n) == phoneNumbers(p).indexOf(n)
 * </pre>
 * @cons <pre>
 *     $DESC$ Un annuaire vide.
 *     $POST$ isEmpty() </pre>
 */
public interface PhoneBook {
    
    // REQUETES
    
    /**
     * Retourne le premier num�ro d'un contact.
     * @pre <pre>
     *     (p != null) && contains(p) </pre>
     */
    String firstPhoneNumber(Contact p);
    /**
     * Retourne une vue non mutable de la liste des num�ros de p.
     * Cette liste est partag�e avec l'annuaire de sorte que tout changement 
     *  dans l'annuaire est refl�t� sur cette liste.
     * On ne peut pas modifier cette liste.
     * @pre <pre>
     *     (p != null) && contains(p) </pre>
     */
    List<String> phoneNumbers(Contact p);
    /**
     * Retourne l'ensemble ordonn� des personnes contenues dans
     *  l'annuaire.
     * Cet ensemble est partag� avec l'annuaire de sorte que tout changement
     *  dans l'annuaire est refl�t� sur cet ensemble, et r�ciproquement, si l'on
     *  retire des �l�ments � cet ensemble l'annuaire est modifi�
     *  en cons�quence.
     * (Remarque : les ajouts sur cet ensemble sont impossibles.)
     */
    NavigableSet<Contact> contacts();
    /**
     * Retourne vrai ssi p est dans l'annuaire.
     * @pre <pre>
     *     p != null </pre>
     */
    boolean contains(Contact p);
    /**
     * Retourne vrai ssi l'annuaire est vide.
     */
    boolean isEmpty();

    // COMMANDES
    
    /**
     * Ajoute une nouvelle entr�e dans l'annuaire.
     * Si la liste des num�ros contient des doublons, ils seront �limin�s mais
     *  l'ordre des num�ros reste inchang�.
     * @pre <pre>
     *     (p != null) && !contains(p)
     *     (nums != null) && (nums.size() > 0) </pre>
     * @post <pre>
     *     contains(p)
     *     phoneNumbers(p) respecte l'ordre des �l�ments de nums et ne contient
     *      pas de doublon </pre>
     */
    void addEntry(Contact p, List<String> nums);
    /**
     * Ajoute un num�ro � la fin de la liste des num�ros d'un contact.
     * Si la personne n'existe pas on cr�e une nouvelle entr�e pour cette
     *  personne avec comme liste de num�ros associ�e la liste constitu�e
     *  du num�ro pass� en param�tre.
     * Si le num�ro est d�j� dans la liste de p, on ne fait rien.
     * @pre <pre>
     *     p != null
     *     (n != null) && !n.equals("") </pre>
     * @post <pre>
     *     contains(p)
     *     !old contains(p)
     *         ==> phoneNumbers(p).size() == 1
     *             firstPhoneNumber(p).equals(n)
     *     old contains(p)
     *         ==> Soit oldSize ::= old phoneNumbers(p).size()
     *             !old phoneNumbers(p).contains(n)
     *                 ==> phoneNumbers(p).size() == oldSize + 1
     *                     phoneNumbers(p).indexOf(n) == oldSize </pre>
     */
    void addPhoneNumber(Contact p, String n);
    /**
     * Supprime un contact de l'annuaire.
     * @pre <pre>
     *     (p != null) && contains(p) </pre>
     * @post <pre>
     *     !contains(p) </pre>
     */
    void removeEntry(Contact p);
    /**
     * Supprime un num�ro donn� pour un contact donn�.
     * Si ce num�ro est le seul num�ro pour la personne, la personne est
     *  retir�e de l'annuaire.
     * Si ce num�ro �tait le premier, l'ancien second devient le nouveau
     *  premier.
     * Ne fait rien si le num�ro n'est pas dans la liste des num�ros de p.
     * @pre <pre>
     *     (p != null) && contains(p)
     *     (n != null) && !n.equals("") </pre>
     * @post <pre>
     *     old phoneNumbers(p).contains(n) 
     *         ==> old phoneNumbers(p).size() == 1
     *                 ==> !contains(p)
     *             old phoneNumbers(p).size() > 1
     *                 ==> Soit oldSize ::= old phoneNumbers(p).size()
     *                     Soit oldIndex ::= old  phoneNumbers(p).indexOf(n)
     *                     phoneNumbers(p).size() == oldSize - 1
     *                     forall i:[oldIndex..phoneNumbers(p).size()[ :
     *                         phoneNumbers(p).get(i).equals(
     *                             old phoneNumbers(p).get(i + 1)
     *                         ) </pre>
     */
    void deletePhoneNumber(Contact p, String n);
    /**
     * R�initialise l'annuaire.
     * @post <pre>
     *     isEmpty() </pre>
     */
    void clear();
}
