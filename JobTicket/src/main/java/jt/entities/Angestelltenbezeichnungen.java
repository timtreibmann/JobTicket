package jt.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the ANGESTELLTENBEZEICHNUNGEN database table.
 * 
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Angestelltenbezeichnungen.findAll", query="SELECT a FROM Angestelltenbezeichnungen a")
public class Angestelltenbezeichnungen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String bezeichnung;

	//bi-directional many-to-one association to Angestellte
	@OneToMany(mappedBy="angestelltenbezeichnungen")
	private List<Angestellte> angestelltes;

	public Angestelltenbezeichnungen() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public List<Angestellte> getAngestelltes() {
		return this.angestelltes;
	}

	public void setAngestelltes(List<Angestellte> angestelltes) {
		this.angestelltes = angestelltes;
	}

	public Angestellte addAngestellte(Angestellte angestellte) {
		getAngestelltes().add(angestellte);
		angestellte.setAngestelltenbezeichnungen(this);

		return angestellte;
	}

	public Angestellte removeAngestellte(Angestellte angestellte) {
		getAngestelltes().remove(angestellte);
		angestellte.setAngestelltenbezeichnungen(null);

		return angestellte;
	}

}