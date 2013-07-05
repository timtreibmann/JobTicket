package jt.beans;

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

@Named
@RequestScoped
public class KundenBean {
	@Inject
	private Kunde kunde;

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

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

	public Kunde findKundenByKuerzel(String kuerzel) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em
				.createQuery("SELECT b FROM Kunde b WHERE b.kundenkuerzel LIKE :kuerzel");
		query.setParameter("kuerzel", kuerzel);
		List<Kunde> kundenListe = query.getResultList();
		if (kundenListe == null) {
			kundenListe = new ArrayList<Kunde>();
		}

		for (Kunde k : kundenListe) {
			System.out.println(k.getName());
		}

		em.getTransaction().commit();
		return kundenListe.get(0);
	}

	public Kunde findKundenByID(int id) {
		return em.find(Kunde.class, id);
	}

	/**
	 * @return the kunde
	 */
	public Kunde getKunde() {
		return kunde;
	}

	/**
	 * @param kunde
	 *            the kunde to set
	 */
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public String editKunde(Kunde kunde) {
		this.kunde = kunde;
		return "kunden_edit.xhtml";
	}

	public String saveKunde() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(kunde);
		em.getTransaction().commit();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(
				"Daten erfolgreich gespeichert!", ""));

		return "kunden_add.xhtml";
	}

	public String delete(Kunde kunde) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		kunde = em.merge(kunde);
		em.remove(kunde);
		em.getTransaction().commit();
		return null;
	}

	public String updateKunde() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Kunde k = em.find(Kunde.class, kunde.getId());
		k.setName(kunde.getName());
		k.setAdresse(kunde.getAdresse());
		k.setTelefon(kunde.getTelefon());
		k.setKundenkuerzel(kunde.getKundenkuerzel());
		k.setJobs(k.getJobs());
		em.getTransaction().commit();
		return "kunden_table.xhtml";
	}

}
