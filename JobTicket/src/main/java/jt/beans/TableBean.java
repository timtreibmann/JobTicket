package jt.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import jt.entities.Job;
@ApplicationScoped
@Named
public class TableBean implements Serializable {
	
	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	
	private List<Job> filteredJobs;

	public List<Job> getFilteredJobs() {
		System.out.println("TEEEST");
		return filteredJobs;
	}

	public void setFilteredCars(List<Job> filteredJobs) {
		System.out.println("TEEEST");
		this.filteredJobs = filteredJobs;
	}
	
	@Inject
	private JobticketBean jobticketBean;
	
	Job job;
	
	private List<Job> jobs;
	
	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(ArrayList<Job> jobs) {
		this.jobs = jobs;
	}
	
	private List<Job> erzeugeJobs() {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final Query query = em.createQuery("SELECT b FROM Job b");
		@SuppressWarnings("unchecked")
		List<Job> jobListe = query.getResultList();
		if (jobListe == null) {
			jobListe = new ArrayList<Job>();
		}
		em.getTransaction().commit();
		return jobListe;
	}

	private final static String[] colors;

	private final static String[] manufacturers;

	private SelectItem[] manufacturerOptions;
	static {
		colors = new String[10];
		colors[0] = "Black";
		colors[1] = "White";
		colors[2] = "Green";
		colors[3] = "Red";
		colors[4] = "Blue";
		colors[5] = "Orange";
		colors[6] = "Silver";
		colors[7] = "Yellow";
		colors[8] = "Brown";
		colors[9] = "Maroon";

		manufacturers = new String[10];
		manufacturers[0] = "Mercedes";
		manufacturers[1] = "BMW";
		manufacturers[2] = "Volvo";
		manufacturers[3] = "Audi";
		manufacturers[4] = "Renault";
		manufacturers[5] = "Opel";
		manufacturers[6] = "Volkswagen";
		manufacturers[7] = "Chrysler";
		manufacturers[8] = "Ferrari";
		manufacturers[9] = "Ford";
	}

	private List<Car> filteredCars;

	private List<Car> carsSmall;

	@PostConstruct
	public void init() {
		
		jobs = erzeugeJobs();
		carsSmall = new ArrayList<Car>();

		populateRandomCars(carsSmall, 9);
		manufacturerOptions = createFilterOptions(manufacturers);
	}
	


	private void populateRandomCars(List<Car> list, int size) {
		for (int i = 0; i < size; i++)
			list.add(new Car(getRandomModel(), getRandomYear(),
					getRandomManufacturer(), getRandomColor()));
	}


	private int getRandomYear() {
		return (int) (Math.random() * 50 + 1960);
	}

	private String getRandomColor() {
		return colors[(int) (Math.random() * 10)];
	}

	private String getRandomManufacturer() {
		return manufacturers[(int) (Math.random() * 10)];
	}

	private String getRandomModel() {
		return UUID.randomUUID().toString().substring(0, 8);
	}

	private SelectItem[] createFilterOptions(String[] data) {
		SelectItem[] options = new SelectItem[data.length + 1];

		options[0] = new SelectItem("", "Select");
		for (int i = 0; i < data.length; i++) {
			options[i + 1] = new SelectItem(data[i], data[i]);
		}

		return options;
	}

	public SelectItem[] getManufacturerOptions() {
		return manufacturerOptions;
	}
}
