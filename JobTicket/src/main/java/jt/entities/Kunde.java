package jt.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the KUNDE database table.
 * 
 */
@Entity
@Table (schema="JOBTICKET")
public class Kunde implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String adresse;

	private String kundenkuerzel;

	private String name;

	private String telefon;

	//bi-directional many-to-one association to Job
	@OneToMany(mappedBy="kunde")
	private List<Job> jobs;

	public Kunde() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getKundenkuerzel() {
		return this.kundenkuerzel;
	}

	public void setKundenkuerzel(String kundenkuerzel) {
		this.kundenkuerzel = kundenkuerzel;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

}