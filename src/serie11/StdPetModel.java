package serie11;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class StdPetModel extends Observable implements PetModel {
	// ATTRIBUTS
	private Document doc;
	private File fichier;
	private boolean synchro;
	private State etat;
	
	// CONSTRUCTEURS
	public StdPetModel() {
		doc = null;
		fichier = null;
		synchro = false;
		etat = State.NOF_NOD;
		setChanged();
	}
	
	// REQUETES
	public Document getDocument() {
		return doc;
	}

	public File getFile() {
		return fichier;
	}

	public State getState() {
		return etat;
	}

	public boolean isSynchronized() {
		return synchro;
	}

	// COMMANDES
	
	public void createEmptyDoc() {
		doc = new PlainDocument();
		synchro = false;
		switch (etat) {
			case NOF_NOD : etat = State.NOF_DOC; break;
			case FIL_NOD : etat = State.FIL_DOC; break;
			default : 
		}
		createController();
		setChanged();
	}

	public void removeDocument() {
		doc = null;
		synchro = false;
		switch (etat) {
			case NOF_DOC : etat = State.NOF_NOD; break;
			case FIL_DOC : etat = State.FIL_NOD; break;
			default : 
		}
		setChanged();
	}

	public void removeFile() {
		fichier = null;
		synchro = false;
		switch (etat) {
			case FIL_NOD : etat = State.NOF_NOD; break;
			case FIL_DOC : etat = State.NOF_DOC; break;
			default : 
		}
		setChanged();
	}
	
	public void load() throws IOException, BadLocationException {
		if (etat != State.FIL_DOC) {
			throw new IllegalStateException();
		}
		FileReader fr = new FileReader(fichier);
		BufferedReader br = new BufferedReader(fr);
		doc.remove(0, doc.getLength());
		int pos = 0;
		String line = "";
		while (line != null) {
			line = br.readLine();
			if (line != null) {
				doc.insertString(pos, line + "\n", null);
				pos += line.length() + 1;
			}			
		}
		doc.remove(doc.getLength() - 1, 1);
		br.close();
		synchro = true;
		setChanged();
	}


	public void save() throws IOException, BadLocationException {
		if (etat != State.FIL_DOC) {
			throw new IllegalStateException();
		}
		FileWriter fw = new FileWriter(fichier);
		BufferedWriter bw = new BufferedWriter(fw);
		String text = doc.getText(0, doc.getLength());
		String[] texts = text.split("\n");
		String line = "";
		for (int i = 0; i < texts.length; i++) {
			line = texts[i];
			bw.write(line);
			bw.newLine();
		}
		bw.close();
		synchro = true;
		setChanged();
	}

	public void setFile(File f) {
		if (f == null || !f.isFile()) {
			throw new IllegalArgumentException();
		}
		fichier = f;
		synchro = false;
		switch (etat) {
			case NOF_DOC : etat = State.FIL_DOC; break;
			case NOF_NOD : etat = State.FIL_NOD; break;
			default : 
		}
		setChanged();
	}
	
	// OUTILS
	private void createController() {
		doc.addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				synchro = false;
				notifyObservers();
				setChanged();
			}

			public void removeUpdate(DocumentEvent e) {
				synchro = false;
				notifyObservers();
				setChanged();
			}

			public void changedUpdate(DocumentEvent e) {
				// Non utilisé.
			}			
		});
	}

}
