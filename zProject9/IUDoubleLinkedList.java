import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Double-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented and
 * ListIterator is supported.
 * 
 * @author melodee
 * 
 * @param <T> type to store
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T>{

	private LinearNode<T> head;
	private LinearNode<T> tail;
	private int size;
	private int modCount;
	
	/**
	 * Creates an empty list
	 */
	public IUDoubleLinkedList(){
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
			head.setPrevious(newNode);
			if(size ==1) {
				tail = head;
			}
			head = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
LinearNode<T> newNode = new LinearNode<T>(element);
		
		if (isEmpty()) {
			head = tail = newNode;
		}
		else {
			tail.setNext(newNode);
			newNode.setPrevious(tail);
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void add(T element) {
		addToRear(element);
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
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		
		ListIterator<T> it = listIterator();
		for (int i = 0; i < index; i++) {
			it.next();
		}
		it.add(element);
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
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();
		tail = tail.getPrevious();
		if (size == 1) {
			head = tail = null;
		}
		else {
			tail.setNext(null);
		}
		size--;
		modCount++;
		
		return retVal;
	}

	@Override
	public T remove(T element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		int index = indexOf(element);
		if (index < 0) {throw new NoSuchElementException();}
		return remove(index);
	}

	@Override
	public T remove(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		ListIterator<T> it = listIterator();
		for (int i = 0; i < index; i++) {
			it.next();
		}
		T retVal = it.next();
		it.remove();
		return retVal;
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
		ListIterator<T> it = listIterator();
		for (int i = 0; i < index; i++) {
			it.next();
		}
		return it.next();
	}

	@Override
	public int indexOf(T element) {
		ListIterator<T> it = listIterator();
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
		ListIterator<T> it = listIterator();
		
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
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		return new DLLIterator(startingIndex);
	}
	
	/**
	 * List iterator class for double linked lists
	 * @author melodee
	 *
	 */
	private class DLLIterator implements ListIterator<T>{
		private LinearNode<T> nextNode;
		private int iterModCount;
		private int nextIndex;
		private int lastCall;
		
		/**
		 * Creates new iterator starting at the beginning
		 */
		public DLLIterator(){
			nextNode = head;
			iterModCount = modCount;
			nextIndex = 0;
			lastCall = 0;
		}
		
		/**
		 * Creates a new iterator starting at startingIndex
		 * @param startingIndex where new iterator starts
		 */
		public DLLIterator(int startingIndex){
			this();
			checkIndexOutOfBounds(startingIndex);
			for(int i = 0; i < startingIndex; i++){
				next();
			}
			lastCall = 0;
		}

		@Override
		public boolean hasNext() {
			checkConcurrentMod();
			return (nextNode != null);
		}

		@Override
		public T next() {
			checkConcurrentMod();
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			nextNode = nextNode.getNext();
			
			nextIndex++;
			lastCall = 1;
			return retVal;
		}

		@Override
		public boolean hasPrevious() {
			checkConcurrentMod();
			return (nextNode != head);
		}

		@Override
		public T previous() {
			checkConcurrentMod();
			if (!hasPrevious()){
				throw new NoSuchElementException();
			}
			if (nextNode == null) {
				nextNode = tail;
			}
			else{
				nextNode = nextNode.getPrevious();
			}
			nextIndex--;
			lastCall = -1;
			return nextNode.getElement();
		}

		@Override
		public int nextIndex() {
			checkConcurrentMod();
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			checkConcurrentMod();
			return (nextIndex -1);
		}

		@Override
		public void remove() {
			checkConcurrentMod();
			switch (lastCall) {
			case -1:
				//previous
				if(size == 0) {
					head = tail = nextNode = null;
				}
				else if(nextNode == head) {//removing first element
					nextNode = nextNode.getNext();//second element is now the first element
					if(nextNode != null) {
						nextNode.setPrevious(null);
					}
					head = nextNode;
				}
				else if(nextNode == tail) {//removing last element
					tail = nextNode.getPrevious();
					tail.setNext(null);
					nextNode = null;
				}
				else {//middle case
					LinearNode<T> prevNode = nextNode.getPrevious();
					prevNode.setNext(nextNode.getNext());
					nextNode = nextNode.getNext();
					nextNode.setPrevious(prevNode.getNext());
				}
				break;
			case 0:
				throw new IllegalStateException();
			case 1:
				//next
				if (size == 1) {
					head = tail = nextNode = null;
				}
				else if (nextNode == null) {//removing last element
					tail = tail.getPrevious();
					tail.setNext(null);
					nextNode = null;
				}
				else if (nextNode.getPrevious() == head) {//removing first element
					head = nextNode;
					head.setPrevious(null);
					if (size == 2) {
						head = tail;
					}
				}
				else {//middle case
					LinearNode<T> prevNode = nextNode.getPrevious().getPrevious();
					prevNode.setNext(nextNode);
					nextNode.setPrevious(prevNode);
				}
				nextIndex--;
				break;
			}
			size--;
			modCount++;
			iterModCount++;
			lastCall = 0;
		}

		@Override
		public void set(T e) {
			checkConcurrentMod();
			switch(lastCall) {
			case -1:
				//previous
				nextNode.setElement(e);
				break;
			case 0:
				throw new IllegalStateException();
			case 1:
				//next
				if (nextNode == null) {
					tail.setElement(e);
				}
				else {
					nextNode.getPrevious().setElement(e);
				}
				break;
			}
			modCount++;
			iterModCount++;
		}

		@Override
		public void add(T e) {
			checkConcurrentMod();
			LinearNode<T> newNode = new LinearNode<T>(e);
			
			if(size == 0) {
				head = tail = newNode;
			}
			else if(nextNode == null) {
				tail.setNext(newNode);
				newNode.setPrevious(tail);
				//newNode.setNext(null);
				tail = newNode;
			}
			else if(nextNode == head) {
				head.setPrevious(newNode);
				newNode.setNext(head);
				head = newNode;
			}
			else {
				newNode.setNext(nextNode);
				nextNode.getPrevious().setNext(newNode);
				newNode.setPrevious(nextNode.getPrevious());
				nextNode.setPrevious(newNode);
				
			}
			nextIndex++;
			size++;
			modCount++;
			iterModCount++;
			lastCall = 0;
		}
		
		/**
		 * Helper method that checks whether concurrent modifications are being made
		 * and throws exception if there is
		 * 
		 * @throws ConcurrentModificationException
		 * 
		 */
		private void checkConcurrentMod() {
			if (modCount != iterModCount) {throw new ConcurrentModificationException();}
		}
		
		/**
		 * Helper method that checks the index parameter to see if it is within the range
		 * of the list and throws an IndexOutOfBoundsException if it does
		 * @param index given index to check if it is within bounds
		 */
		private void checkIndexOutOfBounds(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
		}
	}
}
