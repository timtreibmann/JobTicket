package jt.beans;

import java.util.Comparator;

import jt.entities.Job;

import org.primefaces.model.SortOrder;
// TODO: Auto-generated Javadoc

/**
 * s.
 *
 * @author jan & tim
 */
public class LazySorter implements Comparator<Job> {

    /** The sort field. */
    private String sortField;
   
    /** The sort order. */
    private SortOrder sortOrder;
   
    /**
     * Instantiates a new lazy sorter.
     *
     * @param sortField the sort field
     * @param sortOrder the sort order
     */
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
	public int compare(Job job1, Job job2) {
        try {
            Object value1 = Job.class.getField(this.sortField).get(job1);
            Object value2 = Job.class.getField(this.sortField).get(job2);

            @SuppressWarnings("rawtypes")
			int value = ((Comparable)value1).compareTo(value2);
           
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
