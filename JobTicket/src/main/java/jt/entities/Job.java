package jt.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the JOBS database table.
 * 
 */
@Entity
@Table(schema="JOBTICKET", name="JOBS")
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="ALTE_JOBNUMMER")
	private int alteJobnummer;

	@Column(name="BUDGET_IN_EURO")
	private BigDecimal budgetInEuro;

	@Column(name="BUDGET_IN_STD")
	private BigDecimal budgetInStd;

	private String empfaenger;

	private String jobbeschreibung;

	private String name;

	private String print;

	@Temporal(TemporalType.DATE)
	private Date vorlage;

	//bi-directional many-to-one association to Jobbearbeiter
	@OneToMany(mappedBy="job")
	private List<Jobbearbeiter> jobbearbeiters;

	//bi-directional many-to-one association to Kunde
	@ManyToOne
	private Kunde kunde;

	//bi-directional many-to-one association to Kosten
	@OneToMany(mappedBy="job")
	private List<Kosten> kostens;

	//bi-directional many-to-one association to Produkteigenschaften
	@OneToMany(mappedBy="job", cascade = { CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.PERSIST })
	private List<Produkteigenschaften> produkteigenschaftens;

	public Job() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAlteJobnummer() {
		return this.alteJobnummer;
	}

	public void setAlteJobnummer(int alteJobnummer) {
		this.alteJobnummer = alteJobnummer;
	}

	public BigDecimal getBudgetInEuro() {
		return this.budgetInEuro;
	}

	public void setBudgetInEuro(BigDecimal budgetInEuro) {
		this.budgetInEuro = budgetInEuro;
	}

	public BigDecimal getBudgetInStd() {
		return this.budgetInStd;
	}

	public void setBudgetInStd(BigDecimal budgetInStd) {
		this.budgetInStd = budgetInStd;
	}

	public String getEmpfaenger() {
		return this.empfaenger;
	}

	public void setEmpfaenger(String empfaenger) {
		this.empfaenger = empfaenger;
	}

	public String getJobbeschreibung() {
		return this.jobbeschreibung;
	}

	public void setJobbeschreibung(String jobbeschreibung) {
		this.jobbeschreibung = jobbeschreibung;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrint() {
		return this.print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public Date getVorlage() {
		return this.vorlage;
	}

	public void setVorlage(Date vorlage) {
		this.vorlage = vorlage;
	}

	public List<Jobbearbeiter> getJobbearbeiters() {
		return this.jobbearbeiters;
	}

	public void setJobbearbeiters(List<Jobbearbeiter> jobbearbeiters) {
		this.jobbearbeiters = jobbearbeiters;
	}

	public Jobbearbeiter addJobbearbeiter(Jobbearbeiter jobbearbeiter) {
		getJobbearbeiters().add(jobbearbeiter);
		jobbearbeiter.setJob(this);

		return jobbearbeiter;
	}

	public Jobbearbeiter removeJobbearbeiter(Jobbearbeiter jobbearbeiter) {
		getJobbearbeiters().remove(jobbearbeiter);
		jobbearbeiter.setJob(null);

		return jobbearbeiter;
	}

	public Kunde getKunde() {
		return this.kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public List<Kosten> getKostens() {
		return this.kostens;
	}

	public void setKostens(List<Kosten> kostens) {
		this.kostens = kostens;
	}

	public Kosten addKosten(Kosten kosten) {
		getKostens().add(kosten);
		kosten.setJob(this);

		return kosten;
	}

	public Kosten removeKosten(Kosten kosten) {
		getKostens().remove(kosten);
		kosten.setJob(null);

		return kosten;
	}

	public List<Produkteigenschaften> getProdukteigenschaftens() {
		return this.produkteigenschaftens;
	}

	public void setProdukteigenschaftens(List<Produkteigenschaften> produkteigenschaftens) {
		this.produkteigenschaftens = produkteigenschaftens;
	}

	public Produkteigenschaften addProdukteigenschaften(Produkteigenschaften produkteigenschaften) {
		getProdukteigenschaftens().add(produkteigenschaften);
		produkteigenschaften.setJob(this);

		return produkteigenschaften;
	}

	public Produkteigenschaften removeProdukteigenschaften(Produkteigenschaften produkteigenschaften) {
		getProdukteigenschaftens().remove(produkteigenschaften);
		produkteigenschaften.setJob(null);

		return produkteigenschaften;
	}

}