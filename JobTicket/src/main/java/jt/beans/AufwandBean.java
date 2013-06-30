package jt.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import jt.annotations.AktuellerJob;
import jt.entities.Job;

@Named
@RequestScoped
public class AufwandBean {
	@Inject
	@AktuellerJob
	private Job job;

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Inject
	private AngestellteBean angestellteBean;

	private int selectedAngestellteId;

	public int getSelectedAngestellteId() {
		return selectedAngestellteId;
	}

	public void setSelectedAngestellteId(int selectedAngestellteId) {
		this.selectedAngestellteId = selectedAngestellteId;
	}

}
