package com.zj;

import com.zj.sorting.Sort;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FunnelsortTest {

    private Random rand = new Random();

    @Test
    public void testSortsInOrder() throws Exception {
        int testLength = 1_000_000;
        int[] testInput = new int[testLength];
        for (int i = 0; i < testInput.length; i++) {
            testInput[i] = rand.nextInt();
        }
        int[] testInputTwo = testInput.clone();
        Arrays.sort(testInput);
        testInputTwo = Sort.funnelsort(testInputTwo);
        BufferedWriter writer = new BufferedWriter(new FileWriter("sortResult.txt", false));
        for (int i = 0; i < testLength; i++) {
            writer.append("i: ").append(Integer.toString(testInputTwo[i]));
//            assertEquals(testInput[i], testInputTwo[i]);
        }
        writer.close();
        assertArrayEquals(testInput, testInputTwo);
    }
}