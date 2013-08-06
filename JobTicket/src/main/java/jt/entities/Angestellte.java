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
	 */
	public Angestellte() {
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
	 * Gets the nachname.
	 *
	 * @return the nachname
	 */
	public String getNachname() {
		return this.nachname;
	}

	/**
	 * Sets the nachname.
	 *
	 * @param nachname the new nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * Gets the stundenlohn.
	 *
	 * @return the stundenlohn
	 */
	public double getStundenlohn() {
		return this.stundenlohn;
	}

	/**
	 * Sets the stundenlohn.
	 *
	 * @param stundenlohn the new stundenlohn
	 */
	public void setStundenlohn(double stundenlohn) {
		this.stundenlohn = stundenlohn;
	}

	/**
	 * Gets the vorname.
	 *
	 * @return the vorname
	 */
	public String getVorname() {
		return this.vorname;
	}

	/**
	 * Sets the vorname.
	 *
	 * @param vorname the new vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	/**
	 * Gets the angestelltenbezeichnungen.
	 *
	 * @return the angestelltenbezeichnungen
	 */
	public Angestelltenbezeichnungen getAngestelltenbezeichnungen() {
		return this.angestelltenbezeichnungen;
	}

	/**
	 * Sets the angestelltenbezeichnungen.
	 *
	 * @param angestelltenbezeichnungen the new angestelltenbezeichnungen
	 */
	public void setAngestelltenbezeichnungen(Angestelltenbezeichnungen angestelltenbezeichnungen) {
		this.angestelltenbezeichnungen = angestelltenbezeichnungen;
	}

	/**
	 * Gets the jobbearbeiters.
	 *
	 * @return the jobbearbeiters
	 */
	public List<Jobbearbeiter> getJobbearbeiters() {
		return this.jobbearbeiters;
	}

	/**
	 * Sets the jobbearbeiters.
	 *
	 * @param jobbearbeiters the new jobbearbeiters
	 */
	public void setJobbearbeiters(List<Jobbearbeiter> jobbearbeiters) {
		this.jobbearbeiters = jobbearbeiters;
	}

	/**
	 * Adds the jobbearbeiter.
	 *
	 * @param jobbearbeiter the jobbearbeiter
	 * @return the jobbearbeiter
	 */
	public Jobbearbeiter addJobbearbeiter(Jobbearbeiter jobbearbeiter) {
		getJobbearbeiters().add(jobbearbeiter);
		jobbearbeiter.setAngestellte(this);

		return jobbearbeiter;
	}

	/**
	 * Removes the jobbearbeiter.
	 *
	 * @param jobbearbeiter the jobbearbeiter
	 * @return the jobbearbeiter
	 */
	public Jobbearbeiter removeJobbearbeiter(Jobbearbeiter jobbearbeiter) {
		getJobbearbeiters().remove(jobbearbeiter);
		jobbearbeiter.setAngestellte(null);

		return jobbearbeiter;
	}

	/**
	 * Gets the kostens.
	 *
	 * @return the kostens
	 */
	public List<Kosten> getKostens() {
		return this.kostens;
	}

	/**
	 * Sets the kostens.
	 *
	 * @param kostens the new kostens
	 */
	public void setKostens(List<Kosten> kostens) {
		this.kostens = kostens;
	}

	/**
	 * Adds the kosten.
	 *
	 * @param kosten the kosten
	 * @return the kosten
	 */
	public Kosten addKosten(Kosten kosten) {
		getKostens().add(kosten);
		kosten.setAngestellte(this);

		return kosten;
	}

	/**
	 * Removes the kosten.
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