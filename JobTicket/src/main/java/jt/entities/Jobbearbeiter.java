package jt.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.eclipse.persistence.annotations.CascadeOnDelete;


// TODO: Auto-generated Javadoc
/**
 * The Class Jobbearbeiter.
 *
 * @author Jan Müller
 * @author Tim Treibmann 
 * Diese Entity Klasse ist die Projektion der Datenbank
 *         Jobbearbeiter mit ihren jeweiligen Spalten als Eigenschaften
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Jobbearbeiter.findAll", query="SELECT j FROM Jobbearbeiter j")
public class Jobbearbeiter implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	/** Stellt den Primärschlüssel in dieser Datenbank dar. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to Angestellte
	/** Die Eigenschaft angestellte stellt den Angestellten, welcher als Jobbearbieter fungiert dar. */
	@ManyToOne
	private Angestellte angestellte;

	//bi-directional many-to-one association to Job
	/** Die Eigenschaft job stellt den Job welcher dem Jobbearbeiter zugewiesen wird dar. */
	@ManyToOne
	@JoinColumn(name="JOBS_ID")
	private Job job;

	/**
	 * Konstruktor der Klasse Jobbearbeiter
	 * Instantiates a new jobbearbeiter.
	 */
	public Jobbearbeiter() {
	}

	/**
	 * Getter der Eigenschaft id.
	 *
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setter der Eigenschaft id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter der Eigenschaft angestellte.
	 *
	 * @return the angestellte
	 */
	public Angestellte getAngestellte() {
		return this.angestellte;
	}

	/**
	 * Setter der Eigenschaft angestellte.
	 *
	 * @param angestellte the new angestellte
	 */
	public void setAngestellte(Angestellte angestellte) {
		this.angestellte = angestellte;
	}

	/**
	 * Getter der Eigenschaft job.
	 *
	 * @return the job
	 */
	public Job getJob() {
		return this.job;
	}

	/**
	 * Setter der Eigenschaft job.
	 *
	 * @param job the new job
	 */
	public void setJob(Job job) {
		this.job = job;
	}

}