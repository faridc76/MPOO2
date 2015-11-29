package serie05;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import serie04.Civ;
import serie04.Contact;
import serie04.StdContact;
import serie04.StdPhoneBook;

public class StdPersistentPhoneBook extends StdPhoneBook 
									implements PersistentPhoneBook {
	
	//ATTRIBUTS
	private File getFile;
	
	//CONSTRUCTEURS
	public StdPersistentPhoneBook(File file) {
		getFile = file;
	}
	
	public StdPersistentPhoneBook() {
		getFile = null;
	}

	//REQUETES
	public File getFile() {
		return getFile;
	}


	//COMMANDES
	public void setFile(File file) {
		if (file == null) {
			throw new IllegalArgumentException("file != null");
		}
		getFile = file;
	}

	public void load() throws IOException, BadSyntaxException {
		if (getFile == null) {
			throw new IllegalStateException("getFile != null");
		}
		clear();
		BufferedReader buff = new BufferedReader(new FileReader(getFile));
		String line;
		String[] getParam;
		String nom;
		String prenom;
		String civility;
		List<String> phoneNumbers = new LinkedList<String>();
		line = buff.readLine();
		while (line != null) {
			LINE_RECOGNIZER.reset(line);
			if (!LINE_RECOGNIZER.matches()) {
				clear();
				throw new BadSyntaxException();
			}
			getParam = line.split(":");
			civility = getParam[0].trim();
			nom = getParam[1].trim();
			prenom = getParam[2].trim();
			getParam = getParam[3].trim().split(",");
			for (int i = 0; i < getParam.length; i++) {
				phoneNumbers.add(getParam[i].trim());
			}
			int c = new Integer(civility);
			StdContact contact = new StdContact(Civ.values()[c], nom, prenom);
			addEntry(contact, phoneNumbers);
			phoneNumbers.clear();
			line = buff.readLine();
		}
		buff.close();
	}

	public void save() throws IOException {
		if (getFile() == null) {
			throw new IllegalStateException("getFile != null");
		}
		BufferedWriter buff = new BufferedWriter(new FileWriter(getFile));
		String line;
		Iterator<Contact> i = contacts().iterator();
		while (i.hasNext()) {
			Contact c;
			c = i.next();
			Iterator<String> i2 = phoneNumbers(c).iterator();
			line = (c.getCivility().ordinal() + ":" 
					+ c.getLastName() + ":" + c.getFirstName());
			line = line + (":" + i2.next());
			while (i2.hasNext()) {
				line = line + ("," + i2.next());
			}
			buff.write(line);
			buff.newLine();
			/**
			*  Autre façon
			*  
			*List<String> phoneNumbers = new LinkedList<String>();
			*line = (c.getCivility().ordinal() + ":" 
			*		+ c.getLastName() + ":" + c.getFirstName() + ":");
			*phoneNumbers = phoneNumbers(c);
			*for (int j = 0; j < phoneNumbers.size() - 1; j++) {
			*	line = line + (phoneNumbers.get(j) + ",");
			*}	
			*line = line + phoneNumbers.get(phoneNumbers.size() - 1);
			*buff.write(line);
			*buff.newLine();
			*/
		}
		buff.close();
	}

}
