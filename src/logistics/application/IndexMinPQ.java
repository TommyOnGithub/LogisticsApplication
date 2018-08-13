/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Tommy
 */
public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
    private int maxN;
    private int n;
    private int[] pq;
    private int []qp;
    private Key[] keys;
    
    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        n = 0;
        keys = (Key[]) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }
    
    public boolean contains(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }
    
    public int size() {
        return n;
    }
    
    public boolean isEmpty() {
        return n == 0;
    }
    
    public void insert(int i, Key key) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is alerady in the priority queue");
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
//        System.out.format("n = %d\n", n);
        swim(n);
    }
    
    public void decreaseKey(int i, Key key) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("Index is not in the priority queue");
        if (keys[i].compareTo(key) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
        keys[i] = key;
        swim(qp[i]);
    }
    
    public int delMin() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exch(1, n--);
        sink(1);
        assert min == pq[n+1];
        keys[min] = null;
        pq[n+1] = -1;
        return min;
    }
    
    private boolean greater(int i, int j) {
        // System.out.format("keys[pq[i]] = %s, keys[pq[j]] = %s\n", keys[pq[i]].toString(), keys[pq[j]].toString());
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }
    
    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }
    
    private void swim(int k) {
//        System.out.format("k/2 equals: %d, k equals: %d\n", k/2, k);
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }
    
    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
    
    public Iterator<Integer> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Integer> {
        
        private IndexMinPQ<Key> copy;

        public HeapIterator() {
            copy = new IndexMinPQ<Key>(pq.length - 1);
            for ( int i = 1; i <= n; i++)
                copy.insert(pq[i], keys[pq[i]]);
        }
        
        public boolean hasNext() { return !copy.isEmpty(); }
        public void remove() {throw new UnsupportedOperationException(); }
        
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
}
