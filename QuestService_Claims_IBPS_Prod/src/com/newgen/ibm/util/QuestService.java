/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newgen.ibm.util;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.event.ComponentEvent;
import com.newgen.omniforms.event.FormEvent;
import com.newgen.omniforms.excp.CustomExceptionHandler;
import com.newgen.omniforms.listener.FormListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import com.newgen.ibm.commons.QuestService_UtilityHelper;
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
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import net.sf.json.JSONSerializer;
import org.json.*;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author sudadmin
 */
public class QuestService implements Runnable 
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
    
    private String Policy_Number = "";
    private String Proposal_Number = "";
    private String Query_Type = "";
    private String DoP_DoC = "";
    private String Sum_Assured = "";
    private String LA_First_Name = "";
    private String LA_Middle_Name = "";
    private String LA_Last_Name = "";
    private String LA_DoB = "";
    private String LA_Gender = "";
    private String LA_Current_Address = "";
    private String LA_Permanent_Address = "";
    private String LA_Pin_Code = "";
    private String LA_PAN = "";
    private String LA_Aadhar = "";
    private String LA_Ckyc = "";
    private String LA_Passport = "";
    private String LA_DL = "";
    private String la_Voter_ID = "";
    private String LA_Phone_Number_1 = "";
    private String LA_Phone_Number_2 = "";
    private String LA_Email_1 = "";
    private String LA_Email_2 = "";
    private String Proposer_First_Name = "";
    private String Proposer_Middle_Name  = "";
    private String Proposer_Last_Name = "";
    private String Proposer_DOB = "";
    private String Proposer_Gender = "";
    private String Proposer_Pin_Code = "";
    private String Proposer_PAN = "";
    private String Proposer_Aadhar = "";
    private String Proposer_Ckyc = "";
    private String Proposer_Passport = "";
    private String Proposer_DL = "";
    private String Proposer_Voter_ID = "";
    private String Proposer_Phone_Number_1 = "";
    private String Proposer_Phone_Number_2 = "";
    private String Proposer_Email_1 = "";
    private String Proposer_Email_2 = "";
    private String Date_of_Death = "";
    private String Nominee_First_Name = "";
    private String Nominee_Last_Name = "";
    private String Nominee_Gender = "";
    private String Nominee_DOB = "";
    private String Nominee_Relationship = "";
    private String BNYRLN = "";
    private String Nominee_PAN = "";
    private String Nominee_Aadhar = "";
    private String Nominee_Phone_Number = "";
    private String Nominee_Email = "";
    private String Intermediary_First_Name = "";
    private String Intermediary_Middle_Name  = "";
    private String Intermediary_Last_Name = "";
    private String Intermediary_DoB = "";
    private String Intermediary_PAN = "";
    private String Intermediary_Aadhar = "";
    private String Intermediary_Passport = "";
    private String Intermediary_DL = "";
    private String Intermediary_Voter_Id = "";
    private String Intermediary_Phone_Number = "";
    private String Intermediary_Email = "";
    //added by amit on 17-08-21 start
    private String Company_Number = "";
    private String Product_Type = "";
    private String Product_UIN = "";
    private String Annual_Income = "";
    private String Cause_Of_Death = "";
     //added by amit on 17-08-21 End
    //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
    public QuestService(String threadName) 
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
            //elog.WriteErrorData("Error in starting ImageDownload Thread...."+e, QuestService_UtilityHelper.ErrorLogPath, "ErrorLog");
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
            System.getProperty("file.separator") + "Quest_WebService_Claim.ini"));
            log.WriteSuccessLog("INI Path:"+System.getProperty("user.dir") + System.getProperty("file.separator") + "Quest_WebService_Claim.ini","SuccessLog");
            sSleepTime=ini.getProperty("SleepTime");
            sUtilityStartTime=ini.getProperty("UtilityStartTime");  
            sQueueID = ini.getProperty("QueueID_Packet");
            sProcessName=ini.getProperty("ProcessName");
        }
        catch(Exception e) 
        {
                    log.WriteSuccessLog("Error in starting ImageDownload Thread...."+e, "SuccessLog");
	            //elog.WriteErrorData("Error in reading ImageDownload ini file.."+e, QuestService_UtilityHelper.ErrorLogPath, "ErrorLog");
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
                    //elog.WriteErrorData("An interrupted exception has occurred...."+ex, QuestService_UtilityHelper.ErrorLogPath, "ErrorLog");
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
                System.out.println("ImageDownloadUtilityHelper sessionID=="+QuestService_UtilityHelper.sessionID);
                if(QuestService_UtilityHelper.sessionID.equals("null"))
                {
                    log.WriteSuccessLog("Reconnecting.","SuccessLog");
                    System.out.println("Reconnecting.");
                    comm.reconnectToWorkflow();
                    return;
                }
                log.WriteSuccessLog("sQueueID is :" + sQueueID,"SuccessLog");
                inXml = XMLGen.WFFetchInstrumentsList(QuestService_UtilityHelper.cabinetName,
                        QuestService_UtilityHelper.sessionID, "Y",
                        sQueueID, "256", "0", "", "", "0",
                        QuestService_UtilityHelper.BatchSize, "1", "A", "", "", "", "Y", "Y");
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
                String tag = splitRs.substring(startIndex, lastIndex);
                if (lastIndex < length) 
                {
                    splitRs = splitRs.substring(lastIndex, length);
                } 
                else 
                {
                    isTagsPending = false;
                }
                par2 = new XMLParser(tag);
                if (ResVal == "") 
                {
                    ResVal = par2.getValueOf("Result").substring(1,par2.getValueOf("Result").length());
                } 
                else 
                {
                    ResVal = ResVal+ "~"+ par2.getValueOf("Result").substring(1,par2.getValueOf("Result").length());
                }
            }
        } 
        else 
        {		
        }
        String TagArray[] = ResVal.split("~");
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
    
    
    public String FetchPolicyStatusDesc(String policystatus)
    {
        String inputXML_query;
        String outputXML_query;
        String mainCode_query="";
        String result="";
        try
        {
            String query = "select POLICY_STATUS_DESCRIPTION FROM  NG_SUD_QUEST_POLICY_STATUS_MASTER WHERE POLICY_STATUS_CODE ='"+policystatus+"'";
            inputXML_query = XMLGen.WFSelectTest_new(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, query, "1");
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
                    result = resultGL2[0].toString();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    public String WFCustomProcedure_CallSP(String procedureName,String paramCount, String inParam) 
    {
        return "<?xml version=\"1.0\"?>" + "<WFCustomProcedure_CallSP_Input>"
                + "<Option>WFCustomProcedure_CallSP</Option>" 
                + "<ParamCount>"+ paramCount + "</ParamCount>" 
                + "<ProcedureName>" + procedureName + "</ProcedureName>" 
                + "<EngineName>" + QuestService_UtilityHelper.cabinetName + "</EngineName>" 
                + inParam
                + "</WFCustomProcedure_CallSP_Input>";
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
                inputXML = XMLGen.WMGetWorkItem(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID,processInstanceID, workItemID);
                log.WriteSuccessLog("WMGetWorkItem input xml....."+inputXML,"SuccessLog");
                System.out.println("WMGetWorkItem inputXML xml....."+inputXML);
                outputXML = objWF.execute(inputXML);
                log.WriteSuccessLog("WMGetWorkItem output xml....."+outputXML,"SuccessLog");
                System.out.println("WMGetWorkItem output xml....."+outputXML);
                xmlParser.setInputXML(outputXML);
                mainCode = xmlParser.getValueOf("MainCode");  
                if (mainCode.equals("0")) 
                {
                    inputXML = XMLGen.WFGetWorkitemDataExt(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, processInstanceID,workItemID,sQueueID);	                    
                    log.WriteSuccessLog("WFGetWorkitemDataExt input xml is :"+inputXML,"SuccessLog");
                    System.out.println("WFGetWorkitemDataExt input xml is :"+inputXML);
                    outputXML = objWF.execute(inputXML);
                    log.WriteSuccessLog("WFGetWorkitemDataExt outputXML xml is :"+outputXML,"SuccessLog");
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
                        log.WriteSuccessLog("wiid======================"+wiid,"SuccessLog");
                        String attributes = xmlParser.getValueOf("Attributes");	
                        log.WriteSuccessLog("attributes======================"+attributes,"SuccessLog");
                        log.WriteSuccessLog("****attributes fetched*****","SuccessLog");
                        s1 =attributes.substring(attributes.indexOf("<Contract_No") , attributes.indexOf("</Contract_No>"));
                        log.WriteSuccessLog("s1======================"+s1,"SuccessLog");
                        CONTRACT_POLICY_NUMBER = s1.substring(s1.indexOf(">")+1 ,s1.length());
                        System.out.println("CONTRACT_POLICY_NUMBER======================"+CONTRACT_POLICY_NUMBER);
                        log.WriteSuccessLog("CONTRACT_POLICY_NUMBER======================"+CONTRACT_POLICY_NUMBER,"SuccessLog");
                        
                        String sParamCount = "2";
                        String sParam_Value = "<INParam1>"+CONTRACT_POLICY_NUMBER+"</INParam1>";
                        //SIT
                        //String Inputxml = WFCustomProcedure_CallSP("[dc-bui-uat01].CDCSQL.LACDC.GET_IIB_INPUT_DATA", sParamCount, sParam_Value);
                        //Prod
                        String Inputxml = WFCustomProcedure_CallSP("[MUMSQLAPP04\\SQLINT04].CDCSQL.LACDC.GET_IIB_INPUT_DATA", sParamCount, sParam_Value);
                        System.out.println("Inputxml======================"+Inputxml);
                        String outXml = objWF.execute(Inputxml);
                        System.out.println("outXml======================"+outXml);
                        xmlParser.setInputXML(outXml);
                        String maincd = xmlParser.getValueOf("Status");
                        maincd = maincd+"|a";
                        System.out.println("maincd======================"+maincd);
                        if (!(maincd.equals("")) && maincd.contains("|")) 
                        {
                            //String TagValGL[] = new String[61];
                            String resultGL[];
                            resultGL = maincd.split("\\|");
                            //TagValGL = getCallResult(outXml);
                            if(!resultGL[0].toString().equalsIgnoreCase(""))
                                {
                                    //Below code added by ghazal shah on 18-11-2022 for Fixing discrepency in IIB Quest Input : Start
                                    Policy_Number = "";Proposal_Number = "";Query_Type = "";DoP_DoC = "";Sum_Assured = "";LA_First_Name = "";LA_Middle_Name = "";LA_Last_Name = "";LA_DoB = "";LA_Gender = "";LA_Current_Address = "";LA_Permanent_Address = "";LA_Pin_Code = "";LA_PAN = "";LA_Aadhar = "";LA_Ckyc = "";LA_Passport = "";LA_DL = "";la_Voter_ID = "";LA_Phone_Number_1 = "";LA_Phone_Number_2 = "";LA_Email_1 = "";LA_Email_2 = "";
                                    Proposer_First_Name = "";Proposer_Middle_Name  = "";Proposer_Last_Name = "";Proposer_DOB = "";Proposer_Gender = "";Proposer_Pin_Code = "";Proposer_PAN = "";Proposer_Aadhar = "";Proposer_Ckyc = "";Proposer_Passport = "";Proposer_DL = "";Proposer_Voter_ID = "";Proposer_Phone_Number_1 = "";Proposer_Phone_Number_2 = "";Proposer_Email_1 = "";Proposer_Email_2 = "";Date_of_Death = "";Nominee_First_Name = "";Nominee_Last_Name = "";Nominee_Gender = "";Nominee_DOB = "";Nominee_Relationship = "";BNYRLN = "";Nominee_PAN = "";Nominee_Aadhar = "";Nominee_Phone_Number = "";Nominee_Email = "";Intermediary_First_Name = "";Intermediary_Middle_Name  = "";Intermediary_Last_Name = "";Intermediary_DoB = "";Intermediary_PAN = "";Intermediary_Aadhar = "";Intermediary_Passport = "";Intermediary_DL = "";Intermediary_Voter_Id = "";Intermediary_Phone_Number = "";Intermediary_Email = "";Company_Number = "";Product_Type = "";Product_UIN = "";Annual_Income = "";Cause_Of_Death = "";
                                    //Below code added by ghazal shah on 18-11-2022 for Fixing discrepency in IIB Quest Input : End
                                    Policy_Number=resultGL[0].toString();
                                    Proposal_Number=resultGL[1].toString();
                                    //Query_Type=resultGL[2].toString();
                                    Query_Type="2";
                                    DoP_DoC=resultGL[3].toString();
                                    Sum_Assured=resultGL[4].toString();
                                    LA_First_Name=resultGL[5].toString();
                                    LA_Middle_Name=resultGL[6].toString();
                                    LA_Last_Name=resultGL[7].toString();
                                    LA_DoB=resultGL[8].toString();
                                    LA_Gender=resultGL[9].toString();
                                    LA_Current_Address=resultGL[10].toString();
                                    LA_Permanent_Address=resultGL[11].toString();
                                    LA_Pin_Code=resultGL[12].toString();
                                    LA_PAN=resultGL[13].toString();
                                    LA_Aadhar=resultGL[14].toString();
                                    LA_Ckyc=resultGL[15].toString();
                                    LA_Passport=resultGL[16].toString();
                                    LA_DL=resultGL[17].toString();
                                    la_Voter_ID=resultGL[18].toString();
                                    LA_Phone_Number_1=resultGL[19].toString();
                                    LA_Phone_Number_2=resultGL[20].toString();
                                    LA_Email_1=resultGL[21].toString();
                                    LA_Email_2=resultGL[22].toString();
                                    Proposer_First_Name=resultGL[23].toString();
                                    Proposer_Middle_Name =resultGL[24].toString();
                                    Proposer_Last_Name=resultGL[25].toString();
                                    Proposer_DOB=resultGL[26].toString();
                                    Proposer_Gender=resultGL[27].toString();
                                    Proposer_Pin_Code=resultGL[28].toString();
                                    Proposer_PAN=resultGL[29].toString();
                                    Proposer_Aadhar=resultGL[30].toString();
                                    Proposer_Ckyc=resultGL[31].toString();
                                    Proposer_Passport=resultGL[32].toString();
                                    Proposer_DL=resultGL[33].toString();
                                    Proposer_Voter_ID=resultGL[34].toString();
                                    Proposer_Phone_Number_1=resultGL[35].toString();
                                    Proposer_Phone_Number_2=resultGL[36].toString();
                                    Proposer_Email_1=resultGL[37].toString();
                                    Proposer_Email_2=resultGL[38].toString();
                                    //Date_of_Death=resultGL[39].toString();
                                    
                                    Nominee_First_Name=resultGL[40].toString();
                                    Nominee_Last_Name=resultGL[41].toString();
                                    Nominee_Gender=resultGL[42].toString();
                                    Nominee_DOB=resultGL[43].toString();
                                    Nominee_Relationship=resultGL[44].toString();
                                    BNYRLN=resultGL[45].toString();
                                    Nominee_PAN=resultGL[46].toString();
                                    Nominee_Aadhar=resultGL[47].toString();
                                    Nominee_Phone_Number=resultGL[48].toString();
                                    Nominee_Email=resultGL[49].toString();
                                    Intermediary_First_Name=resultGL[50].toString();
                                    Intermediary_Middle_Name =resultGL[51].toString();
                                    Intermediary_Last_Name=resultGL[52].toString();
                                    Intermediary_DoB=resultGL[53].toString();
                                    Intermediary_PAN=resultGL[54].toString();
                                    Intermediary_Aadhar=resultGL[55].toString();
                                    Intermediary_Passport=resultGL[56].toString();
                                    Intermediary_DL=resultGL[57].toString();
                                    Intermediary_Voter_Id=resultGL[58].toString();
                                    Intermediary_Phone_Number=resultGL[59].toString();
                                    Intermediary_Email=resultGL[60].toString();
                                    //added by amit on 17-08-21 start
                                    Company_Number = "142";
                                    Product_Type = getProductTypeAgainstContractPolNo(Policy_Number);
                                    Product_UIN = getProductUINAgainstContractPolNo(Policy_Number);
                                    Annual_Income = getAnnualIncomeAgainstContractPolNo(Policy_Number);
                                    Cause_Of_Death =getCODAgainstContractPolNo(Policy_Number);
                                    Date_of_Death=getDateOfDeathAgainstContractPolNo(Policy_Number);
                                    //added by amit on 17-08-21 end

                                }
                            
                            System.out.println("Policy_Number is :"+Policy_Number);
                        }
                        
                        LA_Current_Address=LA_Current_Address.replaceAll(" ", "");
                        LA_Permanent_Address=LA_Permanent_Address.replaceAll(" ", "");
                        LA_Current_Address = LA_Current_Address.replaceAll("[^a-zA-Z0-9]", "");  
                        LA_Permanent_Address = LA_Permanent_Address.replaceAll("[^a-zA-Z0-9]", "");  
                        
                        /*String jsonInput = "{\n" +
                        "  \"Policy_Number\": \""+Policy_Number+"\",\n" +
                        "  \"Proposal_Number\": \""+Proposal_Number+"\",\n" +
                        "  \"Query_Type\": \""+Query_Type+"\",\n" +
                        "  \"DoP_DoC\": \""+DoP_DoC+"\",\n" +
                        "  \"Sum_Assured\": "+Sum_Assured+",\n" +
                        "  \"LA_First_Name\": \""+LA_First_Name+"\",\n" +
                        "  \"LA_Middle_Name\": \""+LA_Middle_Name+"\",\n" +
                        "  \"LA_Last_Name\": \""+LA_Last_Name+"\",\n" +
                        "  \"LA_DoB\": \""+LA_DoB+"\",\n" +
                        "  \"LA_Gender\": \""+LA_Gender+"\",\n" +
                        "  \"LA_Current_Address\": \""+LA_Current_Address+"\",\n" +
                        "  \"LA_Permanent_Address\": \""+LA_Permanent_Address+"\",\n" +
                        "  \"LA_Pin_Code\": \""+LA_Pin_Code+"\",\n" +
                        "  \"LA_PAN\": \""+LA_PAN+"\",\n" +
                        "  \"LA_Aadhar\": \""+LA_Aadhar+"\",\n" +
                        "  \"LA_Ckyc\": \""+LA_Ckyc+"\",\n" +
                        "  \"LA_Passport\": \""+LA_Passport+"\",\n" +
                        "  \"LA_DL\": \""+LA_DL+"\",\n" +
                        "  \"LA_Voter_Id\": \""+la_Voter_ID+"\",\n" +
                        "  \"LA_Phone_Number_1\": \""+LA_Phone_Number_1+"\",\n" +
                        "  \"LA_Phone_Number_2\": \""+LA_Phone_Number_2+"\",\n" +
                        "  \"LA_Email_1\": \""+LA_Email_1+"\",\n" +
                        "  \"LA_Email_2\": \""+LA_Email_2+"\",\n" +
                        "  \"Date_of_Death\": \""+Date_of_Death+"\"\n" +
                        "}";
                        */
                       String jsonInput = "{\n" +
                        "  \"Policy_Number\": \""+Policy_Number+"\",\n" +
                        "  \"Proposal_Number\": \""+Proposal_Number+"\",\n" +
                        "  \"Query_Type\": \""+Query_Type+"\",\n" +
                        "  \"DoP_DoC\": \""+DoP_DoC+"\",\n" +
                        "  \"Sum_Assured\": "+Sum_Assured+",\n" +
                        "  \"LA_First_Name\": \""+LA_First_Name+"\",\n" +
                        "  \"LA_Middle_Name\": \"\",\n" +
                        "  \"LA_Last_Name\": \"\",\n" +
                        "  \"LA_DoB\": \""+LA_DoB+"\",\n" +
                        "  \"LA_Gender\": \""+LA_Gender+"\",\n" +
                        "  \"LA_Current_Address\": \"\",\n" +
                        "  \"LA_Permanent_Address\": \"\",\n" +
                        "  \"LA_Pin_Code\": \""+LA_Pin_Code+"\",\n" +
                        "  \"LA_PAN\": \""+LA_PAN+"\",\n" +
                        "  \"LA_Aadhar\": \"\",\n" +
                        "  \"LA_Ckyc\": \"\",\n" +
                        "  \"LA_Passport\": \"\",\n" +
                        "  \"LA_DL\": \"\",\n" +
                        "  \"LA_Voter_Id\": \"\",\n" +
                        "  \"LA_Phone_Number_1\": \""+LA_Phone_Number_1+"\",\n" +
                        "  \"LA_Phone_Number_2\": \"\",\n" +
                        "  \"LA_Email_1\": \"\",\n" +
                        "  \"LA_Email_2\": \"\",\n" +
                        "  \"Date_of_Death\": \""+Date_of_Death+"\",\n" +
                        "  \"Company_Number\": \""+Company_Number+"\",\n" +//added by amit 23-08-21
                        "  \"Product_Type\": \""+Product_Type+"\",\n" +//added by amit 23-08-21
                        "  \"Product_UIN\": \""+Product_UIN+"\",\n" +//added by amit 23-08-21
                        "  \"Annual_Income\": \""+Annual_Income+"\",\n" +//added by amit 23-08-21
                        "  \"Cause_Of_Death\": \""+Cause_Of_Death+"\"\n" +//added by amit 23-08-21
                        "}";
                        System.out.println("jsonInput is :"+jsonInput);
                        log.WriteSuccessLog("jsonInput is :"+jsonInput,"SuccessLog"); 
                        try
                        {
                            SOAPResponse_xml=executeWS_json(jsonInput,QuestService_UtilityHelper.WSEndpointURL);
                            System.out.println("SOAPResponse_xml is :"+SOAPResponse_xml);
                            log.WriteSuccessLog("SOAPResponse_xml is :"+SOAPResponse_xml,"SuccessLog");                           
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        if(SOAPResponse_xml!=null && !SOAPResponse_xml.trim().equals(""))
                        {
                            //System.out.println("SOAPResponse_xml:"+SOAPResponse_xml);
                            String success_op=SOAPResponse_xml;
                            JSONObject obj = new JSONObject(SOAPResponse_xml);
                            JSONObject obj1 = new JSONObject(success_op);
                            String code = obj1.getString("Code");
                            String trnid = obj1.getString("TransactionID");
                            System.out.println("Code:"+code);
                            if(code.equalsIgnoreCase("0"))
                            {
                                //String msg = obj.getJSONObject("DRCalculateResult").getString("Message");
                                //System.out.println("msg:"+msg);
                                
                            }
                            else if(code.equalsIgnoreCase("1"))
                            {
                                List<String> list_polno = new ArrayList<String>();
                                List<String> list_QuestDBNo = new ArrayList<String>();
                                List<String> list_Input_Entity_First_Name = new ArrayList<String>();
                                List<String> list_Input_Entity_Last_Name = new ArrayList<String>();
                                List<String> list_Input_EntityRole = new ArrayList<String>();
                                List<String> list_Input_Entity_DoB = new ArrayList<String>();
                                List<String> list_Input_Entity_Gender = new ArrayList<String>();
                                List<String> list_Input_Matching_Parameter = new ArrayList<String>();
                                List<String> list_Quest_Matching_Parameter_Value = new ArrayList<String>();
                                List<String> list_Quest_EntityRole = new ArrayList<String>();
                                List<String> list_Quest_DoP_DoC = new ArrayList<String>();
                                List<String> list_Quest_Sum_Assured = new ArrayList<String>();
                                List<String> list_Quest_Policy_Status = new ArrayList<String>();
                                List<String> list_Quest_Policy_Status_Desc = new ArrayList<String>();
                                List<String> list_Quest_Date_of_Exit = new ArrayList<String>();
                                List<String> list_Quest_Date_of_Death = new ArrayList<String>();
                                List<String> list_Quest_Cause_of_Death = new ArrayList<String>();
                                List<String> list_Quest_Record_last_updated = new ArrayList<String>();
                                List<String> list_Quest_Entity_Caution_Status = new ArrayList<String>();
                                List<String> list_Quest_Intermediary_Caution_Status = new ArrayList<String>();
                                List<String> list_RedFlaggedPinCode = new ArrayList<String>();
                                List<String> list_RedFlaggedDistrict = new ArrayList<String>();
                                List<String> list_Quest_Company_Number = new ArrayList<String>();
                                List<String> list_Is_Negative_Match = new ArrayList<String>();
                                List<String> list_Product_Type = new ArrayList<String>(); //added by amit 17-08-21
                                List<String> list_Linked_Non_linked = new ArrayList<String>(); //added by amit 17-08-21
                                List<String> list_Medical_Non_Medical = new ArrayList<String>(); //added by amit 17-08-21
                                List<String> list_Whether_Standard_Life = new ArrayList<String>(); //added by amit 17-08-21
                                List<String> list_Reason_for_Decline = new ArrayList<String>(); //added by amit 17-08-21
                                List<String> list_Reason_for_Postpone = new ArrayList<String>(); //added by amit 17-08-21
                                List<String> list_Reason_for_Repudiation = new ArrayList<String>(); //added by amit 17-08-21
                                JSONArray array = obj.getJSONArray("Data");
                                System.out.println("----1-----");
                                String randomno = GenerateRandomNumber(processInstanceID);
                                //String Rno = processInstanceID.substring(10, 17);
                                UpdateWI_ID_IN_QuestTable(processInstanceID);
                                for(int i = 0 ; i < array.length() ; i++)
                                {
                                    System.out.println("----i-----"+i);
                                    list_polno.add(array.getJSONObject(i).getString("Input_Proposal_Policy_No"));
                                    list_QuestDBNo.add(array.getJSONObject(i).getString("QuestDBNo"));
                                    /*list_Input_Entity_First_Name.add(array.getJSONObject(i).getString("Input_Entity_First_Name"));
                                    list_Input_Entity_Last_Name.add(array.getJSONObject(i).getString("Input_Entity_Last_Name"));
                                    list_Input_EntityRole.add(array.getJSONObject(i).getString("Input_EntityRole"));
                                    list_Input_Entity_DoB.add(array.getJSONObject(i).getString("Input_Entity_DoB"));
                                    list_Input_Entity_Gender.add(array.getJSONObject(i).getString("Input_Entity_Gender"));*/
                                    list_Input_Matching_Parameter.add(array.getJSONObject(i).getString("Input_Matching_Parameter"));
                                    //list_Quest_Matching_Parameter_Value.add(array.getJSONObject(i).getString("Quest_Matching_Parameter_Value"));
                                    //list_Quest_EntityRole.add(array.getJSONObject(i).getString("Quest_EntityRole"));
                                    list_Quest_DoP_DoC.add(array.getJSONObject(i).getString("Quest_DoP_DoC"));
                                    list_Quest_Sum_Assured.add(array.getJSONObject(i).getString("Quest_Sum_Assured"));
                                    list_Quest_Policy_Status.add(array.getJSONObject(i).getString("Quest_Policy_Status"));
                                    /*if(!array.getJSONObject(i).get("Quest_Policy_Status_Desc").equals(null))
                                    {
                                        list_Quest_Policy_Status_Desc.add(array.getJSONObject(i).getString("Quest_Policy_Status_Desc"));
                                    }
                                    else
                                    {*/
                                        //list_Quest_Policy_Status_Desc.add("");
                                        String policystatus_desc = FetchPolicyStatusDesc(array.getJSONObject(i).getString("Quest_Policy_Status"));
                                        System.out.println("----policystatus_desc-----"+policystatus_desc);
                                        log.WriteSuccessLog("policystatus_code:-"+array.getJSONObject(i).getString("Quest_Policy_Status"), "SuccessLog");
                                        log.WriteSuccessLog("policystatus_desc:-"+policystatus_desc, "SuccessLog");
                                        list_Quest_Policy_Status_Desc.add(policystatus_desc);
                                    //}
                                    list_Quest_Date_of_Exit.add(array.getJSONObject(i).getString("Quest_Date_of_Exit"));
                                    list_Quest_Date_of_Death.add(array.getJSONObject(i).getString("Quest_Date_of_Death"));
                                    list_Quest_Cause_of_Death.add(array.getJSONObject(i).getString("Quest_Cause_of_Death"));
                                    list_Quest_Record_last_updated.add(array.getJSONObject(i).getString("Quest_Record_last_updated"));
                                    list_Quest_Entity_Caution_Status.add(array.getJSONObject(i).getString("Quest_Entity_Caution_Status"));
                                    list_Quest_Intermediary_Caution_Status.add(array.getJSONObject(i).getString("Quest_Intermediary_Caution_Status"));
                                    //list_RedFlaggedPinCode.add(array.getJSONObject(i).getString("RedFlaggedPinCode"));
                                    //list_RedFlaggedDistrict.add(array.getJSONObject(i).getString("RedFlaggedDistrict"));
                                    list_Quest_Company_Number.add(array.getJSONObject(i).getString("Quest_Company_Number"));
                                    list_Is_Negative_Match.add(array.getJSONObject(i).getString("Is_Negative_Match"));
                                    //added by amit 0n 17-08-21 start
                                    list_Product_Type.add(array.getJSONObject(i).getString("Product_Type"));
                                    list_Linked_Non_linked.add(array.getJSONObject(i).getString("Linked_Non_linked"));
                                    list_Medical_Non_Medical.add(array.getJSONObject(i).getString("Medical_Non_Medical"));
                                    list_Whether_Standard_Life.add(array.getJSONObject(i).getString("Whether_Standard_Life"));
                                    list_Reason_for_Decline.add(array.getJSONObject(i).getString("Reason_for_Decline"));
                                    list_Reason_for_Postpone.add(array.getJSONObject(i).getString("Reason_for_Postpone"));
                                    list_Reason_for_Repudiation.add(array.getJSONObject(i).getString("Reason_for_Repudiation"));
                                    //added by amit on 17-08-21 end
                                    /*if(!(array.getJSONObject(i).isNull("Is_Negative_Match")))
                                    { 
                                        
                                    }
                                    else
                                    {
                                        list_Is_Negative_Match.add("");
                                    }*/
                                    
                                    
                                    //list_QuestDBNo.get(i);
                                    System.out.println("----2-----");
                                    System.out.println("----processInstanceID-----"+processInstanceID);
                                    System.out.println("----trnid-----"+trnid);
                                    System.out.println("----list_polno.get(i)-----"+list_polno.get(i));
                                    System.out.println("----list_QuestDBNo.get(i)-----"+list_QuestDBNo.get(i));
                                    System.out.println("----list_Quest_DoP_DoC.get(i)-----"+list_Quest_DoP_DoC.get(i));
                                    System.out.println("----list_Quest_Sum_Assured.get(i)-----"+list_Quest_Sum_Assured.get(i));
                                    System.out.println("----list_Quest_Policy_Status.get(i)-----"+list_Quest_Policy_Status.get(i));
                                    System.out.println("----list_Quest_Policy_Status_Desc.get(i)-----"+list_Quest_Policy_Status_Desc.get(i));
                                    
                                    System.out.println("----list_Quest_Date_of_Exit.get(i)-----"+list_Quest_Date_of_Exit.get(i));
                                    System.out.println("----list_Quest_Date_of_Death.get(i)-----"+list_Quest_Date_of_Death.get(i));
                                    System.out.println("----list_Quest_Record_last_updated.get(i)-----"+list_Quest_Record_last_updated.get(i));
                                    
                                    System.out.println("----list_Quest_Entity_Caution_Status.get(i)-----"+list_Quest_Entity_Caution_Status.get(i));
                                    System.out.println("----list_Quest_Intermediary_Caution_Status.get(i)-----"+list_Quest_Intermediary_Caution_Status.get(i));
                                    System.out.println("----list_Quest_Company_Number.get(i)-----"+list_Quest_Company_Number.get(i));
                                    
                                    String Values = "'"+processInstanceID+"','1','"+trnid+"','"+list_polno.get(i)+"','"+list_QuestDBNo.get(i)+"','"+list_Input_Matching_Parameter.get(i)+"','"+list_Quest_DoP_DoC.get(i)+"','"+list_Quest_Sum_Assured.get(i)+"','"+list_Quest_Policy_Status.get(i)+"','"+list_Quest_Policy_Status_Desc.get(i)+"','"+list_Quest_Date_of_Exit.get(i)+"','"+list_Quest_Date_of_Death.get(i)+"','"+list_Quest_Cause_of_Death.get(i)+"','"+list_Quest_Record_last_updated.get(i)+"','"+list_Quest_Entity_Caution_Status.get(i)+"','"+list_Quest_Intermediary_Caution_Status.get(i)+"','"+list_Quest_Company_Number.get(i)+"','"+list_Is_Negative_Match.get(i)+"','"+success_op+"',GETDATE(),'"+randomno+"','"+LA_First_Name+"','"+LA_Last_Name+"','"+LA_Gender+"','"+LA_DoB+"','"+list_Product_Type.get(i)+"','"+list_Linked_Non_linked.get(i)+"','"+list_Medical_Non_Medical.get(i)+"','"+list_Whether_Standard_Life.get(i)+"','"+list_Reason_for_Decline.get(i)+"','"+list_Reason_for_Postpone.get(i)+"','"+list_Reason_for_Repudiation.get(i)+"'";//modified by amit on 17-08-21;
                                    //String Values = "'"+processInstanceID+"','"+trnid+"','"+list_polno.get(i)+"','"+list_QuestDBNo.get(i)+"','"+list_Input_Entity_First_Name.get(i)+"','"+list_Input_Entity_Last_Name.get(i)+"','"+list_Input_EntityRole.get(i)+"','"+list_Input_Entity_DoB.get(i)+"','"+list_Input_Entity_Gender.get(i)+"','"+list_Input_Matching_Parameter.get(i)+"','"+list_Quest_Matching_Parameter_Value.get(i)+"','"+list_Quest_EntityRole.get(i)+"','"+list_Quest_DoP_DoC.get(i)+"','"+list_Quest_Sum_Assured.get(i)+"','"+list_Quest_Policy_Status.get(i)+"','"+list_Quest_Policy_Status_Desc.get(i)+"','"+list_Quest_Date_of_Exit.get(i)+"','"+list_Quest_Date_of_Death.get(i)+"','"+list_Quest_Cause_of_Death.get(i)+"','"+list_Quest_Record_last_updated.get(i)+"','"+list_Quest_Entity_Caution_Status.get(i)+"','"+list_Quest_Intermediary_Caution_Status.get(i)+"','"+list_RedFlaggedPinCode.get(i)+"','"+list_RedFlaggedDistrict.get(i)+"','"+list_Quest_Company_Number.get(i)+"','"+success_op+"',GETDATE()";
                                    System.out.println("----Values-----"+Values);
                                    InsertValuesInQuestTable(Values,processInstanceID,workItemID);
                                }
                                System.out.println("done");
                                /*String success_msg = obj.getJSONObject("DRCalculateResult").getString("Message");
                                System.out.println("success_msg:"+success_msg);
                                int index = success_op.indexOf("ResponseData");
                                System.out.println("index:"+index);
                                String response = "{"+success_op.substring((index-1), success_op.length()-1);
                                System.out.println("success_op:"+response);*/
                                //InsertValuesInQuestTable(response,processInstanceID,workItemID);
                                String sColumnValue_ext_Error="BO_Error='N',QUEST_Error='N',QUEST_ERROR_CODE='1'";
                                String inValues = "'N','N','1'";
                                String inColumns = "BO_Error,QUEST_ERROR_CODE,QUEST_ERROR_CODE";
                               //sColumnValue_ext_Error="BO_Error='ValidationError',QUEST_Error='"+response+"',QUEST_ERROR_CODE='2'";
                               //String sColumnValue_ext_Error="var_int='ValidationError',DRC_Error='"+response+"'";
                               //UpdateWFInstTable(sColumnValue_WFinst,processInstanceID,workItemID);
                                UpdateExternalTable(inValues,processInstanceID,workItemID,inColumns);
                               //UpdateExternalTable(sColumnValue_ext_Error,processInstanceID,workItemID);
                            }
                            else if(code.equalsIgnoreCase("2"))
                            {
                               System.out.println("Service failed to fetch TransactionId:");
                               
                               int index = success_op.indexOf("Description");
                               System.out.println("index:"+index);
                               String response = "{"+success_op.substring((index-1), success_op.length()-1)+"}";
                               System.out.println("success_op:"+response);
                               log.WriteSuccessLog("response:"+response, "SuccessLog");
                               String sColumnValue_ext_Error="";
                               String inColumns = "";
                               String inValues = "";
                               if(response.contains("No Match Found."))
                               {
                                   log.WriteSuccessLog("---if---", "SuccessLog");
                                    inColumns = "BO_Error,QUEST_Error,QUEST_ERROR_CODE";
                                   inValues = "'No Match Found.','No Match Found.','2'";
                                   sColumnValue_ext_Error="BO_Error='No Match Found.',QUEST_Error='No Match Found.',QUEST_ERROR_CODE='2'";
                               }
                               else
                               {
                                   log.WriteSuccessLog("---else---", "SuccessLog");
                                   response = response.replaceAll("'", "");
                                   response = response.replaceAll(",", "");
                                   log.WriteSuccessLog("---else---"+response ,"SuccessLog");
                                   inColumns = "BO_Error,QUEST_Error,QUEST_ERROR_CODE";
                                    inValues = "'ValidationError','"+response+"','2'";
                                   sColumnValue_ext_Error="BO_Error='ValidationError',QUEST_Error='"+response+"',QUEST_ERROR_CODE='2'";
                               }
                               //sColumnValue_ext_Error="BO_Error='ValidationError',QUEST_Error='"+response+"',QUEST_ERROR_CODE='2'";
                               //String sColumnValue_ext_Error="var_int='ValidationError',DRC_Error='"+response+"'";
                               //UpdateWFInstTable(sColumnValue_WFinst,processInstanceID,workItemID);
                               
                               UpdateExternalTable(inValues,processInstanceID,workItemID,inColumns);
                               //UpdateExternalTable(sColumnValue_ext_Error,processInstanceID,workItemID);
                            }
                            else if(code.equalsIgnoreCase("3"))
                            {
                               System.out.println("TechnicalError");
                               int index = success_op.indexOf("TechnicalError");
                               String response = "{"+success_op.substring((index-1), success_op.length()-1);
                               String sColumnValue_ext_Error="BO_Error='TechnicalError',QUEST_Error='"+response+"',QUEST_ERROR_CODE='3',Decision_IIBFailure='RE-TRY'";
                               //UpdateWFInstTable(sColumnValue_WFinst,processInstanceID,workItemID);
                               String inColumns = "BO_Error,QUEST_Error,QUEST_ERROR_CODE,Decision_IIBFailure";
                               String inValues = "'TechnicalError','"+response+"','3','RE-TRY'";
                               UpdateExternalTable(inValues,processInstanceID,workItemID,inColumns);
                               //UpdateExternalTable(sColumnValue_ext_Error,processInstanceID,workItemID);
                            }
                            else
                            {
                                System.out.println("Invalid code");
                                log.WriteSuccessLog("Invalid code", "SuccessLog");
                                String sColumnValue_ext_Error="BO_Error='QUEST_WS_ERROR',QUEST_ERROR_CODE='"+code+"',Decision_IIBFailure='RE-TRY'";
                                //UpdateWFInstTable(sColumnValue_WFinst,processInstanceID,workItemID);
                                String inValues = "'QUEST_WS_ERROR','" + code + "','RE-TRY'";
                                 String inColumns = "BO_Error,QUEST_ERROR_CODE,Decision_IIBFailure";
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
    //added by amit 17-08-21 start
    public String getProductTypeAgainstContractPolNo(String CONTRACT_POLICY_NUMBER)
    {   
        log.WriteSuccessLog("Inside getProductTypeAgainstContractPolNo" ,"SuccessLog");
        String inputXML_query1;
        String outputXML_query1;
        String mainCode_query1="";
        String Product_Code="";
		String Product_Type="";
        try
        {   
            String query1 ="select Product_Code from NG_SUD_CLM_EXT_TABLE with (nolock) where Contract_No ='"+CONTRACT_POLICY_NUMBER+"'";
            inputXML_query1 = XMLGen.WFSelectTest_new(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, query1, "1");
            outputXML_query1 = objWF.execute(inputXML_query1);
            xmlParser.setInputXML(outputXML_query1);
             
            log.WriteSuccessLog("Inside getProductTypeAgainstContractPolNo outputXML_query1" +outputXML_query1 ,"SuccessLog");
            xmlParser.setInputXML(outputXML_query1);
            mainCode_query1 = xmlParser.getValueOf("MainCode");
            if (mainCode_query1.equals("0")) 
        {
               String TagValGL2[] = null;
                String resultGL2[];
                TagValGL2 = getCallResult(outputXML_query1);
                for (int iCase = 0; iCase < TagValGL2.length; iCase++) 
                {
                    resultGL2 = TagValGL2[iCase].split("\\|");
                    if(!resultGL2[0].toString().equalsIgnoreCase(""))
                    {
                    Product_Code = resultGL2[0].toString();
                    log.WriteSuccessLog("Inside getProductTypeAgainstContractPolNo Product_Code:" +Product_Code ,"SuccessLog");
                    }
                }
            
        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.WriteSuccessLog("Inside getProductTypeAgainstContractPolNo catch" +e,"SuccessLog");
        }
	try
        {   
            String query1 ="select IIB_Annexure_Code from NG_SUD_CLM_PROD_MASTER with (nolock) where Prod_code ='"+Product_Code+"'";
            inputXML_query1 = XMLGen.WFSelectTest_new(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, query1, "1");
            outputXML_query1 = objWF.execute(inputXML_query1);
             
            log.WriteSuccessLog("Inside getProductTypeAgainstContractPolNo outputXML_query1" +outputXML_query1 ,"SuccessLog");
            xmlParser.setInputXML(outputXML_query1);
            mainCode_query1 = xmlParser.getValueOf("MainCode");
            if (mainCode_query1.equals("0")) 
			{
               String TagValGL2[] = null;
                String resultGL2[];
                TagValGL2 = getCallResult(outputXML_query1);
                for (int iCase = 0; iCase < TagValGL2.length; iCase++) 
                {
                    resultGL2 = TagValGL2[iCase].split("\\|");
                    if(!resultGL2[0].toString().equalsIgnoreCase(""))
                    {
                    Product_Type = resultGL2[0].toString();
                    log.WriteSuccessLog("Inside getProductTypeAgainstContractPolNo Product_Type:" +Product_Type ,"SuccessLog");
                    }
                }
            
			}
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.WriteSuccessLog("Inside getProductTypeAgainstContractPolNo catch" +e,"SuccessLog");
        }
        
        return Product_Type;
    }
    public String getProductUINAgainstContractPolNo(String CONTRACT_POLICY_NUMBER)
    {   
        log.WriteSuccessLog("Inside getProductTypeAgainstContractPolNo" ,"SuccessLog");
        String inputXML_query1;
        String outputXML_query1;
        String mainCode_query1="";
        String Product_Code="";
	String Product_UIN="";
        try
        {   
            String query1 ="select Product_Code from NG_SUD_CLM_EXT_TABLE with (nolock) where Contract_No ='"+CONTRACT_POLICY_NUMBER+"'";
            inputXML_query1 = XMLGen.WFSelectTest_new(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, query1, "1");
            outputXML_query1 = objWF.execute(inputXML_query1);
            xmlParser.setInputXML(outputXML_query1);
             
            log.WriteSuccessLog("Inside getProductUINAgainstContractPolNo outputXML_query1" +outputXML_query1 ,"SuccessLog");
            xmlParser.setInputXML(outputXML_query1);
            mainCode_query1 = xmlParser.getValueOf("MainCode");
            if (mainCode_query1.equals("0")) 
        {
               String TagValGL2[] = null;
                String resultGL2[];
                TagValGL2 = getCallResult(outputXML_query1);
                for (int iCase = 0; iCase < TagValGL2.length; iCase++) 
                {
                    resultGL2 = TagValGL2[iCase].split("\\|");
                    if(!resultGL2[0].toString().equalsIgnoreCase(""))
                    {
                    Product_Code = resultGL2[0].toString();
                    log.WriteSuccessLog("Inside getProductUINAgainstContractPolNo Product_Code:" +Product_Code ,"SuccessLog");
                    }
                }
            
        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.WriteSuccessLog("Inside getProductUINAgainstContractPolNo catch" +e,"SuccessLog");
        }
	try
        {   
            String query1 ="select IIB_UIN from NG_SUD_CLM_PROD_MASTER with (nolock) where Prod_Code ='"+Product_Code+"'";
            inputXML_query1 = XMLGen.WFSelectTest_new(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, query1, "1");
            outputXML_query1 = objWF.execute(inputXML_query1);
             
            log.WriteSuccessLog("Inside getProductUINAgainstContractPolNo outputXML_query1" +outputXML_query1 ,"SuccessLog");
            xmlParser.setInputXML(outputXML_query1);
            mainCode_query1 = xmlParser.getValueOf("MainCode");
            if (mainCode_query1.equals("0")) 
            {
               String TagValGL2[] = null;
                String resultGL2[];
                TagValGL2 = getCallResult(outputXML_query1);
                for (int iCase = 0; iCase < TagValGL2.length; iCase++) 
                {
                    resultGL2 = TagValGL2[iCase].split("\\|");
                    if(!resultGL2[0].toString().equalsIgnoreCase(""))
                    {
                    Product_UIN = resultGL2[0].toString();
                    log.WriteSuccessLog("Inside getProductUINAgainstContractPolNo Product_UIN:" +Product_UIN ,"SuccessLog");
                    }
                }
            
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.WriteSuccessLog("Inside getProductTypeAgainstContractPolNo catch" +e,"SuccessLog");
        }
        
        return Product_UIN;
    }
    public String getAnnualIncomeAgainstContractPolNo(String CONTRACT_POLICY_NUMBER)
    {   
        log.WriteSuccessLog("Inside getAnnualIncomeAgainstContractPolNo" ,"SuccessLog");
        String inputXML_query1;
        String outputXML_query1;
        String mainCode_query1="";
        String wi_name="";
	String anNUAL_INCOME_LA="";
        
	try
        {   //SIT quesry
            //String query1 ="select DECGRSAL from [DC-BUI-UAT04\\SQLUAT12].cdcsql.LACDC.SALHPF where CLNTNUM = '"+CONTRACT_POLICY_NUMBER+"' and TAXYR in (select max(taxyr) from [DC-BUI-UAT04\\SQLUAT12].cdcsql.LACDC.SALHPF where CLNTNUM='"+CONTRACT_POLICY_NUMBER+"')";
            //Prod quesry
            String query1 ="select DECGRSAL from [MUMSQLAPP04].cdcsql.LACDC.SALHPF where CLNTNUM = '"+CONTRACT_POLICY_NUMBER+"' and TAXYR in (select max(taxyr) from [MUMSQLAPP04].cdcsql.LACDC.SALHPF where CLNTNUM='"+CONTRACT_POLICY_NUMBER+"')";
            
            inputXML_query1 = XMLGen.WFSelectTest_new(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, query1, "1");
            outputXML_query1 = objWF.execute(inputXML_query1);
             
            log.WriteSuccessLog("Inside getAnnualIncomeAgainstContractPolNo outputXML_query1" +outputXML_query1 ,"SuccessLog");
            xmlParser.setInputXML(outputXML_query1);
            mainCode_query1 = xmlParser.getValueOf("MainCode");
            if (mainCode_query1.equals("0")) 
            {
               String TagValGL2[] = null;
                String resultGL2[];
                TagValGL2 = getCallResult(outputXML_query1);
                for (int iCase = 0; iCase < TagValGL2.length; iCase++) 
                {
                    resultGL2 = TagValGL2[iCase].split("\\|");
                    if(!resultGL2[0].toString().equalsIgnoreCase(""))
                    {
                    anNUAL_INCOME_LA = resultGL2[0].toString();
                    log.WriteSuccessLog("Inside getAnnualIncomeAgainstContractPolNo anNUAL_INCOME_LA:" +anNUAL_INCOME_LA ,"SuccessLog");
                    }
                }
            
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.WriteSuccessLog("Inside getAnnualIncomeAgainstContractPolNo catch" +e,"SuccessLog");
        }
        
        
        if(anNUAL_INCOME_LA.equalsIgnoreCase("0"))
        return "";
        else 
        return anNUAL_INCOME_LA;
    }
    
    public String getCODAgainstContractPolNo(String CONTRACT_POLICY_NUMBER)
    {   
        log.WriteSuccessLog("Inside getCODAgainstContractPolNo" ,"SuccessLog");
        String inputXML_query1;
        String outputXML_query1;
        String mainCode_query1="";
        String Death_Cause="";
	String COD="";
        try
        {   
            String query1 ="select Death_Cause from NG_SUD_CLM_EXT_TABLE with (nolock) where Contract_No ='"+CONTRACT_POLICY_NUMBER+"'";
            inputXML_query1 = XMLGen.WFSelectTest_new(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, query1, "1");
            outputXML_query1 = objWF.execute(inputXML_query1);
            xmlParser.setInputXML(outputXML_query1);
             
            log.WriteSuccessLog("Inside getCODAgainstContractPolNo outputXML_query1" +outputXML_query1 ,"SuccessLog");
            xmlParser.setInputXML(outputXML_query1);
            mainCode_query1 = xmlParser.getValueOf("MainCode");
            if (mainCode_query1.equals("0")) 
        {
               String TagValGL2[] = null;
                String resultGL2[];
                TagValGL2 = getCallResult(outputXML_query1);
                for (int iCase = 0; iCase < TagValGL2.length; iCase++) 
                {
                    resultGL2 = TagValGL2[iCase].split("\\|");
                    if(!resultGL2[0].toString().equalsIgnoreCase(""))
                    {
                    Death_Cause = resultGL2[0].toString();
                    log.WriteSuccessLog("Inside getCODAgainstContractPolNo Death_Cause:" +Death_Cause ,"SuccessLog");
                    }
                }
            
        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.WriteSuccessLog("Inside getCODAgainstContractPolNo catch" +e,"SuccessLog");
        }
	try
        {   
            String query1 ="select IIB_Code from NG_SUD_CLM_DEATHCAUSE_MASTER with (nolock) where DESCRIPTION ='"+Death_Cause+"'";
            inputXML_query1 = XMLGen.WFSelectTest_new(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, query1, "1");
            outputXML_query1 = objWF.execute(inputXML_query1);
             
            log.WriteSuccessLog("Inside getCODAgainstContractPolNo outputXML_query1" +outputXML_query1 ,"SuccessLog");
            xmlParser.setInputXML(outputXML_query1);
            mainCode_query1 = xmlParser.getValueOf("MainCode");
            if (mainCode_query1.equals("0")) 
            {
               String TagValGL2[] = null;
                String resultGL2[];
                TagValGL2 = getCallResult(outputXML_query1);
                for (int iCase = 0; iCase < TagValGL2.length; iCase++) 
                {
                    resultGL2 = TagValGL2[iCase].split("\\|");
                    if(!resultGL2[0].toString().equalsIgnoreCase(""))
                    {
                    COD = resultGL2[0].toString();
                    log.WriteSuccessLog("Inside getCODAgainstContractPolNo COD:" +COD ,"SuccessLog");
                    }
                }
            
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.WriteSuccessLog("Inside getCODAgainstContractPolNo catch" +e,"SuccessLog");
        }
        
        return COD;
    }
    public String getDateOfDeathAgainstContractPolNo(String CONTRACT_POLICY_NUMBER)
    {   
        log.WriteSuccessLog("Inside getDateOfDeathAgainstContractPolNo" ,"SuccessLog");
        String inputXML_query1;
        String outputXML_query1;
        String mainCode_query1="";
        
	String DOD="";
        
	try
        {   
            String query1 ="select CONVERT(date, Maturity_Date) from NG_SUD_CLM_EXT_TABLE with (nolock) where Contract_No ='"+CONTRACT_POLICY_NUMBER+"'";
            
            inputXML_query1 = XMLGen.WFSelectTest_new(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, query1, "1");
            outputXML_query1 = objWF.execute(inputXML_query1);
             
            log.WriteSuccessLog("Inside getDateOfDeathAgainstContractPolNo outputXML_query1" +outputXML_query1 ,"SuccessLog");
            xmlParser.setInputXML(outputXML_query1);
            mainCode_query1 = xmlParser.getValueOf("MainCode");
            if (mainCode_query1.equals("0")) 
            {
               String TagValGL2[] = null;
                String resultGL2[];
                TagValGL2 = getCallResult(outputXML_query1);
                for (int iCase = 0; iCase < TagValGL2.length; iCase++) 
                {
                    resultGL2 = TagValGL2[iCase].split("\\|");
                    if(!resultGL2[0].toString().equalsIgnoreCase(""))
                    {
                    DOD = resultGL2[0].toString();
                    log.WriteSuccessLog("Inside getDateOfDeathAgainstContractPolNo anNUAL_INCOME_LA:" +DOD ,"SuccessLog");
                    }
                }
            
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log.WriteSuccessLog("Inside getDateOfDeathAgainstContractPolNo catch" +e,"SuccessLog");
        }
        
        return DOD;
    }
    
    //added by amit 17-08-21 end
    public String GenerateRandomNumber(String processInstanceID)
    {
        String inputXML_query;
        String outputXML_query;
        String mainCode_query="";
        String result="";
        try
        {
            String query = "Select max(RandomNumber) from NG_SUD_CLAIM_QUEST_OP_MAIN with(nolock) where wi_name ='"+processInstanceID+"' and WI_ID='1'";
            inputXML_query = XMLGen.WFSelectTest_new(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, query, "1");
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
                    result = resultGL2[0].toString();
                }     
                if(result.equalsIgnoreCase("null")||result.equalsIgnoreCase("null"))
                {
                    result="1";
                }
                else
                {
                    int a = Integer.parseInt(result);
                    a = a + 1;
                    result = String.valueOf(a);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    public void InsertValuesInQuestTable(String response,String wiid,String workitemid) throws JSONException
    {
        String sDRCTableName="NG_SUD_CLAIM_QUEST_OP_MAIN";
        String sColumnName="WI_NAME,WI_ID,Transaction_ID,Input_Proposal_Policy_No,QuestDBNo,Input_Matching_Parameter,Quest_DoP_DoC,Quest_Sum_Assured,Quest_Policy_Status,Quest_Policy_Status_Desc,Quest_Date_of_Exit,Quest_Date_of_Death,Quest_Cause_of_Death,Quest_Record_last_updated,Quest_Entity_Caution_Status,Quest_Intermediary_Caution_Status,Quest_Company_Number,Is_Negative_Match,Quest_Output,Quest_OP_DateTime,RandomNumber,LA_FirstName,LA_LastName,LA_Gender,LA_DOB,Product_Type,Linked_Non_linked,Medical_Non_Medical,Whether_Standard_Life,Reason_for_Decline,Reason_for_Postpone,Reason_for_Repudiation";//added by amit on 17-08-21
        String sColumnValue=""; 
        String inputXML;
        String outputXML;
        String mainCode;
        
        sColumnValue = response;
        inputXML = XMLGen.WFInsert_new(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID, sDRCTableName, sColumnName, sColumnValue,QuestService_UtilityHelper.ProcessDef);
        log.WriteSuccessLog("WFInsert_new input xml is :"+inputXML,"SuccessLog");
        System.out.println("WFInsert_new input xml is :"+inputXML);
        outputXML = objWF.execute(inputXML);
        log.WriteSuccessLog("WFInsert_new output xml is :"+outputXML,"SuccessLog");
        xmlParser.setInputXML(outputXML);
        System.out.println("WFInsert_new outputXML----"+outputXML);
        mainCode = xmlParser.getValueOf("MainCode");
        if (mainCode.equals("0")) 
        {
                log.WriteSuccessLog("Values inserted in QUEST table Success...","SuccessLog");
                System.out.println("Values inserted in QUEST table Success...");
        }
        else
        {
                log.WriteSuccessLog("Values inserted in QUEST table Failed...","SuccessLog");
                System.out.println("Values inserted in QUEST table Failed...");
        }
        
    }
    public void UpdateWI_ID_IN_QuestTable(String wi_name)
    {
        String inputXML_ext;
        String outputXML_ext;
        String mainCode;
        String queryToUpdateExtTable="UPDATE NG_SUD_CLAIM_QUEST_OP_MAIN SET WI_ID=(select max(WI_ID)+1 from NG_SUD_CLAIM_QUEST_OP_MAIN where WI_NAME='"+wi_name+"') WHERE WI_ID='1' and WI_NAME='"+wi_name+"'";
        log.WriteSuccessLog("queryToUpdateExtTable is ******* :"+queryToUpdateExtTable,"SuccessLog");
        inputXML_ext = XMLGen.IGSetData(QuestService_UtilityHelper.cabinetName,queryToUpdateExtTable);
        log.WriteSuccessLog("Update Core Status in Route Table input xml is ******* :"+inputXML_ext,"SuccessLog");
        System.out.println("Update Core Status in Route Table input xml is ******* :"+inputXML_ext);
        outputXML_ext = objWF.execute(inputXML_ext);
        log.WriteSuccessLog("Update Core Status in Route Table output xml is :"+outputXML_ext,"SuccessLog");
        xmlParser.setInputXML(outputXML_ext);
        System.out.println("Update Core Status in Route Table outputXML----"+outputXML_ext);
        mainCode = xmlParser.getValueOf("MainCode");

        if (mainCode.equals("0")) 
        {
                log.WriteSuccessLog("WI_ID Column Value updated into NG_SUD_CLAIM_QUEST_OP_MAIN Success...","SuccessLog");
                System.out.println("WI_ID Column Value updated into NG_SUD_CLAIM_QUEST_OP_MAIN Success...");
                
        }
        else
        {
                log.WriteSuccessLog("WI_ID Column Value updated into NG_SUD_CLAIM_QUEST_OP_MAIN Table Failed...","SuccessLog");
                System.out.println("WI_ID Column Value updated into NG_SUD_CLAIM_QUEST_OP_MAIN Table Failed...");
        }       
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
    public void UpdateExternalTable(String values,String wiid,String workitemid,String columns)
    {
        String inputXML_ext;
        String outputXML_ext;
        String mainCode;
        String where=" WI_NAME='"+wiid+"'";
        String EXT_TABLE = "NG_SUD_CLM_EXT_TABLE";
        String queryToUpdateExtTable="UPDATE "+EXT_TABLE+" SET "+values+" WHERE WI_NAME='"+wiid+"'";
        inputXML_ext=WFUpdate_Input(QuestService_UtilityHelper.cabinetName,QuestService_UtilityHelper.sessionID,"3",EXT_TABLE,columns,values,where);

        //inputXML_ext = XMLGen.IGSetData(QuestService_UtilityHelper.cabinetName,queryToUpdateExtTable);
        log.WriteSuccessLog("Update Core Status in Route Table input xml is ******* :"+inputXML_ext,"SuccessLog");
        System.out.println("Update Core Status in Route Table input xml is ******* :"+inputXML_ext);
        outputXML_ext = objWF.execute(inputXML_ext);
        log.WriteSuccessLog("Update Core Status in Route Table output xml is :"+outputXML_ext,"SuccessLog");
        xmlParser.setInputXML(outputXML_ext);
        System.out.println("Update Core Status in Route Table outputXML----"+outputXML_ext);
        mainCode = xmlParser.getValueOf("MainCode");

        if (mainCode.equals("0")) 
        {
                log.WriteSuccessLog("Quest Columns Values updated into Route Table Success...","SuccessLog");
                System.out.println("Quest Columns Values updated into Route Table Success...");
                CompleteWI(wiid,workitemid);
        }
        else
        {
                log.WriteSuccessLog("Quest Columns Values updated into Route Table Failed...","SuccessLog");
                System.out.println("Quest Columns Values updated into Route Table Failed...");
        }       
    }
    public void UpdateWFInstTable(String values,String wiid,String workitemid)
    {
        String inputXML_ext;
        String outputXML_ext;
        String mainCode;
        String EXT_TABLE = "WFINSTRUMENTTABLE";
        String queryToUpdateExtTable="UPDATE "+EXT_TABLE+" SET "+values+" WHERE ProcessInstanceID='"+wiid+"'";
        inputXML_ext = XMLGen.IGSetData(QuestService_UtilityHelper.cabinetName,queryToUpdateExtTable);
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
        String inputXML = XMLGen.workItemCompleteCall(QuestService_UtilityHelper.sessionID,QuestService_UtilityHelper.cabinetName,sParentWIName,sWorkItemId);
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
                System.err.println("Unable to connect to the URL..."+urlConn.getErrorStream());
                //return;
                log.WriteSuccessLog("09022021:error:1:"+urlConn.getResponseMessage(),"SuccessLog");
                log.WriteSuccessLog("09022021:error:2:"+urlConn.getErrorStream(),"SuccessLog");
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

