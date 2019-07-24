package com.zj.ds;

public interface Buffer {

    int next();

    int peep();

    void sort();

    boolean isEmpty();

    int size();

    int[] array();

    void put(int input);
}
