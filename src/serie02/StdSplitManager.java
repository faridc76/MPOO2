package serie02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StdSplitManager implements SplitManager {
	private File file;
	private long[] size;

	public StdSplitManager(File fichier) {
		if (fichier == null) {
			throw new IllegalArgumentException();
		}
		file = fichier;
		if (getFileSize() == 0) {
			size = new long[0];
		} else {
			size = new long[1];
			size[0] = getFileSize();
		}
	}

	// REQUETES
	
	public String getFileName() {
		return file.getAbsolutePath();
	}
	
	public long getFileSize() {
		return file.length();
	}
	
	public String[] getSplitsNames() {
		String []tab = new String[size.length];
		for (int i = 0; i < size.length; i++) {
			tab[i] = getFileName() + "." + (i + 1);
		}
		
		return tab;
	}
	
	public long[] getSplitsSizes() {
		return size;
	}
	
	// COMMANDES
	public void setSplitsSizes(long newSize) {
		if ((newSize < 1) || ((getFileSize() / newSize) > Integer.MAX_VALUE)) {
			throw new IllegalArgumentException();
		}
		
		int nbFile = (new Long(getFileSize() / newSize)).intValue();
		long tailleEnPlus = getFileSize() - (nbFile * newSize);
		if (tailleEnPlus == 0) {
			size = new long[nbFile];
		
			for (int i = 0; i < nbFile; i++) {
				size[i] = newSize;
			}
		} else {
			size = new long[nbFile + 1];
		
			for (int i = 0; i < nbFile + 1; i++) {
				size[i] = newSize;
			}
			
			size[nbFile] = tailleEnPlus;
		}
	}
	
	public void setSplitsSizes(long[] newSizes) {
		if (newSizes == null || newSizes.length <= 0) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < newSizes.length; i++) {
			if (newSizes[i] < 1) {
				throw new IllegalArgumentException();
			}
		}
		long rest = getFileSize();
		size = new long[0];
		long[] tab;
		for (int i = 0; i < newSizes.length 
		&& (rest - newSizes[i]) >= 0; i++) {
			tab = size;
			size = new long[i + 1];
			for (int j = 0; j < tab.length; j++) {
				size[j] = tab[j];
			}
			size[i] = newSizes[i];
			rest = rest - newSizes[i];
		}
		if (rest != 0) {
			tab = size;
			size = new long[tab.length + 1];
			for (int j = 0; j < tab.length; j++) {
				size[j] = tab[j];
			}
			size[tab.length] = rest;
		}
		
	}

	public void setSplitsNumber(int number) {
		if (number < 1) {
			throw new IllegalArgumentException();
		}
		if (getFileSize() == 0) {
			size = new long[0];
		} else {
			long[] tab;
			if (number <= getFileSize()) {
				tab = new long[number];
			} else {
				tab = new long[(int) getFileSize()];
			}
			for (int i = 0; i < tab.length; i++) {
				tab[i] = getFileSize() / number;
			}
			for (int i = 0; i < getFileSize() % number; i++) {
				tab[i] = tab[i] + 1;
			}
			size = tab;
		}
	}
	public void split() throws IOException {
		FileInputStream fis = new FileInputStream(file);
		int lecture;
		long bitalire;
		String []nom = getSplitsNames();
		for (int i = 0; i < size.length; i++) {
			FileOutputStream fos = new FileOutputStream(new File(nom[i]));
			bitalire = 0;
			while (bitalire != size[i]) {
				lecture = fis.read();
				fos.write(lecture);
				bitalire = bitalire + 1;
			}
			fos.close();
		}
		fis.close();
	}
}
