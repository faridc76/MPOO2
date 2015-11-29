package serie07;

import java.util.Observable;
import java.util.Random;

public class StdSuspenseModel extends Observable implements SuspenseModel {

	public int maxNumber;
	public int drawsCount;
	public int proposalsCount;
	public int drawNumber;
	public int proposalNumber;
	public Random alea;
	
	
	
	
	public StdSuspenseModel(int max) {
		if (max < 2) {
			throw new IllegalArgumentException();
		}
		maxNumber = max;
		drawsCount = 0;
		proposalsCount = 0;
	}
		
	// REQUETES	
	
	public int getDrawsCount() {
		return drawsCount;
	}


	public int getProposalsCount() {
		return proposalsCount;
	}

	
	public int getMaxNumber() {
		return maxNumber;
	}


	public int getDrawNumber() {
		if (drawsCount <= 0) {
			throw new IllegalStateException();
		}
		return drawNumber;
	}


	public int getProposalNumber() {
		if (proposalsCount <= 0) {
			throw new IllegalStateException();
		}
		return proposalNumber;
	}


	public boolean sameNumbers() {
		if ((proposalsCount <= 0) || (drawsCount <= 0)) {
			throw new IllegalStateException();
		}
		return (getProposalNumber() == getDrawNumber());
	}

	 // COMMANDES

	public void drawNumber() {
		drawsCount = getDrawsCount() + 1;
		alea = new Random();
		drawNumber = alea.nextInt(maxNumber) + 1;
		setChanged();
	}


	public void proposeNumber(int number) {
		if ((number < 1) || (number > maxNumber)) {
			throw new IllegalArgumentException();
		}
		proposalsCount = getProposalsCount() + 1;
		proposalNumber = number;
		setChanged();
	}


	public void reinit() {
		drawsCount = 0;
	    proposalsCount = 0;
	    setChanged();
	}

}
