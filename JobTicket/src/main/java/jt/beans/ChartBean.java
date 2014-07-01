package jt.beans;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import jt.entities.Angestellte;
import jt.entities.Job;
import jt.entities.Kosten;

/**
 * Diese Bean hilft bei der erstellung der Charts.
 * @author Marcus Wanka
 */
@Named
@RequestScoped
public class ChartBean implements Serializable{
	
	@Inject
	StartBean startBean;
	
	@Inject
	AngestellteBean angestellteBean;

	@Inject
	OptionenBean options;
	
	private PieChartModel pieChartModel;
	
	/**
	 * Initialisiert das Model für das Kreisdiagramm mit den Stunden,
	 * die der angemeldete Mitarbeiter für die verschiedenen Kunden aufgebracht hat.
	 */
	@PostConstruct
	public void initPieModel(){		
		pieChartModel = new PieChartModel();
	}
	
	/**
	 * Überschreibt die Daten vom pieChartModel mit den Daten vom Übergebenen User 
	 * und schickt den Client dann auf die chart-Seite.
	 * @param angestellte Angestellter dessen Diagramm man sehen möchte
	 */
	public void openUserChart(Angestellte angestellte){
		pieChartModel.setData(createPieModelDataForUser(angestellte));
		startBean.goToPage("user_charts.xhtml");
	}
	
	/**
	 * Überschreibt die Daten vom pieChartModel mit den Daten vom angemeldeten User 
	 * und schickt den Client dann auf die chart-Seite.
	 */
	public void openActiveUserChart(){
		Angestellte aktuellerAngestellter = angestellteBean.findAngestelltenByID(options.getAngemeldeterMitarbeiterId());
		pieChartModel.setData(createPieModelDataForUser(aktuellerAngestellter));
		startBean.goToPage("user_charts.xhtml");
	}
	
	/**
	 * Gibt eine Map zurück die als Data für ein pieChartModel genutzt werden kann 
	 * und die Stunden angibt, die der übergebene Angestellte für verschiedene Kunden gearbeitet hat.
	 * @param angestellte Der Angestellte dessen statistik geladen werdensoll.
	 * @return Die Map die man als Data für ein pieChartModel verwenden kann.
	 */
	private Map<String, Number> createPieModelDataForUser(Angestellte angestellte){
		Map<String, Number> map = new LinkedHashMap<String, Number>();
		List<Job> jobs = angestellteBean.getJobsFromAngestellten(angestellte);
		for(Job j: jobs){
			List<Kosten> kosten = j.getKostens();
			for(Kosten k: kosten){
				if(k.getAngestellte().getId() == angestellte.getId()){
					if(k.getArbeitsaufwandIstInEuro() == 1){
						double stundenlohn = angestellte.getStundenlohn();
						if (stundenlohn != 0) {
							k.setArbeitsaufwand(k.getArbeitsaufwand() / stundenlohn);
						} else {
							k.setArbeitsaufwand(0);
						}
						k.setArbeitsaufwandIstInEuro(0);
					}
					String kunde = "Unbekannter Kunde";
					if(j.getKunde() != null){
						kunde = j.getKunde().getName();
					}
					if(map.containsKey(kunde)){
						Number alteKosten = map.get(kunde);
						Number summe = alteKosten.doubleValue() + k.getArbeitsaufwand();
						map.put(kunde, summe);
					}else{
						map.put(kunde, k.getArbeitsaufwand());
					}
				}
			}
		}
		return map;
	}
	
	public PieChartModel getPieChartModel(){
		return pieChartModel;
	}
}
