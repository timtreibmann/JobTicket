package jt.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class Angestellte.
 *
 * @author Jan Müller
 * @author Tim Treibmann
 * Diese Entity Klasse ist die Projektion der Datenbank Angestellte mit ihren jeweiligen Spalten als Eigenschaften
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Angestellte.findAll", query="SELECT a FROM Angestellte a")
public class Angestellte implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	/** Stellt den Primärschlüssel in dieser Datenbank dar. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	/** Die Eigenschaft Nachname stellt den Wert Nachname des jeweiligen Angestellten dar. */
	private String nachname;

	/** Die Eigenschaft Stundenlohn stellt das Gehalt des jeweiligen Angestellten pro Stunde dar. */
	private double stundenlohn;

	/** Die Eigenschaft Vorname stellt den Wert Vorname des jeweiligen Angestellten dar. */
	private String vorname;

	//bi-directional many-to-one association to Angestelltenbezeichnungen
	/** Die Eigenschaft Angestelltenbezeichnung speichert die Berufsbezeichnung der Angestellten. */
	@ManyToOne
	@JoinColumn(name="ANGESTELLTENBEZEICHNUNG_ID")
	private Angestelltenbezeichnungen angestelltenbezeichnungen;

	//bi-directional many-to-one association to Jobbearbeiter
	/** Die Eigenschaft jobbearbeiters speichert zusammengehörige Job- und Mitarbeiter-Ids.
	 * Damit wird identifiziert, welcher Mitarbeiter welchen Job bearbeitet*/
	@OneToMany(mappedBy="angestellte")
	private List<Jobbearbeiter> jobbearbeiters;

	//bi-directional many-to-one association to Kosten
	/** Die Eigenschaft kostens speichert zusammengehörige Kosten- und Angestellte-Ids.
	 * Damit wird identifiziert, wie viel Kosten ein Job beansprucht*/
	@OneToMany(mappedBy="angestellte")
	private List<Kosten> kostens;

	/**
	 * Instantiates a new angestellte.
	 * Konstruktor der Klasse Angestellte.
	 */
	public Angestellte() {
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
	 * Getter für die Eigenschaft nachname.
	 *
	 * @return the nachname
	 */
	public String getNachname() {
		return this.nachname;
	}

	/**
	 * Setter für die Eigenschaft nachname.
	 *
	 * @param nachname the new nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 *  Getter für die Eigenschaft stundenlohn.
	 *
	 * @return the stundenlohn
	 */
	public double getStundenlohn() {
		return this.stundenlohn;
	}

	/**
	 * Setter für die Eigenschaft stundenlohn.
	 *
	 * @param stundenlohn the new stundenlohn
	 */
	public void setStundenlohn(double stundenlohn) {
		this.stundenlohn = stundenlohn;
	}

	/**
	 * Getter für die Eigenschaft vorname.
	 *
	 * @return the vorname
	 */
	public String getVorname() {
		return this.vorname;
	}

	/**
	 * Setter für die Eigenschaft vorname.
	 *
	 * @param vorname the new vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	/**
	 * Getter für die Eigenschaft angestelltenbezeichnungen.
	 *
	 * @return the angestelltenbezeichnungen
	 */
	public Angestelltenbezeichnungen getAngestelltenbezeichnungen() {
		return this.angestelltenbezeichnungen;
	}

	/**
	 * Setter für die Eigenschaft angestelltenbezeichnungen.
	 *
	 * @param angestelltenbezeichnungen the new angestelltenbezeichnungen
	 */
	public void setAngestelltenbezeichnungen(Angestelltenbezeichnungen angestelltenbezeichnungen) {
		this.angestelltenbezeichnungen = angestelltenbezeichnungen;
	}

	/**
	 * Getter für die Eigenschaft jobbearbeiters.
	 *
	 * @return the jobbearbeiters
	 */
	public List<Jobbearbeiter> getJobbearbeiters() {
		return this.jobbearbeiters;
	}

	/**
	 *  Setter für die Eigenschaft jobbearbeiters.
	 *
	 * @param jobbearbeiters the new jobbearbeiters
	 */
	public void setJobbearbeiters(List<Jobbearbeiter> jobbearbeiters) {
		this.jobbearbeiters = jobbearbeiters;
	}

	/**
	 * Einen jobbearbeiter hinzufügen, indem der Parameterwert dieser Methode der Liste jobbearbeiters hinzugefügt wird.
	 * Der Wert der Eigenschaft Angestellte in der Entity Jobbearbeiter entspricht dem Angestellten der mit dieser Entity erzeugt wurde.
	 * @param jobbearbeiter the jobbearbeiter
	 * @return the jobbearbeiter
	 */
	public Jobbearbeiter addJobbearbeiter(Jobbearbeiter jobbearbeiter) {
		getJobbearbeiters().add(jobbearbeiter);
		jobbearbeiter.setAngestellte(this);

		return jobbearbeiter;
	}

	/**
	 * Einen jobbearbeiter entfernen, indem der Parameterwert dieser Methode in der Liste jobbearbeiters gelöscht wird.
	 * Der Wert der Eigenschaft Angestellte in der Entity Jobbearbeiter wird auf Null gesetzt.
	 * Da kein Jobbearbeiter gegeben ist, ist auch kein Angestellter für diese Position verfügbar. 
	 * @param jobbearbeiter the jobbearbeiter
	 * @return the jobbearbeiter
	 */
	public Jobbearbeiter removeJobbearbeiter(Jobbearbeiter jobbearbeiter) {
		getJobbearbeiters().remove(jobbearbeiter);
		jobbearbeiter.setAngestellte(null);

		return jobbearbeiter;
	}

	/**
	 * Getter für die Eigenschaft kostens.
	 *
	 * @return the kostens
	 */
	public List<Kosten> getKostens() {
		return this.kostens;
	}

	/**
	 * Setter für die Eigenschaft kostens.
	 *
	 * @param kostens the new kostens
	 */
	public void setKostens(List<Kosten> kostens) {
		this.kostens = kostens;
	}

	/**
	 * Einen Kostenbetrag hinzufügen, indem der Parameterwert dieser Methode der Liste kosten hinzugefügt wird.
	 * Der Wert der Eigenschaft angestellte in der Entity Kosten entspricht dem Angestellten der mit dieser Entity erzeugt wurde.
	 * @param kosten the kosten
	 * @return the kosten
	 */
	public Kosten addKosten(Kosten kosten) {
		getKostens().add(kosten);
		kosten.setAngestellte(this);

		return kosten;
	}

	/**
	 * Einen Kostenbetrag entfernen, indem der Parameterwert dieser Methode in der Liste kosten gelöscht wird.
	 * Der Wert der Eigenschaft Angestellte in der Entity Kosten wird auf Null gesetzt.
	 * Da kein Kostenbetrag gegeben ist, ist auch kein Angestellter für diese Position verfügbar. 
	 *
	 * @param kosten the kosten
	 * @return the kosten
	 */
	public Kosten removeKosten(Kosten kosten) {
		getKostens().remove(kosten);
		kosten.setAngestellte(null);

		return kosten;
	}

}