package jt.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import jt.annotations.AktuellerJob;
import jt.entities.Angestellte;
import jt.entities.Job;
import jt.entities.Kosten;

@Named
@RequestScoped
public class AufwandBean {
	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;
	
	@Inject 
	private Kosten kosten;
	
	@Inject
	private AngestellteBean angestellteBean;
	
	@Inject
	@AktuellerJob
	private Job job;


	public Kosten getKosten() {
		return kosten;
	}

	public void setKosten(Kosten kosten) {
		this.kosten = kosten;
	}

	private int selectedAngestellteId;

	public int getSelectedAngestellteId() {
		return selectedAngestellteId;
	}

	public void setSelectedAngestellteId(int selectedAngestellteId) {
		this.selectedAngestellteId = selectedAngestellteId;
	}
	
	public String saveKosten() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Angestellte angestellte = angestellteBean.findAngestelltenByID(selectedAngestellteId);
		kosten.setAngestellte(angestellte);
		kosten.setJob(job);
		em.merge(job);
		em.persist(kosten);
		em.getTransaction().commit();
		return "start.xhtml";
	}
	

}
