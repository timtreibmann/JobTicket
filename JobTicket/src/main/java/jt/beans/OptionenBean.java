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
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import jt.entities.Angestellte;

/**
 * Diese Klasse speichert die vom Nutzer bestimmten Einstellungen für die Session.
 * 
 * @author Jan Müller
 * @author Tim Treibmann
 */


@SessionScoped
@Named
public class OptionenBean implements Serializable {
	
	@Inject
	private transient EntityManagerFactory entityManagerFactory;
	private transient EntityManager em;
	
	private static final long serialVersionUID = 1L;
	private boolean zeigeAllesAufEinerSeite;
	private boolean versteckeFertigeJobs;
	private boolean filtereNachMitarbeiter;
	private int selectedAngestellterId;
	private int angemeldeterMitarbeiterId;

	@PostConstruct
	private void init() {
		angemeldeterMitarbeiterId = findeAngemeldetenMitarbeiterId();;
		selectedAngestellterId = angemeldeterMitarbeiterId;
		zeigeAllesAufEinerSeite = true;
		versteckeFertigeJobs = true;
		filtereNachMitarbeiter = true;
	}
	
	private int findeAngemeldetenMitarbeiterId() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String user = fc.getExternalContext().getRemoteUser();
		int id = -1;
		em = entityManagerFactory.createEntityManager();
		Query query = em
				.createQuery("SELECT j FROM Angestellte j where j.loginName = :username");
		query.setParameter("username", user);
		List<Angestellte> angestellteListe = query.getResultList();
		if (angestellteListe.size() > 0) {
			id = angestellteListe.get(0).getId();
		}
		return id;

	}

	public boolean isZeigeAllesAufEinerSeite() {
		return zeigeAllesAufEinerSeite;
	}

	public void setZeigeAllesAufEinerSeite(boolean zeigeAllesAufEinerSeite) {
		this.zeigeAllesAufEinerSeite = zeigeAllesAufEinerSeite;
	}

	public boolean isVersteckeFertigeJobs() {
		return versteckeFertigeJobs;
	}

	public void setVersteckeFertigeJobs(boolean versteckeFertigeJobs) {
		this.versteckeFertigeJobs = versteckeFertigeJobs;
	}

	public boolean isFiltereNachMitarbeiter() {
		return filtereNachMitarbeiter;
	}

	public void setFiltereNachMitarbeiter(boolean filtereNachMitarbeiter) {
		this.filtereNachMitarbeiter = filtereNachMitarbeiter;
	}

	public int getSelectedAngestellterId() {
		return selectedAngestellterId;
	}

	public void setSelectedAngestellterId(int selectedAngestellterId) {
		System.out.println("SEEEEEEEEEEEEEET"+selectedAngestellterId);
		this.selectedAngestellterId = selectedAngestellterId;
	}

	public int getAngemeldeterMitarbeiterId() {
		return angemeldeterMitarbeiterId;
	}

	public void setAngemeldeterMitarbeiterId(int angemeldeterMitarbeiterId) {
		this.angemeldeterMitarbeiterId = angemeldeterMitarbeiterId;
	}

}
