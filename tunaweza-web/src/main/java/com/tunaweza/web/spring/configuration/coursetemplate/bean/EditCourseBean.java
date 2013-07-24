package com.tunaweza.web.spring.configuration.coursetemplate.bean;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Jacktone Mony
 * 
 */

public class EditCourseBean {

	private String id;

	@NotNull
	@Size(min = 2, max = 50)
	private String name;
	@NotNull
	private String description;

	private String evaluated;

	private String _evaluated;

	private String preRequisites;

	// This field is not useful.when posting input field from the form with path
	// preRequisites, json posts the input to preRequisites then posts a value
	// of 1 to _preRequisites. The lack of this variable _preRequisites would
	// result in an error when the form is posted.
	private String _preRequisites;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
