package com.tunaweza.web.spring.configuration.topic.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class ResultsBean {
    @NotEmpty
    private String question;
    
    @NotEmpty
    private String result;
    
    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

}
