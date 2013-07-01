package jt.beans;

import java.util.Comparator;

import jt.entities.Job;

import org.primefaces.model.SortOrder;
/**
 * s
 * @author pelli
 *
 */
public class LazySorter implements Comparator<Job> {

    private String sortField;
   
    private SortOrder sortOrder;
   
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

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
