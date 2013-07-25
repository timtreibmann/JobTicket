package jt.beans;

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
 * @author jan & tim
 */
@Named
@ApplicationScoped
public class KundenBean {
	
	/** The kunde. */
	@Inject
	private Kunde kunde;
	
	@Inject JobticketBean jobticketBean;

	/** The entity manager factory. */
	@Inject
	private EntityManagerFactory entityManagerFactory;
	
	/** The em. */
	private EntityManager em;
	
	private String kuerzel;

	public String getKuerzel() {
		return kuerzel;
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

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
	 * Find kunden by kuerzel.
	 *
	 * @param kuerzel the kuerzel
	 * @return the kunde
	 */
	public Kunde findKundenByKuerzelAndUpdateJobticket() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em
				.createQuery("SELECT b FROM Kunde b WHERE b.kundenkuerzel LIKE :kuerzel");
		query.setParameter("kuerzel", kuerzel);
		
		
		List<Kunde> kundenListe = query.getResultList();
		em.getTransaction().commit();
		if (kundenListe.size() > 0){
			jobticketBean.setSelectedKundeId(kundenListe.get(0).getId());
			jobticketBean.updateJobticket();
			return kundenListe.get(0);
			
		} else {
			return null;
		}
		
	}

	/**
	 * Find kunden by id.
	 *
	 * @param id the id
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
	 * @param kunde the kunde to set
	 */
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	/**
	 * Edits the kunde.
	 *
	 * @param kunde the kunde
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
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(kunde);
		em.getTransaction().commit();
		FacesContext context = FacesContext.getCurrentInstance();
		resetKunde();
		context.addMessage(null, new FacesMessage(
				"Daten erfolgreich gespeichert!", ""));
	
		return "kunden_add.xhtml";
	}
	
	private void resetKunde() {
		this.kunde.setName("");
		this.kunde.setAdresse("");
		this.kunde.setKundenkuerzel("");
		this.kunde.setTelefon("");
	}

	/**
	 * Delete.
	 *
	 * @param kunde the kunde
	 * @return the string
	 */
	public String delete(Kunde kunde) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		kunde = em.merge(kunde);
		em.remove(kunde);
		em.getTransaction().commit();
		return null;
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
