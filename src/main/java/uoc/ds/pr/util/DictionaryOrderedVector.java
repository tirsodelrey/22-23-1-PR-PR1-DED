package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.DictionaryArrayImpl;
import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.util.Utils;
import edu.uoc.ds.adt.helpers.KeyValue;
import uoc.ds.pr.exceptions.DSException;

import java.util.Comparator;

public class DictionaryOrderedVector<K,V> extends DictionaryArrayImpl<K,V> implements FiniteContainer<V> {
    private static final long serialVersionUID = Utils.getSerialVersionUID();
    private static final int KEY_NOT_FOUND = -1;
    private Comparator<K> comparator;
    public DictionaryOrderedVector(int max, Comparator<K> comparator) {
        super(max);
        this.comparator = comparator;
    }

    public boolean isFull(){
        return (super.n == super.dictionary.length);
    }

    @Override
    public void put(K key, V value) {
        //adds key value
        super.put(key, value);

        // add Key-Value
        int i = n - 1;

        //KeyValue to compare through the while loop against the last one added
        KeyValue keyVal;
        //KeyyValue last added
        KeyValue lastAdded = dictionary[n - 1];
        //Seeks and puts the value in the position in the dictionary determined by the comparator value sorted upon
        while (i >= 0 ) {
            keyVal = dictionary[i];

            if (comparator.compare((K) keyVal.getKey(), key) > 0) {
                // swap
                dictionary[i] = lastAdded;
                dictionary[i+1] = keyVal;
                lastAdded = dictionary[i];
            }

            i--;
        }
    }

    //looks up value associated to key by performing a binary search, returns the object if found or -1 otherwise
    @Override
    public V get(K key) {
        int pos = binarySearch(key, n);
        return (pos != KEY_NOT_FOUND ? dictionary[pos].getValue() : null);
    }

    //overload of get method in case it needs to throw and exception
    public V get(K key, String errorMssg) throws DSException {
        V value = get(key);
        if (value == null) {
            throw new DSException(errorMssg);
        }
        return value;
    }


    //aux method for getting the value associated to a key using a dicotomic search [O(Log n)]
    public int binarySearch (K key, int n){
        int lo = 0;
        int hi = n-1;

        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;
            if     (comparator.compare(dictionary[mid].getKey() ,key) > 0) hi = mid -1;
            else if(comparator. compare(dictionary[mid].getKey(), key) < 0) lo = mid + 1;
            else return mid;
        }
        return KEY_NOT_FOUND;
    }
}
