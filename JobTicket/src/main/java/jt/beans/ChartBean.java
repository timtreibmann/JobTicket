package jt.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import jt.entities.Angestellte;
import jt.entities.Job;
import jt.entities.Kosten;

/**
 * Diese Bean hilft bei der erstellung der Charts.
 * 
 * @author Marcus Wanka
 */
@Named
@RequestScoped
public class ChartBean implements Serializable {

	@Inject
	StartBean startBean;

	@Inject
	AngestellteBean angestellteBean;

	@Inject
	OptionenBean options;

	private PieChartModel pieChartModel;
	private BarChartModel barChartModel;

	/**
	 * Initialisiert die Models für die Diagramme.
	 */
	@PostConstruct
	public void init() {
		pieChartModel = new PieChartModel();
		initBarModel();
	}

	/**
	 * Gibt eine Map zurück die als Data für ein pieChartModel genutzt werden
	 * kann und die Stunden angibt, die der übergebene Angestellte für
	 * verschiedene Kunden gearbeitet hat.
	 * 
	 * @param angestellte
	 *            Der Angestellte dessen statistik geladen werdensoll.
	 * @return Die Map die man als Data für ein pieChartModel verwenden kann.
	 */
	private Map<String, Number> createPieModelDataForUser(Angestellte angestellte){
		Map<String, Number> map = new LinkedHashMap<String, Number>();
		List<Job> jobs = angestellteBean.getJobsFromAngestellten(angestellte);
		for(Job j: jobs){
			List<Kosten> kosten = j.getKostens();
			for(Kosten k: kosten){
				if(k.getAngestellte().getId() == angestellte.getId()){
					double neueKosten = 0;
					if(k.getArbeitsaufwandIstInEuro() == 1){					
						AufwandBean aB = new AufwandBean();
						neueKosten = aB.berechneAufwandInStd(k);
					}else{
						neueKosten = k.getArbeitsaufwand();
					}
					String kunde = "Unbekannter Kunde";
					if(j.getKunde() != null){
						kunde = j.getKunde().getName();
					}
					if(map.containsKey(kunde)){
						Number alteKosten = map.get(kunde);
						Number summe = alteKosten.doubleValue() + neueKosten;
						map.put(kunde, summe);
					}else{
						map.put(kunde, neueKosten);
					}
				}
			}
		}
		return map;
	}

	/**
	 * Initialisiert die Eigenschaft barChartModel mit den Stunden die für alle Kunden jemals gearbeitet wurden.
	 */
	private void initBarModel(){
		barChartModel = new BarChartModel();
		List<Job> jobs = startBean.getAllJobs();
		Map<Object, Number> data = new LinkedHashMap<Object, Number>();
		for(Job j: jobs){
			String key = "Unbekannter Kunde";
			if(j.getKunde() != null){
				key = j.getKunde().getName();
			}
			Number oldValue = 0;
			if(data.containsKey(key)){
				oldValue = data.get(key);	
			}
			Number summe = oldValue.doubleValue() + j.getGesamtZeitaufwand();
			data.put(key, summe);
		}
		ChartSeries serie = new ChartSeries();
		serie.setData(data);
		barChartModel.addSeries(serie);
	}

	/**
	 * Überschreibt die Daten vom pieChartModel mit den Daten vom Übergebenen
	 * User und schickt den Client dann auf die chart-Seite.
	 * 
	 * @param angestellte
	 *            Angestellter dessen Diagramm man sehen möchte
	 */
	public void openUserChart(Angestellte angestellte) {
		pieChartModel.setData(createPieModelDataForUser(angestellte));
		startBean.goToPage("user_charts.xhtml");
	}

	/**
	 * Überschreibt die Daten vom pieChartModel mit den Daten vom angemeldeten
	 * User und schickt den Client dann auf die chart-Seite.
	 */
	public void openActiveUserChart() {
		Angestellte aktuellerAngestellter = angestellteBean
				.findAngestelltenByID(options.getAngemeldeterMitarbeiterId());
		openUserChart(aktuellerAngestellter);
	}
	
	
	public PieChartModel getPieChartModel() {
		return pieChartModel;
	}

	/**
	 * @return the barChartModel
	 */
	public BarChartModel getBarChartModel() {
		return barChartModel;
	}
}
