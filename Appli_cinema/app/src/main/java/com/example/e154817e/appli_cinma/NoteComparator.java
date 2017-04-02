package com.example.e154817e.appli_cinma;

import java.util.Comparator;

/**
 * Created by Riku on 02/04/2017.
 */

public class NoteComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return Float.compare(Float.parseFloat(((Film)o1).getNote()),(Float.parseFloat(((Film)o2).getNote())));
    }
}
