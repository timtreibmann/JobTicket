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
import jt.entities.Kosten;

// TODO: Auto-generated Javadoc
/**
 * The Class AufwandBean.
 * @author jan & tim
 */
@Named
@RequestScoped
public class AufwandBean {
	
	/** The entity manager factory. */
	@Inject
	private EntityManagerFactory entityManagerFactory;
	
	/** The em. */
	private EntityManager em;

	/** The kosten. */
	@Inject
	private Kosten kosten;

	/** The angestellte bean. */
	@Inject
	private AngestellteBean angestellteBean;

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
	 * @param aufwandInEuro the new aufwand in euro
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
	 * @param kosten the new kosten
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
		return gesamtKosten;
	}

	/** The selected angestellte id. */
	private int selectedAngestellteId;

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
	 * @param selectedAngestellteId the new selected angestellte id
	 */
	public void setSelectedAngestellteId(int selectedAngestellteId) {
		this.selectedAngestellteId = selectedAngestellteId;
	}

	/**
	 * Checks if is t angestellter vorhanden.
	 *
	 * @param angestellte the angestellte
	 * @return true, if is t angestellter vorhanden
	 */
	private boolean istAngestellterVorhanden(Angestellte angestellte) {

		List<Kosten> kostenListe = getKostenFromJob();

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
			kosten.setAngestellte(angestellte);
			kosten.setJob(job);
			em.merge(job);
			em.persist(kosten);
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
	 * @param kosten the kosten
	 * @return the string
	 */
	public String updateKosten(Kosten kosten) {

		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Kosten k = em.find(Kosten.class, kosten.getId());
		k.setArbeitsaufwand(kosten.getArbeitsaufwand());
		k.setArbeitsaufwandIstInEuro(kosten.getArbeitsaufwandIstInEuro());

		em.getTransaction().commit();
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage("Daten erfolgreich gespeichert"));
		return null;
	}

	/**
	 * Berechne aufwand in std.
	 *
	 * @param kosten the kosten
	 * @return the double
	 */
	private double berechneAufwandInStd(Kosten kosten) {

		return kosten.getArbeitsaufwand()
				/ kosten.getAngestellte().getStundenlohn();

	}

	/**
	 * Berechne aufwand in euro.
	 *
	 * @param kosten the kosten
	 * @return the double
	 */
	private double berechneAufwandInEuro(Kosten kosten) {
		return kosten.getArbeitsaufwand()
				* kosten.getAngestellte().getStundenlohn();

	}

	/**
	 * Rechne um.
	 *
	 * @param kosten the kosten
	 * @return the string
	 */
	public String rechneUm(Kosten kosten) {
		System.out.println("RECHNE");
		double erg;
		System.out.println(kosten.getArbeitsaufwandIstInEuro());
		if (!(kosten.getArbeitsaufwandIstInEuro() == 1)) {
			erg = berechneAufwandInEuro(kosten);
			kosten.setArbeitsaufwandIstInEuro(1);

		} else {
			erg = berechneAufwandInStd(kosten);
			kosten.setArbeitsaufwandIstInEuro(0);
		}
		kosten.setArbeitsaufwand(erg);
		System.out.println(erg);
		updateKosten(kosten);
		return null;
	}

	/**
	 * Gets the kosten from job.
	 *
	 * @return the kosten from job
	 */
	public List<Kosten> getKostenFromJob() {
		em = entityManagerFactory.createEntityManager();

		em.getTransaction().begin();
		int id = job.getId();

		final Query query = em
				.createQuery("SELECT p FROM Kosten p WHERE p.job.id = :id");
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Kosten> kostenListe = query.getResultList();
		if (kostenListe == null) {
			kostenListe = new ArrayList<Kosten>();
		}

		em.getTransaction().commit();

		try {
			// Gesamtkosten berechnen
			gesamtKosten = 0;
			double betrag = 0;
			for (Kosten k : kostenListe) {
				if (k.getArbeitsaufwandIstInEuro() == 0) {
					betrag = berechneAufwandInEuro(k);
				} else {
					betrag = k.getArbeitsaufwand();
				}
				gesamtKosten += betrag;
			}
			
		} catch (NullPointerException e) {

		}
		return kostenListe;
	}

	/**
	 * Delete.
	 *
	 * @param kosten the kosten
	 * @return the string
	 */
	public String delete(Kosten kosten) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		kosten = em.merge(kosten);
		em.remove(kosten);
		em.getTransaction().commit();
		return null;
	}

}
