package serie07;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Notre mod�le consiste en une Dimension que l'on fait cro�tre
 *  et d�cro�tre � volont�.
 * La Dimension ne peut �voluer en dehors de certaines limites.
 * @inv <pre>
 *     MINIMAL_DIM.getWidth() <= getDimension().getWidth()
 *     getDimension().getWidth() <= MAXIMAL_DIM.getWidth()
 *     MINIMAL_DIM.getHeight() <= getDimension().getHeight()
 *     getDimension().getHeight() <= MAXIMAL_DIM.getHeight() </pre>
 * @cons <pre>
 * $DESC$ un mod�le de taille MINIMAL_DIM
 * $POST$ getDimension().equals(MINIMAL_DIM) </pre>
 */
public interface SwellingModel extends ObservableModel {
    
    // ATTRIBUTS
    
    /**
     * Indique la dimension minimale du mod�le.
     */
    Dimension MINIMAL_DIM = new Dimension(200, 100);
    /**
     * Indique la dimension maximale du mod�le.
     */
    Dimension MAXIMAL_DIM = Toolkit.getDefaultToolkit().getScreenSize();

    // REQUETES
    
    /**
     * Indique la dimension du mod�le.
     */
    Dimension getDimension();
    
    // COMMANDES
    
    /**
     * Permet de faire cro�tre la dimension dans un rapport
     *  de <code>factor</code>.
     * @pre <pre>
     *     0 <= factor </pre>
     * @post <pre>
     *     getDimension().getWidth() == min(
     *         old getDimension().getWidth() * (1 + factor), 
     *         MAXIMAL_DIM.getWidth()
     *     )
     *     getDimension().getHeight() == min(
     *         old getDimension().getHeight() * (1 + factor), 
     *         MAXIMAL_DIM.getHeight()
     *     ) </pre>
     */
    void inflate(double factor);
    /**
     * Permet de faire d�cro�tre la dimension dans un rapport
     *  de <code>factor</code>.
     * @pre <pre>
     *     0 <= factor <= 1 </pre>
     * @post <pre>
     *     getDimension().getWidth() == max(
     *         old getDimension().getWidth() * (1 - factor), 
     *         MINIMAL_DIM.getWidth()
     *     )
     *     getDimension().getHeight() == max(
     *         old getDimension().getHeight() * (1 - factor), 
     *         MINIMAL_DIM.getHeight()
     *     ) </pre>
     */
    void deflate(double factor);
}
