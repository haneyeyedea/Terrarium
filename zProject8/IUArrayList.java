import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported. 
 * 
 * @author 
 *
 * @param <T> type to store
 */
/**
 * @author melodee
 *
 * @param <T>
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int NOT_FOUND = -1;
	
	private T[] array;
	private int rear;
	private int modCount;
	
	/** Creates an empty list with default initial capacity */
	public IUArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	/** 
	 * Creates an empty list with the given initial capacity
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int initialCapacity) {
		array = (T[])(new Object[initialCapacity]);
		rear = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		expandIfFull();
		shiftElementsDown(0);
		array[0] = element;
		rear++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		add(element);
	}

	@Override
	public void add(T element) {
		expandIfFull();
		array[rear] = element;
		rear++;
		modCount++;
		
	}

	@Override
	public void addAfter(T element, T target) {
		int index = indexOf(target);
		if (index < 0 || index >= rear) {throw new NoSuchElementException();}
		expandIfFull();
		shiftElementsDown(index);
		array[index +1] = element;
		rear++;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		checkIndexOutOfBounds(index);
		expandIfFull();
		shiftElementsDown(index);
		array[index] = element;
		rear++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		checkIfEmpty();
		
		T retVal = array[0];
		
		shiftElementsUp(0);
		rear--;
		array[rear] = null;
		modCount++;
		
		return retVal;
	}

	@Override
	public T removeLast() {
		checkIfEmpty();

		T retVal = array[rear - 1];
		array[rear - 1] = null;
		rear--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {
		int index = indexOf(element);
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}
		
		T retVal = array[index];
		
		
		//shift elements
		for (int i = index; i < rear -1; i++) {
			array[i] = array[i+1];
		}
		rear--;
		array[rear] = null;
		modCount++;
		
		return retVal;
	}

	@Override
	public T remove(int index) {
		//checkIndexOutOfBounds(index);
		if(index < 0 || index >= rear) {
			throw new IndexOutOfBoundsException();
		}
		
		T retVal = array[index];
		
		//shift elements
		for (int i = index; i < rear -1; i++) {
			array[i] = array[i+1];
		}
		rear--;
		array[rear] = null;
		modCount++;
		
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		rear--;
		checkIndexOutOfBounds(index);
		rear++;
		array[index] = element;
	}

	@Override
	public T get(int index) {
		rear--;
		checkIndexOutOfBounds(index);
		rear++;
		return array[index];
	}

	@Override
	public int indexOf(T element) {
		int index = NOT_FOUND;
		
		if (!isEmpty()) {
			int i = 0;
			while (index == NOT_FOUND && i < rear) {
				if (element.equals(array[i])) {
					index = i;
				} else {
					i++;
				}
			}
		}
		
		return index;
	}

	@Override
	public T first() {
		checkIfEmpty();
		return array[0];
	}

	@Override
	public T last() {
		checkIfEmpty();
		return array[rear - 1];
	}

	@Override
	public boolean contains(T target) {
		return (indexOf(target) != NOT_FOUND);
	}

	@Override
	public boolean isEmpty() {
		return (rear == 0);
	}

	@Override
	public int size() {
		return rear;
	}
	
	/**
	 * Double the length of the array if the last element is full
	 */
	private void expandIfFull() {
		if (array.length == rear) {
			array = Arrays.copyOf(array, array.length*2);
		}
	}

	/**
	 * Checks if the array is empty and throws no such element exception if it is
	 */
	private void checkIfEmpty() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
	}
	
	/**
	 * Checks if index is out of bounds and throws index out of bounds exception if it is
	 * 
	 * @param index index to check if in bounds
	 */
	private void checkIndexOutOfBounds(int index) {
		if(index < 0 || index > rear) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	/**
	 * Moves all elements down in the array until and including the end parameter
	 * @param end index of last shifted element
	 */
	private void shiftElementsDown(int end) {
		for (int i = rear; i > end; i--) {
			array[i] = array[i-1];
		}
	}
	
	/**
	 * Moves all elements up in the array starting at the start parameter
	 * @param start index of the first shifted element
	 */
	private void shiftElementsUp(int start) {
		for (int i = start; i < rear -1; i++) {
			array[i] = array[i+1];
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder("[");
		for (int i = 0; i < rear; i++) {
			str.append(array[i]);
			str.append(", ");
		}
		if(!isEmpty()) {
			str.delete(str.length()-2, str.length());
		}
		str.append("]");
		return str.toString();
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ALIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUArrayList */
	private class ALIterator implements Iterator<T> {
		private int nextIndex;
		private int iterModCount;
		boolean nextCheck;
		
		public ALIterator() {
			nextIndex = 0;
			iterModCount = modCount;
			nextCheck = false;
		}

		@Override
		public boolean hasNext() {
			checkConcurrentMod();
			return (array[nextIndex] != null);
		}

		@Override
		public T next() {
			checkConcurrentMod();
			if(!hasNext()) {throw new NoSuchElementException();}
			nextCheck = true;
			nextIndex++;
			return (array[nextIndex -1]);
		}
		
		@Override
		public void remove() {
			if(nextCheck == false) {throw new IllegalStateException();}
			checkConcurrentMod();
			shiftElementsUp(nextIndex);
			nextCheck =false;
		}
		
		private void checkConcurrentMod() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			modCount++;
			iterModCount++;
		}
	}
}
