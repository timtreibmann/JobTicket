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
import jt.entities.Job;
import jt.entities.Kunde;

/**
 * Diese Klasse stellt die Anwendungslogik für die "jobticket_main.xhtml"
 * bereit.
 * 
 * 
 * @author Jan Müller
 * @author Tim Treibmann
 * @author Marcus Wanka
 */

@RequestScoped
@Named
public class JobBean {
	@Inject
	@AktuellerJob
	private Job job;

	@Inject
	AktuellerJobBean aktuellerJobBean;

	private int selectedKundeId;
	private String kuerzel;

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	/**
	 * Initialisierungsmethode der Bean in der versucht wird,
	 * die Eigenschaften kuerzel und selected KundeId zu initialisieren.
	 */
	@PostConstruct
	private void init() {
		try {
			kuerzel = job.getKunde().getKundenkuerzel();
			selectedKundeId = job.getKunde().getId();
		} catch (NullPointerException e) {
			System.out.println("nullpo");
		}
	}

	/**
	 * Speichert den Job in der Datenbank ab.
	 */
	private void saveJob() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		if (aktuellerJobBean.isIstNeuesTicket()) {
			em.persist(job);
			aktuellerJobBean.setIstNeuesTicket(false);
		} else {
			em.merge(job);
		}
		aktuellerJobBean.setUnsavedChanges(false);
		em.getTransaction().commit();
	}

	/**
	 * Die methode die von den xhtml Seiten aufgerufen wird,
	 * um den job zu speichern und dann die Seite zu verlassen.
	 * @param target Name der Datei die nach dem Speichen geladen werden soll oder null wenn eine geladen werden soll.
	 * @return Der übergebene Parameter Target wird wieder an den Client zurück gegeben.
	 */
	public String saveJobAndRedirect(String target) {
		if ((job.getName()) == null && job.getKunde() == null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(
					"Bevor gespeichert werden kann, muss entweder ein Jobname oder Kunde angegeben werden."));
		} else {
			saveJob();
			if (target.equals("start.xhtml")) {
				aktuellerJobBean.setJob(null);
			}
		}
		return target;
	}

	/**
	 * Sucht den Kunden in der Datenbank und speichert das Ergebnis im Job
	 */
	public void findeKunde() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		if (selectedKundeId != 0) {
			Kunde k = em.find(Kunde.class, selectedKundeId);
			job.setKunde(k);
			kuerzel = k.getKundenkuerzel();
		}
		aktuellerJobBean.setUnsavedChanges(true);
		em.getTransaction().commit();
	}

	/**
	 * Sucht nach einem Kunde über sein Kürzel in der Datenbank und speichert
	 * den Namen im Job.
	 */
	public void findeKundeUeberKuerzel() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em
				.createQuery("SELECT b FROM Kunde b WHERE b.kundenkuerzel LIKE :kuerzel");
		query.setParameter("kuerzel", kuerzel);

		List<Kunde> kundenListe = query.getResultList();
		em.getTransaction().commit();
		if (kundenListe.size() > 0) {
			setSelectedKundeId(kundenListe.get(0).getId());
			findeKunde();
		}
	}

	public String getKuerzel() {
		return kuerzel;
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public int getSelectedKundeId() {
		return selectedKundeId;
	}

	public void setSelectedKundeId(int selectedKundeId) {
		this.selectedKundeId = selectedKundeId;
	}
}
