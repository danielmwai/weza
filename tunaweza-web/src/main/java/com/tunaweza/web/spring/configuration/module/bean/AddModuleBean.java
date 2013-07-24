package com.tunaweza.web.spring.configuration.module.bean;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

/**
 * @author Jacktone Mony
 */
public class AddModuleBean {

	@NotNull
	@Size(min = 2, max = 20)
	private String moduleName;

	@NotNull
	private String moduleDescription;

	private boolean enabled;

	private String evaluated;

	private String timeToComplete;

	private String _evaluated;

	private String _enabled;

	private String _mentoring;

	private String mentoring;
	
	private String duration;
	
	private String preRequisites;

	// This field is not useful.when posting input field from the form with path
	// preRequisites, json posts the input to preRequisites then posts a value of
	// 1 to _preRequisites. The lack of this variable _preRequisites would
	// result in an error when the form is posted.
	private String _preRequisites;

	public String getMentoring() {
		return mentoring;
	}

	public void setMentoring(String mentoring) {
		this.mentoring = mentoring;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDescription() {
		return moduleDescription;
	}

	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEvaluated() {
		return evaluated;
	}

	public void setEvaluated(String evaluated) {
		this.evaluated = evaluated;
	}

	public String getTimeToComplete() {
		return timeToComplete;
	}

	public void setTimeToComplete(String timeToComplete) {
		this.timeToComplete = timeToComplete;
	}

	public String get_evaluated() {
		return _evaluated;
	}

	public void set_evaluated(String _evaluated) {
		this._evaluated = _evaluated;
	}

	public String get_enabled() {
		return _enabled;
	}

	public void set_enabled(String _enabled) {
		this._enabled = _enabled;
	}

	public String get_mentoring() {
		return _mentoring;
	}

	public void set_mentoring(String _mentoring) {
		this._mentoring = _mentoring;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getPreRequisites() {
		return preRequisites;
	}

	public void setPreRequisites(String preRequisites) {
		this.preRequisites = preRequisites;
	}

	public String get_preRequisites() {
		return _preRequisites;
	}

	public void set_preRequisites(String _preRequisites) {
		this._preRequisites = _preRequisites;
	}

}
