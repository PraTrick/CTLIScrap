package com.centurylink.xprsr.service.impl;

import java.util.Comparator;

/**
 * @author Anurag Chowdhury
 * @author Prateek Gupta
 * @version 1.0
 * @since JUNE 2013
 */
public class SortAssignment {

    public static final Comparator<SortAssignment> BY_NAME = new ByNameComparator();

    public static final Comparator<SortAssignment> BY_TOTALREQUIREDTICKETS = new ByTotalRequiredTicketsComparator();


    private static SortAssignment[] currentAssignedCountList = null;

    private String name;
    private Integer [] countArray;

    public SortAssignment() {
        super();
    }

    public SortAssignment(String name, Integer [] countArray) {
        super();
        this.name = name;
        this.setCountArray(countArray);  //threshold - count
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Integer [] getCountArray() {
		return countArray;
	}

	public void setCountArray(Integer [] countArray) {
		this.countArray = countArray;
	}

    public static SortAssignment[] getSortAssignmentList() {
        return currentAssignedCountList;
    }

    public static void setSortAssignmentList(SortAssignment[] sortAssignment) {
        SortAssignment.currentAssignedCountList = sortAssignment;
    }

    /**
     * Sorts generic array using "Insertion Sort"
     * 
     * @param a
     *            Array of Object[] type
     * @param comparator
     *            Type of the comparator called (ByNameComparator OR
     *            ByTotalRequiredTicketsComparator)
     * @see SortAssignment#exch(Object[], int, int)
     * @see SortAssignment#more(Comparator, Object, Object)
     * @see SortAssignment#BY_NAME
     * @see SortAssignment#BY_TOTALREQUIREDTICKETS           
     */
    @SuppressWarnings("rawtypes")
    public static void sort(Object[] a, Comparator comparator) {
        int N = a.length;
        int i, j;
        for (i = 1; i < N; i++) {
            j = i;
            while (j > 0 && more(comparator, a[j - 1], a[j])) {
                exch(a, (j - 1), j);
                j--;
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean more(Comparator c, Object v, Object w) {
        return c.compare(v, w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static class ByNameComparator implements Comparator<SortAssignment> {
        @Override
        public int compare(SortAssignment o1, SortAssignment o2) {
            return o1.getName().compareTo(o2.getName());
        }

    }

    private static class ByTotalRequiredTicketsComparator implements
            Comparator<SortAssignment> {
        @Override
        public int compare(SortAssignment o1, SortAssignment o2) {
            return (o1.getCountArray()[0]).compareTo(o2.getCountArray()[0]);
        }
    }

    @Override
    public String toString() {
        return "SortAssignment [name=" + name + ", RequiredTickets=" + countArray;
    }
}
