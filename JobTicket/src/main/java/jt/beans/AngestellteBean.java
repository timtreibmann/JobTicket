package jt.beans;

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
	
	private List<Job> getJobsFromAngestellten(Angestellte angestellte) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em
				.createQuery("SELECT j.job FROM Jobbearbeiter j where j.angestellte.id = :id");
		query.setParameter("id", angestellte.getId());
		List<Job> jobListe = query.getResultList();
		em.getTransaction().commit();
		return jobListe;
	}

	public String delete(Angestellte angestellte) {
		List<Job> jobsVomAngestellten = getJobsFromAngestellten(angestellte);
		if (jobsVomAngestellten.size() == 0) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		angestellte = em.merge(angestellte);
		em.remove(angestellte);
		em.getTransaction().commit();
	} else {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(
				"Angestellter kann nicht gelöscht werden, da er folgenden Jobs zugeordnet ist: "
						+ jobsVomAngestellten));

	}
		return null;
	}

	public String saveAngestellte() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(angestellte);
		em.getTransaction().commit();
		resetAngestellte();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(
				"Daten erfolgreich gespeichert!", ""));
		return "angestellte_add.xhtml";

	}

	private void resetAngestellte() {
		angestellte = new Angestellte();
		angestellte.setNachname("");
		angestellte.setVorname("");
		angestellte.setStundenlohn(0);

	}	

	public String editAngestellten(Angestellte angestellte) {
		this.angestellte = angestellte;
		return "angestellte_edit.xhtml";
	}

	public String updateAngestellten() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		System.out.println("NACHNAMEd :" + angestellte.getNachname());
		em.merge(angestellte);
		em.getTransaction().commit();
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage("Daten erfolgreich gespeichert"));
		return null;
	}

}
