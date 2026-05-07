package List6;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E extends Comparable<E>> implements IList<E>{

    private class Element {
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
            if (this == sentinel) {
                throw new NoSuchElementException();
            }

            size--;
            prev.next = next;
            next.prev = prev;
        }

        E object;
        Element next = null;
        Element prev = null;
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
            return current.next != sentinel;
        }

        @Override
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            current = current.next;
            return current.object;
        }
    }

    private class InnerListIterator implements ListIterator<E>{
        Element current;

        public InnerListIterator() {
            current = sentinel;
        }

        @Override
        public boolean hasNext() {
            return current.next != sentinel;
        }

        @Override
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            current = current.next;
            return current.object;
        }
        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasPrevious() {
            return current != sentinel;
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
            E result = current.object;
            current = current.prev;
            return result;
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
        clear();
    }

    //@SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        Element current = sentinel;
        while(current.next != sentinel && current.next.object.compareTo(e) <= 0) {
            current = current.next;
        }

        current.addAfter(new Element(e));
        size++;
        return true;
    }

    private Element getElement(int index) {
        checkIndex(index);

        Element current = sentinel.next;

        for(int i=0; i<index; i++) {
            current = current.next;
        }

        return current;
    }

    private Element getElement(E obj) {
        Element current = sentinel.next;

        while(current != sentinel && !current.object.equals(obj)) {
            current = current.next;
        }

        if(current == sentinel) {
            throw new NoSuchElementException();
        }

        return current;
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        sentinel = new Element(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        try{
            getElement(element);
            return true;
        } catch(NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public E get(int index) {
        return getElement(index).object;
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(E element) {
        Element current = sentinel.next;
        int index = 0;

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
        return size == 0;
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
        Element current = getElement(index);
        E result = current.object;
        current.remove();

        return result;
    }

    @Override
    public boolean remove(E e) {
        try{
            Element current = getElement(e);
            current.remove();
            return true;
        } catch(NoSuchElementException ex) {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    //@SuppressWarnings("unchecked")
    public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
        if (other == null || other.size() == 0 || other == this) {
            return;
        }

        Element current = sentinel;

        if (current.next == sentinel) {
            sentinel = other.sentinel;
            size = other.size;
            other.clear();
            return;
        }


        Element otherElement = other.sentinel.next;

        for (int i = 0; i < other.size(); i++) {
            while (current.next != sentinel && current.next.object.compareTo(otherElement.object) <= 0) {
                current = current.next;
            }
            Element previousNext = otherElement.next;

            current.addAfter(otherElement);

            current = current.next;
            otherElement = previousNext;
            size++;
        }
        other.clear();
    }

    //@SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeAll(E e) {
        Element current = sentinel;

        while(current.next != sentinel) {
            if(current.next.object.equals(e)) {
                current.next.remove();
                size--;
            } else {
                current = current.next;
            }
        }
    }

    private void checkIndex(int index) {
        if(index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
    }
}