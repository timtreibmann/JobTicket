/*
 *  Copyright (C) 2014  Jan Müller, Tim Treibmann
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jt.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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

/**
 * Diese Klasse stellt die Anwendungslogik für die "jobticket_aufwand.xhtml"
 * bereit. Sie dient zum Ermitteln der Kosten eines Jobs und zum Verwalten der
 * Mitarbeiter die zu einem Job gehören.
 * 
 * @author Jan Müller
 * @author Tim Treibmann
 */
@Named
@RequestScoped
public class AufwandBean {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;
	@Inject
	private Kosten kosten;
	private int selectedAngestellteId;
	@Inject
	@AktuellerJob
	private Job job;
	private double gesamtKosten;
	private boolean istAufwandInEuro;
	@Inject
	OptionenBean options;
	@Inject
	private AktuellerJobBean aktuellerJobBean;

	@PostConstruct
	private void init() {
		if (aktuellerJobBean.isIstNeuesTicket()) {
			selectedAngestellteId = options.getSelectedAngestellterId();
			saveKosten();
			aktuellerJobBean.setIstNeuesTicket(false);
		}
	}

	public String saveKosten() {

		try {
			em = entityManagerFactory.createEntityManager();
			Angestellte angestellte = em.find(Angestellte.class,
					selectedAngestellteId);
			if (!istAngestellterVorhanden(angestellte)) {
				em.getTransaction().begin();
				kosten.setArbeitsaufwand(0);
				kosten.setArbeitsaufwandIstInEuro(0);
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
		} catch (NullPointerException e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(
					"Bitte Angestellten auswählen!"));
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
		em.merge(kosten);
		FacesContext fc = FacesContext.getCurrentInstance();
		kosten.setKommentar(kosten.getKommentar()+" --zuletzt editiert von: "+fc.getExternalContext().getRemoteUser());
		em.getTransaction().commit();
		
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
		System.out.println(kosten);
		System.out.println("ASD" + kosten.getArbeitsaufwand());
		if (!(kosten.getArbeitsaufwandIstInEuro() == 1)) {
			erg = berechneAufwandInEuro(kosten);
			kosten.setArbeitsaufwandIstInEuro(1);
		} else {
			erg = berechneAufwandInStd(kosten);
			kosten.setArbeitsaufwandIstInEuro(0);
		}
		System.out.println(erg);
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
		berechneGesamtkosten(kosten);
		return gesamtKosten;
	}

	public int getSelectedAngestellteId() {
		return selectedAngestellteId;
	}

	public void setSelectedAngestellteId(int selectedAngestellteId) {
		this.selectedAngestellteId = selectedAngestellteId;
	}

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

}
