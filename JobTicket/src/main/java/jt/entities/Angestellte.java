package jt.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the ANGESTELLTE database table.
 * 
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Angestellte.findAll", query="SELECT a FROM Angestellte a")
public class Angestellte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nachname;

	private BigDecimal stundenlohn;

	private String vorname;

	//bi-directional many-to-one association to Angestelltenbezeichnungen
	@ManyToOne
	@JoinColumn(name="ANGESTELLTENBEZEICHNUNG_ID")
	private Angestelltenbezeichnungen angestelltenbezeichnungen;

	//bi-directional many-to-one association to Jobbearbeiter
	@OneToMany(mappedBy="angestellte")
	private List<Jobbearbeiter> jobbearbeiters;

	//bi-directional many-to-one association to Kosten
	@OneToMany(mappedBy="angestellte")
	private List<Kosten> kostens;

	public Angestellte() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNachname() {
		return this.nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public BigDecimal getStundenlohn() {
		return this.stundenlohn;
	}

	public void setStundenlohn(BigDecimal stundenlohn) {
		this.stundenlohn = stundenlohn;
	}

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Angestelltenbezeichnungen getAngestelltenbezeichnungen() {
		return this.angestelltenbezeichnungen;
	}

	public void setAngestelltenbezeichnungen(Angestelltenbezeichnungen angestelltenbezeichnungen) {
		this.angestelltenbezeichnungen = angestelltenbezeichnungen;
	}

	public List<Jobbearbeiter> getJobbearbeiters() {
		return this.jobbearbeiters;
	}

	public void setJobbearbeiters(List<Jobbearbeiter> jobbearbeiters) {
		this.jobbearbeiters = jobbearbeiters;
	}

	public Jobbearbeiter addJobbearbeiter(Jobbearbeiter jobbearbeiter) {
		getJobbearbeiters().add(jobbearbeiter);
		jobbearbeiter.setAngestellte(this);

		return jobbearbeiter;
	}

	public Jobbearbeiter removeJobbearbeiter(Jobbearbeiter jobbearbeiter) {
		getJobbearbeiters().remove(jobbearbeiter);
		jobbearbeiter.setAngestellte(null);

		return jobbearbeiter;
	}

	public List<Kosten> getKostens() {
		return this.kostens;
	}

	public void setKostens(List<Kosten> kostens) {
		this.kostens = kostens;
	}

	public Kosten addKosten(Kosten kosten) {
		getKostens().add(kosten);
		kosten.setAngestellte(this);

		return kosten;
	}

	public Kosten removeKosten(Kosten kosten) {
		getKostens().remove(kosten);
		kosten.setAngestellte(null);

		return kosten;
	}

}