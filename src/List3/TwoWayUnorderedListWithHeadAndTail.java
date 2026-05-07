package List3;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class TwoWayUnorderedListWithHeadAndTail<E extends Comparable<E>> implements IList<E> {
	private class Element{
        E object;
        Element next=null;
        Element prev=null;

        public Element(E e) {
            this.object=e;
        }
	}
	
	Element head;
	Element tail;
	int size = 0;
	
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
                throw new NoSuchElementException();
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
            throw new UnsupportedOperationException();
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
            throw new UnsupportedOperationException();
        }

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
			
		}

		@Override
		public void set(E e) {
			throw new UnsupportedOperationException();
		}
	}
	
	public TwoWayUnorderedListWithHeadAndTail() {
		head=null;
		tail=null;
	}
	
	@Override
	public boolean add(E e) {
		Element newElement = new Element(e);
        Element current = head;
        if(current == null) {
            head = newElement;
            tail = newElement;
            size++;
            return true;
        }
        if(newElement.object.compareTo(tail.object) >= 0) {
            tail.next = newElement;
            newElement.prev = tail;
            tail = newElement;
            size++;
            return true;
        }
        while(current != null) {
            if(newElement.object.compareTo(current.object) >= 0 && newElement.object.compareTo(current.next.object) < 0) {
                newElement.next = current.next;
                newElement.prev = current;
                current.next.prev = newElement;
                current.next = newElement;
                size++;
                return true;
            }
            current = current.next;
        }
        size++;
        return true;
	}

	@Override
	public void add(int index, E element) {
		if(index < 0 || index > size) {
            throw new NoSuchElementException();
        }
        Element newElement = new Element(element);
        if(index == size) {
            add(element);
            return;
        }
        if(index == 0) {
            if(head == null) {
                head = newElement;
                tail = newElement;
            } else {
                newElement.next = head;
                head.prev = newElement;
                head = newElement;
            }
        } else {
            Element current = getElement(index);
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
        int index = indexOf(element);
        return index != -1;
    }

	@Override
	public E get(int index) {
        if(checkIndex(index)) {
             throw new NoSuchElementException();
        }
		Element current = getElement(index);
        return current.object;
	}

	@Override
	public E set(int index, E element) {
        if(checkIndex(index)) {
            throw new NoSuchElementException();
        }
		Element current = getElement(index);
		E result = current.object;
        current.object = element;
        return result;
	}

	@Override
	public int indexOf(E element) {
		Element current = head;
        int index = 0;
        while (current != null) {
            if (current.object.equals(element)) {
                return index;
            }
            current = current.next;
            index++;
        }
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
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
        if(checkIndex(index)) {
            throw new NoSuchElementException();
        }
        Element toRemove = getElement(index);
        E value = toRemove.object;

        if(toRemove == head) {
            head = toRemove.next;
        } else {
            toRemove.prev.next = toRemove.next;
        }

        if(toRemove == tail) {
            tail = toRemove.prev;
        } else {
            toRemove.next.prev = toRemove.prev;
        }

        size--;
        return value;
    }

    @Override
    public boolean remove(E e) {
        Element current = head;
        int index = 0;
        while(current != null) {
            if(current.object.equals(e)) {
                remove(index);
                return true;
            }
            current = current.next;
            index++;
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

    public boolean checkIndex(int index) {
        return index < 0 || index >= size;
    }

    public Element getElement(int index) {
        Element current = head;
        for(int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}

