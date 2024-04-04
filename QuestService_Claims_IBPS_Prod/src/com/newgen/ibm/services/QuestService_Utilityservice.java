/*
 ----------------------------------------------------------------------------------------------------
 NEWGEN SOFTWARE TECHNOLOGIES LIMITED
 Group : Application �Projects
 Product / Project : 
 Module :  						
 File Name : 					DRC_Utilityservice.java
 Author :    					Ghazal Shah
 Date written (DD/MM/YYYY) : 	13-02-2018
 Description : 					
 ----------------------------------------------------------------------------------------------------
 
 CHANGE HISTORY
 ----------------------------------------------------------------------------------------------------
 Date Change By Change Description (Bug No. (If Any))
 (DD/MM/YYYY)
 ----------------------------------------------------------------------------------------------------
 */
package com.newgen.ibm.services;
import com.newgen.ibm.util.QuestService;
import com.newgen.ibm.util.*;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;
import com.newgen.ibm.commons.QuestService_UtilityHelper;
import com.newgen.srvr.FunctionsWI;
import com.newgen.ibm.commons.CreateLog;
/**
 *
 * @author sudadmin
 */
public class QuestService_Utilityservice {
    boolean keepRunning = true;
    private CreateLog log=new CreateLog();
    private static String sSleepTime;
    FunctionsWI objWF = new FunctionsWI();
    public static void main(String[] args) 
    {
        try 
	{
                QuestService_Utilityservice utilityService = new QuestService_Utilityservice();
		utilityService.startService();	    
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    private void startService() 
    {
        try
        {
            readINI();
            connectToWF();
            new QuestService("QuestService");
        }
        catch (Exception e) 
        {
            e.printStackTrace();
	}
    }
    private void connectToWF() 
    {
        try 
        {
            if (objWF.connectToServer()) 
            {	
                log.WriteSuccessLog("connectToWF Start \n","SuccessLog");
		String str = objWF.connectToWorkFlow("Y");
		String temp[] = str.split("~");
		if (!temp[0].equals("0")) 
		{
                    Thread.sleep(Long.parseLong(sSleepTime));
                    log.WriteSuccessLog("keepRunning","SuccessLog");
                    System.out.println("keepRunning");
                    if (keepRunning) 
                    {
                        connectToWF();
                    }
                }
            }
        } 
	catch (Exception ex) 
        {
            ex.printStackTrace();
	}
    }
    public void readINI()
    {
        try
        {
            Properties ini = new Properties();
            ini.load(new FileInputStream(System.getProperty("user.dir")+ System.getProperty("file.separator")+ "Quest_WebService_Config_Claim.ini"));
            QuestService_UtilityHelper.cabinetName = ini.getProperty("CabinetName");
            System.out.println("CabinetName"+QuestService_UtilityHelper.cabinetName);
            QuestService_UtilityHelper.jtsIP = ini.getProperty("JTSIP");
            System.out.println("jtsIP="+QuestService_UtilityHelper.jtsIP);
            System.out.println("jtsPort WITHOUT ASSIGNMENT="+QuestService_UtilityHelper.jtsPort);
            System.out.println("jtsPort read from ini="+ini.getProperty("JTSPORT"));
            QuestService_UtilityHelper.jtsPort = Integer.parseInt(ini.getProperty("JTSPORT"));
            System.out.println("jtsPort after assignment="+QuestService_UtilityHelper.jtsPort);
            QuestService_UtilityHelper.ProcessName = ini.getProperty("ProcessName");
            QuestService_UtilityHelper.VolumeID = ini.getProperty("VolumeID");
            QuestService_UtilityHelper.userName = ini.getProperty("userName");
            QuestService_UtilityHelper.userPassword = ini.getProperty("userPassword");
            QuestService_UtilityHelper.siteID = ini.getProperty("siteID");
            QuestService_UtilityHelper.BatchSize = ini.getProperty("BatchSize");
            QuestService_UtilityHelper.sDateFolderFormat = ini.getProperty("DateFolderFormat");
            QuestService_UtilityHelper.SuccessLogPath=ini.getProperty("SuccessLogPath").trim();
            QuestService_UtilityHelper.ErrorLogPath=ini.getProperty("ErrorLogPath").trim();
            QuestService_UtilityHelper.WSEndpointURL=ini.getProperty("WSEndpointURL").trim();
            log.WriteSuccessLog("READ INI END \n","SuccessLog");
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
	}
    }
}
   