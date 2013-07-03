package jt.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import jt.annotations.AktuellerJob;
import jt.entities.Job;
import jt.entities.Kunde;

@ApplicationScoped
@Named
public class JobticketBean {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	@Produces
	@AktuellerJob
	private Job job;

	private int selectedKundeId;

	private String kuerzel;

	@Inject
	private KundenBean kundenBean;

	private boolean neuerJob = false;

	public int getSelectedKundeId() {
		return selectedKundeId;
	}

	public void setSelectedKundeId(int selectedKundeId) {
		this.selectedKundeId = selectedKundeId;
	}

	public void updateKuerzel() {
		Kunde k = kundenBean.findKundenByID(selectedKundeId);
		kuerzel = k.getKundenkuerzel();

	}

	public String getKuerzel() {
		return kuerzel;
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
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
		return "jobticket_produktbeschreibung.xhtml";
	}

	public String findKundeByKuerzel() {
		System.out.println("testi");
	
		Kunde k = kundenBean.findKundenByKuerzel(kuerzel);
		selectedKundeId = k.getId();
		return null;
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

	public String delete(Job job) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		job = em.merge(job);
		em.remove(job);
		em.getTransaction().commit();
		return null;
	}

}
