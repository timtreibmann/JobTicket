package jt.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the PRODUKTEIGENSCHAFTEN database table.
 * 
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Produkteigenschaften.findAll", query="SELECT p FROM Produkteigenschaften p")
public class Produkteigenschaften implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date ausgangsdatum;

	private String beschnitt;

	private String bindung;

	private String dummy;

	@Temporal(TemporalType.DATE)
	private Date eingangsdatum;

	private int erledigt;

	private String falzung;

	@Column(name="FARBE_4C")
	private String farbe4c;

	@Column(name="FARBE_SW")
	private String farbeSw;

	private String fomat;

	private String produktbeschreibung;

	private String proof;

	private int seitenzahl;

	private String sonderfarbe;

	@Temporal(TemporalType.DATE)
	private Date vorlagedatum;

	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn(name="JOBS_ID")
	private Job job;

	public Produkteigenschaften() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getAusgangsdatum() {
		return this.ausgangsdatum;
	}

	public void setAusgangsdatum(Date ausgangsdatum) {
		this.ausgangsdatum = ausgangsdatum;
	}

	public String getBeschnitt() {
		return this.beschnitt;
	}

	public void setBeschnitt(String beschnitt) {
		this.beschnitt = beschnitt;
	}

	public String getBindung() {
		return this.bindung;
	}

	public void setBindung(String bindung) {
		this.bindung = bindung;
	}

	public String getDummy() {
		return this.dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

	public Date getEingangsdatum() {
		return this.eingangsdatum;
	}

	public void setEingangsdatum(Date eingangsdatum) {
		this.eingangsdatum = eingangsdatum;
	}

	public int getErledigt() {
		return this.erledigt;
	}

	public void setErledigt(int erledigt) {
		this.erledigt = erledigt;
	}

	public String getFalzung() {
		return this.falzung;
	}

	public void setFalzung(String falzung) {
		this.falzung = falzung;
	}

	public String getFarbe4c() {
		return this.farbe4c;
	}

	public void setFarbe4c(String farbe4c) {
		this.farbe4c = farbe4c;
	}

	public String getFarbeSw() {
		return this.farbeSw;
	}

	public void setFarbeSw(String farbeSw) {
		this.farbeSw = farbeSw;
	}

	public String getFomat() {
		return this.fomat;
	}

	public void setFomat(String fomat) {
		this.fomat = fomat;
	}

	public String getProduktbeschreibung() {
		return this.produktbeschreibung;
	}

	public void setProduktbeschreibung(String produktbeschreibung) {
		this.produktbeschreibung = produktbeschreibung;
	}

	public String getProof() {
		return this.proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public int getSeitenzahl() {
		return this.seitenzahl;
	}

	public void setSeitenzahl(int seitenzahl) {
		this.seitenzahl = seitenzahl;
	}

	public String getSonderfarbe() {
		return this.sonderfarbe;
	}

	public void setSonderfarbe(String sonderfarbe) {
		this.sonderfarbe = sonderfarbe;
	}

	public Date getVorlagedatum() {
		return this.vorlagedatum;
	}

	public void setVorlagedatum(Date vorlagedatum) {
		this.vorlagedatum = vorlagedatum;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

}