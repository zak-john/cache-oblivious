package com.zj.ds;

import java.util.Arrays;

public class BufferImpl implements Buffer {

    public static Buffer EMPTY_INSTANCE = new BufferImpl(0);
    private static final int DEFAULT_SIZE = 50;

    private int[] data;
    private int nextIndex;
    private int inputIndex;

    public BufferImpl() {
        this(DEFAULT_SIZE);
    }

    public BufferImpl(int size) {
        this.nextIndex = 0;
        this.inputIndex = 0;
        this.data = new int[size];
    }

    public BufferImpl(int[] input) {
        this.nextIndex = 0;
        this.inputIndex = input.length;
        this.data = input;
    }

    @Override
    public int next() {
        return data[nextIndex++];
    }

    @Override
    public int peep() {
        return data[nextIndex];
    }

    @Override
    public void sort() {
        Arrays.sort(this.data, 0, inputIndex);
    }

    @Override
    public boolean isEmpty() {
        return nextIndex == inputIndex;
    }

    @Override
    public int size() {
        return inputIndex;
    }

    @Override
    public int[] array() {
        return data;
    }

    @Override
    public void put(int input) {
        data[inputIndex] = input;
        inputIndex++;
        if (inputIndex == data.length - 2) {
            int[] old = data;
            data = new int[old.length * 2];
            System.arraycopy(old, 0, data, 0, old.length);
        }
    }
}
