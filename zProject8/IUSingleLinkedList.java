import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author melodee
 * 
 * @param <T> type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private LinearNode<T> head, tail;
	private int size;
	private int modCount;
	
	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);

		if (isEmpty()) {
			head = tail = newNode;
		}
		else {
			newNode.setNext(head);
			head = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		add(element);
	}

	@Override
	public void add(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		
		if (isEmpty()) {
			head = tail = newNode;
		}
		else {
			LinearNode<T> current = head;
			for (int i = 0; i < size -1; i++) {
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void addAfter(T element, T target) {
		int findIndex = indexOf(target);
		if (findIndex < 0) {
			throw new NoSuchElementException();
		}
		add(findIndex +1, element);
	}

	@Override
	public void add(int index, T element) {
		//use iterator
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> newNode = new LinearNode<T>(element);

		if (isEmpty()) {
			head = tail = newNode;
		}
		else if (index == 0) {//new head
			newNode.setNext(head.getNext());
			head = newNode;
		}
		else if (index == size)  {//new tail
			tail.setNext(newNode);
			tail = newNode;
		}
		else {//for middle of the list
			LinearNode<T> current = head;
			for (int i = 0; i < index-1; i++) {
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
		}
		size++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = head.getElement();
		head = head.getNext();
		if (size ==1) {
			tail = null;
		}
		size--;
		modCount++;
		
		return retVal;
	}

	@Override
	public T removeLast() {
		//create iterator
		//advance to the end where next is tail
		if (isEmpty()) {throw new NoSuchElementException();}
		Iterator<T> it = iterator();
		T retVal = null;
		while (it.hasNext()) {
			retVal = it.next();
		}
		it.remove();
		return retVal;
	}

	@Override
	public T remove(T element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		boolean found = false;
		LinearNode<T> previous = null;
		LinearNode<T> current = head;
		
		while (current != null && !found) {
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}
		
		if (!found) {
			throw new NoSuchElementException();
		}
		
		if (size() == 1) { //only node
			head = tail = null;
		} else if (current == head) { //first node
			head = current.getNext();
		} else if (current == tail) { //last node
			tail = previous;
			tail.setNext(null);
		} else { //somewhere in the middle
			previous.setNext(current.getNext());
		}
		
		size--;
		modCount++;
		
		return current.getElement();
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> current = head;
		LinearNode<T> previous = null;
		for (int i = 0; i < index; i++) {
			previous = current;
			current = current.getNext();
		}
		if (size == 1) { //only node
			head = tail = null;
		} else if (current == head) { //first node
			head = current.getNext();
		} else if (current == tail) { //last node
			tail = previous;
			tail.setNext(null);
		} else { //somewhere in the middle
			previous.setNext(current.getNext());
		}
		
		size--;
		modCount++;
		
		return current.getElement();
	}

	@Override
	public void set(int index, T element) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> targetNode = head;
		for (int i = 0; i < index; i++) {
			targetNode = targetNode.getNext();
		}
		targetNode.setElement(element);
		modCount++; 
		
	}

	@Override
	public T get(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		//create iterator
		Iterator<T> it = iterator();
		//call next index amount of times
		for (int i = 0; i < index; i++) {
			it.next();
		}
		//last returned is value at that index
		return it.next();
	}

	@Override
	public int indexOf(T element) {
		Iterator<T> it = iterator();
		int index = -1;
		int candidateIndex = 0;
		while (it.hasNext() && index < 0) {
			if (it.next().equals(element)) {
				index = candidateIndex;
			}
			candidateIndex++;
		}
		return index;
	}

	@Override
	public T first() {
		if (isEmpty()){
			throw new NoSuchElementException();
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if (isEmpty()){
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		return (indexOf(target) > -1);
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public int size() {
		return size;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder("[");
		Iterator<T> it = iterator();
		
		while (it.hasNext()) {
			str.append(it.next());
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
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> {
		private LinearNode<T> nextNode;
		private int iterModCount;
		private boolean canRemove;
		
		/** Creates a new iterator for the list */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
			canRemove = false;
		}

		@Override
		public boolean hasNext() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != null);
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			nextNode = nextNode.getNext();

			canRemove = true;
			return retVal;
		}
		
		@Override
		public void remove() {
			if (!canRemove){
				throw new IllegalStateException();
			}
			canRemove = false;
			if (size() == 1) {
				head = tail = null;
			}
			else {
				if (nextNode == head.getNext()) {//head
					head = head.getNext();
				}
				else {
					LinearNode<T> cur = head;
					while (cur.getNext().getNext() != nextNode) {
						cur = cur.getNext();
					}
					if (nextNode == null) { // removing tail
						tail = cur;
					}
					cur.setNext(nextNode);
				}	
			}
			modCount++;
			iterModCount++;
			size--;
		}
	}
}
