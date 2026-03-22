package List2;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<E> implements IList<E>{
	
	private class Element{
		public Element(E e) {
			this.object=e;
		}
		E object;
		Element next=null;
	}
	
	Element sentinel;
    int size;
	
	private class InnerIterator implements Iterator<E>{
		Element current;

		public InnerIterator() {
			current = sentinel;
		}
		@Override
		public boolean hasNext() {
			return current.next != null;
		}
		
		@Override
		public E next() {
			current = current.next;
			return current.object;
		}
	}
	
	public OneWayLinkedList() {
		sentinel = new Element(null);
		size = 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e) {
		Element current = sentinel;
        while(current.next != null) {
            current = current.next;
        }
        current.next = new Element(e);
        size++;
        return true;
	}

	@Override
	public void add(int index, E element) {
        if(index < 0 || index > size) {
            throw new NoSuchElementException();
        }
        Element newElement = new Element(element);
        Element current = getElement(sentinel, index);
        newElement.next = current.next;
        current.next = newElement;
        size++;
	}

	@Override
	public void clear() {
		sentinel.next = null;
        size = 0;
	}

	@Override
	public boolean contains(E element) {
        return indexOf(element) != -1;
	}

	@Override
	public E get(int index)  {
		if(checkIfIndexRight(index)) {
            throw new NoSuchElementException();
        }
        Element current = getElement(sentinel.next, index);
        return current.object;
	}

	@Override
	public E set(int index, E element) throws NoSuchElementException {
		if(checkIfIndexRight(index)) {
            throw new NoSuchElementException();
        }
        Element current = getElement(sentinel.next, index);
        E oldValue = current.object;
        current.object = element;
        return oldValue;
	}

	@Override
	public int indexOf(E element) {
		int index = 0;
        Element current = sentinel.next;
        while(current != null) {
            if(current.object.equals(element)) {
                return index;
            }
            index++;
            current = current.next;
        }
        return -1;
	}

	@Override
	public boolean isEmpty() {
        return sentinel.next == null;
	}

	@Override
	public E remove(int index)  {
		if(checkIfIndexRight(index)) {
            throw new NoSuchElementException();
        }
        Element previous = getElement(sentinel, index);
        E oldValue = previous.next.object;
        previous.next = previous.next.next;
        size--;
        return oldValue;
	}

	@Override
	public boolean remove(E e) {
		Element current = sentinel;
        while(current.next != null) {
            if(current.next.object.equals(e)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
	}

	@Override
	public int size() {
		return size;
	}

    public boolean checkIfIndexRight(int index) {
        return index < 0 || index >= size;
    }

    private Element getElement(Element start, int index) {
        for(int i = 0; i < index; i++) {
            start = start.next;
        }
        return start;
    }
	
}

