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
 * @author Jan Müller
 * @author Tim Treibmann 
 * Diese Entity Klasse ist die Projektion der Datenbank
 *         Jobs mit ihren jeweiligen Spalten als Eigenschaften
 */
@Entity
@Table(schema = "JOBTICKET", name = "JOBS")
@NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j")
public class Job implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Stellt den Primärschlüssel in dieser Datenbank dar. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * Die Eigenschaft altejobnummer stellt den Wert für eine Jobnummer von
	 * Jobtickts aus der Vergangenheit dar.
	 */
	@Column(name = "ALTE_JOBNUMMER")
	private int alteJobnummer;

	/**
	 * Die Eigenschaft budgetInEuro stellt den Wert für das Budget in Euro für
	 * das jeweilige Projekt dar.
	 */
	@Column(name = "BUDGET_IN_EURO")
	private double budgetInEuro;

	/**
	 * Die Eigenschaft budgetInStd stellt den Wert für das Budget in Stunde für
	 * das jeweilige Projekt dar.
	 */
	@Column(name = "BUDGET_IN_STD")
	private double budgetInStd;

	/**
	 * Die Eigenschaft empfaenger stellt den Wert der Person, die für die
	 * Bearbeitung des Jobs verantwortlich ist dar.
	 */
	private String empfaenger;

	/**
	 * Die Eigenschaft jobbeschreibung dient für zusätzliche
	 * Informationenbeschreibungen bezüglich eines Job.
	 */
	private String jobbeschreibung;

	/** Die Eigenschaft name beschreibt den Namen eines Jobs. */
	private String name;

	/** The print. */
	private String print;

	/**
	 * Die Eigenschaft ersteller stellt den Wert des Erzeugers des Jobtickets
	 * dar.
	 */
	private String ersteller;

	/** Die Eigenschaft erstellDatum gibt an wann der Job erstellt wurde. */
	@Temporal(TemporalType.DATE)
	private Date erstellDatum;

	/**
	 * Die Eigenschaft fortschritt gibt an, ob ein Job beendet wurde oder sich
	 * noch im Bearbeitungszyklus befindet
	 */
	private double fortschritt;

	// bi-directional many-to-one association to Jobbearbeiter
	/**
	 * Die Eigenschaft jobbearbeiters stellt eine Liste aller Jobbearbeiter
	 * eines Jobs dar.
	 */
	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Jobbearbeiter> jobbearbeiters;

	// bi-directional many-to-one association to Kunde
	/** Die Eigenschaft kunde stellt den jeweiligen Kunden eines Jobs dar. */
	@ManyToOne
	private Kunde kunde;

	// bi-directional many-to-one association to Kosten
	/** Die Eigenschaft kostens stellt eine Liste aller Kosten eines Jobs dar. */
	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
	@CascadeOnDelete
	private List<Kosten> kostens;

	// bi-directional many-to-one association to Produkteigenschaften
	/**
	 * Die Eigenschaft produkteigenschaftens stellt eine Liste aller
	 * Produkteigenschaften eines Jobs dar.
	 */
	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
	@CascadeOnDelete
	private List<Produkteigenschaften> produkteigenschaftens;

	/**
	 * Konstruktor der Klasse Job Instantiates a new job.
	 */
	public Job() {
	}

	/**
	 * Getter der Eigenschaft the id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setter der Eigenschaft id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter der Eigenschaft alte jobnummer.
	 * 
	 * @return the alte jobnummer
	 */
	public int getAlteJobnummer() {
		return this.alteJobnummer;
	}

	/**
	 * Setter der Eigenschaft alte jobnummer.
	 * 
	 * @param alteJobnummer
	 *            the new alte jobnummer
	 */
	public void setAlteJobnummer(int alteJobnummer) {
		this.alteJobnummer = alteJobnummer;
	}

	/**
	 * Getter der Eigenschaft budget in euro.
	 * 
	 * @return the budget in euro
	 */
	public double getBudgetInEuro() {
		return this.budgetInEuro;
	}

	/**
	 * Setter der Eigenschaft budget in euro.
	 * 
	 * @param budgetInEuro
	 *            the new budget in euro
	 */
	public void setBudgetInEuro(double budgetInEuro) {
		this.budgetInEuro = budgetInEuro;
	}

	/**
	 * Getter der Eigenschaft budget in std.
	 * 
	 * @return the budget in std
	 */
	public double getBudgetInStd() {
		return this.budgetInStd;
	}

	/**
	 * Setter der Eigenschaft budget in std.
	 * 
	 * @param budgetInStd
	 *            the new budget in std
	 */
	public void setBudgetInStd(double budgetInStd) {
		this.budgetInStd = budgetInStd;
	}

	/**
	 * Getter der Eigenschaft empfaenger.
	 * 
	 * @return the empfaenger
	 */
	public String getEmpfaenger() {
		return this.empfaenger;
	}

	/**
	 * Setter der Eigenschaft empfaenger.
	 * 
	 * @param empfaenger
	 *            the new empfaenger
	 */
	public void setEmpfaenger(String empfaenger) {
		this.empfaenger = empfaenger;
	}

	/**
	 * Getter der Eigenschaft jobbeschreibung.
	 * 
	 * @return the jobbeschreibung
	 */
	public String getJobbeschreibung() {
		return this.jobbeschreibung;
	}

	/**
	 * Setter der Eigenschaft jobbeschreibung.
	 * 
	 * @param jobbeschreibung
	 *            the new jobbeschreibung
	 */
	public void setJobbeschreibung(String jobbeschreibung) {
		this.jobbeschreibung = jobbeschreibung;
	}

	/**
	 * Getter der Eigenschaft name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter der Eigenschaft name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter der Eigenschaft print.
	 * 
	 * @return the prints
	 */
	public String getPrint() {
		return this.print;
	}

	/**
	 * Setter der Eigenschaft prints the.
	 * 
	 * @param print
	 *            the new prints
	 */
	public void setPrint(String print) {
		this.print = print;
	}

	/** Getter der Eigenschaft ersteller */
	public String getErsteller() {
		return ersteller;
	}

	/**
	 * Setter der Eigenschaft ersteller
	 * 
	 * @param ersteller
	 *            the new ersteller
	 */
	public void setErsteller(String ersteller) {
		this.ersteller = ersteller;
	}

	/**
	 * Getter der Eigenschaft erstellDatum.
	 * 
	 * @return the erstellDatum
	 */
	public Date getErstellDatum() {
		return this.erstellDatum;
	}

	/**
	 * Setter der Eigenschaft erstellDatum.
	 * 
	 * @param erstellDatum
	 *            the new erstellDatum
	 */
	public void setErstellDatum(Date erstellDatum) {
		this.erstellDatum = erstellDatum;
	}

	/** Getter der Eigenschaft fortschritt */
	public double getFortschritt() {
		return fortschritt;
	}

	/**
	 * Setter der Eigenschaft fortschritt
	 * 
	 * @param fortschritt
	 *            the new fortschritt
	 */
	public void setFortschritt(double fortschritt) {
		this.fortschritt = fortschritt;
	}

	/**
	 * Getter der Eigenschaft jobbearbeiters.
	 * 
	 * @return the jobbearbeiters
	 */
	public List<Jobbearbeiter> getJobbearbeiters() {
		return this.jobbearbeiters;
	}

	/**
	 * Setter der Eigenschaft jobbearbeiters.
	 * 
	 * @param jobbearbeiters
	 *            the new jobbearbeiters
	 */
	public void setJobbearbeiters(List<Jobbearbeiter> jobbearbeiters) {
		this.jobbearbeiters = jobbearbeiters;
	}

	/**
	 * Einen jobbearbeiter hinzufügen, indem der Parameterwert dieser Methode
	 * der Liste jobbearbeiters hinzugefügt wird. Der Wert der Eigenschaft Job
	 * in der Entity Jobbearbeiter entspricht dem Job der mit dieser Entity
	 * erzeugt wurde.
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
	 * Einen jobbearbeiter entfernen, indem der Parameterwert dieser Methode in
	 * der Liste jobbearbeiters gelöscht wird. Der Wert der Eigenschaft Job in
	 * der Entity Jobbearbeiter wird auf Null gesetzt. Somit wird die Verbindung
	 * zwischen einem Job und dem zugewiesenden Jobbearbeiter getrennt.
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
	 * Getter der Eigenschaft kunde.
	 * 
	 * @return the kunde
	 */
	public Kunde getKunde() {
		return this.kunde;
	}

	/**
	 * Setter der Eigenschaft the kunde.
	 * 
	 * @param kunde
	 *            the new kunde
	 */
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	/**
	 * Getter der Eigenschaft kostens.
	 * 
	 * @return the kostens
	 */
	public List<Kosten> getKostens() {
		return this.kostens;
	}

	/**
	 * Setter der Eigenschaft kostens.
	 * 
	 * @param kostens
	 *            the new kostens
	 */
	public void setKostens(List<Kosten> kostens) {
		this.kostens = kostens;
	}

	/**
	 * Einen Kostenbetrag hinzufügen, indem der Parameterwert dieser Methode der
	 * Liste kosten hinzugefügt wird. Der Wert der Eigenschaft Job in der Entity
	 * Kosten entspricht dem Job der mit dieser Entity erzeugt wurde.
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
	 * Einen Kostenbetrag entfernen, indem der Parameterwert dieser Methode in
	 * der Liste kosten gelöscht wird. Der Wert der Eigenschaft Job in der
	 * Entity Kosten wird auf Null gesetzt. Somit wird die Verbindung zwischen
	 * einem Job und dem zugewiesenden Kostenbetrag getrennt.
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
	 * Getter der Eigenschaft produkteigenschaftens.
	 * 
	 * @return the produkteigenschaftens
	 */
	public List<Produkteigenschaften> getProdukteigenschaftens() {
		return this.produkteigenschaftens;
	}

	/**
	 * Setter der Eigenschaft produkteigenschaftens.
	 * 
	 * @param produkteigenschaftens
	 *            the new produkteigenschaftens
	 */
	public void setProdukteigenschaftens(
			List<Produkteigenschaften> produkteigenschaftens) {
		this.produkteigenschaftens = produkteigenschaftens;
	}

	/**
	 * Produkteigenschaften hinzufügen, indem der Parameterwert dieser Methode
	 * der Liste produkteigenschaftens hinzugefügt wird. Der Wert der
	 * Eigenschaft Job in der Entity produkteigenschaften entspricht dem Job der
	 * mit dieser Entity erzeugt wurde.
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
	 * Eine Produkteigenschaften entfernen, indem der Parameterwert dieser
	 * Methode in der Liste produkteigenschaftens gelöscht wird. Der Wert der
	 * Eigenschaft Job in der Entity Produkteigenschaften wird auf Null gesetzt.
	 * Somit wird die Verbindung zwischen einem Job und dem zugewiesenden
	 * Produkteigenschaften getrennt.
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

	/** Stellt den Wert der Eigenschaft id als String dar */
	public String toString() {
		return id + " ";
	}

}