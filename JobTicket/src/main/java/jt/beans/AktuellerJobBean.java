/*
 *  Copyright (C) 2014  Jan Müller, Tim Treibmann, Marcus Wanka
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

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import jt.annotations.AktuellerJob;
import jt.entities.Job;

/**
 * Die Attribute des aktuell zu bearbeitenden Jobs werden in dieser Klasse
 * zwischengespeichert, so dass sie beim Navigieren zwischen den Seiten erhalten
 * bleiben.
 * 
 * @author Jan Müller
 * @author Tim Treibmann
 * @author Marcus Wanka
 */
@Named
@SessionScoped
public class AktuellerJobBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	@AktuellerJob
	private transient Job job;

	private boolean istNeuesTicket;

	/**
	 * Getter methode für den aktuellen Job.
	 * @return
	 */
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public boolean isIstNeuesTicket() {
		return istNeuesTicket;
	}

	public void setIstNeuesTicket(boolean istNeuesTicket) {
		this.istNeuesTicket = istNeuesTicket;
	}
	
	/**
	 * Gibt zurück ob der Aktuelle Job vom aktuell angemeldeten User erstellt wurde.
	 * @return TRUE wenn der Job vom Angemeldeten User ist, FALSE falls nicht
	 */
	public boolean jobMadeByRemoteUser(){
		FacesContext fc = FacesContext.getCurrentInstance();
		return job.getErsteller().equals(fc.getExternalContext().getRemoteUser());
	}
}
