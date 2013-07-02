package jt.beans;

import jt.annotations.AktuellerJob;
import jt.entities.Angestellte;
import jt.entities.Job;
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
public class AngestellteBean {
	@Inject
	private Angestellte angestellte;

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	@Inject
	private Job job;

	public Angestellte getAngestellte() {
		return angestellte;
	}

	public void setAngestellte(Angestellte angestellte) {
		this.angestellte = angestellte;
	}

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

	public Angestellte findAngestelltenByID(int id) {
		return em.find(Angestellte.class, id);
	}
	
	public String delete(Angestellte angestellte) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		angestellte = em.merge(angestellte);
		em.remove(angestellte);
		em.getTransaction().commit();
		return null;
	}


	public String saveAngestellte() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(angestellte);
		em.getTransaction().commit();
		return "angestellte_add.xhtml";
	}

}
