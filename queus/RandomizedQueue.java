import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first = null;

    private class Node
    {
        //TODO?
        Item item;
        Node next;
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        public boolean hasNext()
        {
            //TODO
            return false;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            //TODO
            return first.item;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        //TODO
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        //TODO
        return false;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        //TODO
        return 0;
    }

    // add the item
    public void enqueue(Item item)
    {
        //TODO
    }

    // remove and return a random item
    public Item dequeue()
    {
        //TODO
        return first.item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        //TODO
        return first.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        //TODO
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        //TODO
    }

}