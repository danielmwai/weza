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
package com.tunaweza.web.course;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
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
