package jt.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the KOSTEN database table.
 * 
 */
@Entity
@Table (schema="JOBTICKET")
public class Kosten implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="ARBEITSAUFWAND_IN_EURO")
	private BigDecimal arbeitsaufwandInEuro;

	@Column(name="ARBEITSAUFWAND_IN_STD")
	private BigDecimal arbeitsaufwandInStd;

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

	public BigDecimal getArbeitsaufwandInEuro() {
		return this.arbeitsaufwandInEuro;
	}

	public void setArbeitsaufwandInEuro(BigDecimal arbeitsaufwandInEuro) {
		this.arbeitsaufwandInEuro = arbeitsaufwandInEuro;
	}

	public BigDecimal getArbeitsaufwandInStd() {
		return this.arbeitsaufwandInStd;
	}

	public void setArbeitsaufwandInStd(BigDecimal arbeitsaufwandInStd) {
		this.arbeitsaufwandInStd = arbeitsaufwandInStd;
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