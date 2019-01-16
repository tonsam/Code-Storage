package com.javera.util;

import java.util.Comparator;

public class InverseComparator implements Comparator {
    Comparator c;

    public InverseComparator(Comparator c) {
        this.c = c;
    }

    public int compare(Object o1, Object o2) {
        return -c.compare(o1, o2);
    }

    public boolean equals(Object obj) {
        return false;
    }

    public Comparator getBaseComparator() {
        return c;
    }
}