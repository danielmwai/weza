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
package com.tunaweza.web.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class ChangeAdminPasswordBean {

    @NotNull
    private Long company_id;

    @NotNull
    @Size(min = 6, max = 20)
    private String newPassword;

    @NotNull
    @Size(min = 6, max = 20)
    private String newPasswordConfirm;

    /**
     * returns the company id
     *
     * @return company_id
     */
    public Long getCompany_id() {
        return company_id;
    }

    /**
     * sets the company_id
     *
     * @param company_id
     */
    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    /**
     * gets the new password
     *
     * @return newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * sets the new password
     *
     * @param newPassword
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * gets the new confirm password
     *
     * @return newPasswordConfirm
     */
    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    /**
     * sets the confirm password
     *
     * @param newPasswordConfirm
     */
    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }

}