package jt.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


// TODO: Auto-generated Javadoc
/**
 * The Class Kunde.
 *
 * @author jan & tim
 * The persistent class for the KUNDE database table.
 */
@Entity
@Table(schema="JOBTICKET")
@NamedQuery(name="Kunde.findAll", query="SELECT k FROM Kunde k")
public class Kunde implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	/** The adresse. */
	private String adresse;

	/** The kundenkuerzel. */
	private String kundenkuerzel;

	/** The name. */
	private String name;

	/** The telefon. */
	private String telefon;

	//bi-directional many-to-one association to Job
	/** The jobs. */
	@OneToMany(mappedBy="kunde")
	private List<Job> jobs;

	/**
	 * Instantiates a new kunde.
	 */
	public Kunde() {
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
	 * Gets the adresse.
	 *
	 * @return the adresse
	 */
	public String getAdresse() {
		return this.adresse;
	}

	/**
	 * Sets the adresse.
	 *
	 * @param adresse the new adresse
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * Gets the kundenkuerzel.
	 *
	 * @return the kundenkuerzel
	 */
	public String getKundenkuerzel() {
		return this.kundenkuerzel;
	}

	/**
	 * Sets the kundenkuerzel.
	 *
	 * @param kundenkuerzel the new kundenkuerzel
	 */
	public void setKundenkuerzel(String kundenkuerzel) {
		this.kundenkuerzel = kundenkuerzel;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the telefon.
	 *
	 * @return the telefon
	 */
	public String getTelefon() {
		return this.telefon;
	}

	/**
	 * Sets the telefon.
	 *
	 * @param telefon the new telefon
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * Gets the jobs.
	 *
	 * @return the jobs
	 */
	public List<Job> getJobs() {
		return this.jobs;
	}

	/**
	 * Sets the jobs.
	 *
	 * @param jobs the new jobs
	 */
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	/**
	 * Adds the job.
	 *
	 * @param job the job
	 * @return the job
	 */
	public Job addJob(Job job) {
		getJobs().add(job);
		job.setKunde(this);

		return job;
	}

	/**
	 * Removes the job.
	 *
	 * @param job the job
	 * @return the job
	 */
	public Job removeJob(Job job) {
		getJobs().remove(job);
		job.setKunde(null);

		return job;
	}

}