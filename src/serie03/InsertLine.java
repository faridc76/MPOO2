package serie03;

public class InsertLine extends AbstractCommand {

	private final int index;
    private String line;
    private Text texte;

	
	
	public InsertLine(Text t, int numLine, String s) {
		super(t);
		if ((numLine < 1) || (s == null)) {
            throw new IllegalArgumentException();
		}
		index = numLine;
		line = s;
		texte = t;
	}
	public int getIndex() {
		return index;
	}
	
	public String getLine() {
		return line;
	}
	
	public Text getText() {
		return texte;
	}
	
	@Override
	void doIt() {
		if (!super.canDo()) {
			throw new IllegalStateException();
		}
		getText().insertLine(index, line);
	}

	@Override
	void undoIt() {
		if (!super.canUndo()) {
			throw new IllegalStateException();
		}
		getText().deleteLine(index);

	}

}
