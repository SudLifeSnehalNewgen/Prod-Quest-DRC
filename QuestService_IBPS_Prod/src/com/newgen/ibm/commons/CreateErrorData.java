package com.newgen.ibm.commons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateErrorData {
	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	Date date = new Date();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateErrorData alog = new CreateErrorData();
		//alog.WriteErrorData("A,B,C","C:\\GroupMutility\\UtilityData\\unProcessed", "AAA");
	}
	public static void WriteErrorData(String strOutput,String fPath, String fileName)
	{
		
		DateFormat sDateFormat = new SimpleDateFormat("dd-MMM-yyy HH:mm:ss");
		StringBuffer str = new StringBuffer();
		str.append(sDateFormat.format(new java.util.Date()));
		str.append(",");
		str.append(strOutput);
		str.append("\n");

		StringBuffer strFilePath = null;
		String tmpFilePath="";

		Calendar calendar=new GregorianCalendar();
		String DtString=String.valueOf(""+calendar.get(Calendar.DAY_OF_MONTH) +(calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR));
		
		try 
		{
			strFilePath = new StringBuffer(50);
			//strFilePath.append(DocUploadHelper.ErrorLogPath);
			strFilePath.append(fPath);
			
			File fBackup=new File(strFilePath.toString());
			if(fBackup == null || !fBackup.isDirectory())
				fBackup.mkdir();

			strFilePath.append(File.separatorChar);
			strFilePath.append(fileName+"_"+DtString+".csv");
			tmpFilePath = strFilePath.toString();
			
			
			BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));		
			out.write(str.toString());
	        out.close();
		} catch (Exception exception) {
		} finally {
			strFilePath = null;
		}	
	}
}
