package jt.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.myfaces.view.facelets.tag.jstl.core.CatchHandler;

import jt.annotations.AktuellerJob;
import jt.entities.Angestellte;
import jt.entities.Job;
import jt.entities.Kosten;
import jt.entities.Produkteigenschaften;

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

	private double gesamtKosten;

	private boolean istAufwandInEuro;

	public boolean isAufwandInEuro() {
		return istAufwandInEuro;
	}

	public void setAufwandInEuro(boolean aufwandInEuro) {
		this.istAufwandInEuro = aufwandInEuro;
	}

	public Kosten getKosten() {
		return kosten;
	}

	public void setKosten(Kosten kosten) {
		this.kosten = kosten;
	}

	public double getGesamtKosten() {
		return gesamtKosten;
	}

	private int selectedAngestellteId;

	public int getSelectedAngestellteId() {
		return selectedAngestellteId;
	}

	public void setSelectedAngestellteId(int selectedAngestellteId) {
		this.selectedAngestellteId = selectedAngestellteId;
	}

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

	public String saveKosten() {
		Angestellte angestellte = angestellteBean
				.findAngestelltenByID(selectedAngestellteId);
		if (!istAngestellterVorhanden(angestellte)) {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			System.out.println("NOCH NICHT VORHANDEN");
			kosten.setArbeitsaufwandInEuro(new BigDecimal(0));
			kosten.setArbeitsaufwandInStd(new BigDecimal(0));
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

	public String updateKosten(Kosten kosten) {

		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Kosten k = em.find(Kosten.class, kosten.getId());
		k.setArbeitsaufwandInEuro(kosten.getArbeitsaufwandInEuro());
		em.getTransaction().commit();
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage("Daten erfolgreich gespeichert"));
		return null;
	}

	public String rechneUm(Kosten kosten) {
		System.out.println("rechneUm");

		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		BigDecimal stundenlohn = kosten.getAngestellte().getStundenlohn();
		if (!istAufwandInEuro) {

			kosten.setArbeitsaufwandInEuro(kosten.getArbeitsaufwandInStd()
					.multiply(stundenlohn));

		} else {
			kosten.setArbeitsaufwandInStd(kosten.getArbeitsaufwandInEuro()
					.divide(stundenlohn));

		}
		em.getTransaction().commit();

		return null;
	}

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
			for (Kosten k : kostenListe) {
				gesamtKosten += k.getArbeitsaufwandInEuro().doubleValue();
			}
		} catch (NullPointerException e) {

		}
		return kostenListe;
	}

	public String delete(Kosten kosten) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		kosten = em.merge(kosten);
		em.remove(kosten);
		em.getTransaction().commit();
		return null;
	}

}
