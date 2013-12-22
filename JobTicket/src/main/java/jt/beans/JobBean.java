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
 * Diese Klasse stellt die Anwendungslogik für die "jobticket_main.xhtml" bereit.
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
