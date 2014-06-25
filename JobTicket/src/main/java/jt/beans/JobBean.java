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

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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
	 * Alte Methode die zum Speichern des Jobs in der Datenbank genutzt wurde
	 * und aufgerufen wurde wann immer etwas im Formular geändert wurde.
	 * 
	 * @return null
	 */
	public String updateJobticket() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		if (selectedKundeId != 0) {
			Kunde k = em.find(Kunde.class, selectedKundeId);
			job.setKunde(k);
			kuerzel = k.getKundenkuerzel();
		}
		em.merge(job);
		em.getTransaction().commit();
		return null;
	}

	/**
	 * Speichert den Job in der Datenbank ab.
	 */
	public String saveJob() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		if (aktuellerJobBean.isIstNeuesTicket()) {
			em.persist(job);
			em.getTransaction().commit();
			aktuellerJobBean.setIstNeuesTicket(false);
			return "start.xhtml";
		} else {
			em.merge(job);
		}
		em.getTransaction().commit();
		return null;
	}

	public String saveJobAndRedirect(String target){
		saveJob();
		if(target.equals("start.xhtml")){
			aktuellerJobBean.setJob(null);
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
		em.getTransaction().commit();
	}

	/**
	 * Sucht nach einem Kunde über sein Kürzel in der Datenbank 
	 * und speichert den Namen im Job.
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

	/**
	 * Alte Methode die zum Suchen nach einem Kunde über sein Kürzel verwendet
	 * wurde. Speichert im nachinein das Ergebnis in der Datenbank
	 */
	public void findKundenByKuerzelAndUpdateJobticket() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em
				.createQuery("SELECT b FROM Kunde b WHERE b.kundenkuerzel LIKE :kuerzel");
		query.setParameter("kuerzel", kuerzel);

		List<Kunde> kundenListe = query.getResultList();
		em.getTransaction().commit();
		if (kundenListe.size() > 0) {
			setSelectedKundeId(kundenListe.get(0).getId());
			updateJobticket();
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
