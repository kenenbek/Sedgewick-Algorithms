import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int head, tail;

    // construct an empty randomized queue
    public RandomizedQueue(){
        s = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return tail - head == 0;
    }

    // return the number of items on the randomized queue
    public int size(){return tail - head;}

    // add the item
    public void enqueue(Item item){
        if (item == null) {
            throw new  java.lang.IllegalArgumentException();
        }
        if (tail == s.length) resize(2 * s.length);
        s[tail++] = item;
    }

    private void resize(int capacity){
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = head; i < tail; i++) {
            copy[i-head] = s[i];
        }
        s = copy;
        tail -= head;
        head = 0;
    }

    // remove and return a random item
    public Item dequeue(){
        if (size() == 0){
            throw new java.util.NoSuchElementException();
        }

        Item item = s[head];
        s[head++] = null;
        if (head >= tail){
            head = 0;
            tail = 0;
        }
        if (tail-head > 0 && tail-head == s.length / 4) resize(s.length/2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (size() == 0){
            throw new java.util.NoSuchElementException();
        }
        int i = StdRandom.uniform(head, tail);
        return s[i];
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item>{
        private int[] permutation;
        private int current;

        public RandomQueueIterator(){
            int s = size();
            permutation = StdRandom.permutation(s, s);
        }

        @Override
        public boolean hasNext() {
            return current < permutation.length;
        }

        @Override
        public Item next() {
            if (current >= tail){
                throw new  java.util.NoSuchElementException();
            }
            return s[head + permutation[current++]];
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(370);
        rq.enqueue(253);
        rq.enqueue(156);
        rq.dequeue(); //     ==> 370
        rq.enqueue(326);
        System.out.println(rq.isEmpty()); //     ==> false
        rq.enqueue(221);

        for (Integer s: rq) {
            System.out.println(s);
        }
    }

}