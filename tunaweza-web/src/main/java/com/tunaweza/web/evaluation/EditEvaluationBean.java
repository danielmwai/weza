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
package com.tunaweza.web.evaluation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class EditEvaluationBean {

    private String id;

    public String getId() {
        return id;
    }

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    @Size(min = 3)
    private String description;

    @NotNull
    private double factoring;

    @NotNull
    private double numberOfQuestions;

    @NotNull
    private double duration;

    @NotNull
    @Min(1)
    @Max(100)
    private int passMark;

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

    public double getFactoring() {
        return factoring;
    }

    public void setFactoring(double factoring) {
        this.factoring = factoring;
    }

    public double getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(double numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the passMark
     */
    public int getPassMark() {
        return passMark;
    }

    /**
     * @param passMark the passMark to set
     */
    public void setPassMark(int passMark) {
        this.passMark = passMark;
    }

}
