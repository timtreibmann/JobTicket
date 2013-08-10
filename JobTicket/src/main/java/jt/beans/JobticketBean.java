package jt.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import jt.annotations.AktuellerJob;
import jt.entities.Angestellte;
import jt.entities.Job;
import jt.entities.Kunde;

// TODO: Auto-generated Javadoc
/**
 * The Class JobticketBean.
 * 
 * @author jan & tim
 */
@SessionScoped
@Named
public class JobticketBean implements Serializable {

	private List<Job> filteredJobs;

	@Inject
	private transient EntityManagerFactory entityManagerFactory;

	private transient EntityManager em;

	@Produces
	@AktuellerJob
	private transient Job job;

	@Inject
	private transient ProdukteigenschaftenBean produkteigenschaftenBean;

	private int selectedKundeId;
	private int selectedAngestellterId;
	private boolean filterJoblistByAngestellten;
	private boolean hideFinishedJobs;
	private boolean neuesTicket;
	private int kundenAnzahl;

	public boolean isNeuesTicket() {
		return neuesTicket;
	}

	public void setNeuesTicket(boolean neuesTicket) {
		this.neuesTicket = neuesTicket;
	}

	public int getKundenAnzahl() {
		return kundenBean.getKunden().size();
	}

	public boolean isFilterJoblistByAngestellten() {
		return filterJoblistByAngestellten;
	}

	public void setFilterJoblistByAngestellten(
			boolean filterJoblistByAngestellten) {
		this.filterJoblistByAngestellten = filterJoblistByAngestellten;
	}

	public int getSelectedAngestellterId() {
		return selectedAngestellterId;
	}

	public void setSelectedAngestellterId(int selectedAngestellterId) {
		this.selectedAngestellterId = selectedAngestellterId;
	}

	public boolean isHideFinishedJobs() {
		return hideFinishedJobs;
	}

	public void setHideFinishedJobs(boolean hideFinishedJobs) {
		this.hideFinishedJobs = hideFinishedJobs;
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

		System.out.println("initialisieren");
		showAllOnOnePage = true;
		hideFinishedJobs = true;
		filterJoblistByAngestellten = true;
		selectedAngestellterId = findLoggedInMitarbeiterId();
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
		neuesTicket = false;
		this.job = job;
		// damit in im selectOneMenu auf der jobticket_main-Seite der richtige
		// Kunde gesetzt wird
		try {
			selectedKundeId = job.getKunde().getId();
			kuerzel = job.getKunde().getKundenkuerzel();
		} catch (NullPointerException e) {
			// kein Kunde gesetzt
			selectedKundeId = 0;
			this.kuerzel = "";
		}

		if (showAllOnOnePage) {
			return "ticketanzeige.xhtml";
		} else {
			return "jt_main.xhtml";
		}
	}

	public String createJobticket() {
		neuesTicket = true;
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		selectedKundeId = 0;
		this.kuerzel = "";
		this.job = new Job();
		FacesContext fc = FacesContext.getCurrentInstance();
		job.setErsteller(fc.getExternalContext().getRemoteUser());
		Date d = new Date();

		job.setErstellDatum(d);
		em.persist(job);

		em.getTransaction().commit();
		produkteigenschaftenBean.createProdukteigenschaft();
		refreshFilter();

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
			kuerzel = k.getKundenkuerzel();
		}

		em.merge(job);
		em.getTransaction().commit();

		return null;
	}

	public List<Job> getJobs() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createQuery("SELECT b FROM Job b");
		if (filterJoblistByAngestellten) {
			if (hideFinishedJobs) {
				query = findUnfinishedJobsByAngestellten();

			} else {
				query = findJobByAngestellten();
			}
		} else {
			if (hideFinishedJobs) {
				query = em
						.createQuery("SELECT b FROM Job b WHERE b.fortschritt < 100");
			} else {

			}

		}
		@SuppressWarnings("unchecked")
		List<Job> jobListe = query.getResultList();

		if (jobListe == null) {
			jobListe = new ArrayList<Job>();
		}
		em.getTransaction().commit();
		return jobListe;

	}

	private int findLoggedInMitarbeiterId() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String user = fc.getExternalContext().getRemoteUser();
		int id = -1;

		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Query query = em
				.createQuery("SELECT j FROM Angestellte j where j.loginName = :username");
		query.setParameter("username", user);
		List<Angestellte> angestellteListe = query.getResultList();
		if (angestellteListe.size() > 0) {
			id = angestellteListe.get(0).getId();
		}
		em.getTransaction().commit();
		return id;

	}

	private Query findJobByAngestellten() {

		Query query = em
				.createQuery("SELECT j.job FROM Jobbearbeiter j where j.angestellte.id = :id");
		query.setParameter("id", selectedAngestellterId);
		return query;
	}

	private Query findUnfinishedJobsByAngestellten() {

		Query query = em
				.createQuery("SELECT j.job FROM Jobbearbeiter j where j.angestellte.id = :id and j.job.fortschritt<100");
		query.setParameter("id", selectedAngestellterId);
		return query;
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

	public void findKundenByKuerzelAndUpdateJobticket() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em
				.createQuery("SELECT b FROM Kunde b WHERE b.kundenkuerzel LIKE :kuerzel");
		query.setParameter("kuerzel", kuerzel);

		List<Kunde> kundenListe = query.getResultList();
		em.getTransaction().commit();
		if (kundenListe.size() > 0) {
			setSelectedKundeId(kundenListe.get(0).getId());
			updateJobticket();

		}

	}

	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.invalidateSession();

		return "/logout.xhtml?faces-redirect=true";
	}

}
