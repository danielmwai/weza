/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.utils;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 *
 * @author naistech
 */
public class CredentialsEncoder {
	
	public static String generateMD5(String password)
	{
	Md5PasswordEncoder encoder = new Md5PasswordEncoder();
   
   String hashedPass= encoder.encodePassword(password, null);
return hashedPass;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
