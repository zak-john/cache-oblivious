package com.zj.sorting;

import com.zj.ds.KTree;
import com.zj.ds.KTreeImpl;

import java.util.*;

@SuppressWarnings("unchecked")
public enum Sort {

    ;

    public static List funnelsort(List input) {
        int N = input.size();
        int k = (int) Math.pow(N, 1.00/3);
        int subArraySize = N / k;
        System.out.println(subArraySize);
        List<List> subArrays = divideInput(input, N, k, subArraySize);
        mergeSortSubArrays(subArrays);
        KTree kTree = new KTreeImpl();
        kTree.init(subArrays);
        return kTree.sort();
    }

    private static void mergeSortSubArrays(List<List> subArrays) {
        for (List subArray: subArrays) {
            Collections.sort(subArray);
        }
    }

    private static List<List> divideInput(List input, int n, int k, int subArraySize) {
        List<List> subArrays = new ArrayList();
        int inputPosition = 0;
        for (int i = 1; i <= k; i++) {
            List subArray = new ArrayList();
            inputPosition = fillSubArray(input, subArraySize, inputPosition, subArray);
            boolean finalSubArray = i == k;
            if (finalSubArray) {
                boolean inputRemaining = inputPosition < n;
                if (inputRemaining) {
                    addRemainingToFinalSubArray(input, n, inputPosition, subArray);
                }
            }

            subArrays.add(subArray);
        }
        return subArrays;
    }

    private static int fillSubArray(List input, int subArraySize, int inputPosition, List subArray) {
        for (int j = 0; j < subArraySize; j++) {
            subArray.add(input.get(inputPosition++));
        }
        return inputPosition;
    }

    private static void addRemainingToFinalSubArray(List input, int n, int inputPosition, List subArray) {
        for (int j = inputPosition; j < n; j++) {
            subArray.add(input.get(j));
        }
    }

    public static void main(String[] args) {
        ArrayList input = new ArrayList();
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            input.add(rand.nextInt(1000));
        }
        Sort.funnelsort(input);
    }
}
