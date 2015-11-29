package serie10;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class StdSplitterModel extends Observable implements SplitterModel {

	//ATTRIBUTS
	private SplitManager manager;
	private Observer observer;
	
	//CONSTRUCTEURS
	public StdSplitterModel() {
		manager = null;
		observer = null;
		
	}
	
	public SplitManager getSplitManager() {
		return manager;
	}

	public int getFragmentNb() {
		return (int) Math.min(MAX_FRAGMENT_NB, Math.ceil(
				getSplitManager().getFileSize() / MIN_FRAGMENT_SIZE));
	}


	public String describeSplit() {
		if (manager == null) {
			return "";
		} else {
			String s = "Taille totale du fichier : " 
				    + manager.getFileSize() + " octets"
					+ "\n\n"
					+ "Description des fragments du fichier : \n";
			String t = "";
			for (int i = 0; i < manager.getSplitsNames().length; i++) {
				t = t + manager.getSplitsNames()[i] + " : " 
				      + manager.getSplitsSizes()[i] + " octets\n";
			}
			String r = "\n Taille moyenne d'un fragment : " 
				       + manager.getFileSize() 
				       / manager.getSplitsSizes().length 
				       + " octets";
			return s + t + r;
		}
	}


	public Observer getSplitManagerObserver() {
		return observer;
	}

	//COMMANDES
	public void createSplitManager(File f) {
		if (f == null) {
			throw new IllegalArgumentException();
		}
		if (getSplitManager() != null) {
			getSplitManager().deleteObservers();
		}
		if (f.exists() && f.canRead()) {
			manager = new StdSplitManager(f);
			if (getSplitManagerObserver() != null) {
				getSplitManager().addObserver(getSplitManagerObserver());
			}
		} else {
			manager = null;
		}
		setChanged();
	}

	public void defineSplitManagerObserver(Observer obs) {
		observer = obs;
		setChanged();
	}

}
