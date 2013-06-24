package jt.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PRODUKTEIGENSCHAFTEN database table.
 * 
 */
@Entity
@Table(schema="JOBTICKET", name="Produkteigenschaften")
public class Produkteigenschaften implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int beschnitt;

	private String bindung;

	private int dummy;

	private String falzung;

	@Column(name="FARBE_4C")
	private String farbe4c;

	@Column(name="FARBE_SW")
	private String farbeSw;

	private int fomat;

	private String produktbeschreibung;

	private int proof;

	private int seitenzahl;

	private String sonderfarbe;

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

	public int getBeschnitt() {
		return this.beschnitt;
	}

	public void setBeschnitt(int beschnitt) {
		this.beschnitt = beschnitt;
	}

	public String getBindung() {
		return this.bindung;
	}

	public void setBindung(String bindung) {
		this.bindung = bindung;
	}

	public int getDummy() {
		return this.dummy;
	}

	public void setDummy(int dummy) {
		this.dummy = dummy;
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

	public int getFomat() {
		return this.fomat;
	}

	public void setFomat(int fomat) {
		this.fomat = fomat;
	}

	public String getProduktbeschreibung() {
		return this.produktbeschreibung;
	}

	public void setProduktbeschreibung(String produktbeschreibung) {
		this.produktbeschreibung = produktbeschreibung;
	}

	public int getProof() {
		return this.proof;
	}

	public void setProof(int proof) {
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

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

}