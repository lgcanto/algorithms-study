import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int numberOfItems = 0;

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int i = numberOfItems;

        public boolean hasNext()
        {
            return i > 0;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if ( i < 0)
            {
                throw new NoSuchElementException();
            }

            i--;
            while (queue[i] == null)
            {
                i--;
            }

            return queue[i];
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return numberOfItems == 0;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return numberOfItems;
    }

    // add the item
    public void enqueue(Item item)
    {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (numberOfItems == queue.length)
        {
            resize(2 * queue.length);
        }
        queue[numberOfItems++] = item;
    }

    // remove and return a random item
    public Item dequeue()
    {
        checkEmptyQueue();
        int randomIndex = StdRandom.uniform(numberOfItems);
        Item item = queue[randomIndex];
        queue[randomIndex] = null;
        numberOfItems--;
        if (numberOfItems > 0 && numberOfItems == queue.length/4)
        {
            resize(queue.length/2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        checkEmptyQueue();
        int randomIndex = StdRandom.uniform(numberOfItems);
        return queue[randomIndex];
    }

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < numberOfItems; i++)
        {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    private void checkEmptyQueue()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        StdRandom.shuffle(queue);
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue randomizedQueue = new RandomizedQueue<String>();
        randomizedQueue.enqueue("to");
        randomizedQueue.enqueue("be");
        randomizedQueue.enqueue("or");
        randomizedQueue.enqueue("not");
        StdOut.println("Is RandomizedQueue empty (must be false): " + randomizedQueue.isEmpty());
        StdOut.println("RandomizedQueue size (must equal 4): " + randomizedQueue.size());
        StdOut.println("Printing Deque, must be  different than 'to be or not' (random)");
        for (Object word : randomizedQueue)
        {
            StdOut.println(word);
        }
        StdOut.println("Getting random sample: " + randomizedQueue.sample());
        StdOut.println("Removing some random: " + randomizedQueue.dequeue());
        StdOut.println("Removing some other random: " + randomizedQueue.dequeue());
        StdOut.println("RandomizedQueue size (must equal 2): " + randomizedQueue.size());
        StdOut.println("Printing Deque, must be different than 'be or' (random)");
        for (Object word : randomizedQueue)
        {
            StdOut.println(word);
        }
    }

}