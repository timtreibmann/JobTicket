package jt.beans;

import jt.entities.Angestellte;

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

	@Inject
	private Angestellte angestellte;

	@Inject
	private EntityManagerFactory entityManagerFactory;

	public Angestellte getAngestellte() {
		return angestellte;
	}

	public void setAngestellte(Angestellte angestellte) {
		this.angestellte = angestellte;
	}

	private EntityManager em;

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
		System.out.println("DELETE");
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
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(
				"Daten erfolgreich gespeichert!", ""));
		return "angestellte_add.xhtml";
	}

	public String editAngestellten(Angestellte angestellte) {
		this.angestellte = angestellte;
		return "angestellte_edit.xhtml";
	}

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
