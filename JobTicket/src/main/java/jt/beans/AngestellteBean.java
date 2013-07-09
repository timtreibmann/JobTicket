package jt.beans;

import jt.annotations.AktuellerJob;
import jt.entities.Angestellte;
import jt.entities.Job;
import jt.entities.Kunde;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

// TODO: Auto-generated Javadoc
/**
 * The Class AngestellteBean.
 *
 * @author jan & tim eine Bean die die angestellten in der datenbank verwaltet
 */
@Named
@RequestScoped
public class AngestellteBean {
	
	/** The angestellte. */
	@Inject
	private Angestellte angestellte;

	/** The entity manager factory. */
	@Inject
	private EntityManagerFactory entityManagerFactory;
	
	/** The em. */
	private EntityManager em;

	/** The job. */
	@Inject
	private Job job;

	/**
	 * Gets the angestellte.
	 *
	 * @return the angestellte
	 */
	public Angestellte getAngestellte() {
		return angestellte;
	}

	/**
	 * Sets the angestellte.
	 *
	 * @param angestellte the new angestellte
	 */
	public void setAngestellte(Angestellte angestellte) {
		this.angestellte = angestellte;
	}

	/**
	 * Gets the angestelltes.
	 *
	 * @return the angestelltes
	 */
	public List<Angestellte> getAngestelltes() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em.createQuery("SELECT b FROM Angestellte b");
		@SuppressWarnings("unchecked")
		List<Angestellte> angestellteListe = query.getResultList();
		if (angestellteListe == null) {
			angestellteListe = new ArrayList<Angestellte>();
		}
		em.getTransaction().commit();
		return angestellteListe;
	}

	/**
	 * Find angestellten by id.
	 *
	 * @param id the id
	 * @return the angestellte
	 */
	public Angestellte findAngestelltenByID(int id) {
		return em.find(Angestellte.class, id);
	}

	/**
	 * Delete.
	 *
	 * @param angestellte the angestellte
	 * @return the string
	 */
	public String delete(Angestellte angestellte) {
		System.out.println("DELETE");
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		angestellte = em.merge(angestellte);
		em.remove(angestellte);
		em.getTransaction().commit();
		return null;
	}

	/**
	 * Save angestellte.
	 *
	 * @return the string
	 */
	public String saveAngestellte() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(angestellte);
		em.getTransaction().commit();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(
				"Daten erfolgreich gespeichert!", ""));
		return "angestellte_add.xhtml";
	}

	/**
	 * Edits the angestellten.
	 *
	 * @param angestellte the angestellte
	 * @return the string
	 */
	public String editAngestellten(Angestellte angestellte) {
		this.angestellte = angestellte;
		return "angestellte_edit.xhtml";
	}

	/**
	 * Update angestellten.
	 *
	 * @return the string
	 */
	public String updateAngestellten() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Angestellte k = em.find(Angestellte.class, angestellte.getId());
		k.setNachname(angestellte.getNachname());
		k.setVorname(angestellte.getVorname());
		k.setStundenlohn(angestellte.getStundenlohn());
		em.getTransaction().commit();
		return "angestellte_table.xhtml";
	}

}
