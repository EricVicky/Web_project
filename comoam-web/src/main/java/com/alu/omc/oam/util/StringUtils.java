/* ------------------------------------------------------------------------------------------
 * Copyright (c) 2005 by Alcatel CIT. All rights reserver
 * ------------------------------------------------------------------------------------------
 * FILE                           : OrchUtils
 * ------------------------------------------------------------------------------------------
 * DESCRIPTION              : 
 * CREATION DATE            : July, 2014 
 * AUTHOR                   : Rex duan
 * 
 * PROJECT                  : OMC-CN
 * ------------------------------------------------------------------------------------------
 * CLASS
 *
 * ------------------------------------------------------------------------------------------
 * HISTORY
 * July, 2014     rex duan     Creation 
 * ------------------------------------------------------------------------------------------
 */
package com.alu.omc.oam.util;

public class StringUtils {

    /**
     * helper method: check the string is null
     * 
     * @param String
     * @return boolean
     */
    public static boolean checkStringIsNull(String s) {
        if (s == null) {
            return true;
        }
        if (s.trim().equals("")) {
            return true;
        }
        if (s.trim().equals("null")) {
            return true;
        }
        if (s.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * helper method: check the object is null
     * 
     * @param object
     *            []
     * @return boolean
     */
    public static boolean checkObjectIsNull(Object object) {
        if (object == null) {
            return true;
        }
        return false;
    }
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
