package jt.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the KOSTEN database table.
 * 
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Kosten.findAll", query="SELECT k FROM Kosten k")
public class Kosten implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private BigDecimal arbeitsaufwand;

	@Column(name="ARBEITSAUFWAND_IST_IN_EURO")
	private int arbeitsaufwandIstInEuro;

	private String kommentar;

	//bi-directional many-to-one association to Angestellte
	@ManyToOne
	private Angestellte angestellte;

	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn(name="JOBS_ID")
	private Job job;

	public Kosten() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getArbeitsaufwand() {
		return this.arbeitsaufwand;
	}

	public void setArbeitsaufwand(BigDecimal arbeitsaufwand) {
		this.arbeitsaufwand = arbeitsaufwand;
	}

	public int getArbeitsaufwandIstInEuro() {
		return this.arbeitsaufwandIstInEuro;
	}

	public void setArbeitsaufwandIstInEuro(int arbeitsaufwandIstInEuro) {
		this.arbeitsaufwandIstInEuro = arbeitsaufwandIstInEuro;
	}

	public String getKommentar() {
		return this.kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public Angestellte getAngestellte() {
		return this.angestellte;
	}

	public void setAngestellte(Angestellte angestellte) {
		this.angestellte = angestellte;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

}