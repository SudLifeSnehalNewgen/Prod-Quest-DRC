/**
        Project       : YES BANK
        Module        : Download Utility
        Author        : Vikram Mane
        Date          : 21-01-2013
**/
package com.newgen.ibm.util;

import com.newgen.srvr.FunctionsWI;
import com.newgen.srvr.XML.*;
import com.newgen.ibm.commons.CreateErrorData;
import com.newgen.ibm.commons.CreateLog;
import com.newgen.ibm.commons.QuestService_UtilityHelper;
import java.io.*;
import java.sql.Connection;
import java.util.*;

public class Common{

  
    private CreateErrorData elog=new CreateErrorData();
    boolean keepRunning=true;
    FunctionsWI objWF = new FunctionsWI();
  
	
  


    public void reconnectToWorkflow() {
        try {
            objWF.disconnectFromWorkFlow();
            objWF.disconnectFromServer();
        } catch (Exception ex) {
            //writeORnotLog(ex.toString());
        	elog.WriteErrorData("Exception "+ex.toString(), QuestService_UtilityHelper.ErrorLogPath, "ErrorLog");
        	
        }
        try {
            if (objWF.connectToServer()) {
                String str = objWF.connectToWorkFlow("N");
                String temp[] = str.split("~");
                if (!temp[0].equals("0")) {
                	Thread.sleep(30000);
                   if (keepRunning) {
                        reconnectToWorkflow();
                    }
                }
            }
        } catch (Exception ex) {
            //writeORnotLog(ex.toString());
        	elog.WriteErrorData("Exception occured while reconnecting to WorkFlow...."+ex.toString(), QuestService_UtilityHelper.ErrorLogPath, "ErrorLog");
        }
    }
        public static Date adjustDateBy(Date d, int numberOfDays) {
             java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
             cal.setTime(d);
             cal.add(java.util.GregorianCalendar.DATE, numberOfDays);
            return cal.getTime();
    }

    
    
    
   
}

