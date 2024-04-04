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
import com.newgen.ibm.util.DRC;
import com.newgen.ibm.util.*;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;
import com.newgen.ibm.commons.DRCWebservice_UtilityHelper;
import com.newgen.srvr.FunctionsWI;
import com.newgen.ibm.commons.CreateLog;
/**
 *
 * @author sudadmin
 */
public class DRC_Utilityservice {
    boolean keepRunning = true;
    private CreateLog log=new CreateLog();
    private static String sSleepTime;
    FunctionsWI objWF = new FunctionsWI();
    public static void main(String[] args) 
    {
        try 
	{
                DRC_Utilityservice utilityService = new DRC_Utilityservice();
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
            new DRC("DRC");
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
            ini.load(new FileInputStream(System.getProperty("user.dir")+ System.getProperty("file.separator")+ "DRC_WebService_Config.ini"));
            DRCWebservice_UtilityHelper.cabinetName = ini.getProperty("CabinetName");
            System.out.println("CabinetName"+DRCWebservice_UtilityHelper.cabinetName);
            DRCWebservice_UtilityHelper.jtsIP = ini.getProperty("JTSIP");
            System.out.println("jtsIP="+DRCWebservice_UtilityHelper.jtsIP);
            System.out.println("jtsPort WITHOUT ASSIGNMENT="+DRCWebservice_UtilityHelper.jtsPort);
            System.out.println("jtsPort read from ini="+ini.getProperty("JTSPORT"));
            DRCWebservice_UtilityHelper.jtsPort = Integer.parseInt(ini.getProperty("JTSPORT"));
            System.out.println("jtsPort after assignment="+DRCWebservice_UtilityHelper.jtsPort);
            DRCWebservice_UtilityHelper.ProcessName = ini.getProperty("ProcessName");
            DRCWebservice_UtilityHelper.VolumeID = ini.getProperty("VolumeID");
            DRCWebservice_UtilityHelper.userName = ini.getProperty("userName");
            DRCWebservice_UtilityHelper.userPassword = ini.getProperty("userPassword");
            DRCWebservice_UtilityHelper.siteID = ini.getProperty("siteID");
            DRCWebservice_UtilityHelper.BatchSize = ini.getProperty("BatchSize");
            DRCWebservice_UtilityHelper.sDateFolderFormat = ini.getProperty("DateFolderFormat");
            DRCWebservice_UtilityHelper.SuccessLogPath=ini.getProperty("SuccessLogPath").trim();
            DRCWebservice_UtilityHelper.ErrorLogPath=ini.getProperty("ErrorLogPath").trim();
            DRCWebservice_UtilityHelper.WSEndpointURL=ini.getProperty("WSEndpointURL").trim();
            log.WriteSuccessLog("READ INI END \n","SuccessLog");
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
	}
    }
}
   