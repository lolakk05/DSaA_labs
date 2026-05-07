package List4;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E>{

	private class Element{
		public Element(E e) {
			this.object = e;
		}
		public Element(E e, Element next, Element prev) {
			this.object = e;
            this.next = next;
            this.prev = prev;
		}
		// add element e after this
		public void addAfter(Element elem) {
			elem.next = this.next;
            elem.prev = this;
            this.next.prev = elem;
            this.next = elem;
		}
		// assert it is NOT a sentinel
		public void remove() {
			if(this == sentinel) {
                return;
            }
            this.prev.next = this.next;
            this.next.prev = this.prev;
		}
		E object;
		Element next=null;
		Element prev=null;
        Element jump=null;
	}


	Element sentinel;
	int size;

	private class InnerIterator implements Iterator<E>{
		Element current;
		public InnerIterator() {
			this.current = sentinel.next;
		}
		@Override
		public boolean hasNext() {
			return current != sentinel;
		}

		@Override
		public E next() {
			if(!hasNext()) {
                throw new NoSuchElementException();
            }
            E e = current.object;
            current = current.next;
			return e;
		}
	}

	private class InnerListIterator implements ListIterator<E>{
		Element current;
		public InnerListIterator() {
			this.current = sentinel.next;
		}
		@Override
		public boolean hasNext() {
            return current != sentinel;
		}

		@Override
		public E next() {
			if(!hasNext()) {
                throw new NoSuchElementException();
            }
            E e = current.object;
            current = current.next;
			return e;
		}
		@Override
		public void add(E arg0) {
			throw new UnsupportedOperationException();
		}
		@Override
		public boolean hasPrevious() {
			return current.prev != sentinel;
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
            current = current.prev;
            return current.object;
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
		public void set(E arg0) {
			throw new UnsupportedOperationException();
		}
	}
	public TwoWayCycledOrderedListWithSentinel() {
		sentinel = new Element(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
	}

	//@SuppressWarnings("unchecked")
	@Override
	public boolean add(E e) {
		Comparable<E> comparable = (Comparable<E>) e;
        Element current = sentinel.next;

        while(current != sentinel) {
            if(comparable.compareTo(current.object) >= 0) {
                Element jump = current.jump;
                if(comparable.compareTo(jump.object) <= 0) {
                    current = current.next;
                } else {
                    current = current.jump;
                }
            }
        }

        Element newElement = new Element(e);
        current.prev.addAfter(newElement);
        size++;
        updateJumpElement();
        return true;
	}

	private Element getElement(int index) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        };
		Element current = sentinel.next;
        for(int i = 0; i < index; i++) {
            current = current.next;
        };
		return current;
	}

    private void updateJumpElement() {
        int k = (int)Math.sqrt(size);
        int pos = -1;
        InnerIterator innerIterator = new InnerIterator();
        while (innerIterator.hasNext()) {
            int index = ((pos+k) % size) + 1;
            innerIterator.current.jump = getElement(index);
            innerIterator.next();
            pos++;
        }
    }

	private Element getElement(E obj) {
		Element current = sentinel.next;
        while(!current.object.equals(obj)) {
            current = current.next;
        }
		return current;
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
	}

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

	@Override
	public E get(int index) {
		checkIndex(index);
        Element current = getElement(index);
		return current.object;
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(E element) {
        int index = 0;
		Element current = sentinel.next;
        while(current != sentinel && !current.object.equals(element)) {
            current = current.next;
            index++;
        }
        if(current == sentinel) {
            return -1;
        }
        return index;
	}

	@Override
	public boolean isEmpty() {
        return sentinel.next == sentinel && sentinel.prev == sentinel;
    }

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new InnerListIterator();
	}

	@Override
	public E remove(int index) {
        checkIndex(index);
		Element current = getElement(index);
        current.remove();
        size--;
        updateJumpElement();
        return current.object;
	}

	@Override
	public boolean remove(E e) {
		Element current = getElement(e);
        if(current != sentinel) {
            current.remove();
            size--;
            return true;
        }
		return false;
	}

	@Override
	public int size() {
        return size;
	}

	//@SuppressWarnings("unchecked")
	public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
		if(this == other || other.isEmpty()) {
            return;
        }
        Element current = this.sentinel.next;
        Element currentOther = other.sentinel.next;

        while(currentOther != other.sentinel) {
            Element nextOther = currentOther.next;
            Comparable<E> comparable = (Comparable<E>) currentOther.object;

            while(current != this.sentinel && comparable.compareTo(current.object) >= 0) {
                current = current.next;
            }

            current.prev.addAfter(currentOther);
            this.size++;

            currentOther = nextOther;
        }

        other.clear();
	}
	
	//@SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeAll(E e) {
        Element current = sentinel.next;

        while (current != sentinel) {
            Element nextElement = current.next;

            if (current.object.equals(e)) {
                current.remove();
                size--;
            }

            current = nextElement;
        }
	}

    public void checkIndex(int index) {
        if(index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
    }

    private String formatElement(Element element) {
        if (element == null) {
            return "null";
        }
        if (element == sentinel) {
            return "SENTINEL";
        }
        return String.valueOf(element.object);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Element current = sentinel;

        do {
            builder.append(formatElement(current))
                   .append(" -> ")
                   .append(formatElement(current.jump));
            current = current.next;
            if (current != sentinel) {
                builder.append("\n");
            }
        } while (current != sentinel);

        return builder.toString();
    }
}
