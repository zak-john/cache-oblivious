package com.zj;

import com.zj.sorting.Sort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FunnelsortTest {

    Random rand = new Random();

    @Test
    public void testSortsInOrder() {
        int[] testInput = new int[1_000_000];
        for (int i = 0; i < testInput.length; i++) {
            testInput[i] = rand.nextInt();
        }
        int[] testInputTwo = testInput.clone();
        Arrays.sort(testInput);
        testInputTwo = Sort.funnelsort(testInputTwo);
        assertArrayEquals(testInput, testInputTwo);
    }
}