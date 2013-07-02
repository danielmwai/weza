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
package com.tunaweza.web.question;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class EditQuestionBean {

    private String level;
    @NotEmpty
    private String text;
    @NotEmpty
    private String choiceOne;
    @NotEmpty
    private String choiceTwo;
    @NotEmpty
    private String choiceThree;
    @NotEmpty
    private String choiceFour;

    private boolean choiceTwoCorrect;

    private boolean choiceThreeCorrect;

    private boolean choiceFourCorrect;

    public boolean getChoiceThreeCorrect() {
        return choiceThreeCorrect;
    }

    public void setChoiceThreeCorrect(boolean choiceThreeCorrect) {
        this.choiceThreeCorrect = choiceThreeCorrect;
    }

    public boolean getChoiceFourCorrect() {
        return choiceFourCorrect;
    }

    public void setChoiceFourCorrect(boolean choiceFourCorrect) {
        this.choiceFourCorrect = choiceFourCorrect;
    }

    public String getChoiceThree() {
        return choiceThree;
    }

    public void setChoiceThree(String choiceThree) {
        this.choiceThree = choiceThree;
    }

    public String getChoiceFour() {
        return choiceFour;
    }

    public void setChoiceFour(String choiceFour) {
        this.choiceFour = choiceFour;
    }

    public boolean getChoiceTwoCorrect() {
        return choiceTwoCorrect;
    }

    public boolean isChoiceTwoCorrect() {
        return choiceTwoCorrect;
    }

    public void setChoiceTwoCorrect(boolean choiceTwoCorrect) {
        this.choiceTwoCorrect = choiceTwoCorrect;
    }

    public String getChoiceOne() {
        return choiceOne;
    }

    public void setChoiceOne(String choiceOne) {
        this.choiceOne = choiceOne;
    }

    public String getChoiceTwo() {
        return choiceTwo;
    }

    public void setChoiceTwo(String choiceTwo) {
        this.choiceTwo = choiceTwo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
