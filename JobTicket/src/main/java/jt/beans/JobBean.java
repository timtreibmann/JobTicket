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

@RequestScoped
@Named
public class JobBean {
	@Inject
	@AktuellerJob
	private Job job;

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
