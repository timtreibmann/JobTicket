package jt.beans;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import jt.entities.Job;
import jt.entities.Produkteigenschaften;


public class JobticketRepository {
	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;
	
	public Job saveJob(Job job) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(job);
		em.getTransaction().commit();		
		return job;
	}
	
	public Produkteigenschaften saveProdukteigenschaften(Produkteigenschaften produkteigenschaften) {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(produkteigenschaften);
		em.getTransaction().commit();		
		return produkteigenschaften;
	}
	

}
