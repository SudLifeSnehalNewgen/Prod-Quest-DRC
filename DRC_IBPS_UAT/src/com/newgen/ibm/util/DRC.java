/*
 ----------------------------------------------------------------------------------------------------
 NEWGEN SOFTWARE TECHNOLOGIES LIMITED
 Group : Application �Projects
 Product / Project : 
 Module :  						
 File Name : 					DRC.java
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
package com.newgen.ibm.util;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import com.newgen.ibm.commons.DRCWebservice_UtilityHelper;
import com.newgen.srvr.FunctionsWI;
import com.newgen.srvr.XML.XMLGen;
import com.newgen.srvr.XML.XMLParser;
import com.newgen.wfdesktop.xmlapi.WFXmlList;
import com.newgen.wfdesktop.xmlapi.WFXmlResponse;
import com.newgen.ibm.commons.CreateLog;
import com.newgen.ibm.commons.CreateErrorData;
import com.newgen.ibm.util.Common;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import net.sf.json.JSONSerializer;
import org.json.*;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author sudadmin
 */
public class DRC implements Runnable 
{
    private CreateLog log=new CreateLog();
    private CreateErrorData elog=new CreateErrorData();
    private Thread t;
    private String name;
    private String sSleepTime="";
    public String sProcessDefId; 
    private String SOAP_inxml="";
    FunctionsWI objWF = new FunctionsWI();
    XMLParser xmlParser = new XMLParser();
    Common comm=new Common();
    private static String sUtilityStartTime="";
    boolean isFristIntr = true;
    long currntTime;
    long tmpDiff;
    long startTime;
    Date startDate;
    private String sQueueID="";
    private String sProcessName="";
    private String sSourcePath="";
    private boolean moreWI = false;
    private String documentInfo="";
    private String proposalNumber="";
    private String CORE_RESPONSE_STATUS="";
    private String SOAPResponse_xml="";
    private String NATURE_OF_AGE_PROOF="";
    private String APPLICATION_NO="";
    private String SUM_ASSURED_SECC1="";
    private String FREQUENCY="";
    private String CONTRACT_POLICY_NUMBER="";
    private String AGE_SECA="";
    private String AGE_PROOF="";
    private String ANNUAL_INCOME="";
    private String DATE_OF_BIRTH_A="";
    private String GENDER_SECA="";
    private String MARRITAL_STATUS_A="";
    private String OCCUPATION="";
    private String EDUCATION_QUALIFICATION="";
    private String ADDR_LINE_1_SECA="";
    private String Life_assured_City="";
    private String Life_assured_State="";
    private String PIN_CODE_SECA="";
    private String ANNUAL_INCOME_SECB="";
    private String POLICY_TERM="";
    private String PREM_PYT_TERMS="";
    private String PREMIUM_SECC="";
    private String PRODUCT_DETAILS="";
    private String PRODUCT_RIDER_NAME="";
    private String CHANNEL_TYPE_SECA="";
    private String Zone="";
    private String SUM_UNDER_CONSIDERATION="";
    private String RELATION_WITH_LA="";
    private String risk_Commencement_Date=null;
    private String policy_Issue_Date=null;
    private String life_Assured_Client_ID="";
    private String life_Assured_Industry=null;
    private String COUNTRY_OF_RESIDENCE_SECB="";
    private String NATIONALITY_SECA="";
    private String MARITAL_STATUS_A="";
    private String RELATIONSHIP_TO_LIFE_ASSURED="";
    private String IDENTIFY_PROOF="";
    private String INCOME_PROOF_B="";
    private String PROPOSER_CLIENT_ID="";        
    private String DATE_OF_BIRTH_SECB="";        
    private String AGE_SECB1="";        
    private String Owner_Age_Proof="";
    private String GENDER_SECB=""; 
    private String OCCUPATION_SECB="";        
    private String policy_Owner_Industry=null;        
    private String Owner_Education="";
    private String ADDR_LINE_1_SECB="";
    private String policy_Owner_City="";
    private String PIN_CODE_SECB="";
    private String policy_Owner_Country=null;
    private String MARITAL_STATUS_A_PO="";
    private String NATIONALITY_SECA_PO="";
    private String IDENTIFY_PROOF_PO="";
    private String INCOME_PROOF_B_PO="";
    private String premium_Payment_Mode=null;
    private String nominee_Relation="";
    private String nominee_grid="";
    private String ANNUAL_PREMIUM="";
    private String ParentBank="";
    private String ChannelType="";
    //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
    public DRC(String threadName) 
    {
        try
        {
            readINI(); 
            System.out.println(" Start Main.");
            File dir = new File(sSourcePath);
            System.out.println("Dir.--"+dir);
            if(dir == null || !dir.isDirectory())
                    dir.mkdir();
            name = threadName;
            t = new Thread(this, name);	   
            System.out.println("Before Start Main.");
            t.start();
        }
        catch(Exception e)
        {
            log.WriteSuccessLog("Error in starting ImageDownload Thread...."+e, "SuccessLog");
            //elog.WriteErrorData("Error in starting ImageDownload Thread...."+e, DRCWebservice_UtilityHelper.ErrorLogPath, "ErrorLog");
        }
    }
    public void readINI() 
    {
        try 
        {
            System.out.println("Reading PacketGeneration ini file..............................................");
            log.WriteSuccessLog("Reading PacketGeneration ini file...","SuccessLog");
            Properties ini = new Properties();
            ini.load(new FileInputStream(System.getProperty("user.dir") +
            System.getProperty("file.separator") + "DRC_WebService.ini"));
            sSleepTime=ini.getProperty("SleepTime");
            sUtilityStartTime=ini.getProperty("UtilityStartTime");  
            sQueueID = ini.getProperty("QueueID_Packet");
            sProcessName=ini.getProperty("ProcessName");
        }
        catch(Exception e) 
        {
	            //elog.WriteErrorData("Error in reading ImageDownload ini file.."+e, DRCWebservice_UtilityHelper.ErrorLogPath, "ErrorLog");
	    log.WriteSuccessLog("Error in starting ImageDownload Thread...."+e, "SuccessLog");        
            e.printStackTrace();
	            // System.exit(0);
        }
    }

    
    public void run() {
       System.out.println("Inside Run.");
       log.WriteSuccessLog("Inside Run... \n","SuccessLog");
       while (true)
	    {
	    	try 
	    	{
                    System.out.println("sUtilityStartTime===="+sUtilityStartTime);
                    if(!sUtilityStartTime.equals(""))
                    {
                    if (isFristIntr) 
                    {
                        SimpleDateFormat formater = new SimpleDateFormat("ddMMyyyy HH:mm");
                        SimpleDateFormat formatter1 = new SimpleDateFormat("ddMMyyyy");
                        startDate = formater.parse(formatter1.format(new Date()) + " " + sUtilityStartTime);
	                startTime = startDate.getTime();
	                currntTime = System.currentTimeMillis();
	                tmpDiff = startTime - currntTime;
                        if (tmpDiff < 0) 
	                {
	                    startDate = Common.adjustDateBy(startDate, 1);
	                    startTime = startDate.getTime();
	                    tmpDiff = startTime - currntTime;
	                     System.out.println("tmpDiff is::"+tmpDiff);
	                }
                        isFristIntr = false;
                        Thread.sleep(tmpDiff);
                    }
                    else
                    {
                        apiCall();
                        Thread.sleep(Long.parseLong(sSleepTime)*100);
                    }
                    }
                    else
                    {
                        System.out.println("2nd else");
                        apiCall();
                        System.out.println("2nd else END :"+sSleepTime);
                        Thread.sleep(Long.parseLong(sSleepTime)*100);
                    }
                }
                catch(Exception ex)
                {
                    log.WriteSuccessLog("Error in starting ImageDownload Thread...."+ex, "SuccessLog");
                    //elog.WriteErrorData("An interrupted exception has occurred...."+ex, DRCWebservice_UtilityHelper.ErrorLogPath, "ErrorLog");
                }
            }
    }
    public void apiCall() 
    {
        System.out.println("Inside apiCall()");
    	String inXml="";
    	String outXml="";
    	int iRetrivedCnt=0;
    	int lPidTotCount=0;
    	String sParentWIName="";
    	String sWorkItemId="";
    	do
        {
            try
	    {
                System.out.println("ImageDownloadUtilityHelper sessionID=="+DRCWebservice_UtilityHelper.sessionID);
                if(DRCWebservice_UtilityHelper.sessionID.equals("null"))
                {
                    log.WriteSuccessLog("Reconnecting.","SuccessLog");
                    System.out.println("Reconnecting.");
                    comm.reconnectToWorkflow();
                    return;
                }
                inXml = XMLGen.WFFetchInstrumentsList(DRCWebservice_UtilityHelper.cabinetName,
                        DRCWebservice_UtilityHelper.sessionID, "Y",
                        sQueueID, "256", "0", "", "", "0",
                        DRCWebservice_UtilityHelper.BatchSize, "1", "A", "", "", "", "Y", "Y");
                log.WriteSuccessLog("WFFetchInstrumentsList input xml is :" + inXml,"SuccessLog");
                outXml = objWF.execute(inXml);
		log.WriteSuccessLog("WFFetchInstrumentsList output xml is :" + outXml,"SuccessLog");
		xmlParser.setInputXML(outXml);
		String mainCode = xmlParser.getValueOf("MainCode");
                if (mainCode.equals("18"))
                {
                    log.WriteSuccessLog("No workitem found on the current queue....","SuccessLog");
                }
                else if(mainCode.equals("11") || mainCode.equals("-50146"))
	    	{
                    log.WriteSuccessLog("FetchInstrumentsList Failed.","SuccessLog");
	    	    comm.reconnectToWorkflow();
	    	    return;
                }
                else if(mainCode.equals("0"))
                {
                    iRetrivedCnt=Integer.parseInt(outXml.substring(outXml.indexOf("<RetrievedCount>")+16 , outXml.indexOf("</RetrievedCount>")));
	    	    log.WriteSuccessLog("iRetrivedCnt: "+iRetrivedCnt,"SuccessLog");
	    	    if (lPidTotCount > iRetrivedCnt) 
	    	    {
                        moreWI = true;
	    	    } 
                    else 
                    {
                        moreWI = false;
	    	    }
                }
                WFXmlResponse objXmlResponsePrnt = new WFXmlResponse(outXml);
	    	WFXmlList objListPrnt = objXmlResponsePrnt.createList("Instruments","Instrument");
	    	for(int n=0;n<iRetrivedCnt;n++,objListPrnt.skip(true))
	    	{
                    sParentWIName=objListPrnt.getVal("ProcessInstanceId");
                    sWorkItemId=objListPrnt.getVal("WorkItemId");
                    if (getAttributeData(sParentWIName,sWorkItemId)) 
                    {
                        
                    }
                }
            }
            catch(Exception ex)
            {
                
            }
            
        }while (moreWI);  
    }
    public String[] getCallResult(String xml) 
    {
        xml = xml.substring(22, xml.length());
         System.out.println("getCallResult xml"+xml);
        boolean isTagsPending = true;
        XMLParser par = new XMLParser(xml);
        String rs = par.getValueOf("Results");
        String ResVal = "";
        String splitRs = rs;
        XMLParser par2 = null;
        if (!splitRs.isEmpty()) 
        {
            while (isTagsPending) 
            {
                int length = splitRs.length();
                int startIndex = splitRs.indexOf("<Result>");
                int lastIndex = splitRs.indexOf("</Result>")+ "</Result>".length();
                System.out.println("nominee_Relation length"+length);
                System.out.println("nominee_Relation startIndex"+startIndex);
                System.out.println("nominee_Relation lastIndex"+lastIndex);
                String tag = splitRs.substring(startIndex, lastIndex);
                  System.out.println("nominee_Relation tag"+tag);
                  
                if (lastIndex < length) 
                {
                    splitRs = splitRs.substring(lastIndex, length);
                    System.out.println("nominee_Relation splitRs"+splitRs);
                    
                } 
                else 
                {
                    isTagsPending = false;
                }
                par2 = new XMLParser(tag);
                if (ResVal == "") 
                {
                    ResVal = par2.getValueOf("Result").substring(1,par2.getValueOf("Result").length());
                     System.out.println("nominee_Relation ResVal"+ResVal);
                } 
                else 
                {
                    ResVal = ResVal+ "~"+ par2.getValueOf("Result").substring(1,par2.getValueOf("Result").length());
                    System.out.println("nominee_Relation ResVal else"+ResVal);
                }
            }
        } 
        else 
        {		
        }
        String TagArray[] = ResVal.split("~");
         for (String i:TagArray){
         
         System.out.println("nominee_Relation TagArray"+i);
         }
        return TagArray;
    }
    public String CalculateAnnualPremium(String premium,int freq)
    {
        double annual_prem = 0;
        if(premium.equalsIgnoreCase(""))
        {
            premium="0";
        }
        annual_prem = Double.parseDouble(premium) * freq;
        return Double.toString(annual_prem);
    }
    public int setBillingFrequency(String billingfrequency)
    {
        int frequency=0;
        if(billingfrequency.equalsIgnoreCase("Annual"))
        {
            frequency = 1;
        }
        else if(billingfrequency.equalsIgnoreCase("Half-Yrly"))
        {
            frequency = 2;
        }
        else if(billingfrequency.equalsIgnoreCase("Monthly"))
        {
            frequency = 12;
        }
        else if(billingfrequency.equalsIgnoreCase("Quarterly"))
        {
            frequency = 4;
        }
        else if(billingfrequency.equalsIgnoreCase("Single"))
        {
            frequency = 0;
        }
        else
        {
            log.WriteSuccessLog("invalid billing frequency:"+billingfrequency,"SuccessLog");
            System.out.println("invalid billing frequency:"+billingfrequency);
        }
        return frequency;
    }
    public String fetchProductDetails(String processInstanceID)
    {
        String inputXML_query;
        String outputXML_query;
        String mainCode_query="";
        try
        {
            String query = "select SUM_ASSURED,POLICY_TERM,PREM_PYT_TERMS from NG_SUD_NBP_PRODUCT_DTLS with(nolock) where (wi_name ='"+processInstanceID+"') and (PRODUCT_DETAILS like 'T%' or PRODUCT_DETAILS like 'U%' or PRODUCT_DETAILS like 'I%')";
            //String query = "Select NOMINEE_RELATION_TO_LA from NG_SUD_NBP_NOMINEE_DTLS with(nolock) where wi_name ='NB-00000000264373-SUD'";
            inputXML_query = XMLGen.WFSelectTest_new(DRCWebservice_UtilityHelper.cabinetName,DRCWebservice_UtilityHelper.sessionID, query, "3");
            outputXML_query = objWF.execute(inputXML_query);
            xmlParser.setInputXML(outputXML_query);
            mainCode_query = xmlParser.getValueOf("MainCode");
            if (mainCode_query.equals("0")) 
            {
                String TagValGL2[] = null;
                String resultGL2[];
                TagValGL2 = getCallResult(outputXML_query);
                for (int iCase = 0; iCase < TagValGL2.length; iCase++) 
                {
                    resultGL2 = TagValGL2[iCase].split("\\|");
                    if(!resultGL2[0].toString().equalsIgnoreCase(""))
                    {
                    SUM_ASSURED_SECC1 = resultGL2[0].toString();
                    POLICY_TERM = resultGL2[1].toString();
                    PREM_PYT_TERMS = resultGL2[2].toString();
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return SUM_ASSURED_SECC1+"|"+POLICY_TERM+"|"+PREM_PYT_TERMS;
    }
    public String fetchNomineeRelation(String processInstanceID)
    {
        String inputXML_query;
        String outputXML_query;
        String mainCode_query="";
        nominee_Relation="";//added by ashwini 30jan2020
        try
        {
            String query = "Select NOMINEE_RELATION_TO_LA from NG_SUD_NBP_NOMINEE_DTLS with(nolock) where wi_name ='"+processInstanceID+"'";
            //String query = "Select NOMINEE_RELATION_TO_LA from NG_SUD_NBP_NOMINEE_DTLS with(nolock) where wi_name ='NB-00000000264373-SUD'";
            inputXML_query = XMLGen.WFSelectTest_new(DRCWebservice_UtilityHelper.cabinetName,DRCWebservice_UtilityHelper.sessionID, query, "1");
            outputXML_query = objWF.execute(inputXML_query);
            xmlParser.setInputXML(outputXML_query);
            System.out.println("nominee_Relation inputXML_query"+inputXML_query);
            System.out.println("nominee_Relation outputXML_query"+outputXML_query);
            mainCode_query = xmlParser.getValueOf("MainCode");
            if (mainCode_query.equals("0")) 
            {    
                String TagValGL2[] = null;
                String resultGL2[];
                TagValGL2 = getCallResult(outputXML_query);
                
                for (String j:TagValGL2){
                System.out.println("TagValGL2"+j+"TagValGL2.length"+TagValGL2.length);
                }
                System.out.println("TagValGL2.length"+TagValGL2.length);
                for (int iCase = 0; iCase < TagValGL2.length; iCase++) 
                {
                    resultGL2 = TagValGL2[iCase].split("\\|");
                    System.out.println("nominee_Relation before"+nominee_Relation);
                    nominee_Relation = nominee_Relation +","+ resultGL2[0].toString();
                    
                    System.out.println("nominee_Relation resultGL2[0].toString()"+resultGL2[0].toString());
                    System.out.println("nominee_Relation"+nominee_Relation);
                    
                }
                if(!nominee_Relation.equalsIgnoreCase(""))
                {
                    nominee_Relation=nominee_Relation.substring(1, nominee_Relation.length());
                    nominee_Relation="\""+nominee_Relation+"\"";
                    System.out.println("nominee_Relation if"+nominee_Relation);
                }
                else
                {
                    nominee_Relation=null;
                }
                System.out.println("======nominee_rel=========:"+nominee_Relation);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return nominee_Relation;
    }
    public String fetchSalesChannel(String processInstanceID,String sCHANNEL_TYPE_SECA)
    {
        String inputXML_query;
        String outputXML_query;
        String mainCode_query="";
        ParentBank="";
        ChannelType="";
        try
        {
            String query = "select parent_bank from NG_SUD_NBP_IND_DATA_ENTRY where wi_name='"+processInstanceID+"' and wi_id=1";
            inputXML_query = XMLGen.WFSelectTest_new(DRCWebservice_UtilityHelper.cabinetName,DRCWebservice_UtilityHelper.sessionID, query, "1");
            outputXML_query = objWF.execute(inputXML_query);
            xmlParser.setInputXML(outputXML_query);
            System.out.println("fetchSalesChannel inputXML_query"+inputXML_query);
            System.out.println("fetchSalesChannel outputXML_query"+outputXML_query);
            log.WriteSuccessLog("01-06-2020:ghazal:fetchSalesChannel inputXML_query::"+inputXML_query,"SuccessLog");
            log.WriteSuccessLog("01-06-2020:ghazal:fetchSalesChannel outputXML_query::"+outputXML_query,"SuccessLog");
            mainCode_query = xmlParser.getValueOf("MainCode");
            if (mainCode_query.equals("0")) 
            {
                String TagValGL2[] = null;
                String resultGL2[];
                TagValGL2 = getCallResult(outputXML_query);
                for (int iCase = 0; iCase < TagValGL2.length; iCase++) 
                {
                    resultGL2 = TagValGL2[iCase].split("\\|");
                    if(!resultGL2[0].toString().equalsIgnoreCase(""))
                    {
                        ParentBank = resultGL2[0].toString();
                    }
                }
            }
            log.WriteSuccessLog("01-06-2020:ghazal:fetchSalesChannel ParentBank::"+ParentBank,"SuccessLog");
            if(ParentBank.equalsIgnoreCase("UBI-AB"))
            {
                ChannelType="BA-A";
            }
            else if(ParentBank.equalsIgnoreCase("UBI-CB"))
            {
                ChannelType="BA-C";
            }
            else
            {
                ChannelType=sCHANNEL_TYPE_SECA;
            }
            log.WriteSuccessLog("01-06-2020:ghazal:fetchSalesChannel ChannelType::"+ChannelType,"SuccessLog");
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ChannelType;
    }
    private boolean getAttributeData(String processInstanceID, String workItemID) 
    {
        String inputXML;
        String outputXML;
        String sColumnValue_ext = "";
        WFXmlResponse xmlResponse = null;       
        String mainCode="";  
        String s1="";
        String wiid="";   
        
        try
        {
            if (!(processInstanceID.equals("") || workItemID.equals(""))) 
            {
                inputXML = XMLGen.WMGetWorkItem(DRCWebservice_UtilityHelper.cabinetName,DRCWebservice_UtilityHelper.sessionID,processInstanceID, workItemID);
                log.WriteSuccessLog("WMGetWorkItem input xml....."+inputXML,"SuccessLog");
                System.out.println("WMGetWorkItem inputXML xml....."+inputXML);
                outputXML = objWF.execute(inputXML);
                log.WriteSuccessLog("WMGetWorkItem output xml....."+outputXML,"SuccessLog");
                System.out.println("WMGetWorkItem output xml....."+outputXML);
                xmlParser.setInputXML(outputXML);
                mainCode = xmlParser.getValueOf("MainCode");  
                if (mainCode.equals("0")) 
                {
                    inputXML = XMLGen.WFGetWorkitemDataExt(DRCWebservice_UtilityHelper.cabinetName,DRCWebservice_UtilityHelper.sessionID, processInstanceID,workItemID,sQueueID);	                    
                    log.WriteSuccessLog("WFGetWorkitemDataExt input xml is :"+inputXML,"SuccessLog");
                    System.out.println("WFGetWorkitemDataExt input xml is :"+inputXML);
                    outputXML = objWF.execute(inputXML);
                    xmlParser.setInputXML(outputXML);
                    mainCode = xmlParser.getValueOf("MainCode");
                    SOAP_inxml="";
                    if (mainCode.equals("0")) 
                    {
                        System.out.println("MAIN CODE is ZERO!!!!");
                        xmlResponse = new WFXmlResponse(outputXML);
                        System.out.println("xmlResponse"+xmlResponse);
                        s1 =outputXML.substring(outputXML.indexOf("<ProcessInstanceId") , outputXML.indexOf("</ProcessInstanceId>"));
                        wiid =s1.substring(s1.indexOf(">")+1 ,s1.length() );                		
                        log.WriteSuccessLog("wiid========updated=============="+wiid,"SuccessLog");
                        String attributes = xmlParser.getValueOf("Attributes");	
                        //log.WriteSuccessLog("18012022:attributes======================"+attributes,"SuccessLog");
                        //s1 =attributes.substring(attributes.indexOf("<NATURE_OF_AGE_PROOF") , attributes.indexOf("</NATURE_OF_AGE_PROOF>"));
                        //NATURE_OF_AGE_PROOF = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        log.WriteSuccessLog("NATURE_OF_AGE_PROOF======================"+NATURE_OF_AGE_PROOF,"SuccessLog");
                        System.out.println("NATURE_OF_AGE_PROOF======================"+NATURE_OF_AGE_PROOF);
                        if(attributes.contains("</NATURE_OF_AGE_PROOF>"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<NATURE_OF_AGE_PROOF") , attributes.indexOf("</NATURE_OF_AGE_PROOF>"));
                            NATURE_OF_AGE_PROOF = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        }
                        else
                        {
                            NATURE_OF_AGE_PROOF ="";
                        }
                        log.WriteSuccessLog("NATURE_OF_AGE_PROOF======================"+NATURE_OF_AGE_PROOF,"SuccessLog");
                        System.out.println("NATURE_OF_AGE_PROOF======================"+NATURE_OF_AGE_PROOF);
                        s1 =attributes.substring(attributes.indexOf("<APPLICATION_NO") , attributes.indexOf("</APPLICATION_NO>"));
                        APPLICATION_NO = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("APPLICATION_NO======================"+APPLICATION_NO);
                        log.WriteSuccessLog("APPLICATION_NO======================"+APPLICATION_NO,"SuccessLog");
                        if(attributes.contains("</SUM_ASSURED_SECC1>"))
                        {
                              s1 =attributes.substring(attributes.indexOf("<SUM_ASSURED_SECC1") , attributes.indexOf("</SUM_ASSURED_SECC1>"));
                              SUM_ASSURED_SECC1 = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        }
                        else
                        {
                            SUM_ASSURED_SECC1="0.00";
                        }
                        System.out.println("SUM_ASSURED_SECC1======================"+SUM_ASSURED_SECC1);
                        log.WriteSuccessLog("SUM_ASSURED_SECC1======================"+SUM_ASSURED_SECC1,"SuccessLog");
                        //s1 =attributes.substring(attributes.indexOf("<FREQUENCY") , attributes.indexOf("</FREQUENCY>"));
                        //FREQUENCY = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("FREQUENCY======================"+FREQUENCY);
                        if(attributes.contains("</FREQUENCY>"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<FREQUENCY") , attributes.indexOf("</FREQUENCY>"));
                            FREQUENCY = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        }
                        else
                        {
                            FREQUENCY ="";
                        }
                        System.out.println("FREQUENCY======================"+FREQUENCY);
                        log.WriteSuccessLog("FREQUENCY======================"+FREQUENCY,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<CONTRACT_POLICY_NUMBER") , attributes.indexOf("</CONTRACT_POLICY_NUMBER>"));
                        CONTRACT_POLICY_NUMBER = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("CONTRACT_POLICY_NUMBER======================"+CONTRACT_POLICY_NUMBER);
                        log.WriteSuccessLog("CONTRACT_POLICY_NUMBER======================"+CONTRACT_POLICY_NUMBER,"SuccessLog");
                        //s1 =attributes.substring(attributes.indexOf("<AGE_SECA") , attributes.indexOf("</AGE_SECA>"));
                        //AGE_SECA = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("AGE_SECA======================"+AGE_SECA);
                        if(attributes.contains("</AGE_SECA>"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<AGE_SECA") , attributes.indexOf("</AGE_SECA>"));
                            AGE_SECA = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        }
                        else
                        {
                            AGE_SECA ="";
                        }
                        System.out.println("AGE_SECA======================"+AGE_SECA);
                        log.WriteSuccessLog("AGE_SECA======================"+AGE_SECA,"SuccessLog");
                        //s1 =attributes.substring(attributes.indexOf("<AGE_PROOF") , attributes.indexOf("</AGE_PROOF>"));
                        //AGE_PROOF = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("AGE_PROOF======================"+AGE_PROOF);
                        if(attributes.contains("</AGE_PROOF>"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<AGE_PROOF") , attributes.indexOf("</AGE_PROOF>"));
                            AGE_PROOF = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        }
                        else
                        {
                            AGE_PROOF ="";
                        }
                        System.out.println("AGE_PROOF======================"+AGE_PROOF);
                        log.WriteSuccessLog("AGE_PROOF======================"+AGE_PROOF,"SuccessLog");
                        if(!(AGE_PROOF.equalsIgnoreCase("") || AGE_PROOF.equalsIgnoreCase("NULL")))
                        {
                            AGE_PROOF="\""+AGE_PROOF+"\"";
                        }
                        else
                        {
                            AGE_PROOF=null;
                        }
                        System.out.println("ANNUAL_INCOME======================"+ANNUAL_INCOME);
                        if(attributes.contains("</ANNUAL_INCOME>"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<ANNUAL_INCOME ") , attributes.indexOf("</ANNUAL_INCOME>"));
                            ANNUAL_INCOME = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        }
                        else
                        {
                            ANNUAL_INCOME ="0.00";
                        }
                        System.out.println("ANNUAL_INCOME======================"+ANNUAL_INCOME);
                        log.WriteSuccessLog("ANNUAL_INCOME======================"+ANNUAL_INCOME,"SuccessLog");
                        // added if by Ashwini on 03-03-2023
                        if(attributes.contains("</DATE_OF_BIRTH_A>"))
                        {
                        System.out.println("DATE_OF_BIRTH_A++ 03032023 before ======================"+DATE_OF_BIRTH_A);
                        s1 =attributes.substring(attributes.indexOf("<DATE_OF_BIRTH_A") , attributes.indexOf("</DATE_OF_BIRTH_A>"));
                        DATE_OF_BIRTH_A = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("DATE_OF_BIRTH_A++ 03032023 after ======================"+DATE_OF_BIRTH_A);
                        }
                        else {
                        DATE_OF_BIRTH_A=null;
                        }
                      
                        System.out.println("DATE_OF_BIRTH_A++ 03032023======================"+DATE_OF_BIRTH_A);
                        log.WriteSuccessLog("DATE_OF_BIRTH_A===03032023==================="+DATE_OF_BIRTH_A,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<GENDER_SECA") , attributes.indexOf("</GENDER_SECA>"));
                        GENDER_SECA = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("GENDER_SECA======================"+GENDER_SECA);
                        log.WriteSuccessLog("GENDER_SECA======================"+GENDER_SECA,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<MARITAL_STATUS_A") , attributes.indexOf("</MARITAL_STATUS_A>"));
                        MARRITAL_STATUS_A = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("MARRITAL_STATUS_A======================"+MARRITAL_STATUS_A);
                        log.WriteSuccessLog("MARRITAL_STATUS_A======================"+MARRITAL_STATUS_A,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<OCCUPATION ") , attributes.indexOf("</OCCUPATION>"));
                        OCCUPATION = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("OCCUPATION======================"+OCCUPATION);
                        log.WriteSuccessLog("OCCUPATION======================"+OCCUPATION,"SuccessLog");
                        System.out.println("EDUCATION_QUALIFICATION======================" + EDUCATION_QUALIFICATION);
                        if (attributes.contains("</EDUCATION_QUALIFICATION>"))
                        {
                          s1 = attributes.substring(attributes.indexOf("<EDUCATION_QUALIFICATION"), attributes.indexOf("</EDUCATION_QUALIFICATION>"));
                          EDUCATION_QUALIFICATION = s1.substring(s1.indexOf(">") + 1, s1.length());
                        }
                        else
                        {
                          EDUCATION_QUALIFICATION = "";
                        }
                        System.out.println("EDUCATION_QUALIFICATION======================"+EDUCATION_QUALIFICATION);
                        log.WriteSuccessLog("EDUCATION_QUALIFICATION======================"+EDUCATION_QUALIFICATION,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<ADDR_LINE_1_SECA") , attributes.indexOf("</ADDR_LINE_1_SECA>"));
                        ADDR_LINE_1_SECA = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("ADDR_LINE_1_SECA======================"+ADDR_LINE_1_SECA);
                        log.WriteSuccessLog("ADDR_LINE_1_SECA======================"+ADDR_LINE_1_SECA,"SuccessLog");
                        String[] address_LA = ADDR_LINE_1_SECA.split("\\n");
                        int length_addr = address_LA.length;
                        log.WriteSuccessLog("ADDR_LINE_1_SECA===========length_addr==========="+length_addr,"SuccessLog");
                        if(length_addr>=4)
                        {
                            Life_assured_City = address_LA[3].trim();
                            Life_assured_City="\""+Life_assured_City+"\"";
                        }
                        else
                        {
                            Life_assured_City =null;
                        }
                        s1 =attributes.substring(attributes.indexOf("<LA_State") , attributes.indexOf("</LA_State>"));
                        Life_assured_State = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        
                        System.out.println("Life_assured_City======================"+Life_assured_City);
                        log.WriteSuccessLog("Life_assured_City======================"+Life_assured_City,"SuccessLog");
                        System.out.println("Life_assured_State======================"+Life_assured_State);
                        log.WriteSuccessLog("Life_assured_State======================"+Life_assured_State,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<NATIONALITY_SECA") , attributes.indexOf("</NATIONALITY_SECA>"));
                        NATIONALITY_SECA = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("NATIONALITY_SECA======================"+NATIONALITY_SECA);
                        log.WriteSuccessLog("NATIONALITY_SECA======================"+NATIONALITY_SECA,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<PIN_CODE_SECA") , attributes.indexOf("</PIN_CODE_SECA>"));
                        PIN_CODE_SECA = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("PIN_CODE_SECA======================"+PIN_CODE_SECA);
                        log.WriteSuccessLog("PIN_CODE_SECA======================"+PIN_CODE_SECA,"SuccessLog");
                        if(attributes.contains("</ANNUAL_INCOME_SECB"))
                        {
                        s1 =attributes.substring(attributes.indexOf("<ANNUAL_INCOME_SECB") , attributes.indexOf("</ANNUAL_INCOME_SECB>"));
                        ANNUAL_INCOME_SECB = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        }
                        else{
                         ANNUAL_INCOME_SECB ="0.00";
                        }
                        
                        System.out.println("ANNUAL_INCOME_SECB======================"+ANNUAL_INCOME_SECB);
                        log.WriteSuccessLog("ANNUAL_INCOME_SECB======================"+ANNUAL_INCOME_SECB,"SuccessLog");
                        if(attributes.contains("</ANNUALIZED_PREMIUM>"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<ANNUALIZED_PREMIUM") , attributes.indexOf("</ANNUALIZED_PREMIUM>"));
                            ANNUAL_PREMIUM = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        }
                        else
                        {
                            ANNUAL_PREMIUM="0.00";
                        }
                        System.out.println("ANNUAL_PREMIUM======================"+ANNUAL_PREMIUM);
                        log.WriteSuccessLog("ANNUAL_PREMIUM======================"+ANNUAL_PREMIUM,"SuccessLog");
                        /*s1 =attributes.substring(attributes.indexOf("<POLICY_TERM") , attributes.indexOf("</POLICY_TERM>"));
                        POLICY_TERM = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("POLICY_TERM======================"+POLICY_TERM);
                        log.WriteSuccessLog("POLICY_TERM======================"+POLICY_TERM,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<PREM_PYT_TERMS") , attributes.indexOf("</PREM_PYT_TERMS>"));
                        PREM_PYT_TERMS = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("PREM_PYT_TERMS======================"+PREM_PYT_TERMS);
                        log.WriteSuccessLog("PREM_PYT_TERMS======================"+PREM_PYT_TERMS,"SuccessLog");*/
                        if(attributes.contains("</PREMIUM_SECC>"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<PREMIUM_SECC") , attributes.indexOf("</PREMIUM_SECC>"));
                            PREMIUM_SECC = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        }
                        else{
                            PREMIUM_SECC="0.00";
                        }System.out.println("PREMIUM_SECC======================"+PREMIUM_SECC);
                        log.WriteSuccessLog("PREMIUM_SECC======================"+PREMIUM_SECC,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<PRODUCTCODE") , attributes.indexOf("</PRODUCTCODE>"));
                        PRODUCT_DETAILS = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("PRODUCT_DETAILS======================"+PRODUCT_DETAILS);
                        log.WriteSuccessLog("PRODUCT_DETAILS======================"+PRODUCT_DETAILS,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<PRODUCT_RIDER_NAME") , attributes.indexOf("</PRODUCT_RIDER_NAME>"));
                        PRODUCT_RIDER_NAME = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("PRODUCT_RIDER_NAME======================"+PRODUCT_RIDER_NAME);
                        log.WriteSuccessLog("PRODUCT_RIDER_NAME======================"+PRODUCT_RIDER_NAME,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<CHANNEL_TYPE_SECA") , attributes.indexOf("</CHANNEL_TYPE_SECA>"));
                        CHANNEL_TYPE_SECA = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("CHANNEL_TYPE_SECA======================"+CHANNEL_TYPE_SECA);
                        log.WriteSuccessLog("CHANNEL_TYPE_SECA======================"+CHANNEL_TYPE_SECA,"SuccessLog");
                        if(attributes.contains("</Zone>"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<Zone") , attributes.indexOf("</Zone>"));
                            Zone = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        }
                        else
                        {
                            Zone ="";
                        }System.out.println("Zone======================"+Zone);
                        log.WriteSuccessLog("Zone======================"+Zone,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<SUM_UNDER_CONSIDERATION") , attributes.indexOf("</SUM_UNDER_CONSIDERATION>"));
                        SUM_UNDER_CONSIDERATION = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("SUM_UNDER_CONSIDERATION======================"+SUM_UNDER_CONSIDERATION);
                        log.WriteSuccessLog("SUM_UNDER_CONSIDERATION======================"+SUM_UNDER_CONSIDERATION,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<LIFE_ASSURED_CLIENT_ID") , attributes.indexOf("</LIFE_ASSURED_CLIENT_ID>"));
                        life_Assured_Client_ID = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("LIFE_ASSURED_CLIENT_ID======================"+life_Assured_Client_ID);
                        log.WriteSuccessLog("LIFE_ASSURED_CLIENT_ID======================"+life_Assured_Client_ID,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<PROPOSER_CLIENT_ID") , attributes.indexOf("</PROPOSER_CLIENT_ID>"));
                        PROPOSER_CLIENT_ID = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("PROPOSER_CLIENT_ID======================"+PROPOSER_CLIENT_ID);
                        log.WriteSuccessLog("PROPOSER_CLIENT_ID======================"+PROPOSER_CLIENT_ID,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<DATE_OF_BIRTH_SECB") , attributes.indexOf("</DATE_OF_BIRTH_SECB>"));
                        DATE_OF_BIRTH_SECB = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("DATE_OF_BIRTH_SECB======================"+DATE_OF_BIRTH_SECB);
                        log.WriteSuccessLog("DATE_OF_BIRTH_SECB======================"+DATE_OF_BIRTH_SECB,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<AGE_SECB1") , attributes.indexOf("</AGE_SECB1>"));
                        AGE_SECB1 = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        if(AGE_SECB1.equalsIgnoreCase("") || AGE_SECB1.equalsIgnoreCase(null) || AGE_SECB1.equalsIgnoreCase("null") || AGE_SECB1==null)
                        {
                            AGE_SECB1=null;
                        }
                        System.out.println("AGE_SECB1======================"+AGE_SECB1);
                        log.WriteSuccessLog("AGE_SECB1======================"+AGE_SECB1,"SuccessLog");
                        if(attributes.contains("</Owner_Age_Proof>"))
                        {
                            log.WriteSuccessLog("Owner_Age_Proof======================"+Owner_Age_Proof,"SuccessLog");
                            s1 =attributes.substring(attributes.indexOf("<Owner_Age_Proof") , attributes.indexOf("</Owner_Age_Proof>"));
                            Owner_Age_Proof = s1.substring(s1.indexOf(">")+1 ,s1.length());
                            if(Owner_Age_Proof.equalsIgnoreCase("") || Owner_Age_Proof.equalsIgnoreCase(null) || Owner_Age_Proof.equalsIgnoreCase("null") || Owner_Age_Proof==null)
                            {
                                Owner_Age_Proof=null;
                            }
                            else
                            {
                                Owner_Age_Proof="\""+Owner_Age_Proof+"\"";
                            }
                        }
                        else
                        {
                            log.WriteSuccessLog("Owner_Age_Proof is blank======================","SuccessLog");
                            Owner_Age_Proof="\"\"";
                        }
                        System.out.println("Owner_Age_Proof======================"+Owner_Age_Proof);
                        log.WriteSuccessLog("Owner_Age_Proof======================"+Owner_Age_Proof,"SuccessLog");
                        if (attributes.contains("</GENDER_SECB>"))
                        {
                            s1 = attributes.substring(attributes.indexOf("<GENDER_SECB"), attributes.indexOf("</GENDER_SECB>"));
                            GENDER_SECB = s1.substring(s1.indexOf(">") + 1, s1.length());
                            if ((GENDER_SECB.equalsIgnoreCase("")) || (GENDER_SECB.equalsIgnoreCase(null)) || (GENDER_SECB.equalsIgnoreCase("null")) || (GENDER_SECB == null)) {
                                GENDER_SECB = null;
                            } else {
                                GENDER_SECB = ("\"" + GENDER_SECB + "\"");
                            }
                        }
                        else
                        {
                           GENDER_SECB = null;
                        }
                        System.out.println("GENDER_SECB======================"+GENDER_SECB);
                        log.WriteSuccessLog("GENDER_SECB======================"+GENDER_SECB,"SuccessLog");
                        if (attributes.contains("</OCCUPATION_SECB>"))
                        {
                          s1 = attributes.substring(attributes.indexOf("<OCCUPATION_SECB"), attributes.indexOf("</OCCUPATION_SECB>"));
                          OCCUPATION_SECB = s1.substring(s1.indexOf(">") + 1, s1.length());
                          if ((OCCUPATION_SECB.equalsIgnoreCase("")) || (OCCUPATION_SECB.equalsIgnoreCase(null)) || (OCCUPATION_SECB.equalsIgnoreCase("null")) || (OCCUPATION_SECB == null)) {
                                OCCUPATION_SECB = null;
                          } else {
                                OCCUPATION_SECB = ("\"" + OCCUPATION_SECB + "\"");
                          }
                        }
                        else
                        {
                            OCCUPATION_SECB = null;
                        }
                        System.out.println("OCCUPATION_SECB======================"+OCCUPATION_SECB);
                        log.WriteSuccessLog("OCCUPATION_SECB======================"+OCCUPATION_SECB,"SuccessLog");
                        System.out.println("Owner_Education======================" + this.Owner_Education);
                        if (attributes.contains("</Owner_Education>"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<Owner_Education") , attributes.indexOf("</Owner_Education>"));
                            Owner_Education = s1.substring(s1.indexOf(">")+1 ,s1.length());
                            if(Owner_Education.equalsIgnoreCase("") || Owner_Education.equalsIgnoreCase(null) || Owner_Education.equalsIgnoreCase("null") || Owner_Education==null)
                            {
                                Owner_Education=null;
                            }
                            else
                            {
                                Owner_Education="\""+Owner_Education+"\"";
                            }
                        }
                        else
                        {
                            Owner_Education = null;
                        }
                        System.out.println("Owner_Education======================"+Owner_Education);
                        log.WriteSuccessLog("Owner_Education======================"+Owner_Education,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<ADDR_LINE_1_SECB") , attributes.indexOf("</ADDR_LINE_1_SECB>"));
                        ADDR_LINE_1_SECB = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("ADDR_LINE_1_SECB======================"+ADDR_LINE_1_SECB);
                        log.WriteSuccessLog("ADDR_LINE_1_SECB======================"+ADDR_LINE_1_SECB,"SuccessLog");
                        String[] address_PO = ADDR_LINE_1_SECB.split("\\n");
                        int length_addr_po = address_PO.length;
                        log.WriteSuccessLog("length_addr_po======================"+length_addr_po,"SuccessLog");
                        if(length_addr_po>=4)
                        {
                            policy_Owner_City = address_PO[3].trim();
                            policy_Owner_City="\""+policy_Owner_City+"\"";
                        }
                        else
                        {
                            policy_Owner_City =null;
                        }
                        System.out.println("policy_Owner_City======================"+policy_Owner_City);
                        log.WriteSuccessLog("policy_Owner_City======================"+policy_Owner_City,"SuccessLog");
                        //s1 =attributes.substring(attributes.indexOf("<PIN_CODE_SECB") , attributes.indexOf("</PIN_CODE_SECB>"));
                        //PIN_CODE_SECB = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("PIN_CODE_SECB======================"+PIN_CODE_SECB);
                        if(attributes.contains("</PIN_CODE_SECB>"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<PIN_CODE_SECB") , attributes.indexOf("</PIN_CODE_SECB>"));
                            PIN_CODE_SECB = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        }
                        else
                        {
                            PIN_CODE_SECB ="";
                        }
                        System.out.println("PIN_CODE_SECB======================"+PIN_CODE_SECB);
                        log.WriteSuccessLog("PIN_CODE_SECB======================"+PIN_CODE_SECB,"SuccessLog");
                        if(PIN_CODE_SECB.equalsIgnoreCase("")||PIN_CODE_SECB.equalsIgnoreCase("NULL") ||PIN_CODE_SECB==null)
                        {
                            PIN_CODE_SECB = null;
                        }
                        else
                        {
                            PIN_CODE_SECB="\""+PIN_CODE_SECB+"\"";
                        }
                        s1 =attributes.substring(attributes.indexOf("<IDENTIFY_PROOF") , attributes.indexOf("</IDENTIFY_PROOF>"));
                        IDENTIFY_PROOF_PO = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        if(IDENTIFY_PROOF_PO.equalsIgnoreCase("")||IDENTIFY_PROOF_PO.equalsIgnoreCase("NULL") ||IDENTIFY_PROOF_PO==null)
                        {
                            IDENTIFY_PROOF_PO = null;
                        }
                        else
                        {
                            IDENTIFY_PROOF_PO="\""+IDENTIFY_PROOF_PO+"\"";
                        }
                        System.out.println("IDENTIFY_PROOF_PO======================"+IDENTIFY_PROOF_PO);
                        log.WriteSuccessLog("IDENTIFY_PROOF_PO======================"+IDENTIFY_PROOF_PO,"SuccessLog");
                        if(attributes.contains("</INCOME_PROOF_B>"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<INCOME_PROOF_B") , attributes.indexOf("</INCOME_PROOF_B>"));
                            INCOME_PROOF_B_PO = s1.substring(s1.indexOf(">")+1 ,s1.length());
                            if(INCOME_PROOF_B_PO.equalsIgnoreCase("")||INCOME_PROOF_B_PO.equalsIgnoreCase("NULL") ||INCOME_PROOF_B_PO==null)
                            {
                                INCOME_PROOF_B_PO = null;
                            }
                            else
                            {
                                INCOME_PROOF_B_PO="\""+INCOME_PROOF_B_PO+"\"";
                            }
                        }
                        else
                        {
                            INCOME_PROOF_B_PO = null;
                        }
                        System.out.println("INCOME_PROOF_B_PO======================"+INCOME_PROOF_B_PO);
                        log.WriteSuccessLog("INCOME_PROOF_B_PO======================"+INCOME_PROOF_B_PO,"SuccessLog");
                        
                        /*s1 =attributes.substring(attributes.indexOf("<q_NomineeDetails") , attributes.indexOf("</q_NomineeDetails>"));
                        nominee_grid = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("nominee_grid======================"+nominee_grid);
                        log.WriteSuccessLog("nominee_grid======================"+nominee_grid,"SuccessLog");*/
                        
                        s1 =attributes.substring(attributes.indexOf("<RELATION_WITH_LA") , attributes.indexOf("</RELATION_WITH_LA>"));
                        RELATION_WITH_LA = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("RELATION_WITH_LA======================"+RELATION_WITH_LA);
                        log.WriteSuccessLog("RELATION_WITH_LA======================"+RELATION_WITH_LA,"SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<RELATIONSHIP_TO_LIFE_ASSURED") , attributes.indexOf("</RELATIONSHIP_TO_LIFE_ASSURED>"));
                        RELATIONSHIP_TO_LIFE_ASSURED = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("RELATIONSHIP_TO_LIFE_ASSURED======================"+RELATIONSHIP_TO_LIFE_ASSURED);
                        log.WriteSuccessLog("RELATIONSHIP_TO_LIFE_ASSURED======================"+RELATIONSHIP_TO_LIFE_ASSURED,"SuccessLog");
                        String prod_details = fetchProductDetails(processInstanceID);
                        String resultGL2[] = prod_details.split("\\|");
                        System.out.println("resultGL2.length======================"+resultGL2.length);
                        log.WriteSuccessLog("resultGL2.length======================"+resultGL2.length,"SuccessLog");
                        if(resultGL2.length !=0 && resultGL2[0].toString().equalsIgnoreCase(""))
                        {
                            System.out.println("inside if::resultGL2.length!=0:::::"+resultGL2[0].toString());
                            SUM_ASSURED_SECC1 = resultGL2[0];
                            System.out.println("inside if : end ::SUM_ASSURED_SECC1:::::"+SUM_ASSURED_SECC1);
                            POLICY_TERM = resultGL2[1];
                            System.out.println("inside if : end ::POLICY_TERM:::::"+POLICY_TERM);
                            POLICY_TERM = POLICY_TERM.substring(0, POLICY_TERM.indexOf("."));
                            System.out.println("1..inside if : end ::POLICY_TERM:::::"+POLICY_TERM);
                            PREM_PYT_TERMS = resultGL2[2];
                            System.out.println("inside if : end ::PREM_PYT_TERMS:::::"+PREM_PYT_TERMS);
                            PREM_PYT_TERMS = PREM_PYT_TERMS.substring(0, PREM_PYT_TERMS.indexOf("."));
                            System.out.println("1..inside if : end ::PREM_PYT_TERMS:::::"+PREM_PYT_TERMS);
                        }
                        System.out.println("30092022 ::ANNUAL_INCOME:::::"+ANNUAL_INCOME);
                        if(ANNUAL_INCOME.equalsIgnoreCase("") || ANNUAL_INCOME.equalsIgnoreCase("NULL"))
                        {
                            ANNUAL_INCOME ="0";
                        }
                        System.out.println("30092022 ::POLICY_TERM:::::"+POLICY_TERM);
                        if(POLICY_TERM.equalsIgnoreCase(""))
                        {
                            POLICY_TERM ="0";
                        }
                        System.out.println("30092022 ::PREM_PYT_TERMS:::::"+PREM_PYT_TERMS);
                        if(PREM_PYT_TERMS.equalsIgnoreCase(""))
                        {
                            PREM_PYT_TERMS ="0";
                        }
                        System.out.println("30092022 ::SUM_ASSURED_SECC1:::::"+SUM_ASSURED_SECC1);
                        if(SUM_ASSURED_SECC1.equalsIgnoreCase(""))
                        {
                            SUM_ASSURED_SECC1 ="0";
                        }
                        System.out.println("30092022 ::ANNUAL_INCOME_SECB:::::"+ANNUAL_INCOME_SECB);
                        if(ANNUAL_INCOME_SECB.equalsIgnoreCase(""))
                        {
                            ANNUAL_INCOME_SECB ="0";
                        }
                        System.out.println("30092022 ::DATE_OF_BIRTH_A:::::"+DATE_OF_BIRTH_A);
                        if(DATE_OF_BIRTH_A != null && !(DATE_OF_BIRTH_A.equalsIgnoreCase("null")) && !(DATE_OF_BIRTH_A.equalsIgnoreCase("")))
                        {
                            Date date = sdf.parse(DATE_OF_BIRTH_A);
                            DATE_OF_BIRTH_A = sdf1.format(date);
                            DATE_OF_BIRTH_A="\""+DATE_OF_BIRTH_A+"\"";
                            //Date dt = sdf.parse(sdf.format(new Date()));
                        }
                        else
                        {
                            DATE_OF_BIRTH_A=null;
                        }
                        System.out.println("30092022 ::DATE_OF_BIRTH_SECB:::::"+DATE_OF_BIRTH_SECB);
                        if(DATE_OF_BIRTH_SECB != null && !(DATE_OF_BIRTH_SECB.equalsIgnoreCase("null")) && !(DATE_OF_BIRTH_SECB.equalsIgnoreCase("")))
                        {
                            Date date = sdf.parse(DATE_OF_BIRTH_SECB);
                            DATE_OF_BIRTH_SECB = sdf1.format(date);
                            DATE_OF_BIRTH_SECB="\""+DATE_OF_BIRTH_SECB+"\"";
                        }
                        else
                        {
                            DATE_OF_BIRTH_SECB=null;
                        }
                        System.out.println("30092022 ::risk_Commencement_Date:::::"+risk_Commencement_Date);
                        if(risk_Commencement_Date != null && !(risk_Commencement_Date.equalsIgnoreCase("null")) && !(risk_Commencement_Date.equalsIgnoreCase("")))
                        {
                            Date date = sdf.parse(risk_Commencement_Date);
                            risk_Commencement_Date = sdf1.format(date);
                            risk_Commencement_Date="\""+risk_Commencement_Date+"\"";
                        }
                        else
                        {
                            risk_Commencement_Date=null;
                        }
                        System.out.println("30092022 ::policy_Issue_Date:::::"+policy_Issue_Date);
                        if(policy_Issue_Date != null && !(policy_Issue_Date.equalsIgnoreCase("null")) && !(policy_Issue_Date.equalsIgnoreCase("")))
                        {
                            Date date = sdf.parse(policy_Issue_Date);
                            policy_Issue_Date = sdf1.format(date);
                            policy_Issue_Date="\""+policy_Issue_Date+"\"";
                        }
                        else
                        {
                            policy_Issue_Date=null;
                        }
                        System.out.println("30092022 ::RELATIONSHIP_TO_LIFE_ASSURED:::::"+RELATIONSHIP_TO_LIFE_ASSURED);
                        if(RELATIONSHIP_TO_LIFE_ASSURED.equalsIgnoreCase("SF"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<COUNTRY_OF_RESIDENCE_SECB") , attributes.indexOf("</COUNTRY_OF_RESIDENCE_SECB>"));
                            COUNTRY_OF_RESIDENCE_SECB = s1.substring(s1.indexOf(">")+1 ,s1.length());
                            if(COUNTRY_OF_RESIDENCE_SECB.equalsIgnoreCase("")|| COUNTRY_OF_RESIDENCE_SECB.equalsIgnoreCase("null") || COUNTRY_OF_RESIDENCE_SECB==null)
                            {
                                COUNTRY_OF_RESIDENCE_SECB=null;
                            }
                            else
                            {
                                COUNTRY_OF_RESIDENCE_SECB="\""+COUNTRY_OF_RESIDENCE_SECB+"\"";
                            }
                            System.out.println("COUNTRY_OF_RESIDENCE_SECB======================"+COUNTRY_OF_RESIDENCE_SECB);
                            log.WriteSuccessLog("COUNTRY_OF_RESIDENCE_SECB======================"+COUNTRY_OF_RESIDENCE_SECB,"SuccessLog"); 
                        }
                        else
                        {
                            COUNTRY_OF_RESIDENCE_SECB = null;
                        }
                        System.out.println("30092022 ::RELATION_WITH_LA:::::"+RELATION_WITH_LA);
                        log.WriteSuccessLog("30092022::RELATION_WITH_LA======================"+RELATION_WITH_LA,"SuccessLog");
                        if(RELATION_WITH_LA.equalsIgnoreCase("SF"))
                        {
                            s1 =attributes.substring(attributes.indexOf("<IDENTIFY_PROOF") , attributes.indexOf("</IDENTIFY_PROOF>"));
                            IDENTIFY_PROOF = s1.substring(s1.indexOf(">")+1 ,s1.length());
                            if(IDENTIFY_PROOF.equalsIgnoreCase("")||IDENTIFY_PROOF.equalsIgnoreCase("NULL") ||IDENTIFY_PROOF==null)
                            {
                                IDENTIFY_PROOF = null;
                            }
                            else
                            {
                                IDENTIFY_PROOF="\""+IDENTIFY_PROOF+"\"";
                            }
                            System.out.println("IDENTIFY_PROOF======================"+IDENTIFY_PROOF);
                            log.WriteSuccessLog("IDENTIFY_PROOF======================"+IDENTIFY_PROOF,"SuccessLog");
                            if(attributes.contains("</INCOME_PROOF_B>"))
                            {
                                log.WriteSuccessLog("Contains DENTIFY_PROOF======================","SuccessLog");
                                s1 =attributes.substring(attributes.indexOf("<INCOME_PROOF_B") , attributes.indexOf("</INCOME_PROOF_B>"));
                                INCOME_PROOF_B = s1.substring(s1.indexOf(">")+1 ,s1.length());
                                if(INCOME_PROOF_B.equalsIgnoreCase("")||INCOME_PROOF_B.equalsIgnoreCase("NULL") ||INCOME_PROOF_B==null)
                                {
                                    INCOME_PROOF_B = null;
                                }
                                else
                                {
                                    INCOME_PROOF_B="\""+INCOME_PROOF_B+"\"";
                                }
                            }
                            else
                            {
                                log.WriteSuccessLog("in else loop :: doesnot Contains DENTIFY_PROOF======================","SuccessLog");
                                INCOME_PROOF_B = null;
                            }
                            System.out.println("INCOME_PROOF_B======================"+INCOME_PROOF_B);
                            log.WriteSuccessLog("INCOME_PROOF_B======================"+INCOME_PROOF_B,"SuccessLog");
                            s1 =attributes.substring(attributes.indexOf("<MARITAL_STATUS_A") , attributes.indexOf("</MARITAL_STATUS_A>"));
                            MARITAL_STATUS_A_PO = s1.substring(s1.indexOf(">")+1 ,s1.length());
                            if(MARITAL_STATUS_A_PO.equalsIgnoreCase("")|| MARITAL_STATUS_A_PO.equalsIgnoreCase("NULL") ||MARITAL_STATUS_A_PO==null)
                            {
                                MARITAL_STATUS_A_PO = null;
                            }
                            else
                            {
                                MARITAL_STATUS_A_PO="\""+MARITAL_STATUS_A_PO+"\"";
                            }
                            System.out.println("MARITAL_STATUS_A_PO======================"+MARITAL_STATUS_A_PO);
                            log.WriteSuccessLog("MARITAL_STATUS_A_PO======================"+MARITAL_STATUS_A_PO,"SuccessLog");
                            s1 =attributes.substring(attributes.indexOf("<NATIONALITY_SECA") , attributes.indexOf("</NATIONALITY_SECA>"));
                            NATIONALITY_SECA_PO = s1.substring(s1.indexOf(">")+1 ,s1.length());
                            if(NATIONALITY_SECA_PO.equalsIgnoreCase("")|| NATIONALITY_SECA_PO.equalsIgnoreCase("NULL") ||NATIONALITY_SECA_PO==null)
                            {
                                NATIONALITY_SECA_PO = null;
                            }
                            else
                            {
                                NATIONALITY_SECA_PO="\""+NATIONALITY_SECA_PO+"\"";
                            }
                            System.out.println("NATIONALITY_SECA_PO======================"+NATIONALITY_SECA_PO);
                            log.WriteSuccessLog("NATIONALITY_SECA_PO======================"+NATIONALITY_SECA_PO,"SuccessLog");
                        }
                        else
                        {
                            IDENTIFY_PROOF= null;
                            INCOME_PROOF_B= null;
                            MARITAL_STATUS_A_PO= null;
                            NATIONALITY_SECA_PO=null;
                        }
                        //ANNUAL_PREMIUM = CalculateAnnualPremium(PREMIUM_SECC, setBillingFrequency(FREQUENCY));
                        log.WriteSuccessLog("01-10-2022:Life_assured_City======================"+Life_assured_City,"SuccessLog");
                        if(Life_assured_City==null)
                        {
                            Life_assured_City=null;
                        }
                        else
                        {
                            Life_assured_City = Life_assured_City.trim();
                        }
                        log.WriteSuccessLog("01-10-2022:Life_assured_City======================"+Life_assured_City,"SuccessLog");
                        if(policy_Owner_City==null)
                        {
                            policy_Owner_City=null;
                        }
                        else
                        {
                            policy_Owner_City = policy_Owner_City.trim();
                        }
                        log.WriteSuccessLog("01-10-2022:policy_Owner_City======================"+policy_Owner_City,"SuccessLog");
                        
                        String jsonInput = "{\n" +
                       
                        "\"clientIdentifier\": \"SUD\",\n" +
                        "\"application_No\":\""+APPLICATION_NO+"\",\n" +
                        "\"contract_No\":\""+CONTRACT_POLICY_NUMBER+"\",\n" +
                        "\"risk_Commencement_Date\":"+risk_Commencement_Date+",\n" +
                        "\"policy_Issue_Date\":"+policy_Issue_Date+",\n" +
                        "\"life_Assured_Client_ID\":\""+life_Assured_Client_ID+"\",\n" +
                        "\"life_Assured_DOB\":"+DATE_OF_BIRTH_A+",\n" +
                        "\"life_Assured_Age\":"+AGE_SECA+",\n" +
                        "\"life_Assured_Age_Proof\":"+AGE_PROOF+",\n" +
                        "\"life_Assured_Gender\":\""+GENDER_SECA+"\",\n" +
                        "\"life_Assured_Annual_Income\":"+ANNUAL_INCOME+",\n" +
                        "\"life_Assured_Occupation\":\""+OCCUPATION+"\",\n" +
                        "\"life_Assured_Industry\":"+life_Assured_Industry+",\n" +
                        "\"life_Assured_Qualification\":\""+EDUCATION_QUALIFICATION+"\",\n" +
                        "\"life_Assured_Marital_Status\":\""+MARRITAL_STATUS_A+"\",\n" +
                        "\"life_Assured_City\":"+Life_assured_City+",\n" +
                        "\"life_Assured_Pincode\":\""+PIN_CODE_SECA+"\",\n" +
                        "\"life_Assured_State\":\""+Life_assured_State+"\",\n" +
                        "\"life_Assured_Country\":"+COUNTRY_OF_RESIDENCE_SECB+",\n" +
                        "\"life_Assured_Nationality\":\""+NATIONALITY_SECA+"\",\n" +
                        "\"life_Assured_ID_Proof\":"+IDENTIFY_PROOF+",\n" +
                        "\"life_Assured_Income_Proof\":"+INCOME_PROOF_B+",\n" +
                        "\"policy_Owner_Client_ID\":\""+PROPOSER_CLIENT_ID+"\",\n" +
                        "\"policy_Owner_DOB\":"+DATE_OF_BIRTH_SECB+",\n" +
                        "\"policy_Owner_Age\":"+AGE_SECB1+",\n" +        
                        "\"policy_Owner_Age_Proof\":"+Owner_Age_Proof+",\n" +            
                        "\"policy_Owner_Gender\":"+GENDER_SECB+",\n" +  
                        "\"policy_Owner_Annual_Income\":"+ANNUAL_INCOME_SECB+",\n" +
                        "\"policy_Owner_Occupation\":"+OCCUPATION_SECB+",\n" +  
                        "\"policy_Owner_Industry\":"+policy_Owner_Industry+",\n" +            
                        "\"policy_Owner_Qualification\":"+Owner_Education+",\n" +
                        "\"policy_Owner_Marital_Status\":"+MARITAL_STATUS_A_PO+",\n" +
                        "\"policy_Owner_City\":"+policy_Owner_City+",\n" +  
                        "\"policy_Owner_Pincode\":"+PIN_CODE_SECB+",\n" +
                        "\"policy_Owner_Country\":"+policy_Owner_Country+",\n" +  
                        "\"policy_Owner_Nationality\":"+NATIONALITY_SECA_PO+",\n" +            
                        "\"policy_Owner_ID_Proof\":"+IDENTIFY_PROOF_PO+",\n" +
                        "\"policy_Owner_Income_Proof\":"+INCOME_PROOF_B_PO+",\n" +
                        "\"product_Code\":\""+PRODUCT_DETAILS+"\",\n" +
                        "\"product_Name\":\""+PRODUCT_RIDER_NAME+"\",\n" +
                        "\"base_Plan_Sum_Assured\":"+SUM_ASSURED_SECC1+",\n" +
                        "\"annual_Premium\":"+ANNUAL_PREMIUM+",\n" +
                        "\"policy_Term\":"+POLICY_TERM+",\n" +
                        "\"premium_Payment_Term\":"+PREM_PYT_TERMS+",\n" +
                        "\"billing_Frequency\":\""+FREQUENCY+"\",\n" +
                        "\"premium_Payment_Mode\":"+premium_Payment_Mode+",\n" +
                        "\"sales_Channel\":\""+fetchSalesChannel(processInstanceID,CHANNEL_TYPE_SECA)+"\",\n" +
                        "\"nominee_Relation\":"+fetchNomineeRelation(processInstanceID)+",\n" +
                        "\"zone_Location\":\""+Zone+"\",\n" +
                        "\"branch_Location\": \"Test\",\n" +
                        "\"online_Offline_Flag\": \"Offline\",\n" +
                        "\"smoker_Flag\": null,\n" +
                        "\"advisor_Code\": null,\n" +
                        "\"agent_Type\": null,\n" +
                        "\"agent_Class\": null,\n" +
                        "\"agent_Code\": null,\n" +
                        "\"line_of_Business\": null,\n" +
                        "\"sum_Under_Consideration\":"+SUM_UNDER_CONSIDERATION+"\n" +
                        "}\n" ;
                        log.WriteSuccessLog("jsonInput is :"+jsonInput,"SuccessLog");
                        /*String jsonInput = "{\n" +
"\"drcInput\":\n" +
"{\n" +
"  \"clientIdentifier\": \"SUD\",\n" +
"  \"application_No\": \"A123UL\",\n" +
"  \"contract_No\": \"C123_+LO\",\n" +
"  \"risk_Commencement_Date\": \"26/03/2018\",\n" +
"  \"policy_Issue_Date\": \"26 march 2018\",\n" +
"  \"life_Assured_Client_ID\": \"123\",\n" +
"  \"life_Assured_DOB\": \"16 July 92\",\n" +
"  \"life_Assured_Age\": 25,\n" +
"  \"life_Assured_Age_Proof\": \"PAN CARD\",\n" +
"  \"life_Assured_Gender\": \"F\",\n" +
"  \"life_Assured_Annual_Income\": 1000000,\n" +
"  \"life_Assured_Occupation\": \"Salaried\",\n" +
"  \"life_Assured_Industry\": \"Medical\",\n" +
"  \"life_Assured_Qualification\": \"Graduate\",\n" +
"  \"life_Assured_Marital_Status\": \"D\",\n" +
"  \"life_Assured_City\": \"Mumbai\",\n" +
"  \"life_Assured_Pincode\": \"110024\",\n" +
"  \"life_Assured_State\": \"Maharashtra\",\n" +
"  \"life_Assured_Country\": \"India\",\n" +
"  \"life_Assured_Nationality\": \"Indian\",\n" +
"  \"life_Assured_ID_Proof\": \"PAN CARD\",\n" +
"  \"life_Assured_Income_Proof\": \"Salary slip\",\n" +
"  \"policy_Owner_Client_ID\": \"12345\",\n" +
"  \"policy_Owner_DOB\": \"15 August 1962\",\n" +
"  \"policy_Owner_Age\": 55,\n" +
"  \"policy_Owner_Age_Proof\": \"VOTER ID\",\n" +
"  \"policy_Owner_Gender\": \"M\",\n" +
"  \"policy_Owner_Annual_Income\": 10000000,\n" +
"  \"policy_Owner_Occupation\": \"Business\",\n" +
"  \"policy_Owner_Industry\": \"Business\",\n" +
"  \"policy_Owner_Qualification\": \"Graduate\",\n" +
"  \"policy_Owner_Marital_Status\": \"M\",\n" +
"  \"policy_Owner_City\": \"Delhi\",\n" +
"  \"policy_Owner_Pincode\": \"110001\",\n" +
"  \"policy_Owner_Country\": \"IN\",\n" +
"  \"policy_Owner_Nationality\": \"IN\",\n" +
"  \"policy_Owner_ID_Proof\": \"voter id\",\n" +
"  \"policy_Owner_Income_Proof\": \"pan card\",\n" +
"  \"product_Code\": \"T10\",\n" +
"  \"product_Name\": \" Premier Protection Plan\",\n" +
"  \"base_Plan_Sum_Assured\": 5000000,\n" +
"  \"annual_Premium\": 10000,\n" +
"  \"policy_Term\": 10,\n" +
"  \"premium_Payment_Term\": 10,\n" +
"  \"billing_Frequency\": \"1\",\n" +
"  \"premium_Payment_Mode\": \"Cheque\",\n" +
"  \"sales_Channel\": \"LPD\",\n" +
"  \"nominee_Relation\": \"Mother\",\n" +
"  \"zone_Location\": \"South\",\n" +
"  \"branch_Location\": \"South\",\n" +
"  \"online_Offline_Flag\": \"Offline\",\n" +
"  \"smoker_Flag\": \"N\",\n" +
"  \"advisor_Code\": \"AS3\",\n" +
"  \"agent_Type\": \"Q12_0\",\n" +
"  \"agent_Class\": \"Risky\",\n" +
"  \"agent_Code\": \"12s3\",\n" +
"  \"line_of_Business\": \"Individual\",\n" +
"  \"sum_Under_Consideration\": 7000000\n" +
"}\n" +
"}";*/
                        try
                        {
                            SOAPResponse_xml=executeWS_json(jsonInput,DRCWebservice_UtilityHelper.WSEndpointURL);
                            System.out.println("SOAPResponse_xml is :"+SOAPResponse_xml);
                            log.WriteSuccessLog("SOAPResponse_xml is :"+SOAPResponse_xml,"SuccessLog");                           
                        }
                        catch(Exception e)
                        {
                            
                        }
                        if(SOAPResponse_xml!=null && !SOAPResponse_xml.trim().equals(""))
                        {
                            //System.out.println("SOAPResponse_xml:"+SOAPResponse_xml);
                            String success_op=SOAPResponse_xml;
                            /*String success_op ="{\n" +
                            "\"DRCalculateResult\":{\n" +
                            "\"Code\":\"1\",\n" +
                            "\"Message\":\"Success.\",\n" +
                            "\"Response\":{\n" +
                            "\"application_No\": \"47857885\",\n" +
                            "\"contract_No\": \"47857885\",\n" +
                            "\"life_Assured_Gender\": \"M\",\n" +
                            "\"life_Assured_Age\": \"33\",\n" +
                            "\"life_Assured_Annual_Income\": \"10000\",\n" +
                            "\"life_Assured_Marital_Status\": \"M\",\n" +
                            "\"product_Name\": \"string\",\n" +
                            "\"base_Plan_Sum_Assured\": \"0\",\n" +
                            "\"annual_Premium\": \"50000\",\n" +
                            "\"billing_Frequency\": \"Annual\",\n" +
                            "\"sum_Under_Consideration\": \"0\",\n" +
                            "\"drC_Life_Assured_Age_Category\": \"string\",\n" +
                            "\"drC_Life_Assured_Annual_Income_Category\": \"string\",\n" +
                            "\"drC_Life_Assured_Occupation_Category\": \"string\",\n" +
                            "\"drC_Life_Assured_Qualification_Category\": \"string\",\n" +
                            "\"drC_Life_Assured_Age_Proof_Category\": \"string\",\n" +
                            "\"drC_Life_Assured_Pincode_Category\": \"string\",\n" +
                            "\"drC_Product_Code_Category\": \"string\",\n" +
                            "\"drC_Base_Plan_Sum_Assured_Category\": \"string\",\n" +
                            "\"drC_Annual_Premium_Category\": \"Super\",\n" +
                            "\"drC_Policy_Term_Category\": \"string\",\n" +
                            "\"drC_Premium_Payment_Term_Category\": \"string\",\n" +
                            "\"drC_Sales_Channel_Category\": \"string\",\n" +
                            "\"drC_Temp1_Category\": \"string\",\n" +
                            "\"drC_Temp2_Category\": \"string\",\n" +
                            "\"drC_Temp3_Category\": \"string\",\n" +
                            "\"drC_Temp4_Category\": \"string\",\n" +
                            "\"drC_Temp5_Category\": \"string\",\n" +
                            "\"drC_Category\": \"Super Preferred\",\n" +
                            "\"action1\": \"Yes\",\n" +
                            "\"action2\": \"Yes\",\n" +
                            "\"action3\": \"Yes\",\n" +
                            "\"action4\": \"Yes\",\n" +
                            "\"action5\": \"Yes\",\n" +
                            "\"action6\": \"string\",\n" +
                            "\"action7\": \"string\",\n" +
                            "\"action8\": \"string\",\n" +
                            "\"action9\": \"string\",\n" +
                            "\"action10\": \"string\",\n" +
                            "\"medical_Flag\": \"Yes\",\n" +
                            "\"nml\": \"123456789012345\",\n" +
                            "\"requirement\": \"string\",\n" +
                            "\"drC_Lapse_Propensity\": \"0\"\n" +
                            "}\n" +
                            "}\n" +
                            "}";*/
                            
                            JSONObject obj = new JSONObject(SOAPResponse_xml);
                            JSONObject obj1 = new JSONObject(success_op);
                            String code1 = obj1.getJSONObject("DRCalculateNewResult").getString("Code");
                            System.out.println("Code:"+code1);
                            String msg1 = obj1.getJSONObject("DRCalculateNewResult").getString("Message");
                            System.out.println("msg:"+msg1);
                            
                            String code = obj.getJSONObject("DRCalculateNewResult").getString("Code");
                            System.out.println("Code:"+code);
                            //code = "1";
                            //String response = obj.getJSONObject("DRCalculateNewResult").getString("Response");
                            //System.out.println("response:"+response);
                            if(code.equalsIgnoreCase("0"))
                            {
                                String msg = obj.getJSONObject("DRCalculateNewResult").getString("Message");
                                System.out.println("msg:"+msg);
                                
                            }
                            else if(code.equalsIgnoreCase("1"))
                            {
                                String success_msg = obj.getJSONObject("DRCalculateNewResult").getString("Message");
                                System.out.println("success_msg:"+success_msg);
                                int index = success_op.indexOf("ResponseData");
                                System.out.println("index:"+index);
                                String response = "{"+success_op.substring((index-1), success_op.length()-1);
                                System.out.println("success_op:"+response);
                                InsertValuesInDRCTable(response,processInstanceID,workItemID);
                            }
                            else if(code.equalsIgnoreCase("2"))
                            {
                               System.out.println("ValidationError:");
                               
                               int index = success_op.indexOf("ValidationError");
                               System.out.println("index:"+index);
                               String response = "{"+success_op.substring((index-1), success_op.length()-1);
                               response = response.replaceAll("'", "");
                               response = response.replaceAll(",", "");
                               System.out.println("success_op:"+response);
                               String sColumnValue_ext_Error="BO_Error='ValidationError',DRC_Error='"+response+"',DRC_ERROR_CODE='2',DECISION_DRC_FAILURE='RE-TRY'";
                               //String sColumnValue_ext_Error="var_int='ValidationError',DRC_Error='"+response+"'";
                               //UpdateWFInstTable(sColumnValue_WFinst,processInstanceID,workItemID);
                               //UpdateExternalTable(sColumnValue_ext_Error,processInstanceID,workItemID);
                               String inColumns="BO_Error,DRC_Error,DRC_ERROR_CODE,DECISION_DRC_FAILURE";
                                String inValues = "'ValidationError','"+response+"','2','RE-TRY'";
                                //UpdateExternalTable(inValues,wiid,processInstanceID,inColumns);
                                UpdateExternalTable(inValues,processInstanceID,workItemID,inColumns);
                            }
                            else if(code.equalsIgnoreCase("3"))
                            {
                               System.out.println("TechnicalError");
                               int index = success_op.indexOf("TechnicalError");
                               String response = "{"+success_op.substring((index-1), success_op.length()-1);
                               response = response.replaceAll("'", "");
                               response = response.replaceAll(",", "");
                               String sColumnValue_ext_Error="BO_Error='TechnicalError',DRC_Error='"+response+"',DRC_ERROR_CODE='3',DECISION_DRC_FAILURE='RE-TRY'";
                               //UpdateWFInstTable(sColumnValue_WFinst,processInstanceID,workItemID);
                               String inColumns="BO_Error,DRC_Error,DRC_ERROR_CODE,DECISION_DRC_FAILURE";
                                String inValues = "'TechnicalError','"+response+"','3','RE-TRY'";
                                //UpdateExternalTable(inValues,wiid,processInstanceID,inColumns);
                                UpdateExternalTable(inValues,processInstanceID,workItemID,inColumns);
                               //UpdateExternalTable(sColumnValue_ext_Error,processInstanceID,workItemID);
                            }
                            else
                            {
                                System.out.println("Invalid code");
                                log.WriteSuccessLog("Invalid code", "SuccessLog");
                                String sColumnValue_ext_Error="BO_Error='DRC_WS_ERROR',DRC_ERROR_CODE='"+code+"',DECISION_DRC_FAILURE='RE-TRY'";
                                //UpdateWFInstTable(sColumnValue_WFinst,processInstanceID,workItemID);
                                String inColumns="BO_Error,DRC_ERROR_CODE,DECISION_DRC_FAILURE";
                                String inValues = "'DRC_WS_ERROR','"+code+"','RE-TRY'";
                                UpdateExternalTable(inValues,processInstanceID,workItemID,inColumns);
                                //UpdateExternalTable(sColumnValue_ext_Error,processInstanceID,workItemID);
                            }
                            return true;
                        }
                        else
                        {
                            log.WriteSuccessLog("Exception: Core Server is Down......", "SuccessLog");
                            return false;
                        }            
                    }                                       
                }
                else
                {
                    log.WriteSuccessLog("Cannot get workitem Attributes for "+processInstanceID, "SuccessLog");
                    return false;
                }
            }           
        }
        catch (Exception ex)
        {
            log.WriteSuccessLog("Exception: Error in gathering Workitem information ...."+ex, "SuccessLog");
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    public void InsertValuesInDRCTable(String response,String wiid,String workitemid) throws JSONException
    {
        String sDRCTableName="NG_SUD_NB_DRC_OUTPUT";
        String sColumnName="WI_NAME,Application_No,Contract_No,Life_Assured_Gender,Life_Assured_Age,Life_Assured_Annual_Income,Life_Assured_Marital_Status,Product_Name,Base_Plan_Sum_Assured,Annual_Premium,Billing_Frequency,Sum_Under_Consideration,DRC_Life_Assured_Age_Category,DRC_Life_Assured_Annual_Income_Category,DRC_Life_Assured_Occupation_Category,DRC_Life_Assured_Qualification_Category,DRC_Life_Assured_Age_Proof_Category,DRC_Life_Assured_Pincode_Category,DRC_Product_Code_Category,DRC_Base_Plan_Sum_Assured_Category,DRC_Annual_Premium_Category,DRC_Policy_Term_Category,DRC_Premium_Payment_Term_Category,DRC_Sales_Channel_Category,DRC_Temp1_Category,DRC_Temp2_Category,DRC_Temp3_Category,DRC_Temp4_Category,DRC_Temp5_Category,DRC_Category,Income_Document_Submission,TeleVerification,Discreet_Risk_Check,Detailed_Sales_Report,CoverToBeOfferedWithChiefUnderwritersApproval,action1,action2,action3,action4,action5,Medical_Flag,NML,Requirement,DRC_Lapse_Propensity,DRC_Output,DRC_OP_DateTime";
        String sColumnValue=""; 
        String inputXML;
        String outputXML;
        String mainCode;
        JSONObject respobj = new JSONObject(response);
        String Application_No = respobj.getJSONObject("ResponseData").getString("application_No");
        System.out.println("Application_No:"+Application_No);
        String Contract_No = "";
        if(!respobj.getJSONObject("ResponseData").get("contract_No").equals(null))
        {
            Contract_No = respobj.getJSONObject("ResponseData").getString("contract_No");
        }
        
        System.out.println("Contract_No:"+Contract_No);
        String Life_Assured_Gender = respobj.getJSONObject("ResponseData").getString("life_Assured_Gender");
        System.out.println("Life_Assured_Gender:"+Life_Assured_Gender);
        String Life_Assured_Age = String.valueOf(respobj.getJSONObject("ResponseData").getDouble("life_Assured_Age"));
        System.out.println("Life_Assured_Age:"+Life_Assured_Age);
        String Life_Assured_Annual_Income = String.valueOf(respobj.getJSONObject("ResponseData").getDouble("life_Assured_Annual_Income"));
        System.out.println("Life_Assured_Annual_Income:"+Life_Assured_Annual_Income);
        String Life_Assured_Marital_Status = respobj.getJSONObject("ResponseData").getString("life_Assured_Marital_Status");
        System.out.println("Life_Assured_Marital_Status:"+Life_Assured_Marital_Status);
        String Product_Name = respobj.getJSONObject("ResponseData").getString("product_Name");
        System.out.println("Product_Name:"+Product_Name);
        String Base_Plan_Sum_Assured = String.valueOf(respobj.getJSONObject("ResponseData").getDouble("base_Plan_Sum_Assured"));
        System.out.println("Base_Plan_Sum_Assured:"+Base_Plan_Sum_Assured);
        String Premium_Amount = String.valueOf(respobj.getJSONObject("ResponseData").getDouble("annual_Premium"));
        System.out.println("Premium_Amount:"+Premium_Amount);
        String Billing_Frequency = respobj.getJSONObject("ResponseData").getString("billing_Frequency");
        System.out.println("Billing_Frequency:"+Billing_Frequency);
        String Sum_Under_Consideration = String.valueOf(respobj.getJSONObject("ResponseData").getDouble("sum_Under_Consideration"));
        System.out.println("Sum_Under_Consideration:"+Sum_Under_Consideration);
        String DRC_Life_Assured_Age_Category = respobj.getJSONObject("ResponseData").getString("drC_Life_Assured_Age_Category");
        System.out.println("DRC_Life_Assured_Age_Category:"+DRC_Life_Assured_Age_Category);
        String DRC_Life_Assured_Annual_Income_Category = respobj.getJSONObject("ResponseData").getString("drC_Life_Assured_Annual_Income_Category");
        System.out.println("DRC_Life_Assured_Annual_Income_Category:"+DRC_Life_Assured_Annual_Income_Category);
        String DRC_Life_Assured_Occupation_Category = respobj.getJSONObject("ResponseData").getString("drC_Life_Assured_Occupation_Category");
        System.out.println("DRC_Life_Assured_Occupation_Category:"+DRC_Life_Assured_Occupation_Category);
        String DRC_Life_Assured_Qualification_Category = respobj.getJSONObject("ResponseData").getString("drC_Life_Assured_Qualification_Category");
        System.out.println("DRC_Life_Assured_Qualification_Category:"+DRC_Life_Assured_Qualification_Category);
        String DRC_Life_Assured_AgeProof_Category = respobj.getJSONObject("ResponseData").getString("drC_Life_Assured_Age_Proof_Category");
        System.out.println("DRC_Life_Assured_AgeProof_Category:"+DRC_Life_Assured_AgeProof_Category);
        String DRC_Life_Assured_Pincode_Category = respobj.getJSONObject("ResponseData").getString("drC_Life_Assured_Pincode_Category");
        System.out.println("DRC_Life_Assured_Pincode_Category:"+DRC_Life_Assured_Pincode_Category);
        String DRC_Product_Code_Category = respobj.getJSONObject("ResponseData").getString("drC_Product_Code_Category");
        System.out.println("DRC_Product_Code_Category:"+DRC_Product_Code_Category);
        String DRC_Base_Plan_Sum_Assured_Category;
        if(!respobj.getJSONObject("ResponseData").get("drC_Base_Plan_Sum_Assured_Category").equals(null))
        {
            DRC_Base_Plan_Sum_Assured_Category = respobj.getJSONObject("ResponseData").getString("drC_Base_Plan_Sum_Assured_Category");
            System.out.println("DRC_Base_Plan_Sum_Assured_Category:"+DRC_Base_Plan_Sum_Assured_Category);
        }
        else
        {
            DRC_Base_Plan_Sum_Assured_Category = null;
            System.out.println("DRC_Base_Plan_Sum_Assured_Category:"+DRC_Base_Plan_Sum_Assured_Category);
        }
        String DRC_Annual_Premium_Category;
        if(!respobj.getJSONObject("ResponseData").get("drC_Annual_Premium_Category").equals(null))
        {
            DRC_Annual_Premium_Category = respobj.getJSONObject("ResponseData").getString("drC_Annual_Premium_Category");
            System.out.println("DRC_Annual_Premium_Category:"+DRC_Annual_Premium_Category);
        }
        else
        {
            DRC_Annual_Premium_Category = null;
            System.out.println("DRC_Annual_Premium_Category:"+DRC_Annual_Premium_Category);
        }
        String DRC_Policy_Term_Category;
        if(!respobj.getJSONObject("ResponseData").get("drC_Policy_Term_Category").equals(null))
        {
            DRC_Policy_Term_Category = respobj.getJSONObject("ResponseData").getString("drC_Policy_Term_Category");
            System.out.println("drC_Policy_Term_Category:"+DRC_Policy_Term_Category);
        }
        else
        {
            DRC_Policy_Term_Category = null;
            System.out.println("DRC_Policy_Term_Category:"+DRC_Policy_Term_Category);
        }
        String DRC_Premium_Term_Category;
        if(!respobj.getJSONObject("ResponseData").get("drC_Premium_Payment_Term_Category").equals(null))
        {
            DRC_Premium_Term_Category = respobj.getJSONObject("ResponseData").getString("drC_Premium_Payment_Term_Category");
            System.out.println("DRC_Premium_Term_Category:"+DRC_Premium_Term_Category);
        }
        else
        {
            DRC_Premium_Term_Category = null;
            System.out.println("DRC_Premium_Term_Category:"+DRC_Premium_Term_Category);
        }
        String DRC_SUD_Channel_Category;
        if(!respobj.getJSONObject("ResponseData").get("drC_Sales_Channel_Category").equals(null))
        {
            DRC_SUD_Channel_Category = respobj.getJSONObject("ResponseData").getString("drC_Sales_Channel_Category");
            System.out.println("DRC_SUD_Channel_Category:"+DRC_SUD_Channel_Category);
        }
        else
        {
            DRC_SUD_Channel_Category = null;
            System.out.println("DRC_SUD_Channel_Category:"+DRC_SUD_Channel_Category);
        }
        String drC_Temp1_Category,drC_Temp2_Category,drC_Temp3_Category,drC_Temp4_Category,drC_Temp5_Category;
        //respobj.getJSONObject("ResponseData").get("drC_Temp1_Category");
        if(!respobj.getJSONObject("ResponseData").get("drC_Temp1_Category").equals(null))
        {
            drC_Temp1_Category = String.valueOf(respobj.getJSONObject("ResponseData").getString("drC_Temp1_Category"));
            System.out.println("drC_Temp1_Category:"+drC_Temp1_Category);
        }
        else
        {
            drC_Temp1_Category = null;
            System.out.println("drC_Temp1_Category:"+drC_Temp1_Category);
        }
        if(!respobj.getJSONObject("ResponseData").get("drC_Temp2_Category").equals(null))
        {
            drC_Temp2_Category = String.valueOf(respobj.getJSONObject("ResponseData").getString("drC_Temp2_Category"));
            System.out.println("drC_Temp2_Category:"+drC_Temp2_Category);
        }
        else
        {
            drC_Temp2_Category = null;
            System.out.println("drC_Temp2_Category:"+drC_Temp2_Category);
        }
        if(!respobj.getJSONObject("ResponseData").get("drC_Temp3_Category").equals(null))
        {
            drC_Temp3_Category = String.valueOf(respobj.getJSONObject("ResponseData").getString("drC_Temp3_Category"));
            System.out.println("drC_Temp3_Category:"+drC_Temp3_Category);
        }
        else
        {
            drC_Temp3_Category = null;
            System.out.println("drC_Temp3_Category:"+drC_Temp3_Category);
        }
        if(!respobj.getJSONObject("ResponseData").get("drC_Temp4_Category").equals(null))
        {
            drC_Temp4_Category = String.valueOf(respobj.getJSONObject("ResponseData").getDouble("drC_Temp4_Category"));
            System.out.println("drC_Temp4_Category:"+drC_Temp4_Category);
        }
        else
        {
            drC_Temp4_Category = null;
            System.out.println("drC_Temp4_Category:"+drC_Temp4_Category);
        }
        if(!respobj.getJSONObject("ResponseData").get("drC_Temp5_Category").equals(null))
        {
            drC_Temp5_Category = String.valueOf(respobj.getJSONObject("ResponseData").getDouble("drC_Temp5_Category"));
            System.out.println("drC_Temp5_Category:"+drC_Temp5_Category);
        }
        else
        {
            drC_Temp5_Category = null;
            System.out.println("drC_Temp5_Category:"+drC_Temp5_Category);
        }
        /*String drC_Temp2_Category = respobj.getJSONObject("ResponseData").getString("drC_Temp2_Category");
        System.out.println("drC_Temp2_Category:"+drC_Temp2_Category);
        String drC_Temp3_Category = respobj.getJSONObject("ResponseData").getString("drC_Temp3_Category");
        System.out.println("drC_Temp3_Category:"+drC_Temp3_Category);
        String drC_Temp4_Category = respobj.getJSONObject("ResponseData").getString("drC_Temp4_Category");
        System.out.println("drC_Temp4_Category:"+drC_Temp4_Category);
        String drC_Temp5_Category = respobj.getJSONObject("ResponseData").getString("drC_Temp5_Category");
        System.out.println("drC_Temp5_Category:"+drC_Temp5_Category);*/
        String DRC_Category = respobj.getJSONObject("ResponseData").getString("drC_Category");
        System.out.println("DRC_Category:"+DRC_Category);
        String Income_Document_Submission,TeleVerification,Discreet_Risk_Check,Detailed_Sales_Report,CoverToBeOfferedWithChiefUnderwritersApproval;
        if(!respobj.getJSONObject("ResponseData").get("action1").equals(null))
        {
            Income_Document_Submission = respobj.getJSONObject("ResponseData").getString("action1");
            System.out.println("Income_Document_Submission:"+Income_Document_Submission);
        }
        else
        {
            Income_Document_Submission = null;
            System.out.println("Income_Document_Submission:"+Income_Document_Submission);
        }
        if(!respobj.getJSONObject("ResponseData").get("action2").equals(null))
        {
            TeleVerification = respobj.getJSONObject("ResponseData").getString("action2");
            System.out.println("TeleVerification:"+TeleVerification);
        }
        else
        {
            TeleVerification = null;
            System.out.println("TeleVerification:"+TeleVerification);
        }
        if(!respobj.getJSONObject("ResponseData").get("action3").equals(null))
        {
            Discreet_Risk_Check = respobj.getJSONObject("ResponseData").getString("action3");
            System.out.println("Discreet_Risk_Check:"+Discreet_Risk_Check);
        }
        else
        {
            Discreet_Risk_Check = null;
            System.out.println("Discreet_Risk_Check:"+Discreet_Risk_Check);
        }
        if(!respobj.getJSONObject("ResponseData").get("action4").equals(null))
        {
            Detailed_Sales_Report = respobj.getJSONObject("ResponseData").getString("action4");
            System.out.println("Detailed_Sales_Report:"+Detailed_Sales_Report);
        }
        else
        {
            Detailed_Sales_Report = null;
            System.out.println("Detailed_Sales_Report:"+Detailed_Sales_Report);
        }
        if(!respobj.getJSONObject("ResponseData").get("action5").equals(null))
        {
            CoverToBeOfferedWithChiefUnderwritersApproval = respobj.getJSONObject("ResponseData").getString("action5");
            System.out.println("CoverToBeOfferedWithChiefUnderwritersApproval:"+CoverToBeOfferedWithChiefUnderwritersApproval);
        }
        else
        {
            CoverToBeOfferedWithChiefUnderwritersApproval = null;
            System.out.println("CoverToBeOfferedWithChiefUnderwritersApproval:"+CoverToBeOfferedWithChiefUnderwritersApproval);
        }
        String action6,action7,action8,action9,action10,Medical_Flag,NML;
        if(!respobj.getJSONObject("ResponseData").get("action6").equals(null))
        {
            action6 = respobj.getJSONObject("ResponseData").getString("action6");
            System.out.println("action6:"+action6);
        }
        else
        {
            action6 = null;
            System.out.println("action6:"+action6);
        }
        if(!respobj.getJSONObject("ResponseData").get("action7").equals(null))
        {
            action7 = respobj.getJSONObject("ResponseData").getString("action7");
            System.out.println("action7:"+action7);
        }
        else
        {
            action7 = null;
            System.out.println("action7:"+action7);
        }
        if(!respobj.getJSONObject("ResponseData").get("action8").equals(null))
        {
            action8 = respobj.getJSONObject("ResponseData").getString("action8");
            System.out.println("action8:"+action8);
        }
        else
        {
            action8 = null;
            System.out.println("action8:"+action8);
        }
        if(!respobj.getJSONObject("ResponseData").get("action9").equals(null))
        {
            action9 = respobj.getJSONObject("ResponseData").getString("action9");
            System.out.println("action6:"+action9);
        }
        else
        {
            action9 = null;
            System.out.println("action9:"+action9);
        }
        if(!respobj.getJSONObject("ResponseData").get("action10").equals(null))
        {
            action10 = respobj.getJSONObject("ResponseData").getString("action10");
            System.out.println("action10:"+action10);
        }
        else
        {
            action10 = null;
            System.out.println("action10:"+action10);
        }
        if(!respobj.getJSONObject("ResponseData").get("medical_Flag").equals(null))
        {
            Medical_Flag = respobj.getJSONObject("ResponseData").getString("medical_Flag");
            System.out.println("Medical_Flag:"+Medical_Flag);
        }
        else
        {
            Medical_Flag = null;
            System.out.println("Medical_Flag:"+Medical_Flag);
        }
        if(!respobj.getJSONObject("ResponseData").get("nml").equals(null))
        {
            //respobj.getJSONObject("ResponseData").getLong("nml");
            //respobj.getJSONObject("ResponseData").getInt("nml");
            NML = String.valueOf(respobj.getJSONObject("ResponseData").getLong("nml"));
            System.out.println("NML:"+NML);
        }
        else
        {
            NML = null;
            System.out.println("NML:"+NML);
        }
        String Requirement;
        if(!respobj.getJSONObject("ResponseData").get("requirement").equals(null))
        {
            Requirement = respobj.getJSONObject("ResponseData").getString("requirement");
            System.out.println("Requirement:"+Requirement);
        }
        else
        {
            Requirement = null;
            System.out.println("Requirement:"+Requirement);
        }
        String DRC_Lapse_Propensity;
        if(!respobj.getJSONObject("ResponseData").get("drC_Lapse_Propensity").equals(null))
        {
            DRC_Lapse_Propensity = String.valueOf(respobj.getJSONObject("ResponseData").getDouble("drC_Lapse_Propensity"));
            System.out.println("DRC_Lapse_Propensity:"+DRC_Lapse_Propensity);
        }
        else
        {
            DRC_Lapse_Propensity = null;
            System.out.println("DRC_Lapse_Propensity:"+DRC_Lapse_Propensity);
        }
        sColumnValue = "'"+wiid+"','"+Application_No+"','"+Contract_No+"','"+Life_Assured_Gender+"',"
                + "'"+Life_Assured_Age+"','"+Life_Assured_Annual_Income+"','"+Life_Assured_Marital_Status+"',"
                + "'"+Product_Name+"','"+Base_Plan_Sum_Assured+"','"+Premium_Amount+"','"+Billing_Frequency+"',"
                + "'"+Sum_Under_Consideration+"','"+DRC_Life_Assured_Age_Category+"','"+DRC_Life_Assured_Annual_Income_Category+"',"
                + "'"+DRC_Life_Assured_Occupation_Category+"','"+DRC_Life_Assured_Qualification_Category+"','"+DRC_Life_Assured_AgeProof_Category+"',"
                + "'"+DRC_Life_Assured_Pincode_Category+"','"+DRC_Product_Code_Category+"','"+DRC_Base_Plan_Sum_Assured_Category+"',"
                + "'"+DRC_Annual_Premium_Category+"','"+DRC_Policy_Term_Category+"','"+DRC_Premium_Term_Category+"','"+DRC_SUD_Channel_Category+"',"
                + "'"+drC_Temp1_Category+"','"+drC_Temp2_Category + "','" + drC_Temp3_Category+"',"
                + "'"+drC_Temp4_Category+"','"+drC_Temp5_Category + "',"
                + "'"+DRC_Category+"','"+Income_Document_Submission+"','"+TeleVerification+"',"
                + "'"+Discreet_Risk_Check+"','"+Detailed_Sales_Report+"','"+CoverToBeOfferedWithChiefUnderwritersApproval+"',"
                + "'"+action6+"','"+action7 + "','" + action8+"',"
                + "'"+action9+"','"+action10 + "',"
                + "'"+Medical_Flag+"','"+NML+"','"+Requirement+"',"
                + "'"+DRC_Lapse_Propensity+"','"+SOAPResponse_xml+"',GETDATE()";
        inputXML = XMLGen.WFInsert_new(DRCWebservice_UtilityHelper.cabinetName,DRCWebservice_UtilityHelper.sessionID, sDRCTableName, sColumnName, sColumnValue,DRCWebservice_UtilityHelper.processDefID);//added last parameter by Ashwini on 03-10-2023 wfinsert defination changed 
        log.WriteSuccessLog("WFInsert_new input xml is :"+inputXML,"SuccessLog");
        System.out.println("WFInsert_new input xml is :"+inputXML);
        outputXML = objWF.execute(inputXML);
        log.WriteSuccessLog("WFInsert_new output xml is :"+outputXML,"SuccessLog");
        xmlParser.setInputXML(outputXML);
        System.out.println("WFInsert_new outputXML----"+outputXML);
        mainCode = xmlParser.getValueOf("MainCode");
        if (mainCode.equals("0")) 
        {
                log.WriteSuccessLog("Values inserted in DRC table Success...","SuccessLog");
                System.out.println("Values inserted in DRC table Success...");
        }
        else
        {
                log.WriteSuccessLog("Values inserted in DRC table Failed...","SuccessLog");
                System.out.println("Values inserted in DRC table Failed...");
        }
        if(DRC_Annual_Premium_Category != null)
        {
        DRC_Annual_Premium_Category = DRC_Annual_Premium_Category.replace("<", "&lt;");
        }
        String sColumnValue_ext="DRC_Category='"+DRC_Category+"',Tele_Verification='"+TeleVerification+"',Discreet_Risk_Check='"+Discreet_Risk_Check+"',Cover_Offered_UW_MA='"+CoverToBeOfferedWithChiefUnderwritersApproval+"',NML='"+NML+"',Med_Flag='"+Medical_Flag+"',DRC_Annual_Premium_Category='"+DRC_Annual_Premium_Category+"',BO_Error='N',DRC_ERROR_CODE='1'";
        //UpdateExternalTable(sColumnValue_ext,wiid,workitemid);
        String inColumns="DRC_Category,Tele_Verification,Discreet_Risk_Check,Cover_Offered_UW_MA,NML,Med_Flag,DRC_Annual_Premium_Category,BO_Error,DRC_ERROR_CODE";
        String inValues = "'"+DRC_Category+"','"+TeleVerification+"','"+Discreet_Risk_Check+"','"+CoverToBeOfferedWithChiefUnderwritersApproval+"','"+NML+"','"+Medical_Flag+"','"+DRC_Annual_Premium_Category+"','N','1'";
        //UpdateExternalTable(inValues,wiid,workitemid,inColumns);
        UpdateExternalTable(inValues,wiid,workitemid,inColumns);
    }
    public static String WFUpdate_Input(String cabinetName,
          String sessionID,
          String processDefID,
         String tableName, String columns, String values, String where) {
	  	return "<?xml version=\"1.0\"?>"+
	  	"<WFUpdate_Input><Option>WFUpdate_new</Option>"+
	  	"<TableName>"+tableName+"</TableName>"+
	  	"<ColName>"+columns+"</ColName>"+
	  	"<Values>"+values+"</Values>"+
	  	"<ProcessDefId>"+processDefID+"</ProcessDefId>\n"+
	  	"<WhereClause>"+where+"</WhereClause>"+
	  	"<EngineName>"+cabinetName+"</EngineName>"+
	  	"<SessionId>"+sessionID+"</SessionId>"+
	  	"</WFUpdate_Input>";
  		}
    public void UpdateExternalTable(String values,String wiid,String workitemid, String columns)
    {
        String inputXML_ext;
        String outputXML_ext;
        String mainCode;
        String EXT_TABLE = "NG_SUD_NBP_EXT_TABLE";
        String where="WI_NAME='"+wiid+"'";
        String queryToUpdateExtTable="UPDATE "+EXT_TABLE+" SET "+values+" WHERE WI_NAME='"+wiid+"'";
        inputXML_ext=WFUpdate_Input(DRCWebservice_UtilityHelper.cabinetName,DRCWebservice_UtilityHelper.sessionID,"2",EXT_TABLE,columns,values,where);
        //inputXML_ext = XMLGen.IGSetData(DRCWebservice_UtilityHelper.cabinetName,queryToUpdateExtTable);
        log.WriteSuccessLog("Update Core Status in Route Table input xml is ******* :"+inputXML_ext,"SuccessLog");
        System.out.println("Update Core Status in Route Table input xml is ******* :"+inputXML_ext);
        outputXML_ext = objWF.execute(inputXML_ext);
        log.WriteSuccessLog("Update Core Status in Route Table output xml is :"+outputXML_ext,"SuccessLog");
        xmlParser.setInputXML(outputXML_ext);
        System.out.println("Update Core Status in Route Table outputXML----"+outputXML_ext);
        mainCode = xmlParser.getValueOf("MainCode");

        if (mainCode.equals("0")) 
        {
                log.WriteSuccessLog("DRC Columns Values updated into Route Table Success...","SuccessLog");
                System.out.println("DRC Columns Values updated into Route Table Success...");
                CompleteWI(wiid,workitemid);
        }
        else
        {
                log.WriteSuccessLog("DRC Columns Values updated into Route Table Failed...","SuccessLog");
                System.out.println("DRC Columns Values updated into Route Table Failed...");
        }       
    }
    public void UpdateWFInstTable(String values,String wiid,String workitemid)
    {
        String inputXML_ext;
        String outputXML_ext;
        String mainCode;
        String EXT_TABLE = "WFINSTRUMENTTABLE";
        String queryToUpdateExtTable="UPDATE "+EXT_TABLE+" SET "+values+" WHERE ProcessInstanceID='"+wiid+"'";
        inputXML_ext = XMLGen.IGSetData(DRCWebservice_UtilityHelper.cabinetName,queryToUpdateExtTable);
        log.WriteSuccessLog("Update Code in WFINSTRUMENTTABLE Table input xml is ******* :"+inputXML_ext,"SuccessLog");
        System.out.println("Update Code in WFINSTRUMENTTABLE Table input xml is ******* :"+inputXML_ext);
        outputXML_ext = objWF.execute(inputXML_ext);
        log.WriteSuccessLog("Update Code in WFINSTRUMENTTABLE Table output xml is :"+outputXML_ext,"SuccessLog");
        xmlParser.setInputXML(outputXML_ext);
        System.out.println("Update Code in WFINSTRUMENTTABLE Table outputXML----"+outputXML_ext);
        mainCode = xmlParser.getValueOf("MainCode");

        if (mainCode.equals("0")) 
        {
                log.WriteSuccessLog("WFINSTRUMENTTABLE code Value updated into Route Table Success...","SuccessLog");
                System.out.println("WFINSTRUMENTTABLE code Values updated into Route Table Success...");
                CompleteWI(wiid,workitemid);
        }
        else
        {
                log.WriteSuccessLog("WFINSTRUMENTTABLE Columns Values updated into WFINSTRUMENTTABLE Table Failed...","SuccessLog");
                System.out.println("WFINSTRUMENTTABLE Columns Values updated into WFINSTRUMENTTABLE Table Failed...");
        }       
    }
    public void CompleteWI(String sParentWIName,String sWorkItemId)
    {
        String mainCode="";
        String inputXML = XMLGen.workItemCompleteCall( DRCWebservice_UtilityHelper.sessionID,DRCWebservice_UtilityHelper.cabinetName,sParentWIName,sWorkItemId);
        log.WriteSuccessLog("workItemCompleteCall input xml is :"+inputXML,"SuccessLog");
        System.out.println("workItemCompleteCall input xml is :"+inputXML);
        String outputXML = objWF.execute(inputXML);
        log.WriteSuccessLog("workItemCompleteCall output xml is :"+outputXML,"SuccessLog");
        xmlParser.setInputXML(outputXML);
        System.out.println("workItemCompleteCall outputXML----"+outputXML);
        mainCode = xmlParser.getValueOf("MainCode");
        if (mainCode.equals("0")) 
        {
                log.WriteSuccessLog("Work Item has been successfully moved to next step...","SuccessLog");
                System.out.println("Work Item has been successfully moved to next step...");
        }
        else
        {
                log.WriteSuccessLog("Work Item is Failed to move to next step...","SuccessLog");
                System.out.println("Work Item is Failed to move to next step...");
        }
    }
    public String executeWS_json(String jsonInput,String endPoint)
    {
        HttpURLConnection urlConn = null;
        BufferedReader reader = null;
        OutputStream ouputStream = null;
        InputStream is = null;
        String tmpStr = null;
        String output = null;
        System.out.println("jsonInput:::::"+jsonInput);
        System.out.println("endPoint::::"+endPoint);
        try {
            URL urlObj = new URL(endPoint);
            System.out.println("11111111111111");
            log.WriteSuccessLog("1111111111111111","SuccessLog");
            urlConn = (HttpURLConnection) urlObj.openConnection();
            System.out.println("22222222222");
            log.WriteSuccessLog("2222222222222","SuccessLog");
            urlConn.setDoOutput(true);
            System.out.println("3333333333333");
            log.WriteSuccessLog("3333333333333","SuccessLog");
            urlConn.setRequestMethod("POST");
            System.out.println("44444444444444");
            log.WriteSuccessLog("444444444444","SuccessLog");
            urlConn.setRequestProperty("Content-Type", "application/json");
            System.out.println("5555555555555555");
            log.WriteSuccessLog("555555555555555","SuccessLog");
            urlConn.setConnectTimeout(500000);
            System.out.println("66666666666666::::::::::"+urlConn.getConnectTimeout());
            log.WriteSuccessLog("6666666666666666666:"+urlConn.getConnectTimeout(),"SuccessLog");
            urlConn.setReadTimeout(500000);
            System.out.println("7777777777777777::::::::"+urlConn.getReadTimeout());
            log.WriteSuccessLog("777777777777:::::"+urlConn.getReadTimeout(),"SuccessLog");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            System.out.println("88888888888888");
            log.WriteSuccessLog("888888888888888888","SuccessLog");
            // send json input request
            ouputStream = urlConn.getOutputStream();
            ouputStream.write(jsonInput.getBytes());
            ouputStream.flush();
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.err.println("Unable to connect to the URL...");
                //return;
            }
            else
            {
                System.out.println("Connected to the server...");
            }
            is = urlConn.getInputStream();
            reader = new BufferedReader(new InputStreamReader((is)));
            
            while((tmpStr = reader.readLine()) != null){
                //System.out.println("tmpStr:1:"+tmpStr);
                output = output + tmpStr;
            }
            output = output.substring(4, output.length());
            //System.out.println("tmpStr:2::::"+output);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                //System.out.println("tmpStr:3::::"+tmpStr);
                if(reader != null) 
                {
                    reader.close();
                    System.out.println("reader close");
                    log.WriteSuccessLog("reader close","SuccessLog");
                }
                if(is != null) 
                {
                    is.close();
                    System.out.println("is close");
                    log.WriteSuccessLog("is close","SuccessLog");
                }
                if(ouputStream != null) 
                {
                    ouputStream.close();
                    System.out.println("ouputStream close");
                    log.WriteSuccessLog("ouputStream close","SuccessLog");
                }
                if(urlConn != null) 
                {
                    urlConn.disconnect();
                    System.out.println("disconnect urlConn");
                    log.WriteSuccessLog("disconnect urlConn","SuccessLog");
                }
            } catch(Exception ex){
                 ex.printStackTrace();
            }
            return output;
        }
 }
}
