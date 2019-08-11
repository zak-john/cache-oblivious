package com.zj.sorting;

import com.zj.ds.KTree;
import com.zj.ds.KTreeImpl;

@SuppressWarnings("unchecked")
public enum Sort {

    ;

    public static int[] funnelsort(int[] input) {
        KTree funnel = null;
        try {
            funnel = new KTreeImpl(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return funnel.sort();
    }
}
