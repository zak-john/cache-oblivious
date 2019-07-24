package com.zj.ds;

public class Node {

    public static Node EMPTY_INSTANCE = new Node(BufferImpl.EMPTY_INSTANCE);

    private Buffer buffer;
    private Node rightChild = null;
    private Node leftChild = null;
    private int numElements = 0;
    private boolean leafNode;

    public Node(Buffer buffer) {
        this.buffer = buffer;
        this.leafNode = true;
    }

    public Node(Node leftChild, Node rightChild) {
        this.leftChild  = leftChild;
        this.rightChild = rightChild;
        this.leafNode = false;

        int leftSize  = leftChild .isEmpty() ? 0 : leftChild.bufferSize();
        int rightSize = rightChild.isEmpty() ? 0 : rightChild.bufferSize();

        this.buffer = new BufferImpl(leftSize + rightSize);

    }

    public Node rightChild() {
        return rightChild;
    }

    public Node leftChild() {
        return leftChild;
    }

    private int bufferSize() {
        return buffer.size();
    }

    public int nextBufferElement() {
        return buffer.next();
    }

    public Buffer buffer() {
        return buffer;
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    public boolean full() {
        return bufferSize() == numElements;
    }

    public boolean isNotLeafNode() {
        return !leafNode;
    }

    public void fill() {
        while (!full()) {
            if (leftChild.isEmpty() && leftChild.isNotLeafNode()) {
                leftChild.fill();
            }
            if (rightChild.isEmpty() && rightChild.isNotLeafNode()) {
                rightChild.fill();
            }
            merge();
        }
    }

    public void merge() {
        if (leftChild.isEmpty()) {
            while (!rightChild.isEmpty()) {
                this.buffer.put(rightChild.nextBufferElement());
            }
            return;
        }

        if (rightChild.isEmpty()) {
            while (!leftChild.isEmpty()) {
                this.buffer.put(leftChild.nextBufferElement());
            }
            return;
        }

        int nextLeftElement = leftChild.buffer().peep();
        int nextRightElement = rightChild.buffer().peep();

        int newElement = nextLeftElement > nextRightElement ? rightChild.nextBufferElement() : leftChild.nextBufferElement();

        this.buffer.put(newElement);
    }

}
