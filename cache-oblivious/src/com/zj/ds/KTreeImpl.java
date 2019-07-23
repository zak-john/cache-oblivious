package com.zj.ds;

import java.util.List;

public class KTreeImpl implements KTree {
    private Node[] tree;
    private Node head;

    @Override
    public void init(Buffer[] buffers) {
        int numInputBuffers = buffers.length;
        int baseSize = findNearestPowerOfTwo(numInputBuffers);
        int size = totalSizeOfTree(baseSize);
        this.tree = new Node[size];
        this.head = tree[tree.length-1];
        for (int i = 0; i < numInputBuffers; i++) {
            tree[i] = new Node(buffers[i]);
        }
        for (int i = numInputBuffers; i < baseSize; i++) {
            tree[i] = Node.EMPTY_INSTANCE;
        }
        int start = 0;
        int end = baseSize;
        while (start < end) {
            tree[end] = new Node(tree[start], tree[start+1]);
            end++;
            start += 2;
        }
    }

    @Override
    public List sort() {
        return null;
    }

    private int findNearestPowerOfTwo(int input) {
        boolean found = false;
        int power = 0;
        while (!found) {
            int powerOfTwo = (int) Math.pow(2, power);
            if (powerOfTwo > input) {
                return powerOfTwo;
            }
            power++;
        }
        return -1;
    }

    private int totalSizeOfTree(int baseSize) {
        // base size will always be a power of two
        int total = baseSize;
        while(baseSize != 1) {
            baseSize /= 2;
            total += baseSize;
        }
        return total;
    }
}
