/*
 *  Copyright (C) 2014  Jan Müller, Tim Treibmann
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jt.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;

import jt.annotations.AktuellerJob;
import jt.entities.Job;
import jt.entities.Produkteigenschaften;

/**
 * Diese Klasse stellt die Anwendungslogik für die
 * "jobticket_produktbeschreibung.xhtml" bereit. Sie dient zur Verwaltung von
 * Produkteigenschaften, die zu einem Job gehören.
 * 
 * @author Jan Müller
 * @author Tim Treibmann
 */
@Named
@RequestScoped
public class ProdukteigenschaftenBean {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	@Inject
	@AktuellerJob
	private Job job;

	@Inject
	private Produkteigenschaften produkteigenschaften;
	private int accordionIndex;

	private int detailsAccordionIndex;

	private boolean wurdeProdukteigenschaftErzeugt;

	@PostConstruct
	private void init() {
		if (job.getProdukteigenschaftens().size() == 0) {
			createProdukteigenschaft();
			wurdeProdukteigenschaftErzeugt = true;
		}
		// Details ausklappen, wenn Inhalte eingetragen wurden
		accordionIndex = 0;
		try {
			if (sindDetailsVorhanden(job.getProdukteigenschaftens().get(
					accordionIndex))) {
				detailsAccordionIndex = 0;
			} else {
				detailsAccordionIndex = -1;
			}
		} catch (Exception e) {
			detailsAccordionIndex = -1;
		}
	}

	/**
	 * Erzeugt eine Produkteigenschaft und ordnet sie dem aktuellen Job zu.
	 * Berechnet den Fortschritt des Jobs neu.
	 * 
	 * @return null - immer
	 */
	public String createProdukteigenschaft() {
		// nur eine neue Produkteigenschaft erzeugen, wenn in der init Methode
		// noch keine automatisch erzeugt wurde
		if (!wurdeProdukteigenschaftErzeugt) {
			Produkteigenschaften produkteigenschaften = new Produkteigenschaften();
			Date d = new Date();
			produkteigenschaften.setEingangsdatum(d);
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			produkteigenschaften
					.setProduktbeschreibung("Neues Produkt - Daten eintragen - "
							+ formatter.format(d));
			produkteigenschaften.setErledigt(0);
			job.addProdukteigenschaften(produkteigenschaften);
			accordionIndex = job.getProdukteigenschaftens().size() - 1;
			ermittleFortschritt();
		}
		return null;
	}

	public String updateProdukteigenschaften(
			Produkteigenschaften produkteigenschaften) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(
				"Daten erfolgreich gespeichert!", "Produkteigenschaften"));
		ermittleFortschritt();
		return "jobticket_produktbeschreibung.xhtml";
	}

	public String ermittleFortschritt() {
		double fortschritt = 0;
		List<Produkteigenschaften> list = job.getProdukteigenschaftens();
		int produktAnzahl = list.size();
		if (produktAnzahl != 0) {
			int erledigtAnzahl = 0;
			for (Produkteigenschaften p : list) {
				System.out.println(p.getErledigt());
				erledigtAnzahl += p.getErledigt();
			}
			fortschritt = (erledigtAnzahl * 100 / produktAnzahl);
		}
		job.setFortschritt(fortschritt);
		return null;
	}

	public String delete(Produkteigenschaften produkteigenschaften) {
		job.removeProdukteigenschaften(produkteigenschaften);
		ermittleFortschritt();
		return null;
	}

	public String toggleErledigt(Produkteigenschaften produkteigenschaften) {
		if (produkteigenschaften.getErledigt() == 0) {
			produkteigenschaften.setErledigt(1);
		} else {
			produkteigenschaften.setErledigt(0);
		}
		updateProdukteigenschaften(produkteigenschaften);
		return null;
	}

	public void onTabChange(TabChangeEvent event) {
		String activeIndex = ((AccordionPanel) event.getComponent())
				.getActiveIndex();
		int index = Integer.parseInt(activeIndex);
		try {

			if (sindDetailsVorhanden(job.getProdukteigenschaftens().get(index))) {
				detailsAccordionIndex = 0;
			} else {
				detailsAccordionIndex = -1;
			}

		} catch (Exception e) {
			detailsAccordionIndex = -1;
		}
		System.out.println("tabchange" + detailsAccordionIndex);
	}

	private boolean sindDetailsVorhanden(Produkteigenschaften p) {
		return (p.getFomat().length() + p.getFarbe4c().length()
				+ p.getSeitenzahl() + p.getBindung().length()
				+ p.getDummy().length() + p.getBeschnitt().length()
				+ p.getSonderfarbe().length() + p.getFalzung().length() + p
				.getProof().length()) > 0;

	}

	public Produkteigenschaften getProdukteigenschaften() {
		return produkteigenschaften;
	}

	public void setProdukteigenschaften(
			Produkteigenschaften produkteigenschaften) {
		this.produkteigenschaften = produkteigenschaften;
	}

	public void setAccordionIndex(int index) {
		this.accordionIndex = index;
	}

	public int getAccordionIndex() {
		return this.accordionIndex;
	}

	public int getProduktAnzahl() {
		return job.getProdukteigenschaftens().size();
	}

	public int getAccordionIndex1() {
		return detailsAccordionIndex;
	}

	public void setAccordionIndex1(int accordionIndex1) {
		this.detailsAccordionIndex = accordionIndex1;
	}

}
