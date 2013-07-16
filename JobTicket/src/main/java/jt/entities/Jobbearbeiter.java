package jt.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.eclipse.persistence.annotations.CascadeOnDelete;


// TODO: Auto-generated Javadoc
/**
 * The Class Jobbearbeiter.
 *
 * @author jan & tim
 * The persistent class for the JOBBEARBEITER database table.
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Jobbearbeiter.findAll", query="SELECT j FROM Jobbearbeiter j")
public class Jobbearbeiter implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

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
	 * Instantiates a new jobbearbeiter.
	 */
	public Jobbearbeiter() {
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