import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item>
{

    private Node first, last = null;
    private int numberOfItems = 0;

    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext()
        {
            return current != null;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // construct an empty deque
    public Deque(){
        //TODO?
    };

    // is the deque empty?
    public boolean isEmpty()
    {
        return first == null;
    }

    // return the number of items on the deque
    public int size()
    {
        return numberOfItems;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.previous = null;
        first.next = oldFirst;
        oldFirst.previous = first;
        if (isEmpty())
        {
            last = first;
        }
        numberOfItems++;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.previous = oldLast;
        last.next = null;
        if (isEmpty())
        {
            first = last;
        }
        else
        {
            oldLast.next = last;
        }
        numberOfItems++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        Item item = first.item;
        first = first.next;
        first.previous = null;
        numberOfItems--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        Item item = last.item;
        last = last.previous;
        last.next = null;
        numberOfItems--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        Deque deque = new Deque<String>();
        deque.addFirst("be");
        deque.addLast("or");
        deque.addLast("not");
        deque.addFirst("to");
    }

}