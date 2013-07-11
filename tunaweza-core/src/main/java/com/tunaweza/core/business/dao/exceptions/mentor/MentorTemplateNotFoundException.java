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
public class MentorTemplateNotFoundException extends Exception {
 
    public MentorTemplateNotFoundException(Throwable cause) {
        super("MentorTemplateNotFoundException"+cause);
    }

    public MentorTemplateNotFoundException(String message, Throwable cause) {
        super("MentorTemplateNotFoundException:"+message, cause);
    }

    public MentorTemplateNotFoundException(String message) {
        super("MentorTemplateNotFoundException:"+message);
    }

    public MentorTemplateNotFoundException() {
        super();
    }
    }
