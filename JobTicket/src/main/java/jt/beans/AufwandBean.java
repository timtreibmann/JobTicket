package jt.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import jt.annotations.AktuellerJob;
import jt.entities.Angestellte;
import jt.entities.Job;
import jt.entities.Kosten;
import jt.entities.Produkteigenschaften;

@Named
@RequestScoped
public class AufwandBean {
	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	@Inject
	private Kosten kosten;

	@Inject
	private AngestellteBean angestellteBean;

	@Inject
	@AktuellerJob
	private Job job;

	public Kosten getKosten() {
		return kosten;
	}

	public void setKosten(Kosten kosten) {
		this.kosten = kosten;
	}

	private int selectedAngestellteId;

	public int getSelectedAngestellteId() {
		return selectedAngestellteId;
	}

	public void setSelectedAngestellteId(int selectedAngestellteId) {
		this.selectedAngestellteId = selectedAngestellteId;
	}

	public String saveKosten() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Angestellte angestellte = angestellteBean
				.findAngestelltenByID(selectedAngestellteId);
		kosten.setAngestellte(angestellte);
		kosten.setJob(job);
		em.merge(job);
		em.persist(kosten);
		em.getTransaction().commit();
		return null;
	}

	public String updateKosten(Kosten kosten) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Kosten k = em.find(Kosten.class, kosten.getId());
		k.setArbeitsaufwandInEuro(kosten.getArbeitsaufwandInEuro());
		em.getTransaction().commit();
		return null;
	}

	public String rechneUm(Kosten kosten) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		BigDecimal stundenlohn = kosten.getAngestellte().getStundenlohn();
		System.out.println("STUNDENLOHN: " + stundenlohn);
		if (!kosten.getArbeitsaufwandInEuro().equals(new BigDecimal(0))) {
			BigDecimal aufwandEuros = kosten.getArbeitsaufwandInEuro();
			System.out.println(aufwandEuros.divide(stundenlohn, 2));

			kosten.setArbeitsaufwandInStd(aufwandEuros.divide(stundenlohn, 2));
		} else {
			System.out.println("blaa");
			if (kosten.getArbeitsaufwandInStd().intValue() != 0) {
				BigDecimal aufwandStunden = kosten.getArbeitsaufwandInStd();
				kosten.setArbeitsaufwandInEuro(aufwandStunden
						.multiply(stundenlohn));
			}
		}

		em.merge(kosten);
		em.getTransaction().commit();
		return null;
	}

	public List<Kosten> getAngestellteFromKosten() {
		em = entityManagerFactory.createEntityManager();

		em.getTransaction().begin();
		int id = job.getId();

		final Query query = em
				.createQuery("SELECT p FROM Kosten p WHERE p.job.id = :id");
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Kosten> angestellteListe = query.getResultList();
		if (angestellteListe == null) {
			angestellteListe = new ArrayList<Kosten>();
		}
		em.getTransaction().commit();
		return angestellteListe;
	}

	public String delete(Kosten kosten) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		kosten = em.merge(kosten);
		em.remove(kosten);
		em.getTransaction().commit();
		return null;
	}

}
