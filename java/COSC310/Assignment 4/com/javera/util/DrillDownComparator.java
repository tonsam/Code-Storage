package com.javera.util;

import java.util.Comparator;

public class DrillDownComparator implements Comparator {
    Comparator c1;
    Comparator c2;

    public DrillDownComparator(Comparator c1, Comparator c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public int compare(Object o1, Object o2) {
        int result = c1.compare(o1, o2);

        if (result == 0) {
            return c2.compare(o1, o2);
        } else {
            return result;
        }
    }

    public boolean equals(Object obj) {
        return false;
    }

    public Comparator getC1() {
        return c1;
    }

    public Comparator getC2() {
        return c2;
    }
}