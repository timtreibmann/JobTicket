/*
 *  Copyright (C) 2014  Jan Müller, Tim Treibmann, Marus Wanka
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

import jt.entities.Angestellte;
import jt.entities.Job;

/**
 * Diese Klasse stellt die Anwendungslogik für die "angestellte_edit.xhtml" und
 * "angestellte_table.xhtml" zur Verfügung. Sie dient zur Verwaltung von
 * Mitarbeiten.
 * 
 * @author Jan Müller
 * @author Tim Treibmann
 * @author Marcus Wanka
 */
@Named
@RequestScoped
public class AngestellteBean {

	@Inject
	private Angestellte angestellte;

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	/**
	 * Holt die Angestellten aus der Datenbank und gibt sie dann zurück.
	 * 
	 * @return
	 */
	public List<Angestellte> getAngestelltes() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em.createQuery("SELECT b FROM Angestellte b");
		@SuppressWarnings("unchecked")
		List<Angestellte> angestellteListe = query.getResultList();
		if (angestellteListe == null) {
			angestellteListe = new ArrayList<Angestellte>();
		}
		em.getTransaction().commit();
		return angestellteListe;
	}

	/**
	 * Sucht über eine id den dazugehörigen Angestellten aus der Datenbank
	 * 
	 * @param id
	 *            Die id des gesuchten Angestellten.
	 * @return Den Angestellten dessen id übergeben wurde.
	 */
	public Angestellte findAngestelltenByID(int id) {
		return em.find(Angestellte.class, id);
	}

	/**
	 * Sucht in der Datenbank nach den Jobs an denen der übergebene Mitarbeiter
	 * arbeitet.
	 * 
	 * @param angestellte
	 *            Angestellter dessen Jobs gesucht werden sollen.
	 * @return Liste an Jobs an denen Der übergebene Angestellte arbeitet.
	 */
	private List<Job> getJobsFromAngestellten(Angestellte angestellte) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em
				.createQuery("SELECT j.job FROM Jobbearbeiter j where j.angestellte.id = :id");
		query.setParameter("id", angestellte.getId());
		List<Job> jobListe = query.getResultList();
		em.getTransaction().commit();
		return jobListe;
	}

	/**
	 * Löscht einen Angestellten aus der Datenbank.
	 * 
	 * @param angestellte
	 *            Zu löschender Angestellter
	 * @return null
	 */
	public String delete(Angestellte angestellte) {
		List<Job> jobsVomAngestellten = getJobsFromAngestellten(angestellte);
		if (jobsVomAngestellten.size() == 0) {
			em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			angestellte = em.merge(angestellte);
			em.remove(angestellte);
			em.getTransaction().commit();
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							"Angestellter kann nicht gelöscht werden, da er folgenden Jobs zugeordnet ist: "
									+ jobsVomAngestellten));

		}
		return null;
	}

	/**
	 * Speichert einen neuen Angestellten in der Datenbank ab.
	 * 
	 * @return "angestellte_add.xhtml"
	 */
	public String saveAngestellte() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(angestellte);
		em.getTransaction().commit();
		resetAngestellte();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(
				"Daten erfolgreich gespeichert!", ""));
		return "angestellte_add.xhtml";

	}

	/**
	 * Resettet die Eigenschaft angestellte.
	 */
	private void resetAngestellte() {
		angestellte = new Angestellte();
		angestellte.setNachname("");
		angestellte.setVorname("");
		angestellte.setStundenlohn(0);

	}

	/**
	 * Ruft die Bearbeitungsseite für den übergebenen Angestellten auf.
	 * 
	 * @param angestellte
	 *            Zu bearbeitender Angestellter
	 * @return "angestellte_edit.xhtml"
	 */
	public String editAngestellten(Angestellte angestellte) {
		this.angestellte = angestellte;
		return "angestellte_edit.xhtml";
	}

	/**
	 * Überschreibt einen vorhandenen Angestellten in der Datenbank. Der zu
	 * überschreibende Angestellte wird aus der Eigenschaft angestellte geholt.
	 * 
	 * @return null
	 */
	public String updateAngestellten() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(angestellte);
		em.getTransaction().commit();
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage("Daten erfolgreich gespeichert"));
		return null;
	}

	public Angestellte getAngestellte() {
		return angestellte;
	}

	public void setAngestellte(Angestellte angestellte) {
		this.angestellte = angestellte;
	}

}
