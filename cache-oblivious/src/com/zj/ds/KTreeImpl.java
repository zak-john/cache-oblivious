package com.zj.ds;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;

public class KTreeImpl implements KTree {
    private final int[] input;
    private Node[] tree;
    private Node head;

    public KTreeImpl(int[] input) throws Exception {
        this.input = input;
        Buffer[] buffers = createInitialBuffers(input);
        this.init(buffers);
    }

    private static void mergeSortBuffers(Buffer[] buffers) {
        for (Buffer buffer : buffers) {
            buffer.sort();
        }
    }

    private static Buffer[] divideInput(int[] input, int n, int k) {
        int targetBufferSize = n / k;
        Buffer[] output = new Buffer[k];
        for (int i = 0; i < output.length; i++) {
            output[i] = new BufferImpl(targetBufferSize);
        }
        int bufferIndex = 0;
        for (int i = 0; i < n; i++) {
            boolean finalBuffer = bufferIndex == k - 1; // we don't want to go beyond the final buffer
            boolean bufferAtCapacity = (i + 1) % targetBufferSize == 0; // we've hit the optimal size for each buffer
            Buffer buffer = output[bufferIndex];
            buffer.put(input[i]);
            if (bufferAtCapacity && !finalBuffer) {
                bufferIndex++;
            }
        }
        return output;
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
        while (baseSize != 1) {
            baseSize /= 2;
            total += baseSize;
        }
        return total;
    }

    @Override
    public void init(Buffer[] buffers) throws Exception {
        int numInputBuffers = buffers.length;
        int baseSize = findNearestPowerOfTwo(numInputBuffers);
        int size = totalSizeOfTree(baseSize);
        this.tree = new Node[size];
        for (int i = 0; i < numInputBuffers; i++) {
            tree[i] = new Node(buffers[i]);
        }
        for (int i = numInputBuffers; i < baseSize; i++) {
            tree[i] = Node.EMPTY_INSTANCE;
        }
        int start = 0;
        int end = baseSize;
        BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt", true));
        while (start+1 != end) {
            tree[end] = new Node(tree[start], tree[start+1]);
            writer.append(' ').append(Integer.toString(end));
            writer.append("Tree end: ").append(String.valueOf(tree[end]));
            end++;
            start += 2;
        }
        writer.close();
        this.head = tree[tree.length-1];
    }

    @Override
    public int[] sort() {
        head.fill();
        int[] headArray = head.buffer().array();
        return Arrays.copyOfRange(headArray, 0, input.length);
    }

    private Buffer[] createInitialBuffers(int[] input) {
        int N = input.length;
        int k = (int) Math.pow(N, 1.00/3);
        Buffer[] buffers = divideInput(input, N, k);
        mergeSortBuffers(buffers);
        return buffers;
    }
}
