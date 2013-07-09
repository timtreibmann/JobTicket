package jt.beans;


import java.util.ArrayList;  
import java.util.Collections;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  

import jt.entities.Job;

import org.primefaces.model.LazyDataModel;  
import org.primefaces.model.SortOrder;  
  

// TODO: Auto-generated Javadoc
/**
 * The Class LazyJobDataModel.
 * @author jan & tim
 */
public class LazyJobDataModel extends LazyDataModel<Job> {  
      
    /** s. */
	private static final long serialVersionUID = 1L;
	
	/** The datasource. */
	private List<Job> datasource;  
      
    /**
     * Instantiates a new lazy job data model.
     *
     * @param datasource the datasource
     */
    public LazyJobDataModel(List<Job> datasource) {  
        this.datasource = datasource;  
    }  
      
    /* (non-Javadoc)
     * @see org.primefaces.model.LazyDataModel#getRowData(java.lang.String)
     */
    @Override  
    public Job getRowData(String rowKey) {  
        for(Job kunde : datasource) {  
            if(kunde.getName().equals(rowKey))  
                return kunde;  
        }  
  
        return null;  
    }  
  
    /* (non-Javadoc)
     * @see org.primefaces.model.LazyDataModel#getRowKey(java.lang.Object)
     */
    @Override  
    public Object getRowKey(Job job) {  
        return job.getName();  
    }  
  
    /* (non-Javadoc)
     * @see org.primefaces.model.LazyDataModel#load(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
     */
    @Override  
    public List<Job> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {  
        List<Job> data = new ArrayList<Job>();  
  
        //filter  
        for(Job job : datasource) {  
            boolean match = true;  
  
            for(Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {  
                try {  
                    String filterProperty = it.next();  
                    String filterValue = filters.get(filterProperty);  
                    String fieldValue = String.valueOf(job.getClass().getField(filterProperty).get(job));  
  
                    if(filterValue == null || fieldValue.startsWith(filterValue)) {  
                        match = true;  
                    }  
                    else {  
                        match = false;  
                        break;  
                    }  
                } catch(Exception e) {  
                    match = false;  
                }   
            }  
  
            if(match) {  
                data.add(job);  
            }  
        }  
  
        //sort  
        if(sortField != null) {  
            Collections.sort(data, new LazySorter(sortField, sortOrder));  
        }  
  
        //rowCount  
        int dataSize = data.size();  
        this.setRowCount(dataSize);  
  
        //paginate  
        if(dataSize > pageSize) {  
            try {  
                return data.subList(first, first + pageSize);  
            }  
            catch(IndexOutOfBoundsException e) {  
                return data.subList(first, first + (dataSize % pageSize));  
            }  
        }  
        else {  
            return data;  
        }  
    }  
}  