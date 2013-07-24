/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tunaweza.core.business.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author naistech
 */
public class StringUtils {
    
        /**
         * 
         * @param myString
         * @return
         */
        public static String[] splitStringToList(String myString) {
                String[] finalStringList = null;
                String pattern = "[,]";
                Pattern splitter = Pattern.compile(pattern);
                finalStringList = splitter.split(myString);
                return finalStringList;
        }

        /**
         * Removes the commas and brackets from stringWithCommas
         * 
         * @param stringWithCommas ,
         *            <code>String</code> to remove commas
         * @return <code>String</code> array without the commas and brackets
         */
        public static String[] removeCommas(String stringWithCommas) {
                Pattern splitter = Pattern.compile("[,]");
                String[] result = null;
                if (stringWithCommas != null)
                        result = splitter.split(stringWithCommas);
                return result;
        }

        public static String[] tokenizeString(String toTokenizer) {
                Pattern splitter = Pattern.compile(" ");
                String[] result = null;
                if (toTokenizer != null)
                        result = splitter.split(toTokenizer);
                return result;
        }

        public static int convertStringToLong(String stringToConvert) {
                int result = 0;
                try {
                        result = Integer.valueOf(stringToConvert);
                } catch (NumberFormatException numberFormatException) {
                }
                return result;
        }

        public static boolean IsConvertableToLong(String stringToConvert) {
                boolean flag = false;
                try {
                        Integer.valueOf(stringToConvert);
                        flag = true;
                } catch (NumberFormatException numberFormatException) {
                }
                return flag;
        }

        public static String convertToStringWithCommas(String[] days) {
                StringBuilder builder = new StringBuilder();
                for (int index = 0; index < days.length; index++) {
                        if (index != days.length - 1) {
                                builder.append(days[index]);
                                builder.append(",");
                        } else {
                                builder.append(days[index]);
                        }
                }
                return builder.toString();
        }

        public static String append(String originalString, long userId) {
                StringBuilder builder = new StringBuilder();
                if (originalString != null && originalString.length() > 0) {
                        builder.append(originalString);
                        char lastchar = originalString.charAt(originalString.length() - 1);
                        if (lastchar != ',')
                                builder.append(",");
                        builder.append(userId);
                } else {
                        builder.append(userId);
                }
                return builder.toString();
        }

        public static String applicationUrl(HttpServletRequest request) {
                URL url = null;
                try {
                        url = new URL(request.getScheme(), request.getServerName(), request
                                        .getServerPort(), request.getContextPath());
                } catch (MalformedURLException e) {
                        e.printStackTrace();
                }
                return url.toString();
        }

        public static void main(String[] args) {
                removeCommas(null);
                tokenizeString("this this");
                System.out.println(append("ddc,", 1));

        }
}
