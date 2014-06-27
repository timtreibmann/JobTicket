/*
 *  Copyright (C) 2014  Jan Müller, Tim Treibmann, Marcus Wanka
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

import org.primefaces.context.RequestContext;

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
 * @author Marcus Wanka
 */
@Named
@RequestScoped
public class AufwandBean {
	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;
	@Inject
	@AktuellerJob
	private Job job;

	@Inject
	OptionenBean options;
	@Inject
	private AktuellerJobBean aktuellerJobBean;

	private int selectedAngestellteId;
	private double gesamtKosten;
	private boolean istAufwandInEuro;
	private boolean initDone;

	/**
	 * Initialisierungsmethode der Bean, 
	 * die sofern der Aktuelle job ein neues Ticket ist den angemeldeten Mitarbeiter 
	 * dem Job hinzufügt und dann die Gesamtkosten berechnet.
	 */
	@PostConstruct
	private void init() {
		if (aktuellerJobBean.isIstNeuesTicket()) {
			selectedAngestellteId = options.getAngemeldeterMitarbeiterId();
			initRelations();
		}
		berechneGesamtkosten();
		initDone = true;
	}

	/**
	 * Initialisiert die Relationen zu den Kosten und dem Jobbearbeiter.
	 * Fügt dem Job einen Mitarbeiter hinzu.
	 * @return null
	 */
	public String initRelations() {
		Kosten kosten = new Kosten();
		em = entityManagerFactory.createEntityManager();
		Angestellte angestellte = em.find(Angestellte.class,
				selectedAngestellteId);
		if (!istAngestellterVorhanden(angestellte) && selectedAngestellteId != 0) {
			kosten.setAngestellte(angestellte);
			Jobbearbeiter jobbearbeiter = new Jobbearbeiter();
			jobbearbeiter.setAngestellte(angestellte);
			job.addJobbearbeiter(jobbearbeiter);
			angestellte.addKosten(kosten);
			job.addKosten(kosten);
			aktuellerJobBean.setUnsavedChanges(true);
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(
					"Angestellter wurde bereits hinzugefügt!"));
		}
		selectedAngestellteId = 0;
		return null;
	}

	/**
	 * Berechnet die Gesamtkosten aus dem Aufwand der verschiedenen Mitarbeiter.
	 */
	private void berechneGesamtkosten() {
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
	 *            Übergebene Kosten die umgerechnet werden sollen.
	 * @return Den Aufwand in Stunden
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
	public double berechneAufwandInEuro(Kosten kosten) {
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
		List<Jobbearbeiter> jobbearbeiters = job.getJobbearbeiters();
		Angestellte angestellte = kosten.getAngestellte();
		Jobbearbeiter jobbearbeiter = null;
		for (Jobbearbeiter j : jobbearbeiters) {
			if (j.getAngestellte() != null) {
				if (j.getAngestellte().getId() == angestellte.getId()) {
					jobbearbeiter = j;
				}
			}
		}
		job.removeJobbearbeiter(jobbearbeiter);
		angestellte.removeJobbearbeiter(jobbearbeiter);
	}

	/**
	 * Löscht Kosten vom Job.
	 * @param kosten Zu löschende Kosten.
	 */
	private void deleteKostenFromJob(Kosten kosten) {
		job.removeKosten(kosten);
		kosten.getAngestellte();
	}

	/**
	 * Löscht Jobbearbeiter und Kosten vom Job.
	 * @param kosten zu löschende Kosten
	 * @return null
	 */
	public String delete(Kosten kosten) {
		deleteJobbearbeitersFromJob(kosten);
		deleteKostenFromJob(kosten);
		berechneGesamtkosten();
		aktuellerJobBean.setUnsavedChanges(true);
		return null;
	}

	public boolean isAufwandInEuro() {
		return istAufwandInEuro;
	}

	public void setAufwandInEuro(boolean aufwandInEuro) {
		this.istAufwandInEuro = aufwandInEuro;
	}

	public double getGesamtKosten() {
		return gesamtKosten;
	}

	public int getSelectedAngestellteId() {
		return selectedAngestellteId;
	}

	public void setSelectedAngestellteId(int selectedAngestellteId) {
		this.selectedAngestellteId = selectedAngestellteId;
	}

	/**
	 * Schaut im Job nach ob der übergebene Angestellte bereits am Job arbeitet.
	 * @param angestellte Zu überprüfender Angestellter
	 * @return Ob der angestellte vorhanden bereits ist (true) oder nicht (false).
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
	 * Getter-methode für initDone Variable.
	 * @return the initDone
	 */
	public boolean isInitDone() {
		return initDone;
	}
}
