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

@Named
@RequestScoped
public class TableBean {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;
	private List<Job> jobs;
	private LazyDataModel<Job> lazyModel;
	@Inject
	private Job job;

	public TableBean() {

		jobs = getJobs();
		System.out.println("job:" + jobs);
		lazyModel = new LazyJobDataModel(jobs);

	}

	public LazyDataModel<Job> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Job> lazyModel) {
		this.lazyModel = lazyModel;
	}

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
