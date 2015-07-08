package com.alu.omc.oam.util;

import java.util.TimeZone;

public class COMTimeZone
{
    public static String timeOffsetIsPositiveorNot (double timeset){
        if(timeset != 0){
            return timeset > 0 ? "+" : "-";
        }else{
            return "";
        }
    }
    

   private static String pad( int val, int cols )
   {
       String valStr = val + "";
       if ( valStr.length() == cols ) return valStr;
       if ( valStr.length() > cols ) return valStr.substring( 0, cols );
       
       StringBuffer paddedStr = new StringBuffer();
       for ( int i = 0; i < cols - valStr.length(); i++ )
       {
           paddedStr.append( "0" );
       }
       
       paddedStr.append( valStr );
       
       return paddedStr.toString();
   }

    
    public static int timeOffSetPositive(int timeset){
        return timeset > 0 ? timeset : -timeset;
    }
    
    /**
     * This function is used for getting all the time zones defined by Java. 
     */
    
    public static Timezone[] getTimeZone(){
        String[] avaIds = TimeZone.getAvailableIDs();
        Timezone[] TIMEZONE = new Timezone[avaIds.length];
        
        for (int i = 0; i < avaIds.length; i++) {
            double offset = (TimeZone.getTimeZone(avaIds[i]).getRawOffset());
            double offset_proper = offset / (3600 * 1000);
            double offset_minute = (offset_proper - (int)offset_proper) > 0 ? (offset_proper - (int)offset_proper) : - ((offset_proper - (int)offset_proper));
            Timezone timezone = new Timezone();
            if(offset != 0){
                timezone.setLabel("(GMT" + timeOffsetIsPositiveorNot(offset_proper) + pad( timeOffSetPositive((int)offset_proper), 2 ) + ":" + pad((int)(offset_minute * 60), 2) + ") " + avaIds[i]);
                }else{
                    timezone.setLabel("(GMT) " + avaIds[i]);
                } 
            timezone.setId(avaIds[i]);
            TIMEZONE[i] = timezone;
        }
        return TIMEZONE;
    }
}
