package jt.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import jt.entities.Job;
import jt.entities.Kunde;
import jt.entities.Produkteigenschaften;

@RequestScoped
@Named
public class JobticketBean {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	@Inject
	private Job job;
	

	@Inject
	private Produkteigenschaften produkteigenschaften;

	@Inject
	KundenBean kundenBean;

	public int getSelectedKundeId() {
		return selectedKundeId;
	}

	private int selectedKundeId;

	public void setSelectedKundeId(int selectedKundeId) {
		this.selectedKundeId = selectedKundeId;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
	
	public Produkteigenschaften getProdukteigenschaften() {
		return produkteigenschaften;
	}

	public void setProdukteigenschaften(Produkteigenschaften produkteigenschaften) {
		this.produkteigenschaften = produkteigenschaften;
	}

	public String saveJobticket() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Kunde k = kundenBean.findKundenByID(selectedKundeId);
		job.setKunde(k);
		em.merge(job);
		em.getTransaction().commit();
		return "jobticket_produktbeschreibung.xhtml";
	}
	
	public String saveProdukteigenschaften() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		System.out.println("Produkteigenschaften " + produkteigenschaften);
		em.persist(produkteigenschaften);
		System.out.println("job " + this.job);
		job.addProdukteigenschaften(produkteigenschaften);
		em.merge(job);		
		em.getTransaction().commit();
		return "jobticket_produktbeschreibung.xhtml";
	}

}
