package jt.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.primefaces.event.ToggleEvent;

import jt.annotations.AktuellerJob;
import jt.entities.Angestellte;
import jt.entities.Job;
import jt.entities.Jobbearbeiter;
import jt.entities.Kosten;
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

	@Inject
	private EntityManagerFactory entityManagerFactory;

	private EntityManager em;

	@Produces
	@AktuellerJob
	private Job job;
	
	
	private int selectedKundeId;

	private int selectedAngestellterId;

	private boolean filterJoblistByAngestellten;

	public boolean isFilterJoblistByAngestellten() {
		return filterJoblistByAngestellten;
	}

	public void setFilterJoblistByAngestellten(
			boolean filterJoblistByAngestellten) {
		this.filterJoblistByAngestellten = filterJoblistByAngestellten;
	}

	private boolean showAllJobs;

	public boolean isFilterJoblistByUser() {
		return showAllJobs;
	}

	public void setFilterJoblistByUser(boolean filterJoblistByUser) {
		this.showAllJobs = filterJoblistByUser;
	}

	public int getSelectedAngestellterId() {
		return selectedAngestellterId;
	}

	public void setSelectedAngestellterId(int selectedAngestellterId) {
		this.selectedAngestellterId = selectedAngestellterId;
	}

	private boolean showAllOnOnePage;

	public boolean isShowAllOnOnePage() {
		return showAllOnOnePage;
	}

	public void setShowAllOnOnePage(boolean showAllOnOnePage) {
		this.showAllOnOnePage = showAllOnOnePage;
	}

	private String kuerzel;

	@Inject
	private KundenBean kundenBean;

	@PostConstruct
	public void init() {
		showAllOnOnePage=true;
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

	public int getSelectedKundeId() {
		return selectedKundeId;
	}

	public void setSelectedKundeId(int selectedKundeId) {
		this.selectedKundeId = selectedKundeId;
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
		//damit in im selectOneMenu auf der jobticket_main-Seite der richtige Kunde gesetzt wird
		try{
		selectedKundeId=job.getKunde().getId();	
		}catch(NullPointerException e) {
			//kein Kunde gesetzt
			selectedKundeId=0;
		}
		
		this.kuerzel="";
		if (showAllOnOnePage) {
			return "ticketanzeige.xhtml";
		} else {
			return "jt_main.xhtml";
		}
	}

	public String createJobticket() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		selectedKundeId=0;
		this.kuerzel="";
		this.job = new Job();
		FacesContext fc = FacesContext.getCurrentInstance();
		job.setErsteller(fc.getExternalContext().getRemoteUser());
		Date d = new Date();

		job.setErstellDatum(d);
		em.persist(job);

		em.getTransaction().commit();

		if (showAllOnOnePage) {
			return "ticketanzeige.xhtml";
		} else {
			return "jt_main.xhtml";
		}
	}

	public String updateJobticket() {

		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		if (selectedKundeId != 0) {
			Kunde k = kundenBean.findKundenByID(selectedKundeId);
			job.setKunde(k);
		}
	
		em.merge(job);
		em.getTransaction().commit();

		return null;
	}

	public List<Job> getJobsFromJobbearbeiter() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Query query = em
				.createQuery("SELECT j FROM Jobbearbeiter j where j.angestellte.id = :id");
		query.setParameter("id", selectedAngestellterId);
		List<Jobbearbeiter> jlist = query.getResultList();
		List<Job> jobListe = new ArrayList<Job>();
		for (Jobbearbeiter j : jlist) {
			jobListe.add(j.getJob());
		}
		return jobListe;
	}

	public List<Job> getJobsFromUser() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		FacesContext fc = FacesContext.getCurrentInstance();
		String user = fc.getExternalContext().getRemoteUser();
		Query query = em
				.createQuery("SELECT j FROM Job j where j.ersteller = :username");
		query.setParameter("username", user);
		List<Job> jobListe = query.getResultList();

		return jobListe;
	}

	public List<Job> getJobs() {
		if (showAllJobs) {
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
		} else {
			return getJobsFromUser();
		}

	}


	public Job findJobByID(int id) {
		return em.find(Job.class, id);
	}

	public String delete(Job job) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		job = em.merge(job);
		em.remove(job);
		em.getTransaction().commit();
		return null;
	}

	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.invalidateSession();
		 return "/logout.xhtml?faces-redirect=true";
	}

}
