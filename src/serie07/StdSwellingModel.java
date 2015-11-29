package serie07;

import java.awt.Dimension;
import java.util.Observable;

public class StdSwellingModel extends Observable implements SwellingModel {

	
	 // ATTRIBUTS
	
	private Dimension dimension;
	
	
	public StdSwellingModel() {
		dimension = MINIMAL_DIM;
	}
	// REQUETES
	
	public Dimension getDimension() {
		return dimension;
	}

	// COMMANDES
	
	public void inflate(double factor) {
		if (factor < 0) {
			throw new IllegalArgumentException();
		}
		double height;
		double width;
		height = Math.min(getDimension().getHeight() * (factor + 1), MAXIMAL_DIM.getHeight());
		width = Math.min(getDimension().getWidth() * (factor + 1), MAXIMAL_DIM.getWidth());
		getDimension().setSize(width, height);
		setChanged();
	}

	@Override
	public void deflate(double factor) {
		if ((factor < 0) || (factor > 1)) {
			throw new IllegalArgumentException();
		}
		double height;
		double width;
		height =  Math.max(getDimension().getHeight() * (1 - factor), MINIMAL_DIM.getHeight());
		width =  Math.max(getDimension().getWidth() * (1 - factor), MINIMAL_DIM.getWidth());
		getDimension().setSize(width, height);
		setChanged();

		
	}
}
