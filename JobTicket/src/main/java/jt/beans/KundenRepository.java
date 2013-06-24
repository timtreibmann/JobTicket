package jt.beans;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import jt.entities.Kunde;


public class KundenRepository {
	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;
	
	public Kunde saveKunde(Kunde kunde) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(kunde);
		em.getTransaction().commit();		
		return kunde;
	}

	public List<Kunde> findKunden() {
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
	
	public Kunde findKundenByID(int id) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Kunde k = em.find(Kunde.class, id);
		//List<Kunde> kundenListe = query.getResultList();
		em.getTransaction().commit();	
		return k;
	}

	public void deleteKunde(Kunde kunde) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		kunde = em.merge(kunde);
		em.remove(kunde);
		em.getTransaction().commit();	
	}
	
	public void updateKunde(Kunde kunde) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Kunde k = em.find(Kunde.class, kunde.getId());
		k.setName(kunde.getName());
		k.setAdresse(kunde.getAdresse());
		k.setTelefon(kunde.getTelefon());
		k.setKundenkuerzel(kunde.getKundenkuerzel());
		em.getTransaction().commit();	
	}
	
	public List<String> findKuerzelByName(String kundenName) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em.createQuery("SELECT b.kundenkuerzel FROM Kunde");
		//query.setParameter("name", kundenName);
		@SuppressWarnings("unchecked")
		List<String> list = query.getResultList();
		em.getTransaction().commit();	
		return list;
	}
	

}

