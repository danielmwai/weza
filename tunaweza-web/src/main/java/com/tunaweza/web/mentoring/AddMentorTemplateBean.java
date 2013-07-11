package com.tunaweza.web.mentoring;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author montex, Keyman
 * 
 */

public class AddMentorTemplateBean {

	// private List<Module> modules;
	@NotNull
	@Size(min = 3, max = 50)
	private String name;

	@NotNull
	private String description;

	private String module;

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module
	 *            the module to set
	 */
	public void setModule(String module) {
		this.module = module;
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

}
