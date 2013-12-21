package jt.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



// TODO: Auto-generated Javadoc
/**
 * The Class Kosten.
 *
 * @author jan & tim
 * The persistent class for the KOSTEN database table.
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Kosten.findAll", query="SELECT k FROM Kosten k")
public class Kosten implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	/** The arbeitsaufwand. */
	private double arbeitsaufwand;

	/** The arbeitsaufwand ist in euro. */
	@Column(name="ARBEITSAUFWAND_IST_IN_EURO")
	private int arbeitsaufwandIstInEuro;

	/** The kommentar. */
	private String kommentar;

	//bi-directional many-to-one association to Angestellte
	/** The angestellte. */
	@ManyToOne
	private Angestellte angestellte;

	//bi-directional many-to-one association to Job
	/** The job. */
	@ManyToOne

	@JoinColumn(name="JOBS_ID")
	private Job job;

	/**
	 * Instantiates a new kosten.
	 */
	public Kosten() {
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
	 * Gets the arbeitsaufwand.
	 *
	 * @return the arbeitsaufwand
	 */
	public double getArbeitsaufwand() {
		return this.arbeitsaufwand;
	}

	/**
	 * Sets the arbeitsaufwand.
	 *
	 * @param arbeitsaufwand the new arbeitsaufwand
	 */
	public void setArbeitsaufwand(double arbeitsaufwand) {
		this.arbeitsaufwand = arbeitsaufwand;
	}

	/**
	 * Gets the arbeitsaufwand ist in euro.
	 *
	 * @return the arbeitsaufwand ist in euro
	 */
	public int getArbeitsaufwandIstInEuro() {
		return this.arbeitsaufwandIstInEuro;
	}

	/**
	 * Sets the arbeitsaufwand ist in euro.
	 *
	 * @param arbeitsaufwandIstInEuro the new arbeitsaufwand ist in euro
	 */
	public void setArbeitsaufwandIstInEuro(int arbeitsaufwandIstInEuro) {
		this.arbeitsaufwandIstInEuro = arbeitsaufwandIstInEuro;
	}

	/**
	 * Gets the kommentar.
	 *
	 * @return the kommentar
	 */
	public String getKommentar() {
		return this.kommentar;
	}

	/**
	 * Sets the kommentar.
	 *
	 * @param kommentar the new kommentar
	 */
	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	/**
	 * Gets the angestellte.
	 *
	 * @return the angestellte
	 */
	public Angestellte getAngestellte() {
		return this.angestellte;
	}

	/**
	 * Sets the angestellte.
	 *
	 * @param angestellte the new angestellte
	 */
	public void setAngestellte(Angestellte angestellte) {
		this.angestellte = angestellte;
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