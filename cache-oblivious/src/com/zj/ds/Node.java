package com.zj.ds;

public class Node {

    public static Node EMPTY_INSTANCE = new Node(BufferImpl.EMPTY_INSTANCE);

    private Buffer buffer;
    private Node rightChild = null;
    private Node leftChild = null;
    private int numElements = 0;
    private boolean leafNode;
    private int targetSize;

    public Node(Buffer buffer) {
        this.buffer = buffer;
        this.leafNode = true;
        this.targetSize = buffer.size();
    }

    public Node(Node leftChild, Node rightChild) {
        this.leftChild  = leftChild;
        this.rightChild = rightChild;
        this.leafNode = false;

        this.targetSize = this.leftChild.targetSize + this.rightChild.targetSize;

        this.buffer = new BufferImpl(targetSize);


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
        return bufferSize() == targetSize;
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
                numElements++;
            }
            return;
        }

        if (rightChild.isEmpty()) {
            while (!leftChild.isEmpty()) {
                this.buffer.put(leftChild.nextBufferElement());
                numElements++;
            }
            return;
        }

        int nextLeftElement = leftChild.buffer().peep();
        int nextRightElement = rightChild.buffer().peep();

        int newElement = nextLeftElement > nextRightElement ? rightChild.nextBufferElement() : leftChild.nextBufferElement();

        this.buffer.put(newElement);
        numElements++;
    }

}
