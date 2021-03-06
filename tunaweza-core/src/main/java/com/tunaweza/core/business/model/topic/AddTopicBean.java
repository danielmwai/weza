/*
 * The MIT License
 *
 * Copyright 2013 Daniel Mwai <naistech.gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tunaweza.core.business.model.topic;

import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class AddTopicBean {
	@NotNull
	private String name;

	@NotNull
	private String module;

	@NotNull
	private String parent;

	@NotNull
	private String text;

	private String _exercise;

	private String exercise;

	private CommonsMultipartFile attachedfile;

	private String evaluationQuestionsLimit;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

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

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the _exercise
	 */
	public String get_exercise() {
		return _exercise;
	}

	/**
	 * @param _exercise
	 *            the _exercise to set
	 */
	public void set_exercise(String _exercise) {
		this._exercise = _exercise;
	}

	/**
	 * @return the exercise
	 */
	public String getExercise() {
		return exercise;
	}

	/**
	 * @param exercise
	 *            the exercise to set
	 */
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public CommonsMultipartFile getAttachedfile() {

		return attachedfile;
	}

	public void setAttachedfile(CommonsMultipartFile attachedfile) {
		this.attachedfile = attachedfile;
	}

	/**
	 * @return the evaluationQuestionsLimit
	 */
	public String getEvaluationQuestionsLimit() {
		return evaluationQuestionsLimit;
	}

	/**
	 * @param evaluationQuestionsLimit
	 *            the evaluationQuestionsLimit to set
	 */
	public void setEvaluationQuestionsLimit(String evaluationQuestionsLimit) {
		this.evaluationQuestionsLimit = evaluationQuestionsLimit;
	}

}

