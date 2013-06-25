package jt.beans;


import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import jt.entities.Job;
import jt.entities.Kunde;
import jt.entities.Produkteigenschaften;

@Named
public class JobticketBean {

	@Inject
	private JobticketRepository jobticketRepository;
	
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
		
	//	kundenBean.findKundenByID(selectedKundeId).addJob(job);
		Kunde k = kundenBean.findKundenByID(selectedKundeId);
		k.addJob(job);
		kundenBean.saveKunde();
		jobticketRepository.saveJob(job);
		
		
		return "jobticket_bearbeitung.xhtml";
	}
	
	public List<String> findKuerzelByName() {
		String name = job.getKunde().getName();
		List<String> list = kundenBean.findKuerzelByName(name);
		return list;
	}
	
	

	
}
