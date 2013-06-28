package jt.beans;

import java.util.ArrayList;
import java.util.List;

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
import jt.entities.Produkteigenschaften;

@ApplicationScoped
@Named
public class JobticketBean {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	@Produces
	@AktuellerJob
	private Job job;

	@Inject
	private KundenBean kundenBean;

	private boolean neuerJob = false;

	public int getSelectedKundeId() {
		return selectedKundeId;
	}

	private int selectedKundeId;

	public void setSelectedKundeId(int selectedKundeId) {
		this.selectedKundeId = selectedKundeId;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public String editJob(Job job) {
		this.job = job;
		neuerJob = false;
		return "jobticket_main.xhtml";
	}

	public String neuesJobticket() {
		this.job = new Job();
		neuerJob = true;
		return "jobticket_main.xhtml";
	}

	public String saveJobticket() {

		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Kunde k = kundenBean.findKundenByID(selectedKundeId);
		job.setKunde(k);
		if (neuerJob)
			em.persist(job);
		else
			em.merge(job);
		em.getTransaction().commit();
		System.out.println("JOBNAME: " + job.getName());
		return "jobticket_produktbeschreibung.xhtml";
	}

	public Job findJobByID(int id) {
		return em.find(Job.class, id);
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
