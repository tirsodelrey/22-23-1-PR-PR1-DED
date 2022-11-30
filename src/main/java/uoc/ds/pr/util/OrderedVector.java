package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

import java.util.Comparator;

public class OrderedVector<E> implements FiniteContainer<E> {
    private final Comparator<E> comparator;
    private int numElements;
    private E[] elements;

    public OrderedVector(int max, Comparator<E> comparator) {
        this.comparator = comparator;
        elements = (E[])new Object[max];
        numElements = 0;
    }

    public E elementAt(int i){
        return elements[i];
    }

    private void shiftElementsRight(int i) {
        //shifts elements one block to the right
        int j = numElements-1;
        while (j >= i) {
            elements[j+1] = elements[j];
            j--;
        }
    }

    private void shiftElementsLeft(int i) {
        //shifts elements one block to the left
        int j = i;
        while (j < numElements-1) {
            elements[j] = elements[j+1];
            j++;
        }
    }

    public void update(E elemIn) {
        int i = 0;

        //Checks whether the element already exists, if so deletes it
        delete(elemIn);

        //orders the array and inserts the element in its correct position
        if (isFull()) {
            E elemOut = last();
            if (comparator.compare(elemOut, elemIn) < 0) {
                delete(elemOut);
                update(elemIn);
                return;
            }
            else {
                return;
            }
        }
        //loops through the array until it finds the insert position
        while (i < numElements && elements[i] != null && comparator.compare(elements[i], elemIn) >= 0) {
            i++;
        }

        // desplazamiento hacia la derecha de todos los elementos
        shiftElementsRight(i);

        // se inserta el elemento en la posición
        elements[i] = elemIn;
        numElements++;

    }

    public void delete (E elem) {

        boolean found = false;
        int i = 0;

        while (!found && i < numElements)
            found = compare(elem, elements[i++]);

        if (found) {
            if (i<numElements) {
                shiftElementsLeft(i-1);
            }
            else {
                shiftElementsLeft(i);
            }
            numElements--;
        }
    }

    public E last(){
        if(numElements > 0){
            return elements[numElements - 1];
        }
        return null;
    }
    private boolean compare(E elem1, E elem2) {
        return ((Comparable<E>)elem1).compareTo(elem2) == 0;
    }

    @Override
    public boolean isFull() {
        return this.numElements == this.elements.length;
    }

    @Override
    public boolean isEmpty() {
        return this.numElements == 0;
    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public Iterator<E> values() {
        return (Iterator<E>)new IteratorArrayImpl(this.elements, this.numElements,0);
    }
}
