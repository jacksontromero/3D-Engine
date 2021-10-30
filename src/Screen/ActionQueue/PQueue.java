package Screen.ActionQueue;

import java.util.*;

public class PQueue<E extends Comparable<E>> {

    //list that is the queue
    ArrayList<E> pQueue = new ArrayList<E>();

    /**
     * @param E element to enqueue
     */
    public void enqueue(E element) {
        pQueue.add(element);

        //swap up
        swapUp(pQueue.size()-1);
    }

    /**
     * @return E object dequeued from the top
     */
    public E dequeue() {

        //if not empty
        if(!isEmpty()) {

            //remove root node
            E output = pQueue.remove(0);
        
            //if still not empty
            if(!isEmpty()) {

                //replace root with lowest node
                E replacement = pQueue.remove(pQueue.size()-1);
                pQueue.add(0, replacement);
    
                //swap new root down to maintain order
                swapDown(0);
            }
    
            return output;
        }

        else return null;
    }

    /**
     * Helper method to recursively swap a value down to maintain order
     * @param index
     */
    private void swapDown(int index) {
        int leftIndex = leftChild(index);
        int rightIndex = rightChild(index);
        Boolean leftChild = null;

        //if both children exist
        if(rightIndex < pQueue.size()) {
            E left = pQueue.get(leftIndex);
            E right = pQueue.get(rightIndex);

            //if left is lower than right and lower than current index
            if(left.compareTo(right) < 0 && pQueue.get(index).compareTo(left) > 0) {
                leftChild = true;
            }

            //else if right is lower than left and lower than curent index
            else if(pQueue.get(index).compareTo(right) > 0) {
                leftChild = false;
            }
        }

        //else if only left child exists
        else if(leftIndex < pQueue.size() && pQueue.get(index).compareTo(pQueue.get(leftIndex)) > 0) {
            leftChild = true;
        }

        //else neither child exists
        else {
            //DO NOTHING
        }

        //if no swap needed
        if(leftChild == null) {
            //DO NOTHING
        }

        //else if need to swap to left
        else if(leftChild) {
            swap(index, leftIndex);
            swapDown(leftIndex);
        }

        //else if need to swap to right
        else {
            swap(index, rightIndex);
            swapDown(rightIndex);
        }
    }

    /**
     * Helper method to recursively swap up to maintain order
     * @param index
     */
    private void swapUp(int index) {
        int parentIndex = getParent(index);

        //if parentIndex is in the queue and is greater than current
        if(parentIndex >= 0 && pQueue.get(parentIndex).compareTo(pQueue.get(index)) > 0) {

            //swap up
            swap(index, parentIndex);
            swapUp(parentIndex);
        }
    }

    /**
     * Helper method that swaps two indexes
     * @param a index
     * @param b index
     */
    private void swap(int a, int b) {
        E temp = pQueue.get(a);
        pQueue.set(a, pQueue.get(b));
        pQueue.set(b, temp);
    }

    /**
     * @return E top of priority queue
     */
    public E peek() {
        if(!isEmpty()) return pQueue.get(0);
        return null;
    }

    /**
     * @return boolean if priority queue is empty
     */
    public boolean isEmpty() {
        return pQueue.size() == 0;
    }

    /**
     * Helper method to get parent
     * @param index of current
     * @return int index of parent
     */
    private int getParent(int index) {
        return (index+1)/2-1;
    }

    /**
     * Helper method to get left child
     * @param index of current
     * @return int index of left child
     */
    private int leftChild(int index) {
        return index*2+1;
    }

    /**
     * Helper method to get right child
     * @param index of current
     * @return int index of right child
     */
    private int rightChild(int index) {
        return index*2+2;
    }
}
