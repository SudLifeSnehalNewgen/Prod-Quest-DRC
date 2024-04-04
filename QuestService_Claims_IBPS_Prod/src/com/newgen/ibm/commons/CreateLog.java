package com.newgen.ibm.commons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateLog 
{
	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	Date date = new Date();
	//private static int iCount = 1;
	
	public void WriteFailedLog(String strHeader,String strOutput, String strPath, String fileName)
	{
		StringBuffer strH = new StringBuffer();
		strH.append(strHeader);
		strH.append("\n");
		
		StringBuffer str = new StringBuffer();
		str.append(strOutput);
		str.append("\n");

		StringBuffer strFilePath = null;
		String tmpFilePath="";
		try 
		{
			strFilePath = new StringBuffer(50);
			strFilePath.append(strPath);
			File fBackup=new File(strFilePath.toString());
			if(fBackup == null || !fBackup.isDirectory())
				fBackup.mkdir();

			strFilePath.append(File.separatorChar);
			strFilePath.append(fileName);
			tmpFilePath = strFilePath.toString();
			BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));
			/*
			File sFile =new File(tmpFilePath);
			if(!sFile.exists())
			{
				out.write(strH.toString());
			}
			*/
			out.flush();
			out.write(str.toString());
	        out.close();
		} catch (Exception exception) 
		{
			System.out.println("Error "+exception.getMessage());
		} finally {
			strFilePath = null;
		}	
	}
		
	public void WriteSuccessLog(String strOutput, String fileName)
	{
		
		StringBuffer str = new StringBuffer();
		str.append(DateFormat.getDateTimeInstance(0,2).format(new java.util.Date()));
		str.append("\n");
		str.append(strOutput);
		str.append("\n");

		StringBuffer strFilePath = null;
		String tmpFilePath="";

		Calendar calendar=new GregorianCalendar();
		String DtString=String.valueOf(""+calendar.get(Calendar.DAY_OF_MONTH) +(calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR));
		
		try 
		{
			strFilePath = new StringBuffer(50);
			strFilePath.append(QuestService_UtilityHelper.SuccessLogPath);
			File fBackup=new File(strFilePath.toString());
			if(fBackup == null || !fBackup.isDirectory())
				fBackup.mkdir();

			strFilePath.append(File.separatorChar);
			strFilePath.append(fileName+"_"+DtString);
			//strFilePath.append(fileName+"_"+DtString+"_1.xml");
			tmpFilePath = strFilePath.toString();
			/*
			 * Code Added
			*/ 
			//tmpFilePath = getFileName(tmpFilePath);
			tmpFilePath = getFileName_New(tmpFilePath);
			BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));		
			out.write(str.toString());
	        out.close();
		} catch (Exception exception) {
		} finally 
		{
			strFilePath = null;
			long minRunningMemory = (1024*1024);  
			   Runtime runtime = Runtime.getRuntime();  
			   if(runtime.freeMemory()<minRunningMemory)  
				   System.gc();
		}	
	}
	
	
	public void WriteErrorLog(String strOutput, String fileName)
	{
		
		StringBuffer str = new StringBuffer();
		str.append(DateFormat.getDateTimeInstance(0,2).format(new java.util.Date()));
		str.append("\n");
		str.append(strOutput);
		str.append("\n");

		StringBuffer strFilePath = null;
		String tmpFilePath="";

		Calendar calendar=new GregorianCalendar();
		String DtString=String.valueOf(""+calendar.get(Calendar.DAY_OF_MONTH) +(calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR));
		
		try 
		{
			strFilePath = new StringBuffer(50);
			strFilePath.append(QuestService_UtilityHelper.ErrorLogPath);
			File fBackup=new File(strFilePath.toString());
			if(fBackup == null || !fBackup.isDirectory())
				fBackup.mkdir();

			strFilePath.append(File.separatorChar);
			//strFilePath.append(fileName+"_"+DtString+".xml");
			strFilePath.append(fileName+"_"+DtString);
			tmpFilePath = strFilePath.toString();
			//tmpFilePath = getFileName(tmpFilePath);
			tmpFilePath = getFileName_New(tmpFilePath);
			
			
			BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));		
			out.write(str.toString());
	        out.close();
		} catch (Exception exception) {
		} finally {
			strFilePath = null;
		}	
	}
	public String getFileName(String tmpFilePath)
	{
		try
		{
			File dem = new File(tmpFilePath);
			String sParentDir=tmpFilePath.substring(0, tmpFilePath.lastIndexOf("\\"));
			File Logdir = new File(sParentDir);
			String[] ChildDir = Logdir.list();
			
			System.out.println("1"+tmpFilePath);
			long fileSize = dem.length();
			if((double)fileSize/(1024*1024)>5)
			 {
				int iCount=1;
				String sCount="";
			
				for (int k = 0; k < ChildDir.length; k++) 
				{
		            //if (ChildDir[k].indexOf(getDateString() + "_" + iCount) != -1) {
		              //strLgFlName = ChildDir[k]; 
		            //}
		            //else {
		           // }
		          }
				sCount=tmpFilePath.substring(tmpFilePath.lastIndexOf('_')+1, tmpFilePath.indexOf('.'));
				iCount = Integer.parseInt(sCount)+1;
				
				tmpFilePath = tmpFilePath.substring(0,tmpFilePath.lastIndexOf('_')+1);
				tmpFilePath = tmpFilePath+iCount+".xml";
				dem = new File(tmpFilePath);
				if(dem.exists())
					tmpFilePath = getFileName(tmpFilePath);
				else
					return tmpFilePath;
			 }
			
		}catch(Exception ex)
		{
			System.out.println("Error in getFileName()"+ex);
			return tmpFilePath;
		}
		finally
		{
			System.out.println("4"+tmpFilePath);
		}
		return tmpFilePath;
	}
	
	public String getFileName_New(String tmpFilePath)
	{
		try
		{
			tmpFilePath = tmpFilePath+"_1.xml";
			File dem = new File(tmpFilePath);
			File sFile=null;
			
			String sParentDir=tmpFilePath.substring(0, tmpFilePath.lastIndexOf("\\"));
			File Logdir = new File(sParentDir);
			String[] ChildDir = Logdir.list();

			if(!dem.exists())
				return tmpFilePath; 
			long fileSize = dem.length();
			if((double)fileSize/(1024*1024)<5)
			 {
				return tmpFilePath;
			 }
			else
			{
				String sFileName = dem.getName();
				String sCheckFileName = sFileName.substring(0, sFileName.lastIndexOf("_"));

				for(int cnt=2;cnt<100;cnt++)
				{
					tmpFilePath=sParentDir+"\\"+sCheckFileName + "_" + cnt+".xml";
					sFile = new File(tmpFilePath);
					if(!sFile.exists())
						return tmpFilePath;
					fileSize = sFile.length();
					if((double)fileSize/(1024*1024)<5)
					 {
						return tmpFilePath;
					 }
				}
			}
		}catch(Exception ex)
		{
			System.out.println("Error in getFileName()"+ex);
		}
		finally
		{
			
		}
		return tmpFilePath;
	}	
}