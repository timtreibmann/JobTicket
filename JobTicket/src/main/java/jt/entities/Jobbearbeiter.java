package jt.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the JOBBEARBEITER database table.
 * 
 */
@Entity
@Table (schema="JOBTICKET")
public class Jobbearbeiter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to Angestellte
	@ManyToOne
	private Angestellte angestellte;

	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn(name="JOBS_ID")
	private Job job;

	public Jobbearbeiter() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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