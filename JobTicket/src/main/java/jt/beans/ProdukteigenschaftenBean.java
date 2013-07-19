package jt.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;

import jt.annotations.AktuellerJob;
import jt.entities.Job;
import jt.entities.Kunde;
import jt.entities.Produkteigenschaften;

// TODO: Auto-generated Javadoc
/**
 * @author jan & tim The Class ProdukteigenschaftenBean.
 */
@Named
@RequestScoped
public class ProdukteigenschaftenBean {

	/** The entity manager factory. */
	@Inject
	private EntityManagerFactory entityManagerFactory;

	/** The em. */
	private EntityManager em;

	/** The job. */
	@Inject
	@AktuellerJob
	private Job job;

	/** The produkteigenschaften. */
	@Inject
	private Produkteigenschaften produkteigenschaften;

	private int accordionIndex;

	/**
	 * Gets the produkteigenschaften.
	 * 
	 * @return the produkteigenschaften
	 */
	public Produkteigenschaften getProdukteigenschaften() {
		return produkteigenschaften;
	}

	/**
	 * Sets the produkteigenschaften.
	 * 
	 * @param produkteigenschaften
	 *            the new produkteigenschaften
	 */
	public void setProdukteigenschaften(
			Produkteigenschaften produkteigenschaften) {
		this.produkteigenschaften = produkteigenschaften;
	}

	/**
	 * Gets the job.
	 * 
	 * @return the job
	 */
	public Job getJob() {
		return job;
	}

	/**
	 * Sets the job.
	 * 
	 * @param job
	 *            the new job
	 */
	public void setJob(Job job) {
		this.job = job;
	}

	public void setAccordionIndex(int index) {
		this.accordionIndex = index;
	}

	public int getAccordionIndex() {
		return this.accordionIndex;
	}

	public int getProduktAnzahl() {
		return job.getProdukteigenschaftens().size();
	}

	public String createProdukteigenschaft() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Produkteigenschaften produkteigenschaften = new Produkteigenschaften();
		Date d = new Date();
		produkteigenschaften.setEingangsdatum(d);
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		produkteigenschaften
				.setProduktbeschreibung("Neues Produkt - Daten eintragen - "
						+ formatter.format(d));
		;
		produkteigenschaften.setErledigt(0);
		job.addProdukteigenschaften(produkteigenschaften);
		em.persist(produkteigenschaften);
		em.merge(job);
		em.getTransaction().commit();
		accordionIndex = job.getProdukteigenschaftens().size() - 1;
		return null;
	}

	/**
	 * Update produkteigenschaften.
	 * 
	 * @param produkteigenschaften
	 *            the produkteigenschaften
	 * @return the string
	 */
	public String updateProdukteigenschaften(
			Produkteigenschaften produkteigenschaften) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(produkteigenschaften);
		em.getTransaction().commit();
		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage(
				"Daten erfolgreich gespeichert!", "Produkteigenschaften"));

		return "jobticket_produktbeschreibung.xhtml";
	}

	public String ermittleFortschritt() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		double fortschritt = 0;
		List<Produkteigenschaften> list = job.getProdukteigenschaftens();
		int produktAnzahl = list.size();
		if (produktAnzahl != 0) {
			int erledigtAnzahl = 0;
			for (Produkteigenschaften p : list) {
				System.out.println(p.getErledigt());
				erledigtAnzahl += p.getErledigt();
			}
			fortschritt = (erledigtAnzahl * 100 / produktAnzahl);
		}
		job.setFortschritt(fortschritt);
		em.merge(job);
		em.getTransaction().commit();
		return "jobticket_overview.xhtml";
	}

	/**
	 * Delete.
	 * 
	 * @param produkteigenschaften
	 *            the produkteigenschaften
	 * @return the string
	 */
	public String delete(Produkteigenschaften produkteigenschaften) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		job.removeProdukteigenschaften(produkteigenschaften);
		em.merge(job);
		em.getTransaction().commit();
		return null;
	}

	/**
	 * Toggle erledigt.
	 * 
	 * @param produkteigenschaften
	 *            the produkteigenschaften
	 * @return the string
	 */
	public String toggleErledigt(Produkteigenschaften produkteigenschaften) {
		if (produkteigenschaften.getErledigt() == 0) {
			produkteigenschaften.setErledigt(1);
		} else {
			produkteigenschaften.setErledigt(0);
		}
		updateProdukteigenschaften(produkteigenschaften);
		return null;
	}

}
