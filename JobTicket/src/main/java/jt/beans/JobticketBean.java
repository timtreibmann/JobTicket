package jt.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import jt.entities.Job;
import jt.entities.Kunde;

@RequestScoped
@Named
public class JobticketBean {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	@Inject
	private Job job;

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

	public String saveJobticket() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Kunde k = kundenBean.findKundenByID(selectedKundeId);
		System.out.println("KundenID: " + k.getId());
		job.setKunde(k);
		em.merge(job);
	//	System.out.println(k);
		em.getTransaction().commit();
		return "jobticket_bearbeitung.xhtml";
	}

}
