package com.zj.sorting;

import com.zj.ds.KTree;
import com.zj.ds.KTreeImpl;

import java.util.*;

@SuppressWarnings("unchecked")
public enum Sort {

    ;

    public static int[] funnelsort(int[] input) {
        KTree funnel = new KTreeImpl(input);
        return funnel.sort();
    }
}
