package jt.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import jt.annotations.AktuellerJob;
import jt.entities.Job;
import jt.entities.Kunde;

// TODO: Auto-generated Javadoc
/**
 * The Class JobticketBean.
 * 
 * @author jan & tim
 */
@ApplicationScoped
@Named
public class JobticketBean {

	private List<Job> filteredJobs;

	/** The entity manager factory. */
	@Inject
	private EntityManagerFactory entityManagerFactory;

	/** The em. */
	private EntityManager em;

	/** The job. */
	@Produces
	@AktuellerJob
	private Job job;

	/** The selected kunde id. */
	private int selectedKundeId;

	/** The kuerzel. */
	private String kuerzel;

	/** The kunden bean. */
	@Inject
	private KundenBean kundenBean;

	/** The neuer job. */
	private boolean neuerJob = false;

	@PostConstruct
	public void init() {
		filteredJobs = getJobs();
	}

	public String refreshFilter() {
		filteredJobs = getJobs();
		return "start.xhtml";
	}

	public List<Job> getFilteredJobs() {
		return filteredJobs;
	}

	public void setFilteredJobs(List<Job> filteredJobs) {
		this.filteredJobs = filteredJobs;
	}

	/**
	 * Gets the selected kunde id.
	 * 
	 * @return the selected kunde id
	 */
	public int getSelectedKundeId() {
		return selectedKundeId;
	}

	/**
	 * Sets the selected kunde id.
	 * 
	 * @param selectedKundeId
	 *            the new selected kunde id
	 */
	public void setSelectedKundeId(int selectedKundeId) {
		this.selectedKundeId = selectedKundeId;
	}

	/**
	 * Gets the kuerzel.
	 * 
	 * @return the kuerzel
	 */
	public String getKuerzel() {
		return kuerzel;
	}

	/**
	 * Sets the kuerzel.
	 * 
	 * @param kuerzel
	 *            the new kuerzel
	 */
	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	/**
	 * Gets the job.
	 * 
	 * @return the job
	 */
	public Job getJob() {
		return job;
	}

	/**
	 * Sets the job.
	 * 
	 * @param job
	 *            the new job
	 */
	public void setJob(Job job) {
		this.job = job;
	}

	/**
	 * Edits the job.
	 * 
	 * @param job
	 *            the job
	 * @return the string
	 */
	public String editJob(Job job) {
		this.job = job;
		neuerJob = false;
		return "jobticket_main.xhtml";
	}

	/**
	 * Neues jobticket.
	 * 
	 * @return the string
	 */
	public String neuesJobticket() {
		this.job = new Job();
		neuerJob = true;
		return "jobticket_main.xhtml";
	}

	/**
	 * Save jobticket.
	 * 
	 * @return the string
	 */
	public String saveJobticket() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Kunde k = kundenBean.findKundenByID(selectedKundeId);
		if (job.getErstellDatum()==null) {
			job.setErstellDatum(new Date());
		}
		job.setKunde(k);
		if (neuerJob)
			em.persist(job);
		else
			em.merge(job);
		em.getTransaction().commit();
		return "jobticket_produktbeschreibung.xhtml";
	}

	/**
	 * Find kunde by kuerzel.
	 * 
	 * @return the string
	 */
	public String findKundeByKuerzel() {
		System.out.println("testi");

		Kunde k = kundenBean.findKundenByKuerzel(kuerzel);
		selectedKundeId = k.getId();
		return null;
	}

	/**
	 * Find job by id.
	 * 
	 * @param id
	 *            the id
	 * @return the job
	 */
	public Job findJobByID(int id) {
		return em.find(Job.class, id);
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
	

	/**
	 * Delete.
	 * 
	 * @param job
	 *            the job
	 * @return the string
	 */
	public String delete(Job job) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		job = em.merge(job);
		em.remove(job);
		em.getTransaction().commit();
		return null;
	}

}
