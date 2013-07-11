/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.dao.exceptions.mentor;

/**
 *
 * @author naistech
 */
public class MentorTemplateExistsException extends Exception {
 
    public MentorTemplateExistsException(Throwable cause) {
        super("MentorTemplateExistsException"+cause);
    }

    public MentorTemplateExistsException(String message, Throwable cause) {
        super("MentorTemplateExistsException:"+message, cause);
    }

    public MentorTemplateExistsException(String message) {
        super("MentorTemplateExistsException:"+message);
    }

    public MentorTemplateExistsException() {
        super();
    }
    }
