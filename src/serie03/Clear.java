package serie03;

import java.util.LinkedList;
import java.util.List;





public class Clear extends AbstractCommand {
	
	private final List<String> backup;
	private Text texte;
	
	
	public Clear(Text t) {
		super(t);
		texte = t;
		backup = new LinkedList<String>();
	}

	
	public List<String> getBackup() {
		return backup;
	}
	
	public Text getText() {
		return texte;
	}
	

	@Override
	public void doIt() {
		if (!super.canDo()) {
			throw new IllegalStateException();
		}
		getBackup().clear();
		String line;
		for (int i = 0; i != getText().getLinesNb(); i++) {
			line = getText().getLine(i + 1);
			getBackup().add(line);
		}
		getText().clear();
	}

	@Override
	public void undoIt() {
		if (!super.canUndo()) {
			throw new IllegalStateException();
		}
		getText().clear();
		String line;
		for (int i = 0; i != getBackup().size(); i++) {
			line = getBackup().get(i);
			getText().insertLine(i + 1, line);
		}
		getBackup().clear();
	}
}
