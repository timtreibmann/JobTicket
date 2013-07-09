package jt.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * @author jan & tim
 * The persistent class for the ANGESTELLTENBEZEICHNUNGEN database table.
 * 
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Angestelltenbezeichnungen.findAll", query="SELECT a FROM Angestelltenbezeichnungen a")
public class Angestelltenbezeichnungen implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	/** The bezeichnung. */
	private String bezeichnung;

	//bi-directional many-to-one association to Angestellte
	/** The angestelltes. */
	@OneToMany(mappedBy="angestelltenbezeichnungen")
	private List<Angestellte> angestelltes;

	/**
	 * Instantiates a new angestelltenbezeichnungen.
	 */
	public Angestelltenbezeichnungen() {
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the bezeichnung.
	 *
	 * @return the bezeichnung
	 */
	public String getBezeichnung() {
		return this.bezeichnung;
	}

	/**
	 * Sets the bezeichnung.
	 *
	 * @param bezeichnung the new bezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	/**
	 * Gets the angestelltes.
	 *
	 * @return the angestelltes
	 */
	public List<Angestellte> getAngestelltes() {
		return this.angestelltes;
	}

	/**
	 * Sets the angestelltes.
	 *
	 * @param angestelltes the new angestelltes
	 */
	public void setAngestelltes(List<Angestellte> angestelltes) {
		this.angestelltes = angestelltes;
	}

	/**
	 * Adds the angestellte.
	 *
	 * @param angestellte the angestellte
	 * @return the angestellte
	 */
	public Angestellte addAngestellte(Angestellte angestellte) {
		getAngestelltes().add(angestellte);
		angestellte.setAngestelltenbezeichnungen(this);

		return angestellte;
	}

	/**
	 * Removes the angestellte.
	 *
	 * @param angestellte the angestellte
	 * @return the angestellte
	 */
	public Angestellte removeAngestellte(Angestellte angestellte) {
		getAngestelltes().remove(angestellte);
		angestellte.setAngestelltenbezeichnungen(null);

		return angestellte;
	}

}