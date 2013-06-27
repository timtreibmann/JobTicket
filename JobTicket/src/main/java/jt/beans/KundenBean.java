package jt.beans;

import jt.entities.Kunde;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
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

	/**
	 * @return the blogEntries
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

	public Kunde findKundenByID(int id) {
		em = entityManagerFactory.createEntityManager();
		Kunde k = em.find(Kunde.class, id);

		return k;
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

	public String updateKunde(Kunde kunde) {
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
