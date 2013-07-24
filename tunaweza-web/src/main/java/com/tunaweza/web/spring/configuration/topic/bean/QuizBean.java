package com.tunaweza.web.spring.configuration.topic.bean;

/**
 * @author Ephraim Muhia
 */
public class QuizBean {

    private String textin;

    private String id;

    private String answer;

    private String _answer;

    /**
     * @return the text
     */
    public String getTextin() {
        return textin;
    }

    /**
     * @param text the text to set
     */
    public void setTextin(String textin) {
        this.textin = textin;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the _answer
     */
    public String get_answer() {
        return _answer;
    }

    /**
     * @param _answer the _answer to set
     */
    public void set_answer(String _answer) {
        this._answer = _answer;
    }

}
