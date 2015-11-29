package serie09;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;

public class GraphicSpeedometer extends JComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1129762680514242072L;
	//ATTRIBUTS
	private static final int
    	// marge horizontale interne de part et d'autre du composant
	    MARGIN       = 40,
	    // épaisseur de la ligne horizontale graduée
	    THICK        =  3,
	    // demi-hauteur d'une graduation
	    MARK         =  5,
	    // largeur de la base du triangle pour la tête de la flèche
	    ARROW_BASE   = 20,
	    // épaisseur du corps de la flèche
	    ARROW_THICK  =  4,
	    // hauteur du corps de la flèche
	    ARROW_HEIGHT = 20,
	    // hauteur préférée d'un GraphicSpeedometer
	    PREFERRED_HEIGHT = 3 * (3 * MARK + ARROW_BASE / 2 + ARROW_HEIGHT);
	// facteur d'échelle pour l'espacement entre deux graduations
	private static final double ASPECT_RATIO = 1.25;
	// couleurs franches lorsque le moteur est allumé et grisées lorsqu'il est éteint
	private static final Color
	    BLUE        = Color.BLUE,
	    RED         = Color.RED,
	    GRAYED_BLUE = new Color(0, 0, 255, 50),
	    GRAYED_RED  = new Color(255, 0, 0, 50);
	// les vitesses affichées sont celles, entre 0 et model.getMaxSpeed(), qui sont les multiples de SPLIT_SIZE
	private static final int SPLIT_SIZE = 10;
	
	private SpeedometerModel model;
	//CONSTRUCTEURS
	public GraphicSpeedometer(SpeedometerModel model) {
		if (model == null) {
			throw new IllegalArgumentException();
		}
		this.model = model;
		int width = (int) (ASPECT_RATIO * ARROW_BASE 
				* (model.getMaxSpeed() / SPLIT_SIZE) + 2 * MARGIN);
		int height = 3 * (3 * MARK + ARROW_BASE / 2 + ARROW_HEIGHT);
		setPreferredSize(new Dimension(width, height));	
	}
	
	//REQUETES
	public SpeedometerModel getModel() {
		return model;
	}
	
	//COMMANDES
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		int h = getHeight() / 3;  
		int w = getWidth() - 2 * MARGIN;
		int xA = (int) (MARGIN + (w * model.getSpeed()) 
				/ model.getMaxSpeed() - ARROW_BASE / 2);
		int xC = (int) (MARGIN + (w * model.getSpeed()) 
				/ model.getMaxSpeed() + ARROW_BASE / 2);
		int xB =  (int) (MARGIN + ((this.getWidth() - 2 * MARGIN) 
				* model.getSpeed()) / model.getMaxSpeed());
		int yAC = h + THICK + 2 * MARK + ARROW_BASE / 2;
		int yB = h + THICK + 2 * MARK;
		int[] abscisses = new int[] {xA, xB, xC};
		int[] ordonnees = new int[] {yAC, yB, yAC};
		int nbPoint = ordonnees.length;
		if (model.isOn()) {
			g.setColor(RED);
		} else {
			g.setColor(GRAYED_RED);
		}
		g.fillPolygon(abscisses, ordonnees, nbPoint);
		int xP = xA + (ARROW_BASE - ARROW_THICK) / 2;
		int yP = yAC;
		g.fillRect(xP, yP, ARROW_THICK, ARROW_HEIGHT);
		if (model.isOn()) {
			g.setColor(BLUE);
		} else {
			g.setColor(GRAYED_BLUE);
		}
		g.fillRect(MARGIN, h, w, THICK);
		for (int i = 0; i <= model.getMaxSpeed() / SPLIT_SIZE; i++) {
			FontMetrics fontMetrics = g.getFontMetrics();
		    String splitSize = String.valueOf(i * SPLIT_SIZE);
		    int splitSizeWidth = fontMetrics.stringWidth(splitSize);
		    int xQ = (int) (MARGIN + i * w / (model.getMaxSpeed() 
		    		/ SPLIT_SIZE));
		    int yQ = (int) (h - MARK);
		    int xChar = (int) (MARGIN - splitSizeWidth / 2 + i * w 
		    		/ (model.getMaxSpeed() / SPLIT_SIZE));
		    int yChar = h - 2 * MARK;
		    g.drawLine(xQ, yQ, xQ, yQ + 2 * MARK + THICK);
			g.drawString(splitSize, xChar, yChar);
		}
	}
}
