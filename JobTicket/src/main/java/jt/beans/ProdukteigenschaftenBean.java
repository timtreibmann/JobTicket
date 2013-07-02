package jt.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import jt.annotations.AktuellerJob;
import jt.entities.Job;
import jt.entities.Kunde;
import jt.entities.Produkteigenschaften;

@Named
@RequestScoped
public class ProdukteigenschaftenBean {
	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	@Inject
	@AktuellerJob
	private Job job;

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Inject
	private Produkteigenschaften produkteigenschaften;

	public String neuesProdukt() {
		this.produkteigenschaften = new Produkteigenschaften();
		return "produktbeschreibung_edit.xhtml";
	}

	public Produkteigenschaften getProdukteigenschaften() {
		return produkteigenschaften;
	}

	public void setProdukteigenschaften(
			Produkteigenschaften produkteigenschaften) {
		this.produkteigenschaften = produkteigenschaften;
	}

	public String editProdukteigenschaften(
			Produkteigenschaften produkteigenschaften) {
		this.produkteigenschaften = produkteigenschaften;
		return "produktbeschreibung_edit.xhtml";
	}

	public String saveProdukteigenschaften() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		produkteigenschaften.setJob(job);
		em.merge(job);
		em.persist(produkteigenschaften);
		em.getTransaction().commit();
		return "jobticket_produktbeschreibung.xhtml";
	}

	public String updateProdukteigenschaften() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Produkteigenschaften p = em.find(Produkteigenschaften.class,
				produkteigenschaften.getId());
		p.setEingangsdatum(produkteigenschaften.getEingangsdatum());
		p.setAusgangsdatum(produkteigenschaften.getAusgangsdatum());
		p.setVorlagedatum(produkteigenschaften.getVorlagedatum());
		p.setProduktbeschreibung(produkteigenschaften.getProduktbeschreibung());
		p.setBeschnitt(produkteigenschaften.getBeschnitt());
		p.setBindung(produkteigenschaften.getBindung());
		p.setDummy(produkteigenschaften.getDummy());
		p.setErledigt(produkteigenschaften.getErledigt());
		p.setFalzung(produkteigenschaften.getFalzung());
		p.setFarbe4c(produkteigenschaften.getFarbe4c());
		p.setFarbeSw(produkteigenschaften.getFarbeSw());
		p.setFomat(produkteigenschaften.getFomat());
		p.setProof(produkteigenschaften.getProof());
		p.setSeitenzahl(produkteigenschaften.getSeitenzahl());
		p.setSonderfarbe(produkteigenschaften.getSonderfarbe());
		em.getTransaction().commit();
		return "jobticket_produktbeschreibung.xhtml";
	}

	public List<Produkteigenschaften> getProdukteigenschaftenFromJob() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		int id = job.getId();
		final Query query = em
				.createQuery("SELECT p FROM Produkteigenschaften p WHERE p.job.id = :id");
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Produkteigenschaften> produktListe = query.getResultList();
		if (produktListe == null) {
			produktListe = new ArrayList<Produkteigenschaften>();
		}
		for (Produkteigenschaften p : produktListe) {
			System.out.println(p);
		}
		em.getTransaction().commit();
		return produktListe;
	}
	
	public String delete(Produkteigenschaften produkteigenschaften) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		produkteigenschaften = em.merge(produkteigenschaften);
		em.remove(produkteigenschaften);
		em.getTransaction().commit();
		return null;
	}
	
	public String weiter() {
		System.out.println("PROID"+produkteigenschaften.getId());
		if (produkteigenschaften.getId()!=0) {
			System.out.println("UPDATE");
			
			updateProdukteigenschaften();
		}else {
			System.out.println("KEIN UPDATE");
			saveProdukteigenschaften();
		}
		return "jobticket_produktbeschreibung.xhtml";
	}

}
