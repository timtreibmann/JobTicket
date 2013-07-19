package jt.beans;

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

import jt.annotations.AktuellerJob;
import jt.entities.Angestellte;
import jt.entities.Job;
import jt.entities.Jobbearbeiter;
import jt.entities.Kosten;
import jt.entities.Produkteigenschaften;

// TODO: Auto-generated Javadoc
/**
 * The Class AufwandBean.
 * 
 * @author jan & tim
 */
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

	/** The selected angestellte id. */
	private int selectedAngestellteId;

	/** The job. */
	@Inject
	@AktuellerJob
	private Job job;

	/** The gesamt kosten. */
	private double gesamtKosten;

	/** The ist aufwand in euro. */
	private boolean istAufwandInEuro;

	/**
	 * Checks if is aufwand in euro.
	 * 
	 * @return true, if is aufwand in euro
	 */
	public boolean isAufwandInEuro() {
		return istAufwandInEuro;
	}

	/**
	 * Sets the aufwand in euro.
	 * 
	 * @param aufwandInEuro
	 *            the new aufwand in euro
	 */
	public void setAufwandInEuro(boolean aufwandInEuro) {
		this.istAufwandInEuro = aufwandInEuro;
	}

	/**
	 * Gets the kosten.
	 * 
	 * @return the kosten
	 */
	public Kosten getKosten() {
		return kosten;
	}

	/**
	 * Sets the kosten.
	 * 
	 * @param kosten
	 *            the new kosten
	 */
	public void setKosten(Kosten kosten) {
		this.kosten = kosten;
	}

	/**
	 * Gets the gesamt kosten.
	 * 
	 * @return the gesamt kosten
	 */
	public double getGesamtKosten() {
		berechneGesamtkosten(kosten);
		return gesamtKosten;
	}

	/**
	 * Gets the selected angestellte id.
	 * 
	 * @return the selected angestellte id
	 */
	public int getSelectedAngestellteId() {
		return selectedAngestellteId;
	}

	/**
	 * Sets the selected angestellte id.
	 * 
	 * @param selectedAngestellteId
	 *            the new selected angestellte id
	 */
	public void setSelectedAngestellteId(int selectedAngestellteId) {
		this.selectedAngestellteId = selectedAngestellteId;
	}

	/**
	 * Checks if is t angestellter vorhanden.
	 * 
	 * @param angestellte
	 *            the angestellte
	 * @return true, if is t angestellter vorhanden
	 */
	private boolean istAngestellterVorhanden(Angestellte angestellte) {

		List<Kosten> kostenListe = job.getKostens();

		boolean angestellterVorhanden = false;
		for (Kosten k : kostenListe) {
			if (k.getAngestellte().getId() == angestellte.getId()) {
				angestellterVorhanden = true;
			}
		}
		return angestellterVorhanden;
	}

	/**
	 * Save kosten.
	 * 
	 * @return the string
	 */
	public String saveKosten() {
		Angestellte angestellte = angestellteBean
				.findAngestelltenByID(selectedAngestellteId);
		if (!istAngestellterVorhanden(angestellte)) {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			kosten.setArbeitsaufwand(0);
			kosten.setArbeitsaufwandIstInEuro(1);
			Jobbearbeiter jobbearbeiter = new Jobbearbeiter();
			jobbearbeiter.setAngestellte(angestellte);
			job.addJobbearbeiter(jobbearbeiter);
			angestellte.addKosten(kosten);
			job.addKosten(kosten);
			em.persist(jobbearbeiter);
			em.persist(kosten);
			em.merge(angestellte);
			em.merge(job);
			em.getTransaction().commit();
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(
					"Angestellter wurde bereits hinzugefügt!"));
		}
		return null;
	}

	/**
	 * Update kosten.
	 * 
	 * @param kosten
	 *            the kosten
	 * @return the string
	 */
	public String updateKosten(Kosten kosten) {

		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Kosten k = em.find(Kosten.class, kosten.getId());
		k.setArbeitsaufwand(kosten.getArbeitsaufwand());
		berechneGesamtkosten(kosten);
		k.setArbeitsaufwandIstInEuro(kosten.getArbeitsaufwandIstInEuro());
		em.getTransaction().commit();
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage("Daten erfolgreich gespeichert"));
		return null;
	}

	private void berechneGesamtkosten(Kosten kosten) {
		try {
			// Gesamtkosten berechnen
			gesamtKosten = 0;
			double betrag = 0;
			List<Kosten> kostenListe = job.getKostens();
			for (Kosten k : kostenListe) {
				if (k.getAngestellte().getStundenlohn() == 0) {
					betrag = 0;
				} else {
					if (k.getArbeitsaufwandIstInEuro() == 0) {
						betrag = berechneAufwandInEuro(k);
					} else {
						betrag = k.getArbeitsaufwand();
					}
				}
				gesamtKosten += betrag;
			}
		} catch (NullPointerException e) {

		}

	}

	public List<Kosten> getKostens() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em.createQuery("SELECT b FROM Kosten b");
		@SuppressWarnings("unchecked")
		List<Kosten> kostenListe = query.getResultList();
		if (kostenListe == null) {
			kostenListe = new ArrayList<Kosten>();
		}
		em.getTransaction().commit();
		return kostenListe;
	}

	/**
	 * Berechne aufwand in std.
	 * 
	 * @param kosten
	 *            the kosten
	 * @return the double
	 */
	private double berechneAufwandInStd(Kosten kosten) {
		double stundenlohn = kosten.getAngestellte().getStundenlohn();
		if (stundenlohn != 0) {
			return kosten.getArbeitsaufwand() / stundenlohn;
		} else {
			return 0;
		}

	}

	/**
	 * Berechne aufwand in euro.
	 * 
	 * @param kosten
	 *            the kosten
	 * @return the double
	 */
	private double berechneAufwandInEuro(Kosten kosten) {
		return kosten.getArbeitsaufwand()
				* kosten.getAngestellte().getStundenlohn();

	}

	/**
	 * Rechne um.
	 * 
	 * @param kosten
	 *            the kosten
	 * @return the string
	 */
	public String rechneUm(Kosten kosten) {
		double erg;
		if (!(kosten.getArbeitsaufwandIstInEuro() == 1)) {
			erg = berechneAufwandInEuro(kosten);
			kosten.setArbeitsaufwandIstInEuro(1);
		} else {
			erg = berechneAufwandInStd(kosten);
			kosten.setArbeitsaufwandIstInEuro(0);
		}
		kosten.setArbeitsaufwand(erg);
		updateKosten(kosten);
		return null;
	}

	/**
	 * Delete.
	 * 
	 * @param kosten
	 *            the kosten
	 * @return the string
	 */
	
	private void deleteJobbearbeitersFromJob(Kosten kosten) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		List<Jobbearbeiter> jobbearbeiters = job.getJobbearbeiters();
		Angestellte angestellte = kosten.getAngestellte();
		Jobbearbeiter jobbearbeiter = null;
		for (Jobbearbeiter j : jobbearbeiters) {
			if (j.getAngestellte().getId() == angestellte.getId()) {
				jobbearbeiter = j;
			}
		}
		jobbearbeiter = em.find(Jobbearbeiter.class, jobbearbeiter.getId());
		em.remove(jobbearbeiter);
		em.getTransaction().commit();
	}
	
	private void deleteKostenFromJob(Kosten kosten) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		job.removeKosten(kosten);
		em.merge(job);
		em.getTransaction().commit();
	}
	
	public String delete(Kosten kosten) {
		deleteJobbearbeitersFromJob(kosten);
		deleteKostenFromJob(kosten);
		return null;
	}
	

}
