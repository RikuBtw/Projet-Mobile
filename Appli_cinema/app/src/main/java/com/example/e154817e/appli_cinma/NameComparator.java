package com.example.e154817e.appli_cinma;

import java.util.Comparator;

/**
 * Created by Riku on 02/04/2017.
 */

public class NameComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return String.valueOf(((Film)o1).getTitre().toUpperCase()).compareTo(((Film)o2).getTitre().toUpperCase());
    }
}
