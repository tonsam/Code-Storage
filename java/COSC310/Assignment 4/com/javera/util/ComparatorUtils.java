package com.javera.util;

import java.util.Comparator;
import java.util.ArrayList;

public class ComparatorUtils {

    static public Comparator combineComparators(ArrayList list) {
        Comparator[] comparators = new Comparator[list.size()];
        list.toArray(comparators);
        return combineComparators(comparators);

    }

    static public Comparator combineComparators(Comparator[] comparators) {
        if (comparators.length == 0) {
            return null;
        } else if (comparators.length == 1) {
            return comparators[0];
        }

        Comparator c = new DrillDownComparator(comparators[comparators.length - 2], comparators[comparators.length - 1]);

        for (int i = comparators.length - 3; i >= 0; i--) {
            c = new DrillDownComparator(comparators[i], c);
        }

        return c;
    }

    static public Comparator[] separateComparator(Comparator c) {
        Comparator c2 = c;
        int        i = 1;

        while (c2 instanceof DrillDownComparator) {
            i++;
            c2 = ((DrillDownComparator) c2).getC2();
        }

        Comparator[] comparators = new Comparator[i];
        i = 0;

        while (c instanceof DrillDownComparator) {
            comparators[i++] = ((DrillDownComparator) c).getC1();
            c = ((DrillDownComparator) c).getC2();
        }

        comparators[i] = c;

        return comparators;
    }
}