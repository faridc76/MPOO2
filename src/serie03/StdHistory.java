package serie03;

	
import java.util.LinkedList;


public class StdHistory<E> implements History<E> {

	private LinkedList<E> liste;
	private int maxHeight;
	private int currentPosition;
	private int endPosition;
	
	public StdHistory(int maxH) {
		if (maxH <= 0) {
			throw new IllegalArgumentException();
		}
		maxHeight = maxH;
		currentPosition = 0;
		endPosition = 0;
		liste = new LinkedList<E>();
	}
	
	@Override
	public int getMaxHeight() {
		return maxHeight;
	}

	@Override
	public int getCurrentPosition() {
		return currentPosition;
	}

	@Override
	public E getCurrentElement() {
		if (getCurrentPosition() <= 0) {
			throw new IllegalStateException();
		}
		return liste.get(currentPosition - 1);
	}

	@Override
	public int getEndPosition() {
		return endPosition;
	}

	@Override
	public void add(E e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		if (currentPosition == maxHeight) {
			liste.pollFirst();
			liste.addLast(e);
			currentPosition = Math.min(currentPosition + 1, maxHeight);
			endPosition = currentPosition;
		}
		liste.addLast(e);
		currentPosition = Math.min(currentPosition + 1, maxHeight);
		endPosition = currentPosition;
	}



	@Override
	public void goForward() {
		if (currentPosition >= endPosition) {
			throw new IllegalStateException();
		}
		currentPosition = currentPosition + 1;
	}


	public void goBackward() {
		if (currentPosition <= 0) {
			throw new IllegalStateException();
		}
		currentPosition = currentPosition - 1;
	}

}
