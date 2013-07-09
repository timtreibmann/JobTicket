package jt.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.primefaces.model.LazyDataModel;

import jt.entities.Job;
// TODO: Auto-generated Javadoc

/**
 * The Class TableBean.
 *
 * @author jan & tim
 */
@Named
@RequestScoped
public class TableBean {

	/** The entity manager factory. */
	@Inject
	private EntityManagerFactory entityManagerFactory;
	
	/** The em. */
	private EntityManager em;
	
	/** The jobs. */
	private List<Job> jobs;
	
	/** The lazy model. */
	private LazyDataModel<Job> lazyModel;
	
	/** The job. */
	@Inject
	private Job job;

	/**
	 * Instantiates a new table bean.
	 */
	public TableBean() {

		jobs = getJobs();
		System.out.println("job:" + jobs);
		lazyModel = new LazyJobDataModel(jobs);

	}

	/**
	 * Gets the lazy model.
	 *
	 * @return the lazy model
	 */
	public LazyDataModel<Job> getLazyModel() {
		return lazyModel;
	}

	/**
	 * Sets the lazy model.
	 *
	 * @param lazyModel the new lazy model
	 */
	public void setLazyModel(LazyDataModel<Job> lazyModel) {
		this.lazyModel = lazyModel;
	}

	/**
	 * Gets the jobs.
	 *
	 * @return the jobs
	 */
	public List<Job> getJobs() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em.createQuery("SELECT b FROM Job b");
		@SuppressWarnings("unchecked")
		List<Job> jobListe = query.getResultList();
		if (jobListe == null) {
			jobListe = new ArrayList<Job>();
		}
		em.getTransaction().commit();
		return jobListe;
	}
}
