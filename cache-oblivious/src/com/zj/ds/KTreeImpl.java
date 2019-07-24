package com.zj.ds;

import java.util.Arrays;

public class KTreeImpl implements KTree {
    private Node[] tree;
    private Node head;

    public KTreeImpl(int[] input) {
        Buffer[] buffers = createInitialBuffers(input);
        this.init(buffers);
    }

    @Override
    public void init(Buffer[] buffers) {
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
        while (start+1 != end) {
            tree[end] = new Node(tree[start], tree[start+1]);
            end++;
            start += 2;
        }
        this.head = tree[tree.length-1];
    }

    @Override
    public int[] sort() {
        head.fill();
        return head.buffer().array();
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

    private Buffer[] createInitialBuffers(int[] input) {
        int N = input.length;
        int k = (int) Math.pow(N, 1.00/3);
        int subArraySize = N / k;
        int[][] subArrays = divideInput(input, N, k, subArraySize);
        mergeSortSubArrays(subArrays);
        Buffer[] output = new Buffer[k];
        for (int i = 0; i < subArrays.length; i++) {
            int[] subArray = subArrays[i];
            output[i] = new BufferImpl(subArray);
        }
        return output;
    }

    private static void mergeSortSubArrays(int[][] subArrays) {
        for (int[] subArray: subArrays) {
            Arrays.sort(subArray);
        }
    }

    private static int[][] divideInput(int[] input, int n, int k, int subArraySize) {
        // To account for rounding errors.
        int increasedArraySize = subArraySize + k;
        int[][] subArrays = new int[k][increasedArraySize];
        int inputPosition = 0;
        for (int i = 0; i < k; i++) {
            int[] subArray = subArrays[i];
            inputPosition = fillSubArray(input, subArraySize, inputPosition, subArray);
            boolean finalSubArray = i == k-1;
            if (finalSubArray) {
                boolean inputRemaining = inputPosition < n;
                if (inputRemaining) {
                    addRemainingToFinalSubArray(input, n, inputPosition, subArray, subArraySize);
                }
            }
        }
        return subArrays;
    }

    private static int fillSubArray(int[] input, int subArraySize, int inputPosition, int[] subArray) {
        for (int j = 0; j < subArraySize; j++) {
            subArray[j] = input[inputPosition++];
        }
        return inputPosition;
    }

    private static void addRemainingToFinalSubArray(int[] input, int n, int inputPosition, int[] subArray, int subArraySize) {
        int posInSubArray = subArraySize;
        for (int j = inputPosition; j < n; j++) {
            subArray[posInSubArray++] = input[j];
        }
    }
}
