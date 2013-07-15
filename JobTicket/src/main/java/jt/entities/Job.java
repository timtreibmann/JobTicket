package jt.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.util.Date;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Job.
 * 
 * @author jan & tim The persistent class for the JOBS database table.
 */
@Entity
@Table(schema = "JOBTICKET", name = "JOBS")
@NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j")
public class Job implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/** The alte jobnummer. */
	@Column(name = "ALTE_JOBNUMMER")
	private int alteJobnummer;

	/** The budget in euro. */
	@Column(name = "BUDGET_IN_EURO")
	private double budgetInEuro;

	/** The budget in std. */
	@Column(name = "BUDGET_IN_STD")
	private double budgetInStd;

	/** The empfaenger. */
	private String empfaenger;

	/** The jobbeschreibung. */
	private String jobbeschreibung;

	/** The name. */
	private String name;

	/** The print. */
	private String print;
	
	private String ersteller;

	/** The erstellDatum. */
	@Temporal(TemporalType.DATE)
	private Date erstellDatum;
	
	

	// bi-directional many-to-one association to Jobbearbeiter
	/** The jobbearbeiters. */
	@OneToMany(mappedBy = "job", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Jobbearbeiter> jobbearbeiters;

	// bi-directional many-to-one association to Kunde
	/** The kunde. */
	@ManyToOne
	private Kunde kunde;

	// bi-directional many-to-one association to Kosten
	/** The kostens. */
	@OneToMany(mappedBy = "job", cascade=CascadeType.ALL, orphanRemoval=true)
	@CascadeOnDelete
	private List<Kosten> kostens;

	// bi-directional many-to-one association to Produkteigenschaften
	/** The produkteigenschaftens. */
	@OneToMany(mappedBy = "job", cascade=CascadeType.ALL, orphanRemoval=true)
	@CascadeOnDelete
	private List<Produkteigenschaften> produkteigenschaftens;

	/**
	 * Instantiates a new job.
	 */
	public Job() {
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
	 * @param id
	 *            the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the alte jobnummer.
	 * 
	 * @return the alte jobnummer
	 */
	public int getAlteJobnummer() {
		return this.alteJobnummer;
	}

	/**
	 * Sets the alte jobnummer.
	 * 
	 * @param alteJobnummer
	 *            the new alte jobnummer
	 */
	public void setAlteJobnummer(int alteJobnummer) {
		this.alteJobnummer = alteJobnummer;
	}

	/**
	 * Gets the budget in euro.
	 * 
	 * @return the budget in euro
	 */
	public double getBudgetInEuro() {
		return this.budgetInEuro;
	}

	/**
	 * Sets the budget in euro.
	 * 
	 * @param budgetInEuro
	 *            the new budget in euro
	 */
	public void setBudgetInEuro(double budgetInEuro) {
		this.budgetInEuro = budgetInEuro;
	}

	/**
	 * Gets the budget in std.
	 * 
	 * @return the budget in std
	 */
	public double getBudgetInStd() {
		return this.budgetInStd;
	}

	/**
	 * Sets the budget in std.
	 * 
	 * @param budgetInStd
	 *            the new budget in std
	 */
	public void setBudgetInStd(double budgetInStd) {
		this.budgetInStd = budgetInStd;
	}

	/**
	 * Gets the empfaenger.
	 * 
	 * @return the empfaenger
	 */
	public String getEmpfaenger() {
		return this.empfaenger;
	}

	/**
	 * Sets the empfaenger.
	 * 
	 * @param empfaenger
	 *            the new empfaenger
	 */
	public void setEmpfaenger(String empfaenger) {
		this.empfaenger = empfaenger;
	}

	/**
	 * Gets the jobbeschreibung.
	 * 
	 * @return the jobbeschreibung
	 */
	public String getJobbeschreibung() {
		return this.jobbeschreibung;
	}

	/**
	 * Sets the jobbeschreibung.
	 * 
	 * @param jobbeschreibung
	 *            the new jobbeschreibung
	 */
	public void setJobbeschreibung(String jobbeschreibung) {
		this.jobbeschreibung = jobbeschreibung;
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
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the prints the.
	 * 
	 * @return the prints the
	 */
	public String getPrint() {
		return this.print;
	}

	/**
	 * Sets the prints the.
	 * 
	 * @param print
	 *            the new prints the
	 */
	public void setPrint(String print) {
		this.print = print;
	}

	public String getErsteller() {
		return ersteller;
	}

	public void setErsteller(String ersteller) {
		this.ersteller = ersteller;
	}

	/**
	 * Gets the erstellDatum.
	 * 
	 * @return the erstellDatum
	 */
	public Date getErstellDatum() {
		return this.erstellDatum;
	}

	/**
	 * Sets the erstellDatum.
	 * 
	 * @param erstellDatum
	 *            the new erstellDatum
	 */
	public void setErstellDatum(Date erstellDatum) {
		this.erstellDatum = erstellDatum;
	}

	/**
	 * Gets the jobbearbeiters.
	 * 
	 * @return the jobbearbeiters
	 */
	public List<Jobbearbeiter> getJobbearbeiters() {
		return this.jobbearbeiters;
	}

	/**
	 * Sets the jobbearbeiters.
	 * 
	 * @param jobbearbeiters
	 *            the new jobbearbeiters
	 */
	public void setJobbearbeiters(List<Jobbearbeiter> jobbearbeiters) {
		this.jobbearbeiters = jobbearbeiters;
	}

	/**
	 * Adds the jobbearbeiter.
	 * 
	 * @param jobbearbeiter
	 *            the jobbearbeiter
	 * @return the jobbearbeiter
	 */
	public Jobbearbeiter addJobbearbeiter(Jobbearbeiter jobbearbeiter) {
		getJobbearbeiters().add(jobbearbeiter);
		jobbearbeiter.setJob(this);

		return jobbearbeiter;
	}

	/**
	 * Removes the jobbearbeiter.
	 * 
	 * @param jobbearbeiter
	 *            the jobbearbeiter
	 * @return the jobbearbeiter
	 */
	public Jobbearbeiter removeJobbearbeiter(Jobbearbeiter jobbearbeiter) {
		getJobbearbeiters().remove(jobbearbeiter);
		jobbearbeiter.setJob(null);

		return jobbearbeiter;
	}

	/**
	 * Gets the kunde.
	 * 
	 * @return the kunde
	 */
	public Kunde getKunde() {
		return this.kunde;
	}

	/**
	 * Sets the kunde.
	 * 
	 * @param kunde
	 *            the new kunde
	 */
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	/**
	 * Gets the kostens.
	 * 
	 * @return the kostens
	 */
	public List<Kosten> getKostens() {
		return this.kostens;
	}

	/**
	 * Sets the kostens.
	 * 
	 * @param kostens
	 *            the new kostens
	 */
	public void setKostens(List<Kosten> kostens) {
		this.kostens = kostens;
	}

	/**
	 * Adds the kosten.
	 * 
	 * @param kosten
	 *            the kosten
	 * @return the kosten
	 */
	public Kosten addKosten(Kosten kosten) {
		getKostens().add(kosten);
		kosten.setJob(this);

		return kosten;
	}

	/**
	 * Removes the kosten.
	 * 
	 * @param kosten
	 *            the kosten
	 * @return the kosten
	 */
	public Kosten removeKosten(Kosten kosten) {
		getKostens().remove(kosten);
		kosten.setJob(null);

		return kosten;
	}

	/**
	 * Gets the produkteigenschaftens.
	 * 
	 * @return the produkteigenschaftens
	 */
	public List<Produkteigenschaften> getProdukteigenschaftens() {
		return this.produkteigenschaftens;
	}

	/**
	 * Sets the produkteigenschaftens.
	 * 
	 * @param produkteigenschaftens
	 *            the new produkteigenschaftens
	 */
	public void setProdukteigenschaftens(
			List<Produkteigenschaften> produkteigenschaftens) {
		this.produkteigenschaftens = produkteigenschaftens;
	}

	/**
	 * Adds the produkteigenschaften.
	 * 
	 * @param produkteigenschaften
	 *            the produkteigenschaften
	 * @return the produkteigenschaften
	 */
	public Produkteigenschaften addProdukteigenschaften(
			Produkteigenschaften produkteigenschaften) {
		getProdukteigenschaftens().add(produkteigenschaften);
		produkteigenschaften.setJob(this);

		return produkteigenschaften;
	}

	/**
	 * Removes the produkteigenschaften.
	 * 
	 * @param produkteigenschaften
	 *            the produkteigenschaften
	 * @return the produkteigenschaften
	 */
	public Produkteigenschaften removeProdukteigenschaften(
			Produkteigenschaften produkteigenschaften) {
		getProdukteigenschaftens().remove(produkteigenschaften);
		produkteigenschaften.setJob(null);

		return produkteigenschaften;
	}

}