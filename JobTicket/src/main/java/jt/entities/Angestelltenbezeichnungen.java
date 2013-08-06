package jt.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * @author Jan Müller
 * @author Tim Treibmann
 * Diese Entity Klasse ist die Projektion der Datenbank ANGESTELLTENBEZEICHNUNGEN mit ihren jeweiligen Spalten als Eigenschaften.
 * 
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Angestelltenbezeichnungen.findAll", query="SELECT a FROM Angestelltenbezeichnungen a")
public class Angestelltenbezeichnungen implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	/** Stellt den Primärschlüssel in dieser Datenbank dar. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	/** Die Eigenschaft bezeichnung stellt die Berufsbezeichnung des Angestellten dar. */
	private String bezeichnung;

	//bi-directional many-to-one association to Angestellte
	/**Die Eigenschaft angestelltes enthält alle Angestellten in Form einer Liste. */
	@OneToMany(mappedBy="angestelltenbezeichnungen")
	private List<Angestellte> angestelltes;

	/**
	 * Konstruktor der Klasse Angestelltenbezeichnungen
	 * Instantiates a new angestelltenbezeichnungen.
	 */
	public Angestelltenbezeichnungen() {
	}

	/**
	 * Getter für die Eigenschaft id.
	 *
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setter für die Eigenschaft id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter für die Eigenschaft bezeichnung.
	 *
	 * @return the bezeichnung
	 */
	public String getBezeichnung() {
		return this.bezeichnung;
	}

	/**
	 * Setter für die Eigenschaft bezeichnung.
	 *
	 * @param bezeichnung the new bezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	/**
	 * Getter für die Eigenschaft angestelltes.
	 *
	 * @return the angestelltes
	 */
	public List<Angestellte> getAngestelltes() {
		return this.angestelltes;
	}

	/**
	 * Setter für die Eigenschaft angestelltes.
	 *
	 * @param angestelltes the new angestelltes
	 */
	public void setAngestelltes(List<Angestellte> angestelltes) {
		this.angestelltes = angestelltes;
	}

	/**
	 * Einen Angestellten hinzufügen, indem der Parameterwert dieser Methode der Liste angestelltes hinzugefügt wird.
	 * Der Wert der Eigenschaft Angestelltenbezeichnungen in der Entity Angestellte entspricht der Angestelltenbezeichnung, die mit dieser Entity erzeugt wurde.
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
	 * Einen Angestellten entfernen, indem der Parameterwert dieser Methode in der Liste angestelltes gelöscht wird.
	 * Der Wert der Eigenschaft Angestelltenbezeichnungen in der Entity Angestellte wird auf Null gesetzt.
	 * Da kein Angestellter gegeben ist, ist auch keine Angestelltenbezeichnung für diese Position verfügbar. 
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