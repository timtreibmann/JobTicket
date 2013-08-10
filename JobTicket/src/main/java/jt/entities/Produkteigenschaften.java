package jt.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.util.Date;


// TODO: Auto-generated Javadoc
/**
 * The Class Produkteigenschaften.
 *
 * @author jan & tim
 * The persistent class for the PRODUKTEIGENSCHAFTEN database table.
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Produkteigenschaften.findAll", query="SELECT p FROM Produkteigenschaften p")
public class Produkteigenschaften implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	/** The ausgangsdatum. */
	@Temporal(TemporalType.DATE)
	private Date ausgangsdatum;

	/** The beschnitt. */
	private String beschnitt;

	/** The bindung. */
	private String bindung;

	/** The dummy. */
	private String dummy;

	/** The eingangsdatum. */
	@Temporal(TemporalType.DATE)
	private Date eingangsdatum;

	/** The erledigt. */
	private int erledigt;

	/** The falzung. */
	private String falzung;

	/** The farbe4c. */
	@Column(name="FARBE_4C")
	private String farbe4c;

	/** The farbe sw. */
	@Column(name="FARBE_SW")
	private String farbeSw;

	/** The fomat. */
	private String fomat;

	/** The produktbeschreibung. */
	private String produktbeschreibung;

	/** The proof. */
	private String proof;

	/** The seitenzahl. */
	private int seitenzahl;

	/** The sonderfarbe. */
	private String sonderfarbe;

	/** The vorlagedatum. */
	@Temporal(TemporalType.DATE)
	private Date vorlagedatum;
	

	//bi-directional many-to-one association to Job
	/** The job. */
	@ManyToOne
	@CascadeOnDelete
	@JoinColumn(name="JOBS_ID")
	private Job job;

	/**
	 * Instantiates a new produkteigenschaften.
	 */
	public Produkteigenschaften() {
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
	 * Gets the ausgangsdatum.
	 *
	 * @return the ausgangsdatum
	 */
	public Date getAusgangsdatum() {
		return this.ausgangsdatum;
	}

	/**
	 * Sets the ausgangsdatum.
	 *
	 * @param ausgangsdatum the new ausgangsdatum
	 */
	public void setAusgangsdatum(Date ausgangsdatum) {
		this.ausgangsdatum = ausgangsdatum;
	}

	/**
	 * Gets the beschnitt.
	 *
	 * @return the beschnitt
	 */
	public String getBeschnitt() {
		return this.beschnitt;
	}

	/**
	 * Sets the beschnitt.
	 *
	 * @param beschnitt the new beschnitt
	 */
	public void setBeschnitt(String beschnitt) {
		this.beschnitt = beschnitt;
	}

	/**
	 * Gets the bindung.
	 *
	 * @return the bindung
	 */
	public String getBindung() {
		return this.bindung;
	}

	/**
	 * Sets the bindung.
	 *
	 * @param bindung the new bindung
	 */
	public void setBindung(String bindung) {
		this.bindung = bindung;
	}

	/**
	 * Gets the dummy.
	 *
	 * @return the dummy
	 */
	public String getDummy() {
		return this.dummy;
	}

	/**
	 * Sets the dummy.
	 *
	 * @param dummy the new dummy
	 */
	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	/**
	 * Gets the eingangsdatum.
	 *
	 * @return the eingangsdatum
	 */
	public Date getEingangsdatum() {
		return this.eingangsdatum;
	}

	/**
	 * Sets the eingangsdatum.
	 *
	 * @param eingangsdatum the new eingangsdatum
	 */
	public void setEingangsdatum(Date eingangsdatum) {
		this.eingangsdatum = eingangsdatum;
	}

	/**
	 * Gets the erledigt.
	 *
	 * @return the erledigt
	 */
	public int getErledigt() {
		return this.erledigt;
	}

	/**
	 * Sets the erledigt.
	 *
	 * @param erledigt the new erledigt
	 */
	public void setErledigt(int erledigt) {
		this.erledigt = erledigt;
	}

	/**
	 * Gets the falzung.
	 *
	 * @return the falzung
	 */
	public String getFalzung() {
		return this.falzung;
	}

	/**
	 * Sets the falzung.
	 *
	 * @param falzung the new falzung
	 */
	public void setFalzung(String falzung) {
		this.falzung = falzung;
	}

	/**
	 * Gets the farbe4c.
	 *
	 * @return the farbe4c
	 */
	public String getFarbe4c() {
		return this.farbe4c;
	}

	/**
	 * Sets the farbe4c.
	 *
	 * @param farbe4c the new farbe4c
	 */
	public void setFarbe4c(String farbe4c) {
		this.farbe4c = farbe4c;
	}

	/**
	 * Gets the farbe sw.
	 *
	 * @return the farbe sw
	 */
	public String getFarbeSw() {
		return this.farbeSw;
	}

	/**
	 * Sets the farbe sw.
	 *
	 * @param farbeSw the new farbe sw
	 */
	public void setFarbeSw(String farbeSw) {
		this.farbeSw = farbeSw;
	}

	/**
	 * Gets the fomat.
	 *
	 * @return the fomat
	 */
	public String getFomat() {
		return this.fomat;
	}

	/**
	 * Sets the fomat.
	 *
	 * @param fomat the new fomat
	 */
	public void setFomat(String fomat) {
		this.fomat = fomat;
	}

	/**
	 * Gets the produktbeschreibung.
	 *
	 * @return the produktbeschreibung
	 */
	public String getProduktbeschreibung() {
		return this.produktbeschreibung;
	}

	/**
	 * Sets the produktbeschreibung.
	 *
	 * @param produktbeschreibung the new produktbeschreibung
	 */
	public void setProduktbeschreibung(String produktbeschreibung) {
		this.produktbeschreibung = produktbeschreibung;
	}

	/**
	 * Gets the proof.
	 *
	 * @return the proof
	 */
	public String getProof() {
		return this.proof;
	}

	/**
	 * Sets the proof.
	 *
	 * @param proof the new proof
	 */
	public void setProof(String proof) {
		this.proof = proof;
	}

	/**
	 * Gets the seitenzahl.
	 *
	 * @return the seitenzahl
	 */
	public int getSeitenzahl() {
		return this.seitenzahl;
	}

	/**
	 * Sets the seitenzahl.
	 *
	 * @param seitenzahl the new seitenzahl
	 */
	public void setSeitenzahl(int seitenzahl) {
		this.seitenzahl = seitenzahl;
	}

	/**
	 * Gets the sonderfarbe.
	 *
	 * @return the sonderfarbe
	 */
	public String getSonderfarbe() {
		return this.sonderfarbe;
	}

	/**
	 * Sets the sonderfarbe.
	 *
	 * @param sonderfarbe the new sonderfarbe
	 */
	public void setSonderfarbe(String sonderfarbe) {
		this.sonderfarbe = sonderfarbe;
	}

	/**
	 * Gets the vorlagedatum.
	 *
	 * @return the vorlagedatum
	 */
	public Date getVorlagedatum() {
		return this.vorlagedatum;
	}

	/**
	 * Sets the vorlagedatum.
	 *
	 * @param vorlagedatum the new vorlagedatum
	 */
	public void setVorlagedatum(Date vorlagedatum) {
		this.vorlagedatum = vorlagedatum;
	}

	/**
	 * Gets the job.
	 *
	 * @return the job
	 */
	public Job getJob() {
		return this.job;
	}

	/**
	 * Sets the job.
	 *
	 * @param job the new job
	 */
	public void setJob(Job job) {
		this.job = job;
	}

}