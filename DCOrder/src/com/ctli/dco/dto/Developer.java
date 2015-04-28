package com.ctli.dco.dto;

import java.util.Comparator;

public class Developer {
	String name;
	int threshold;
	
	public static final Comparator<Developer> BY_NAME = new ByNameComparator();

    public static final Comparator<Developer> BY_TOTALREQUIREDISSUES = new ByTotalRequiredIssuesComparator();

    private static Developer[] currentAssignedCountList = null;
    
	public String getName() {
		return name;
	}
	
	public Developer(String name, int threshold) {
        super();
        this.name = name;
        this.threshold = threshold;
    }

    public static Developer[] getCurrentAssignedCountList() {
        return currentAssignedCountList;
    }
	
    public static void setCurrentAssignedCountList(
            Developer[] currentAssignedCountList) {
        Developer.currentAssignedCountList = currentAssignedCountList;
    }
    
    public void setName(String name) {
		this.name = name;
	}
    
	public int getThreshold() {
		return threshold;
	}
	
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
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
    
	private static class ByNameComparator implements Comparator<Developer> {
        @Override
        public int compare(Developer o1, Developer o2) {
            return o1.getName().compareTo(o2.getName());
        }

    }

    private static class ByTotalRequiredIssuesComparator implements
            Comparator<Developer> {
        @Override
        public int compare(Developer o1, Developer o2) {
            return (Integer.valueOf(o1.getThreshold()).compareTo(o2.getThreshold()));
        }
    }    
}
