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

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import jt.entities.Job;
import jt.entities.Kunde;

/**
 * Diese Klasse stellt die Anwendungslogik für die "kunden_edit.xhtml", "kunden_table.xhtml"
 * bereit. Sie dient zur Verwaltung von Kunden.
 * 
 * @author Jan Müller
 * @author Tim Treibmann
 * @author Marcus Wanka
 */

@Named
@RequestScoped
public class KundenBean {

	/** The kunde. */
	@Inject
	private Kunde kunde;

	/** The entity manager factory. */
	@Inject
	private EntityManagerFactory entityManagerFactory;

	/** The em. */
	private EntityManager em;

	/**
	 * Gets the kunden.
	 * 
	 * @return the kunden
	 */
	public List<Kunde> getKunden() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em.createQuery("SELECT b FROM Kunde b");
		@SuppressWarnings("unchecked")
		List<Kunde> kundenListe = query.getResultList();
		if (kundenListe == null) {
			kundenListe = new ArrayList<Kunde>();
		}
		em.getTransaction().commit();
		return kundenListe;
	}

	/**
	 * Find kunden by id.
	 * 
	 * @param id
	 *            the id
	 * @return the kunde
	 */
	public Kunde findKundenByID(int id) {
		return em.find(Kunde.class, id);
	}

	/**
	 * Gets the kunde.
	 * 
	 * @return the kunde
	 */
	public Kunde getKunde() {
		return kunde;
	}

	/**
	 * Sets the kunde.
	 * 
	 * @param kunde
	 *            the kunde to set
	 */
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	/**
	 * Edits the kunde.
	 * 
	 * @param kunde
	 *            the kunde
	 * @return the string
	 */
	public String editKunde(Kunde kunde) {
		this.kunde = kunde;
		return "kunden_edit.xhtml";
	}

	/**
	 * Save kunde.
	 * 
	 * @return the string
	 */
	public String saveKunde(Kunde kunde) {
		FacesContext context = FacesContext.getCurrentInstance();
		em = entityManagerFactory.createEntityManager();
		
		if (!istKuerzelVorhanden(kunde.getKundenkuerzel())) {
			em.getTransaction().begin();
			em.persist(kunde);	
			em.getTransaction().commit();
			resetKunde();
			context.addMessage(null, new FacesMessage(
					"Daten erfolgreich gespeichert!", ""));
		} else {
			context.addMessage(null, new FacesMessage(
					"Kundenkürzel ist bereits vorhanden", ""));
		}		
		return "kunden_add.xhtml";
	}

	/**
	 * Resettet die Eigenschaften der Eigenschaft kunde auf leere Strings.
	 */
	private void resetKunde() {
		this.kunde.setName("");
		this.kunde.setAdresse("");
		this.kunde.setKundenkuerzel("");
		this.kunde.setTelefon("");
	}

	/**
	 * Sucht in der Datenbank nach allen Jobs die für den übergebenen Kunden sind.
	 * @param kunde Kunde dessen Jobs gesucht werden.
	 * @return Liste von Jobs die für den übergebenen Kunden gemacht werden.
	 */
	private List<Job> getJobsFromKunde(Kunde kunde) {
		em = entityManagerFactory.createEntityManager();
		final Query query = em
				.createQuery("SELECT b FROM Job b WHERE b.kunde.id = :id");
		query.setParameter("id", kunde.getId());
		List<Job> jobListe = query.getResultList();
		return jobListe;
	}

	/**
	 * Delete.
	 * 
	 * @param kunde
	 *            the kunde
	 * @return the string
	 */
	public String delete(Kunde kunde) {
		List<Job> jobsVomKunde = getJobsFromKunde(kunde);
		if (jobsVomKunde.size() == 0) {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			kunde = em.merge(kunde);
			em.remove(kunde);
			em.getTransaction().commit();
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Kunde kann nicht gelöscht werden, da er folgenden Jobs zugeordnet ist: "
							+ jobsVomKunde));

		}
		return null;
	}

	
	private boolean istKuerzelVorhanden(String kuerzel) {
		Query query = em.createQuery(
				"SELECT b FROM Kunde b where b.kundenkuerzel = :kuerzel")
				.setParameter("kuerzel", kuerzel);
		@SuppressWarnings("unchecked")
		List<Kunde> kundenListe = query.getResultList();
		return kundenListe.size() > 0;
	}

	/**
	 * Update kunde.
	 * 
	 * @return the string
	 */
	public String updateKunde() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(kunde);
		em.getTransaction().commit();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(
				"Daten erfolgreich gespeichert!", "Kunde"));
		return null;
	}
}
