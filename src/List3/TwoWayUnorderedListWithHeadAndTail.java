package List3;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class TwoWayUnorderedListWithHeadAndTail<E> implements IList<E>{
	
	private class Element{
		public Element(E e) {
			this.object = e;
		}
		E object;
		Element next=null;
		Element prev=null;
	}
	
	Element head;
	Element tail;
	int size;
	
	private class InnerIterator implements Iterator<E>{
		Element pos;
		
		public InnerIterator() {
            pos = head;
		}
		@Override
		public boolean hasNext() {
			return pos != null;
		}
		
		@Override
		public E next() {
			if(pos == null) {
                throw new java.util.NoSuchElementException();
            }
            E e = pos.object;
            pos = pos.next;
            return e;
		}
	}
	
	private class InnerListIterator implements ListIterator<E>{
		int index;
        Element nextElement;
        Element previousElement;

        public InnerListIterator() {
            index = 0;
            nextElement = head;
            previousElement = null;
        }

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasNext() {
            return nextElement != null;
		}

		@Override
		public boolean hasPrevious() {
			return previousElement != null;
		}

		@Override
		public E next() {
			if(!hasNext()) {
                throw new NoSuchElementException();
            }
            E e = nextElement.object;
            previousElement = nextElement;
            nextElement = nextElement.next;
            index++;
            return e;
		}

		@Override
		public int nextIndex() {
            return index++;
		}

		@Override
		public E previous() {
			if(!hasPrevious()) {
                throw new NoSuchElementException();
            }
            E e = previousElement.object;
            nextElement = previousElement;
            previousElement = previousElement.prev;
            index--;
            return e;
		}

		@Override
		public int previousIndex() {
            return index--;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
			
		}

		@Override
		public void set(E e) {
			previousElement.next = new Element(e);
		}
	}
	
	public TwoWayUnorderedListWithHeadAndTail() {
		head=null;
		tail=null;
	}
	
	@Override
	public boolean add(E e) {
		Element newElement = new Element(e);
        if(head == null) {
            head = newElement;
            tail = newElement;
        } else {
            tail.next = newElement;
            newElement.prev = tail;
            tail = newElement;
        }

        size++;
        return true;
	}

	@Override
	public void add(int index, E element) {
		if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Element newElement = new Element(element);
        if(index == 0) {
            if(head == null) {
                head = newElement;
                tail = newElement;
            } else {
                newElement.next = head;
                head.prev = newElement;
                head = newElement;
            }
        } else if(index == size) {
            newElement.prev = tail;
            tail.next = newElement;
            tail = newElement;
        } else {
            Element current = head;
            for(int i = 0; i < index; i++) {
                current = current.next;
            }
            newElement.next = current;
            newElement.prev = current.prev;
            current.prev.next = newElement;
            current.prev = newElement;
        }
        size++;
	}

	@Override
	public void clear() {
        head = null;
        tail = null;
        size = 0;
	}

	@Override
	public boolean contains(E element) {
		Element current = head;
        //może foreach?
        while(current != element) {
            current = current.next;
            if(current == element) {
                return true;
            }
        }
        return false;
	}

	@Override
	public E get(int index) {
		Element current = head;
        for(int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.object;
	}

	@Override
	public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
		Element current = head;
        for(int i = 0; i < index; i++) {
            current = current.next;
        }
		E result = current.object;
        current.object = element;
        return result;
	}

	@Override
	public int indexOf(E element) {
		Element current = head;
        for(int i = 0; i < size; i++) {
            if(current.object.equals(element)) {
                return i;
            }
            current = current.next;
        }
		return -1;
	}

	@Override
	public boolean isEmpty() {
		if(head == null && tail == null) {
            return true;
        }
        return false;
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
	public E remove(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if(index == 0) {
            E result = head.object;
            head = head.next;
            head.prev = null;
            size--;
            return result;
        } else if(index == size-1) {
            E result = tail.object;
            tail = tail.prev;
            tail.next = null;
            size--;
            return result;
        }
        Element current = head;
        for(int i = 0; i < index; i++) {
            current = current.next;
        }
        E result = current.object;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return result;
	}

	@Override
	public boolean remove(E e) {
        Element current = head;
        for(int i = 0; i < size; i++) {
            if(current.object.equals(e)) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
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
	public String toStringReverse() {
		ListIterator<E> iter=new InnerListIterator();
		while(iter.hasNext())
			iter.next();
		String retStr="";
		while(iter.hasPrevious()) {
            retStr += "\n" + iter.previous();
        }
		return retStr;
	}

	public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {
	    if(other == this) {
            return;
        }
        if(other.head != null) {
            if(head == null) {
                head = other.head;
                tail = other.tail;
            } else {
                tail.next = other.head;
                other.head.prev = tail;
                tail = other.tail;
            }
            size += other.size;
        }

        other.clear();
	}
}

