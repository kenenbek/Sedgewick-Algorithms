// linked list implementation

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size;

    private class Node{
        Item item;
        Node next;
        Node prev;
    }
    // construct an empty deque
    public Deque(){

    }

    // is the deque empty?
    public boolean isEmpty(){
        return first == null;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null){
            throw new java.lang.IllegalArgumentException();
        }
        if (first == null){

        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst != null){
            oldFirst.prev = first;
        }else {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item){
        if (item == null){
            throw new java.lang.IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (oldLast != null) {
            oldLast.next = last;
        }else {
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (first == null){
            throw new java.util.NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;
        size--;
        if (first != null) {
            first.prev = null;
        }else {
            last = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if (last == null){
            throw new java.util.NoSuchElementException();
        }
        Item item = last.item;
        last = last.prev;
        size--;
        if (last != null){
            last.next = null;
        }else {
            first = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private Node current = first;
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null){
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove(Item item){
            throw new java.lang.UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        int x = 5;
        System.out.println(x++);
        System.out.println(5/2.);
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(10);
        d.addFirst(20);
        d.addLast(300);
        d.addLast(400);

        d.removeLast();
        d.removeLast();
        d.removeLast();
        d.removeLast();

        d.addLast(56);
        for (Integer s: d) {
            System.out.println(s);
        }
    }

}