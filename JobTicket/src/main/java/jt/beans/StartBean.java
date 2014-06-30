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

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.primefaces.context.RequestContext;

import jt.annotations.AktuellerJob;
import jt.entities.Job;
import jt.entities.Kunde;

/**
 * Diese Klasse stellt die Anwendungslogik für die "start.xhtml" bereit. Sie
 * dient zum Filtern, Sortieren, Erzeugen und Löschen von Jobtickets.
 * 
 * @author Jan Müller
 * @author Tim Treibmann
 * @author Marcus Wanka
 */

@RequestScoped
@Named
public class StartBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Job> filteredJobs;

	@Inject
	private EntityManagerFactory entityManagerFactory;

	private EntityManager em;

	@Inject
	OptionenBean options;

	@Inject
	@AktuellerJob
	private Job aktuellerJob;

	@Inject
	private OptionenBean optionen;

	@Inject
	private AktuellerJobBean aktuellerJobBean;

	private List<Job> jobs;

	/**
	 * Initialisierungsmethode der Bean wo die Eigenschaft jobs initialisiert
	 * wird.
	 */
	@PostConstruct
	private void init() {
		if (jobs == null) {
			queryJobs();
		}
	}

	public List<Job> getFilteredJobs() {
		return filteredJobs;
	}

	public void setFilteredJobs(List<Job> filteredJobs) {
		this.filteredJobs = filteredJobs;
	}

	/**
	 * Erstellt einen neuen Job und initialisiert das Erstellungsdatum, ohne
	 * direkt einen neuen Eintrag in der Datenbank zu erstellen
	 * 
	 * @return Je nachdem ob der User alles auf einer Seite sehen will oder
	 *         nicht wird ein anderer Datei-name zurückgegeben
	 */
	public String createJobticket() {
		aktuellerJob = new Job();
		aktuellerJobBean.setJob(aktuellerJob);
		FacesContext fc = FacesContext.getCurrentInstance();
		aktuellerJob.setErsteller(fc.getExternalContext().getRemoteUser());
		Date d = new Date();
		aktuellerJob.setErstellDatum(d);
		aktuellerJobBean.setIstNeuesTicket(true);
		aktuellerJobBean.setUnsavedChanges(true);
		if (optionen.isZeigeAllesAufEinerSeite()) {
			return "ticketanzeige.xhtml";
		} else {
			return "jt_main.xhtml";
		}
	}

	public List<Job> getJobs() {
		return jobs;
	}

	/**
	 * Query methode die die Eigenschaft jobs mit einer Liste von Jobs aus der
	 * Datenbank füllt.
	 */
	public void queryJobs() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createQuery("SELECT b FROM Job b");
		if (optionen.isFiltereNachMitarbeiter()) {
			if (optionen.isVersteckeFertigeJobs()) {
				query = findUnfinishedJobsByAngestellten();

			} else {
				query = findJobByAngestellten();
			}
		} else {
			if (optionen.isVersteckeFertigeJobs()) {
				query = em
						.createQuery("SELECT b FROM Job b WHERE b.fortschritt < 100");
			}
		}
		@SuppressWarnings("unchecked")
		List<Job> jobListe = query.getResultList();
		em.getTransaction().commit();
		if (jobListe == null) {
			jobListe = new ArrayList<Job>();
		}
		jobs = jobListe;
	}

	/**
	 * Sucht in der Datenbank nach allen Jobs an denen der ausgewählte
	 * Mitarbeiter arbeitet.
	 * 
	 * @return Query für die gesuchten Jobs
	 */
	private Query findJobByAngestellten() {
		Query query = em
				.createQuery("SELECT j.job FROM Jobbearbeiter j where j.angestellte.id = :id");
		query.setParameter("id", optionen.getSelectedAngestellterId());
		return query;
	}

	/**
	 * Sucht in der Datenbank nach allen nicht fertigen Jobs.
	 * 
	 * @return Query für die gesuchten Jobs
	 */
	private Query findUnfinishedJobsByAngestellten() {
		Query query = em
				.createQuery("SELECT j.job FROM Jobbearbeiter j where j.angestellte.id = :id and j.job.fortschritt<100");
		query.setParameter("id", optionen.getSelectedAngestellterId());
		return query;
	}

	/**
	 * Sucht in der Datenbank nach einem Job über seine id.
	 * 
	 * @param id
	 *            Id des zu findenden Jobs
	 * @return Job dessen id übergeben wurde
	 */
	public Job findJobByID(int id) {
		return em.find(Job.class, id);
	}

	/**
	 * Löscht einen Job aus der Datenbank.
	 * 
	 * @return null
	 */
	public String deleteJob() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Job job = aktuellerJobBean.getJob();
		job = em.merge(job);
		em.remove(job);
		em.getTransaction().commit();
		resetJob();
		return null;
	}

	/**
	 * ButtonListener methode die einen Dialog öffnet sofern es ungespeicherte
	 * Änderungen gibt und ansonsten die Client redirected.
	 * 
	 * @param target
	 *            Ziel-Datei die über den Button erreicht werden sollte als
	 *            String
	 * @return Zielpfad, der vom Browser ausgewertet wird
	 */
	public void checkUnsavedChanges(String target) {
		if (aktuellerJobBean.isUnsavedChanges()) {
			String dialog;
			switch (target) {
			case "start.xhtml":
				dialog = "reminder1";
				break;
			case "jobticket_overview.xhtml":
				dialog = "reminder2";
				break;
			default:
				dialog = "";
			}
			if (!dialog.equals("")) {
				RequestContext.getCurrentInstance().execute(
						"PF('" + dialog + "').show();");
			}
		} else {
			if (target != null) {
				if (target.equals("start.xhtml")) {
					resetJob();
				} else {
					goToPage(target);
				}
			}
		}
	}

	/**
	 * Setzt den aktuellen Job auf null und schickt den Cliet zurück auf die
	 * start.xhtml
	 * 
	 * @return null
	 */
	public String resetJob() {
		aktuellerJobBean.setJob(null);
		goToPage("start.xhtml?faces-redirect=true");
		aktuellerJobBean.setUnsavedChanges(false);
		return null;
	}

	/**
	 * Holt die alte Version des aktuellen Jobs aus der Datenbank und
	 * überschreibt die aktuelle und schickt den client dann auf die
	 * Overviewpage.
	 * 
	 * @return
	 */
	public String reloadJob() {
		aktuellerJobBean.setJob(findJobByID(aktuellerJobBean.getJob().getId()));
		goToPage("jobticket_overview.xhtml?faces-redirect=true");
		aktuellerJobBean.setUnsavedChanges(false);
		return null;
	}

	/**
	 * Stellt den Link zur Logout-Page bereit und invalidiert die Session.
	 * 
	 * @return "/logout.xhtml?faces-redirect=true"
	 */
	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.invalidateSession();
		return "/logout.xhtml?faces-redirect=true";
	}

	/**
	 * Event-Handler der bei der Selection einer Zeile in der Jobtabelle
	 * ausgeführt wird. <schickt den client auf die Job-Overview des
	 * ausgewählten Jobs.
	 */
	public void onRowSelect() {
		aktuellerJobBean.setIstNeuesTicket(false);
		goToPage("jobticket_overview.xhtml?faces-redirect=true");
	}

	/**
	 * Wandelt ein Date-Objekt in einen kurzen String im Format 'dd.MM.yyyy' um.
	 * 
	 * @param date
	 *            Umzuwandelndes Dateobjekt
	 * @return Datum im Format dd.MM.yyyy
	 */
	public String customFormatDate(Date date) {
		if (date != null) {
			DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			return format.format(date);
		}
		return "";
	}

	/**
	 * Schickt die Page dessen Name übergeben wurde an den Client.
	 * 
	 * @param Name
	 *            der zu ladenden Datei in form von:
	 *            "<Dateiname>.xhtml?faces-redirect=true".
	 */
	private void goToPage(String page) {
		ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext
				.getCurrentInstance().getApplication().getNavigationHandler();

		configurableNavigationHandler.performNavigation(page);
	}

	/**
	 * Filtermethode für die Kunden damit man auch nach dem Kürzel suchen kann.
	 * 
	 * @param k
	 *            vom Datatable übergebener Kunde
	 * @return String der vom Datatabe zum filtern benutzt wird
	 */
	public String customKundenFilter(Kunde k) {
		String filter = "";
		if (k != null) {
			filter = k.getName() + k.getKundenkuerzel();
		}
		return filter;
	}
}
