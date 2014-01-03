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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import jt.annotations.AktuellerJob;
import jt.entities.Angestellte;
import jt.entities.Job;

/**
 * Diese Klasse stellt die Anwendungslogik für die "start.xhtml" bereit. 
 * Sie dient zum Filtern, Sortieren, Erzeugen und Löschen von Jobtickets.
 * 
 * @author Jan Müller
 * @author Tim Treibmann
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
	@AktuellerJob
	private Job aktuellerJob;

	@Inject
	private OptionenBean optionen;

	@Inject
	private AktuellerJobBean aktuellerJobBean;

	public String refreshFilter() {
		filteredJobs = getJobs();
		return "start.xhtml";
	}

	public List<Job> getFilteredJobs() {
		return filteredJobs;
	}

	public void setFilteredJobs(List<Job> filteredJobs) {
		this.filteredJobs = filteredJobs;
	}

	public String editJob(Job job) {
		aktuellerJobBean.setJob(job);

		if (optionen.isZeigeAllesAufEinerSeite()) {
			return "ticketanzeige.xhtml";
		} else {
			return "jt_main.xhtml";
		}
	}

	public String createJobticket() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		aktuellerJob = new Job();
		aktuellerJobBean.setJob(aktuellerJob);
		FacesContext fc = FacesContext.getCurrentInstance();
		aktuellerJob.setErsteller(fc.getExternalContext().getRemoteUser());
		Date d = new Date();
		aktuellerJob.setErstellDatum(d);
		em.persist(aktuellerJob);
		em.getTransaction().commit();
		refreshFilter();
		if (optionen.isZeigeAllesAufEinerSeite()) {
			return "ticketanzeige.xhtml";
		} else {
			return "jt_main.xhtml";
		}

	}

	public List<Job> getJobs() {
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
			} else {

			}

		}
		@SuppressWarnings("unchecked")
		List<Job> jobListe = query.getResultList();

		if (jobListe == null) {
			jobListe = new ArrayList<Job>();
		}
		em.getTransaction().commit();
		return jobListe;

	}

	private Query findJobByAngestellten() {
		Query query = em
				.createQuery("SELECT j.job FROM Jobbearbeiter j where j.angestellte.id = :id");
		query.setParameter("id", optionen.getSelectedAngestellterId());
		return query;
	}

	private Query findUnfinishedJobsByAngestellten() {
		Query query = em
				.createQuery("SELECT j.job FROM Jobbearbeiter j where j.angestellte.id = :id and j.job.fortschritt<100");
		query.setParameter("id", optionen.getSelectedAngestellterId());
		return query;
	}

	public Job findJobByID(int id) {
		return em.find(Job.class, id);
	}

	public String delete(Job job) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		job = em.merge(job);
		em.remove(job);
		em.getTransaction().commit();
		return null;
	}

	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.invalidateSession();
		return "/logout.xhtml?faces-redirect=true";
	}


}