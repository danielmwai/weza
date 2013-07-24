package com.tunaweza.web.spring.configuration.module.bean;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

/**
 * @author Jacktone Mony
 * @author David Gitonga
 */
public class EditModuleBean
{
	private String moduleId;
	
	@NotNull
	@Size(min = 2, max = 20)
	private String moduleName;
	
	private String evaluated;
	
	/**
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@NotNull
	private String moduleDescription;

	private String timeToComplete;
	
	private String _evaluated;
	
	private  String mentoring;
	
	private  String _mentoring;
	
	private String duration;

	private String preRequisites;

	// This field is not useful.when posting input field from the form with path
	// preRequisites, json posts the input to preRequisites then posts a value of
	// 1 to _preRequisites. The lack of this variable _preRequisites would
	// result in an error when the form is posted.
	private String _preRequisites;

	public String get_mentoring() {
		return _mentoring;
	}

	public void set_mentoring(String _mentoring) {
		this._mentoring = _mentoring;
	}

	public String getMentoring() {
		return mentoring;
	}

	public void setMentoring(String mentoring) {
		this.mentoring = mentoring;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getModuleDescription()
	{
		return moduleDescription;
	}

	public void setModuleDescription(String moduleDescription)
	{
		this.moduleDescription = moduleDescription;
	}

	public String getTimeToComplete()
	{
		return timeToComplete;
	}

	public void setTimeToComplete(String timeToComplete)
	{
		this.timeToComplete = timeToComplete;
	}

	public String getEvaluated() {
		return evaluated;
	}

	public void setEvaluated(String evaluated) {
		this.evaluated = evaluated;
	}

	public String get_evaluated() {
		return _evaluated;
	}

	public void set_evaluated(String _evaluated) {
		this._evaluated = _evaluated;
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