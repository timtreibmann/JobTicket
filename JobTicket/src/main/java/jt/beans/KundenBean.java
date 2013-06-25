package jt.beans;

import jt.entities.Kunde;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class KundenBean {
	@Inject
	KundenRepository kundenRepository;
	
	@Inject 
	private Kunde kunde;

	/**
	 * @return the blogEntries
	 */
	public List<Kunde> getKunden() {
		return kundenRepository.findKunden();
	}

	public List<String> findKuerzelByName(String name) {
		return kundenRepository.findKuerzelByName(name);
	}
	
	public Kunde findKundenByID(int id) {
		return kundenRepository.findKundenByID(id);
	}
	/**
	 * @return the kunde
	 */
	public Kunde getKunde() {
		return kunde;
	}

	/**
	 * @param kunde
	 *            the kunde to set
	 */
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}
	
	public String editKunde(Kunde kunde) {
		this.kunde = kunde;
		return "kunden_edit.xhtml";
	}

	public String saveKunde() {
		kundenRepository.saveKunde(kunde);
		return "kunden_add.xhtml";
	}

	public String delete(Kunde kunde) {
		kundenRepository.deleteKunde(kunde);
		return null;
	}

	public String updateKunde()  {
		kundenRepository.updateKunde(kunde);
		return "kunden_table.xhtml";
	}
	


}
