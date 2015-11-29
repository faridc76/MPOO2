package serie03;

public class StdEditor implements Editor {

	//ATTRIBUTS
	private int nbOfPossibleRedo;
	private int historySize;
	private Text texte;
	private History<Command> history;
	
	//CONSTRUCTEURS
	public StdEditor() {
		nbOfPossibleRedo = 0;
		historySize = DEFAULT_HISTORY_SIZE;
		texte = new StdText();
		history = new StdHistory<Command>(historySize);
	}
	
	public StdEditor(int historyS) {
		if (historyS <= 0) {
			throw new IllegalArgumentException();
		}
		nbOfPossibleRedo = 0;
		historySize = historyS;
		texte = new StdText();
		history = new StdHistory<Command>(historySize);
	}
	
	//REQUETES
	public int getTextLinesNb() {
		return texte.getLinesNb();
	}

	public String getTextContent() {
		return texte.getContent();
	}

	public int getHistorySize() {
		return historySize;
	}

	public int nbOfPossibleUndo() {
		return history.getCurrentPosition();
	}

	public int nbOfPossibleRedo() {
		return nbOfPossibleRedo;
	}

	
	//COMMANDES
	public void clear() {
		Command c = new Clear(texte);
		c.act();
		history.add(c);
	}

	public void insertLine(int numLine, String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		if ((1 > numLine) || (numLine > texte.getLinesNb() + 1)) {
			throw new IllegalArgumentException();
		}
		Command c = new InsertLine(texte, numLine, s);
		c.act();
		history.add(c);
	}

	public void deleteLine(int numLine) {
		if ((1 > numLine) || (numLine > texte.getLinesNb())) {
			throw new IllegalArgumentException();
		}
		Command c = new DeleteLine(texte, numLine);
		c.act();
		history.add(c);
	}

	public void redo() {
		if (nbOfPossibleRedo <= 0) {
			throw new IllegalArgumentException();
		}
		Command c = history.getCurrentElement();
		c.act();
		history.goForward();
		nbOfPossibleRedo = nbOfPossibleRedo - 1;
		
	}

	public void undo() {
		if (history.getCurrentPosition() <= 0) {
			throw new IllegalArgumentException();
		}
		Command c = history.getCurrentElement();
		c.act();
		history.goBackward();
		nbOfPossibleRedo = nbOfPossibleRedo + 1;
	}

}