package jt.beans;

import jt.entities.Job;
import jt.entities.Kunde;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

// TODO: Auto-generated Javadoc
/**
 * The Class KundenBean.
 * 
 * @author jan & tim
 */
@Named
@ApplicationScoped
public class KundenBean {

	/** The kunde. */
	@Inject
	private Kunde kunde;

	@Inject
	JobticketBean jobticketBean;

	/** The entity manager factory. */
	@Inject
	private EntityManagerFactory entityManagerFactory;

	/** The em. */
	private EntityManager em;

	/**
	 * Gets the kunden.
	 * 
	 * @return the kunden
	 */
	public List<Kunde> getKunden() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em.createQuery("SELECT b FROM Kunde b");
		@SuppressWarnings("unchecked")
		List<Kunde> kundenListe = query.getResultList();
		if (kundenListe == null) {
			kundenListe = new ArrayList<Kunde>();
		}
		em.getTransaction().commit();
		return kundenListe;
	}

	/**
	 * Find kunden by id.
	 * 
	 * @param id
	 *            the id
	 * @return the kunde
	 */
	public Kunde findKundenByID(int id) {
		return em.find(Kunde.class, id);
	}

	/**
	 * Gets the kunde.
	 * 
	 * @return the kunde
	 */
	public Kunde getKunde() {
		return kunde;
	}

	/**
	 * Sets the kunde.
	 * 
	 * @param kunde
	 *            the kunde to set
	 */
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	/**
	 * Edits the kunde.
	 * 
	 * @param kunde
	 *            the kunde
	 * @return the string
	 */
	public String editKunde(Kunde kunde) {
		this.kunde = kunde;
		return "kunden_edit.xhtml";
	}

	/**
	 * Save kunde.
	 * 
	 * @return the string
	 */
	public String saveKunde(Kunde kunde) {
		FacesContext context = FacesContext.getCurrentInstance();
		em = entityManagerFactory.createEntityManager();
		
		if (!istKuerzelVorhanden(kunde.getKundenkuerzel())) {
			em.getTransaction().begin();
			em.persist(kunde);	
			em.getTransaction().commit();
			resetKunde();
			context.addMessage(null, new FacesMessage(
					"Daten erfolgreich gespeichert!", ""));
		} else {
			context.addMessage(null, new FacesMessage(
					"Kundenkürzel ist bereits vorhanden", ""));
		}		
		return "kunden_add.xhtml";
	}

	private void resetKunde() {
		this.kunde.setName("");
		this.kunde.setAdresse("");
		this.kunde.setKundenkuerzel("");
		this.kunde.setTelefon("");
	}

	private List<Job> getJobsFromKunde(Kunde kunde) {
		em = entityManagerFactory.createEntityManager();
		final Query query = em
				.createQuery("SELECT b FROM Job b WHERE b.kunde.id = :id");
		query.setParameter("id", kunde.getId());
		List<Job> jobListe = query.getResultList();
		return jobListe;
	}

	/**
	 * Delete.
	 * 
	 * @param kunde
	 *            the kunde
	 * @return the string
	 */
	public String delete(Kunde kunde) {
		List<Job> jobsVomKunde = getJobsFromKunde(kunde);
		if (jobsVomKunde.size() == 0) {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			kunde = em.merge(kunde);
			em.remove(kunde);
			em.getTransaction().commit();
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Kunde kann nicht gelöscht werden, da er folgenden Jobs zugeordnet ist: "
							+ jobsVomKunde));

		}
		return null;
	}

	private boolean istKuerzelVorhanden(String kuerzel) {
		Query query = em.createQuery(
				"SELECT b FROM Kunde b where b.kundenkuerzel = :kuerzel")
				.setParameter("kuerzel", kuerzel);
		@SuppressWarnings("unchecked")
		List<Kunde> kundenListe = query.getResultList();
		return kundenListe.size() > 0;
	}

	/**
	 * Update kunde.
	 * 
	 * @return the string
	 */
	public String updateKunde() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(kunde);
		em.getTransaction().commit();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(
				"Daten erfolgreich gespeichert!", "Kunde"));
		return null;
	}
}
