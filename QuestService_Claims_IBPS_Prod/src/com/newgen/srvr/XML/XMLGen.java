/**
 * <p>Title: Auto Create Server</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Newgen Software Technologies Ltd.</p>
 * @author V
 * @version 1.0
 */


package com.newgen.srvr.XML;

import com.newgen.wfdesktop.xmlapi.WFInputXml;

public class XMLGen {


    public XMLGen() {
    }

    public static String WMGetWorkItem(String strEngineName,
                                       String strSessionId,
                                       String strProcessInstanceId,
                                       String strWorkItemId) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("WMGetWorkItem", "Input");
        wfInputXml.appendTagAndValue("EngineName", strEngineName);
        wfInputXml.appendTagAndValue("SessionId", strSessionId);
        wfInputXml.appendTagAndValue("ProcessInstanceId", strProcessInstanceId);
        wfInputXml.appendTagAndValue("WorkItemId", strWorkItemId);
        wfInputXml.appendEndCallName("WMGetWorkItem", "Input");
        return wfInputXml.toString();
    }
    public static String WMTestSelect(String strQuery, String noofcol,String sEngineName,String sSessionId) 
    {
        System.out.println("WMTestSelect:" + System.currentTimeMillis());
      //  XMLParser generalDataParser = new XMLParser();
       // generalDataParser.setInputXML(formObject.getWFGeneralData());
       // String sEngineName = generalDataParser.getValueOf("EngineName");
       // String sSessionId = generalDataParser.getValueOf("DMSSessionId");
       // System.out.println("WMTestSelect:" + System.currentTimeMillis());
        return "<?xml version=\"1.0\"?>"
                + "<WMTestSelect_Input><Option>WFSelectTest_new</Option>"
                + "<sQry>" + strQuery + "</sQry>"
                + "<EngineName>" + sEngineName + "</EngineName>"
                + "<NoOfCols>" + noofcol + "</NoOfCols>"
                + "<SessionId>" + sSessionId + "</SessionId></WMTestSelect_Input>";
    }
    
    public static String WFSelectTest_new(String strEngineName,
                                String strSessionId, String strQuery,String NoOfCols) {
     return "<?xml version=\"1.0\"?>" +
		    "<WMTestSelect_Input>" +
			"<Option>WFSelectTest_new</Option>" +
			"<sQry>"+strQuery+"</sQry>" +
			"<EngineName>"+strEngineName+"</EngineName>" +
			"<NoOfCols>"+NoOfCols+"</NoOfCols>" +
			"<SessionId>"+strSessionId+"</SessionId>" +
			"</WMTestSelect_Input>" ;
  }
    
    public static String WFUpdate(String sTable,String sCols,String sValues,String sWhereClause,String sEngineName,String sSessionId) 
    {
 	  	return "<?xml version=\"1.0\"?>\n"
                 + "<WFUpdate_new>\n"
                 + "<Option>WFUpdate</Option>\n"
                 + "<EngineName>" + sEngineName + "</EngineName>\n"
                 + "<SessionId>" + sSessionId + "</SessionId>\n"
 				+ "<TableName>" + sTable + "</TableName>\n"
 				+ "<ColName>" + sCols + "</ColName>\n"
 				+ "<Values>" + sValues + "</Values>\n"
 				+ "<WhereClause>" + sWhereClause + "</WhereClause>\n"
 				+ "</WFUpdate_new>";
    }
    public static String WFGetWorkitemDataExt(String strEngineName,String strSessionId,String strProcessInstanceId,
    		String strWorkItemId,String QueueId)
    {
    	
    	return "<?xml version='1.0'?>"+
    	"<WFGetWorkitemDataExt_Input>"+
    	"<Option>WFGetWorkitemDataExt</Option>"+
    	"<EngineName>"+strEngineName+"</EngineName>"+
    	"<SessionId>"+strSessionId+"</SessionId>"+
    	"<ProcessInstanceId>"+strProcessInstanceId+"</ProcessInstanceId>"+
    	"<ReminderFlag>N</ReminderFlag>"+
    	"<WorkitemId>"+strWorkItemId+"</WorkitemId>"+
    	"<QueueId>"+QueueId+"</QueueId>"+
    	"<QueueType>N</QueueType>"+
    	"<ObjectPreferenceList>W,D</ObjectPreferenceList>"+
    	"<UserDefVarFlag>Y</UserDefVarFlag>"+
    	"</WFGetWorkitemDataExt_Input>";
    }

    public static String WFTestProcedure_new(String sEngineName,String sProcName,String sSessionId,String sParams,String sCols) 
    {
        return "?xml version=\"1.0\"?>\n"
                + "<WFTestProcedure_Input>\n"
                + "<Option>WFTestProcedure_new</Option>\n"
                + "<SessionId>" + sSessionId + "</SessionId>\n"
                + "<ProcName>" + sProcName + "</ProcName>\n"
                + "<Params>"+sParams+"</Params>\n"
                + "<EngineName>" + sEngineName + "</EngineName>\n"
                + "<NoOfCols>" + sCols + "</NoOfCols>"
                + "</WFTestProcedure_Input>\n";
    }
    
    public static String WMFetchWorkItemAttribute(String strEngineName,
                                                  String strSessionId,
                                                  String strProcessInstanceId,
                                                  String strWorkItemId,
                                                  String strCountFlag,
                                                  String strType,
                                                  String strComparison,
                                                  String strFilterString,
                                                  String strLength,
                                                  String strNoOfRecordsToFetch,
                                                  String strLastValue) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("WMFetchWorkItemAttribute", "Input");
        wfInputXml.appendTagAndValue("EngineName", strEngineName);
        wfInputXml.appendTagAndValue("SessionId", strSessionId);
        wfInputXml.appendTagAndValue("ProcessInstanceId", strProcessInstanceId);
        wfInputXml.appendTagAndValue("WorkItemId", strWorkItemId);
        wfInputXml.appendTagAndValue("ZipBuffer", "Y");
        wfInputXml.appendTagAndValue("CountFlag", strCountFlag);
        wfInputXml.appendTagStart("Filter");
        wfInputXml.appendTagAndValue("Type", strType);
        wfInputXml.appendTagAndValue("Comparison", strComparison);
        wfInputXml.appendTagAndValue("FilterString", strFilterString);
        wfInputXml.appendTagAndValue("Length", strLength);
        wfInputXml.appendTagEnd("Filter");
        wfInputXml.appendTagStart("BatchInfo");
        wfInputXml.appendTagAndValue("NoOfRecordsToFetch",
                                     strNoOfRecordsToFetch);
        wfInputXml.appendTagAndValue("LastValue", strLastValue);
        wfInputXml.appendTagEnd("BatchInfo");
        wfInputXml.appendEndCallName("WMFetchWorkItemAttribute", "Input");
        return wfInputXml.toString();
    }


    public static String WMTerminateProcessInstance(String strEngineName,
            String strSessionId,
            String strProcessInstanceId) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("WMTerminateProcessInstance", "Input");
        wfInputXml.appendTagAndValue("EngineName", strEngineName);
        wfInputXml.appendTagAndValue("SessionId", strSessionId);
        wfInputXml.appendTagAndValue("ProcessInstanceID", strProcessInstanceId);
        wfInputXml.appendEndCallName("WMTerminateProcessInstance", "Input");
        return wfInputXml.toString();
    }

    public static String WMStartProcess(String strEngineName,
                                        String strSessionId,
                                        String strProcessInstanceId) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("WMStartProcess", "Input");
        wfInputXml.appendTagAndValue("EngineName", strEngineName);
        wfInputXml.appendTagAndValue("SessionId", strSessionId);
        wfInputXml.appendTagAndValue("ProcessInstanceID", strProcessInstanceId);
        wfInputXml.appendEndCallName("WMStartProcess", "Input");
        return wfInputXml.toString();
    }

    public static String WFFetchInstrumentsList(String strEngineName,
                                                String strSessionId,
                                                String strCountFlag,
                                                String strQueueId,
                                                String strType,
                                                String strComparison,
                                                String strAttributeName,
                                                String strFilterString,
                                                String strLength,
                                                String strNoOfRecordsToFetch,
                                                String strOrderBy,
                                                String strSortOrder,
                                                String strLastValue,
                                                String strLastProcessInstance,
                                                String strLastWorkItemId,
                                                String strDataFlag,String strFetchLockedWI) {
    	//System.out.println("QueueId>>>>>>>>>"+strQueueId);
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("WMFetchWorkList", "Input");
        wfInputXml.appendTagAndValue("EngineName", strEngineName);
        wfInputXml.appendTagAndValue("SessionId", strSessionId);
        wfInputXml.appendTagAndValue("CountFlag", strCountFlag);
        wfInputXml.appendTagAndValue("DataFlag", strDataFlag);
        wfInputXml.appendTagAndValue("ZipBuffer", "N");
        wfInputXml.appendTagStart("Filter");
        wfInputXml.appendTagAndValue("QueueId", strQueueId);
        wfInputXml.appendTagAndValue("Type", strType);
        wfInputXml.appendTagAndValue("Comparison", strComparison);
        wfInputXml.appendTagAndValue("AttributeName", strAttributeName);
        wfInputXml.appendTagAndValue("FilterString", strFilterString);
        wfInputXml.appendTagAndValue("Length", strLength);
        wfInputXml.appendTagEnd("Filter");
        wfInputXml.appendTagStart("BatchInfo");
        wfInputXml.appendTagAndValue("NoOfRecordsToFetch",
                                     strNoOfRecordsToFetch);
        wfInputXml.appendTagAndValue("OrderBy", strOrderBy);
        wfInputXml.appendTagAndValue("SortOrder", strSortOrder);
        wfInputXml.appendTagAndValue("LastValue", strLastValue);
        wfInputXml.appendTagAndValue("LastProcessInstance",
                                     strLastProcessInstance);
        wfInputXml.appendTagAndValue("LastWorkItem", strLastWorkItemId);
        wfInputXml.appendTagAndValue("FetchLockedFlag", strFetchLockedWI);
        wfInputXml.appendTagEnd("BatchInfo");
        wfInputXml.appendEndCallName("WMFetchWorkList", "Input");
        return wfInputXml.toString();
    }

    public static String WFInsert_new2(String sEngineName,String sSessionId,String sTableName,String sColumnName, String sColumnValue) {
	   	 return "<?xml version=\"1.0\"?>\n"+
	   	 "<WFInsert_new>\n"+
	   	 "<Option>WFInsert_new</Option>\n"+
	   	 "<EngineName>" + sEngineName + "</EngineName>\n"+
	   	 "<SessionId>" + sSessionId + "</SessionId>\n"+
	   	 "<TableName>" + sTableName + "</TableName>\n"+
	   	 "<ColName>" + sColumnName + "</ColName>\n"+
	   	 "<Values>" + sColumnValue + "</Values>\n"+
	   	 "</WFInsert_new>";
    }
        public static String WFInsert_new(String cabinetName,String sessionId,String tableName, String columnName, String strValues,String processDefID ) {

        return "<?xml version=\"1.0\"?>" +
                "<WFInsert_Input><Option>WFInsert_new</Option>" +
                "<TableName>" + tableName + "</TableName>" +
                "<ColName>" + columnName + "</ColName>" +
                "<Values>" + strValues + "</Values>" +
                "<ProcessDefId>" + processDefID + "</ProcessDefId>\n" +
                "<EngineName>" + cabinetName + "</EngineName>" +
                "<SessionId>" + sessionId + "</SessionId>" +
                "</WFInsert_Input>";
    }
    public static String WMCompleteWorkItem(String strEngineName,
                                            String strSessionId,
                                            String strProcessInstanceId,
                                            String strWorkItemId,
                                            String strAuditStatus) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("WMCompleteWorkItem", "Input");
        wfInputXml.appendTagAndValue("EngineName", strEngineName);
        wfInputXml.appendTagAndValue("SessionId", strSessionId);
        wfInputXml.appendTagAndValue("ProcessInstanceId", strProcessInstanceId);
        wfInputXml.appendTagAndValue("WorkItemId", strWorkItemId);
        wfInputXml.appendTagAndValue("AuditStatus", strAuditStatus);
        wfInputXml.appendEndCallName("WMCompleteWorkItem", "Input");
        return wfInputXml.toString();
    }

    public static String WFSetAttributes(String strEngineName,
                                         String strSessionId,
                                         String strProcessInstanceId,
                                         String strWorkItemId,
                                         String strAttributeData) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("WMAssignWorkItemAttributes", "Input");
        wfInputXml.appendTagAndValue("EngineName", strEngineName);
        wfInputXml.appendTagAndValue("SessionId", strSessionId);
        wfInputXml.appendTagAndValue("ProcessInstanceId", strProcessInstanceId);
        wfInputXml.appendTagAndValue("WorkItemId", strWorkItemId);
        wfInputXml.appendTagAndValue("Attributes", strAttributeData);
        wfInputXml.appendEndCallName("WMAssignWorkItemAttributes", "Input");
        return wfInputXml.toString();
    }

    public static String WMCreateProcessInstance(String strEngineName,
                                                 String strSessionId,
                                                 String strProcessId) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("WMCreateProcessInstance", "Input");
        wfInputXml.appendTagAndValue("EngineName", strEngineName);
        wfInputXml.appendTagAndValue("SessionId", strSessionId);
        wfInputXml.appendTagAndValue("ProcessDefinitionID", strProcessId);
        wfInputXml.appendEndCallName("WMCreateProcessInstance", "Input");
        return wfInputXml.toString();
    }

    public static String IGSetData(String cabinetName, String query ){
       return "<?xml version=\"1.0\"?>"
                +"<IGSetData_Input>"
                +"<Option>IGSetData</Option>"
                +"<EngineName>"+cabinetName+"</EngineName>"
                +"<Query>"+query+"</Query>"
                +"</IGSetData_Input>";	
    }
    public static String get_NGOAddFolder(String strEngineName,
                                          String strUserDBId,
                                          String strFolderName,
                                          String strParentFolderIndex) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("NGOAddFolder", "Input");
        wfInputXml.appendTagAndValue("CabinetName", strEngineName);
        wfInputXml.appendTagAndValue("UserDBId", strUserDBId);
        wfInputXml.appendTagAndValue("FolderName", strFolderName);
        wfInputXml.appendTagAndValue("ParentFolderIndex", strParentFolderIndex);
        wfInputXml.appendEndCallName("NGOAddFolder", "Input");
        return wfInputXml.toString();
    }
    public static String NGOGetDocumentListExt(String cabinetName,
               String sessionID, String folderID) {
    return "<?xml Version=\"1.0\"?>\n"
                + "<NGOGetDocumentListExt_Input>\n"
                + "<Option>NGOGetDocumentListExt</Option>\n"
                + "<LinkDocFlag>N</LinkDocFlag>\n"
                + "<AnnotationFlag>N</AnnotationFlag>\n"
                + "<DocumentType>B</DocumentType>\n"
                + "<CabinetName>" + cabinetName +
                "</CabinetName>\n"
                + "<UserDBId>" + sessionID + "</UserDBId>\n"
                + "<FolderIndex>" + folderID + "</FolderIndex>\n"
                + "<StartPos>1</StartPos>\n"
                + "<RecursiveFlag>N</RecursiveFlag>\n"
                + "<NoOfRecordsToFetch>100</NoOfRecordsToFetch>\n"
                + "<TotalRecCountFlag>Y</TotalRecCountFlag>\n"
                + "<SortOrder>D</SortOrder>\n"
                + "<OrderBy>5</OrderBy>\n"
                + "<DataAlsoFlag>N</DataAlsoFlag>\n"
                + "<ReferenceFlag>B</ReferenceFlag>\n"
                + "</NGOGetDocumentListExt_Input>";



       }

    public static String get_NGOCopyDocumentExt(String strCabinetName,
                                                 String strSessionId,
                                                 String strFolderIndex,
                                                 String strDocumentList)
     {
         return "<?xml version=\"1.0\"?>"+
                 "<NGOCopyDocumentExt_Input>"+
                 "<Option>NGOCopyDocumentExt</Option>"+
                 "<CabinetName>"+strCabinetName+"</CabinetName>"+
                 "<UserDBId>"+strSessionId+"</UserDBId>"+
                 "<DestFolderIndex>"+strFolderIndex+"</DestFolderIndex>"+
                 "<Documents>"+strDocumentList+"</Documents>"+
                 "</NGOCopyDocumentExt_Input>";
}
 public static String APInsert(String strEngineName,String strSessionId,String strFileNetTable,String columns, String strValues) {
return "<?xml version=\"1.0\"?>"+
"<WMTestSelect_Input><Option>APInsert</Option>"+
"<TableName>"+strFileNetTable+"</TableName>"+
"<ColName>"+columns+"</ColName>"+
"<Values>"+strValues+"</Values>"+
"<EngineName>"+strEngineName+"</EngineName>"+
"<SessionId>"+strSessionId+"</SessionId>"+
"</WMTestSelect_Input>";
}

 public static String get_NGOChangeDocumentProperty(String strEngineName,String strUserDBId,
         String strFolderIndex, String strDataDefIndex, String FieldTag,String strDocIndex) {

return "<?xml version=\"1.0\"?>\n"
+"<NGOChangeDocumentProperty_Input>\n"
+"<Option>NGOChangeDocumentProperty</Option>\n"
+"<CabinetName>" + strEngineName + "</CabinetName>\n"
+"<UserDBId>" + strUserDBId + "</UserDBId>\n"
+"<ParentFolderIndex>"+ strFolderIndex + "</ParentFolderIndex>\n"
+"<Document><DocumentIndex>" + strDocIndex + "</DocumentIndex>\n"
//+"<NoOfPages>1</NoOfPages>\n"
+"<Owner>Supervisor</Owner>\n"
//+"<FinalizedFlag>Y</FinalizedFlag>\n"
+"<DataDefinition><DataDefIndex>" + strDataDefIndex + "</DataDefIndex>\n"
+"<Fields>" + FieldTag + "</Fields>\n"
+"</DataDefinition></Document>\n"
+"</NGOChangeDocumentProperty_Input>";

}


public static String get_NGOChangeDocumentProperty(String strCabinetName, String strSessionId, String strFields)
{
    return "<?xml version=\"1.0\"?><NGOChangeDocumentProperty_Input><Option>NGOChangeDocumentProperty</Option>"
                +"<CabinetName>"+strCabinetName+"</CabinetName>"
                +"<UserDBId>"+strSessionId+"</UserDBId>"
                +"<Document>"+strFields+"<DuplicateName>N</DuplicateName>"
                +"</Document>"
                +"</NGOChangeDocumentProperty_Input>";
 }

    public static String get_NGOGetDataDefIdForName(String strEngineName,
            String strUserDBId, String strDataDefName) {
    	//System.out.println("In XMLGen : get_NGOGetDataDefIdForName :"+strDataDefName);
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("NGOGetDataDefIdForName", "Input");
        wfInputXml.appendTagAndValue("CabinetName", strEngineName);
        wfInputXml.appendTagAndValue("UserDBId", strUserDBId);
        wfInputXml.appendTagAndValue("DataDefName", strDataDefName);
        wfInputXml.appendEndCallName("NGOGetDataDefIdForName", "Input");
        return wfInputXml.toString();
    }

    public static String get_NGOChangeFolderProperty_Old(String strEngineName,
            String strUserDBId,
            String strFolderName,
            String strFolderIndex,
            String strDataDefIndex,
            String FieldTag) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("NGOChangeFolderProperty", "Input");
        wfInputXml.appendTagAndValue("CabinetName", strEngineName);
        wfInputXml.appendTagAndValue("UserDBId", strUserDBId);
        wfInputXml.appendTagStart("Folder");
        wfInputXml.appendTagAndValue("FolderIndex", strFolderIndex);
        wfInputXml.appendTagAndValue("FolderName", strFolderName);
        wfInputXml.appendTagStart("DataDefinition");
        wfInputXml.appendTagAndValue("DataDefIndex", strDataDefIndex);
        wfInputXml.appendTagAndValue("Fields", FieldTag);
        wfInputXml.appendTagEnd("DataDefinition");
        wfInputXml.appendTagEnd("Folder");
        wfInputXml.appendEndCallName("NGOChangeFolderProperty", "Input");
        return wfInputXml.toString();
    }
    
    public static String get_NGOChangeFolderProperty(String strEngineName,
            String strUserDBId,            
            String strFolderIndex
            ) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("NGOChangeFolderProperty", "Input");
        wfInputXml.appendTagAndValue("CabinetName", strEngineName);
        wfInputXml.appendTagAndValue("UserDBId", strUserDBId);
        wfInputXml.appendTagStart("Folder");
        wfInputXml.appendTagAndValue("FolderIndex", strFolderIndex);
        wfInputXml.appendTagAndValue("FolderIndex", "I");
        wfInputXml.appendTagEnd("Folder");
        wfInputXml.appendEndCallName("NGOChangeFolderProperty", "Input");
        return wfInputXml.toString();
    }

    public static String get_NGOGetDataDefProperty(String strEngineName,
            String strSessionId,
            String strDataDefIndex) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("NGOGetDataDefProperty", "Input");
        wfInputXml.appendTagAndValue("CabinetName", strEngineName);
        wfInputXml.appendTagAndValue("UserDBId", strSessionId);
        wfInputXml.appendTagAndValue("DataDefIndex", strDataDefIndex);
        wfInputXml.appendEndCallName("NGOGetDataDefProperty", "Input");
        return wfInputXml.toString();
    }

    public static String WMUnlockWorkItem(String strEngineName,String strSessionId,String strProcessInstanceId,String strWorkItemId) {
        WFInputXml wfInputXml = new WFInputXml();
        wfInputXml.appendStartCallName("WMUnlockWorkItem", "Input");
        wfInputXml.appendTagAndValue("EngineName", strEngineName);
        wfInputXml.appendTagAndValue("SessionId", strSessionId);
        wfInputXml.appendTagAndValue("ProcessInstanceID", strProcessInstanceId);
        wfInputXml.appendTagAndValue("WorkItemID", strWorkItemId);
        wfInputXml.appendEndCallName("WMUnlockWorkItem", "Input");
        return wfInputXml.toString();
    }
    public static String WFLinkWorkItem(String strEngineName, String strSessionId, String strWorkItemId, String strChildPID, String strParentPID)
    {
        return "<? Xml version=\"1.0\"?>"+
                "<WFLinkWorkitem_Input>"+
                "<Option>WFLinkWorkitem</Option>"+
                "<EngineName>"+strEngineName+"</EngineName>"+
                "<SessionId>"+strSessionId+"</SessionId>"+
                "<WorkItemID>"+strWorkItemId+"</WorkItemID>"+
                "<ProcessInstanceID>"+strChildPID+"</ProcessInstanceID>"+
                "<LinkedProcessInstanceID>"+strParentPID+"</LinkedProcessInstanceID>"+
                "</WFLinkWorkitem _Input>";


    }
  public static String APUpdate(String strEngineName,
            String strSessionId, String strValues, String sWhere,String tableName, String columnName) {
return  "<?xml version=\"1.0\"?>"+
"<APUpdate_Input><Option>APUpdate</Option>"+
"<TableName>"+tableName+"</TableName>"+
"<ColName>"+columnName+"</ColName>"+
"<Values>"+strValues+"</Values>"+
"<WhereClause>"+sWhere+"</WhereClause>"+
"<EngineName>"+strEngineName+"</EngineName>"+
"<SessionId>"+strSessionId+"</SessionId>"+
"</APUpdate_Input>";
}


    public static String APProcedure(String strEngineName, String strSessionId, String strParam, String procName)
    {
       return "<?xml version=\"1.0\"?>" +
                "<APProcedure_Input>" +
                "<Option>APProcedure</Option>" +
                "<SessionId>"+strSessionId+"</SessionId>\n"+
                "<ProcName>"+procName+"</ProcName>\n" +
                "<Params>"+strParam+"</Params>"+
                "<EngineName>"+strEngineName+"</EngineName>" +
                "<APProcedure_Input>";

    }
    public static String get_WMConnect_Input(String cabinetName,
                                             String userName, String password,
                                             String forceful) {
    	forceful="N";
        return
                "<?xml version=\"1.0\"?><WMConnect_Input><Option>WMConnect</Option><UserExist>" +
                forceful + "</UserExist><EngineName>" +
                cabinetName + "</EngineName>\n" +
                "<Particpant>\n" +
                "<Name>" + userName + "</Name>\n" +
                "<Password>" +
                password + "</Password>\n" +
                "<Scope>USER</Scope>\n" +
                "<ParticipantType>U</ParticipantType>\n" +
                "</Participant>\n" + "</WMConnect_Input>";
    }

    public static String get_NGOConnectCabinet_Input(String
            cabinetName, String userName, String password, String forceful) {
        return "<?xml version=\"1.0\"?>\n<NGOConnectCabinet_Input>\n<Option>NGOConnectCabinet</Option>\n<UserExist>" +
                forceful + "</UserExist>\n<CabinetName>" +
                cabinetName + "</CabinetName>\n" + "<UserName>" + userName +
                "</UserName>\n" + "<UserPassword>" + password +
                "</UserPassword>\n" + "</NGOConnectCabinet_Input>";
    }

    public static String get_WMDisConnect_Input(String cabinetName,
                                                String sessionID) {
        return "<? Xml Version=\"1.0\"?>"
                + "<WMDisConnect_Input>"
                + "<Option>WMDisConnect</Option>"
                + "<EngineName>" + cabinetName +
                "</EngineName>"
                + "<SessionID>" + sessionID +
                "</SessionID>"
                + "</WMDisConnect_Input>";
    }

    public static String get_WMFetchProcessDefinitions_Input(String cabinetName,
            String sessionID) {
        return "<? Xml Version=\"1.0\"?>\n"
                + "<WMFetchProcessDefinitions_Input>\n"
                +
                "<Option>WMFetchProcessDefinitions</Option>\n"
                + "<EngineName>" + cabinetName +
                "</EngineName>\n"
                + "<SessionID>" + sessionID +
                "</SessionID>\n"
                + "<CountFlag>Y</CountFlag>\n"
                +
                "<BatchInfo><NoOfRecordsToFetch></NoOfRecordsToFetch>\n"
                + "<LastValue>0</LastValue></BatchInfo>\n"
                + "</WMFetchProcessDefinitions_Input>";
    }

    public static String NGOCreateReferences(String cabinetName,
                                             String sessionID,
                                             String destFolderId,
                                             String srcDocumentId) {
        return "<?xml version=\"1.0\"?>\n"
                + "<NGOCreateReferences_Input>\n"
                + "<Option>NGOCreateReferences</Option>\n"
                + "<CabinetName>" + cabinetName +
                "</CabinetName>\n"
                + "<UserDBId>" + sessionID +
                "</UserDBId>\n"
                + "<DestinationFolder>" + destFolderId +
                "</DestinationFolder>\n"
                + "<ObjectType>D</ObjectType>\n"
                + "<ObjectIndex>" + srcDocumentId + "</ObjectIndex>\n"
                + "</NGOCreateReferences_Input>";
    }


    public static String get_NGOGetListOfCabinets_Input() {
        return "<?xml version=\"1.0\"?><NGOGetListOfCabinets_Input><Option>NGOGetListOfCabinets</Option></NGOGetListOfCabinets_Input>";
    }

    public static String get_NGOISGetSitesList_Input(String cabinetName) {
        return "<?xml version=\"1.0\"?><NGOISGetSitesList_Input>\n<Option>NGOISGetSitesList</Option>\n<CabinetName>" +
                cabinetName + "</CabinetName>\n</NGOISGetSitesList_Input>";
    }

    public static String get_NGOISGetVolumesList_Input(String cabinetName) {
        return "<?xml version=\"1.0\"?><NGOISGetVolumesList_Input>\n<Option>NGOISGetVolumesList</Option>\n<CabinetName>" +
                cabinetName + "</CabinetName>\n</NGOISGetVolumesList_Input>";
    }


    public static String get_WMConnectCS_Input(String cabinetName, String name) {
        return "<?xml version=\"1.0\"?><WMConnect_Input>\n<Option>WMConnect</Option><EngineName>" +
                cabinetName + "</EngineName>\n<Name>" + name +
                "</Name>\n<ParticipantType>C</ParticipantType>\n</WMConnect_Input>";
    }

    public static String get_WMGetProcessList_Input(String cabinetName,
            String sessionID) {
        return "<?xml version=\"1.0\"?><WMGetProcessList_Input>\n<Option>WMGetProcessList</Option>\n<EngineName>" +
                cabinetName + "</EngineName>\n<SessionID>" + sessionID + "</SessionID>\n<DataFlag>N</DataFlag>\n<LatestVersionFlag>N</LatestVersionFlag>\n<OrderBy>2</OrderBy>\n<BatchInfo><NoOfRecordsToFetch>9999</NoOfRecordsToFetch></BatchInfo>\n</WMGetProcessList_Input>";
    }

    public static String get_WMGetActivityList_Input(String cabinetName,
            String sessionID, String processDefID) {
        return "<?xml version=\"1.0\"?><WMGetActivityList_Input>\n<Option>WMGetActivityList</Option>\n<EngineName>" +
                cabinetName + "</EngineName>\n<SessionId>" + sessionID +
                "</SessionId>\n<ProcessDefinitionID>" + processDefID +
                "</ProcessDefinitionID>\n<BatchInfo>\n<SortOrder>A</SortOrder>\n<OrderBy>2</OrderBy>\n</BatchInfo>\n</WMGetActivityList_Input>";
    }

    public static String get_WFGetUserQueueDetails_Input(String cabinetName,
            String sessionID, String processDefID, String activityID,
            String userID) {
        return "<?xml version=\"1.0\"?><WFGetUserQueueDetails_Input>\n<Option>WFGetUserQueueDetails</Option>\n<EngineName>" +
                cabinetName + "</EngineName>\n<SessionId>" + sessionID +
                "</SessionId>\n<UserId>" + userID +
                "</UserId>\n<ProcessDefinitionId>" + processDefID +
                "</ProcessDefinitionId>\n<ActivityId>" + activityID +
                "</ActivityId>\n<DataFlag>N</DataFlag>\n</ WFGetUserQueueDetails _Input>";
    }

    public static String get_WFGetIdforName_Input(String cabinetName,
                                                  String sessionID,
                                                  String processDefID,
                                                  String objectType,
                                                  String objectName) {
        return "<?xml version=\"1.0\"?><WFGetIdforName_Input>\n<Option>WFGetIdforName</Option>\n<EngineName>"
                + cabinetName + "</EngineName>\n<SessionId>" + sessionID +
                "</SessionId>\n<ObjectType>" + objectType +
                "</ObjectType>\n<ObjectName>" + objectName +
                "</ObjectName>\n<ProcessDefID>" + processDefID +
                "</ProcessDefID>\n</WFGetIdforName_Input>";

    }

   /*
     public static String get_NGOGetDocumentListExtView_Input(String cabinetName,
            String sessionID, String folderID) {
        return "<? Xml Version=\"1.0\"?>\n"
                + "<NGOGetDocumentListExtView_Input>\n"
                + "<Option>NGOGetDocumentListExt</Option>\n"
                + "<LinkDocFlag>N</LinkDocFlag>\n"
                + "<AnnotationFlag>N</AnnotationFlag>\n"
                + "<DocumentType>B</DocumentType>\n"
                + "<CabinetName>" + cabinetName +"</CabinetName>\n"
                + "<UserDBId>" + sessionID + "</UserDBId>\n"
                + "<FolderIndex>" + folderID + "</FolderIndex>\n"
                + "<StartPos>1</StartPos>\n"
                + "<RecursiveFlag>N</RecursiveFlag>\n"
                + "<NoOfRecordsToFetch>100</NoOfRecordsToFetch>\n"
                + "<TotalRecCountFlag>Y</TotalRecCountFlag>\n"
                + "<SortOrder>D</SortOrder>\n"
                + "<OrderBy>5</OrderBy>\n"
                + "<DataAlsoFlag>N</DataAlsoFlag>\n"
                + "<ReferenceFlag>B</ReferenceFlag>\n"
                + "</NGOGetDocumentListExtView_Input>";
    }


  public static String NGOGetDocumentListExt(String cabinetName,
                                               String sessionID,
                                               String folderID) {
        return "<? Xml Version=\"1.0\"?>\n"
                + "<NGOGetDocumentListExt_Input>\n"
                + "<Option>NGOGetDocumentListExt</Option>\n"
                + "<CabinetName>" + cabinetName + "</CabinetName>\n"
                + "<UserDBId>" + sessionID + "</UserDBId>\n"
                + "<FolderIndex>" + folderID + "</FolderIndex>\n"
                + "<StartPos>1</StartPos>\n"
                + "<RecursiveFlag>N</RecursiveFlag>\n"
                + "<TotalRecCountFlag>Y</TotalRecCountFlag>\n"
                + "<SortOrder>D</SortOrder>\n"
                + "<OrderBy>5</OrderBy>\n"
                + "<DataAlsoFlag>N</DataAlsoFlag>\n"
                + "</NGOGetDocumentListExt_Input>";
    }
*/

    public static String get_WMCompleteWorkItem_Input(String cabinetName,
            String sessionID,
            String workitemNo,
            String workitemID) {
        return "<?xml version=\"1.0\"?>"
                + "<WMCompleteWorkItem_Input>\n"
                + "<Option>WMCompleteWorkItem</Option>\n"
                + "<EngineName>" + cabinetName + "</EngineName>\n"
                + "<SessionId>" + sessionID + "</SessionId>\n"
                + "<ProcessInstanceId>" + workitemNo + "</ProcessInstanceId>\n"
                + "<WorkItemId>" + workitemID + "</WorkItemId>\n"
                + "</WMCompleteWorkItem_Input>";
    }
    
    public static String APSelectWithColumnNames(String strEngineName,
            String strSessionId, String strQuery) {
		WFInputXml wfInputXml = new WFInputXml();
		wfInputXml.appendStartCallName("APSelectWithColumnNames", "Input");
		wfInputXml.appendTagAndValue("EngineName", strEngineName);
		wfInputXml.appendTagAndValue("SessionId", strSessionId);
		wfInputXml.appendTagAndValue("Query", strQuery);
		wfInputXml.appendEndCallName("APSelectWithColumnNames", "Input");
		return wfInputXml.toString();
	}

    public static String WFUploadWorkItem(String strEngineName,
            String strSessionId,String strProcessDefId,String strValidationRequired,
            String strDocuments,String strAttributes)
    {
    	WFInputXml wfInputXml = new WFInputXml();
		wfInputXml.appendStartCallName("WFUploadWorkItem", "Input");
		wfInputXml.appendTagAndValue("EngineName", strEngineName);
		wfInputXml.appendTagAndValue("SessionId", strSessionId);
		wfInputXml.appendTagAndValue("ProcessDefId", strProcessDefId);
		wfInputXml.appendTagAndValue("ValidationRequired", strValidationRequired);
		wfInputXml.appendTagAndValue("Documents", strDocuments);
		wfInputXml.appendTagAndValue("Attributes", strAttributes);		
		wfInputXml.appendEndCallName("WFUploadWorkItem", "Input");
		return wfInputXml.toString();
    }
    
    public static String WFUploadWorkItemEmail(String strEngineName,
            String strSessionId,String strProcessDefId,String strValidationRequired,
            String strDocuments,String strAttributes,String ActivityId, String sUserDefVarFlag)
    {
    	WFInputXml wfInputXml = new WFInputXml();
		wfInputXml.appendStartCallName("WFUploadWorkItem", "Input");
		wfInputXml.appendTagAndValue("EngineName", strEngineName);
		wfInputXml.appendTagAndValue("SessionId", strSessionId);
		wfInputXml.appendTagAndValue("ProcessDefId", strProcessDefId);
		wfInputXml.appendTagAndValue("ValidationRequired", strValidationRequired);
		wfInputXml.appendTagAndValue("Documents", strDocuments);
		wfInputXml.appendTagAndValue("UserDefVarFlag", sUserDefVarFlag);
		wfInputXml.appendTagAndValue("Attributes", strAttributes);
		wfInputXml.appendTagAndValue("InitiateFromActivityId", ActivityId);
		wfInputXml.appendEndCallName("WFUploadWorkItem", "Input");
		return wfInputXml.toString();
    }

    public static String WMGetProcessList(String strEngineName,
            String strSessionId)
    {
    	WFInputXml wfInputXml = new WFInputXml();
		wfInputXml.appendStartCallName("WMGetProcessList", "Input");
		wfInputXml.appendTagAndValue("EngineName", strEngineName);
		wfInputXml.appendTagAndValue("SessionId", strSessionId);
		wfInputXml.appendEndCallName("WMGetProcessList", "Input");
		return wfInputXml.toString();
    }
    
    public static String WFGetDMSFolderDefinition(String strEngineName,
            String strSessionId, String strProcessDefinitionId)
    {	System.out.println("Superman");
    	WFInputXml wfInputXml = new WFInputXml();
		wfInputXml.appendStartCallName("WFGetDMSFolderDefinition", "Input");
		wfInputXml.appendTagAndValue("EngineName", strEngineName);
		wfInputXml.appendTagAndValue("SessionId", strSessionId);
		wfInputXml.appendTagAndValue("ProcessDefinitionId", strProcessDefinitionId);
		wfInputXml.appendEndCallName("WFGetDMSFolderDefinition", "Input");
		//System.out.println("wfInputXml is Sammy :"+wfInputXml);
		return wfInputXml.toString();
    }
    public static String NGOAddDocument(String strEngineName,String strSessionId,String strParentFolderIndex,String strAccessType,String strDocName,String strCreatedByApp,String strVolID,String strISIndex,String iNoOfpages,String strDocumentType,String strDocumentSize) 
    { 
     return "?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
        + "<NGOAddDocument_Input>\n" 
        + "<Option>NGOAddDocument</Option>\n" 
        + "<CabinetName>" + strEngineName +"</CabinetName>\n" 
        + "<UserDBId>" + strSessionId + "</UserDBId>\n" 
        +"<ParentFolderIndex>"+strParentFolderIndex+"</ParentFolderIndex>\n" 
        +"<AccessType>" + strAccessType + "</AccessType>\n" 
        +"<DocumentName>" + strDocName + "</DocumentName>\n" 
        +"<CreatedByAppName>" + strCreatedByApp + "</CreatedByAppName>\n" 
        +"<VolumeIndex>" + strVolID + "</VolumeIndex>\n" 
        + "<ISIndex>"+strISIndex+"</ISIndex>"+"<NoOfPages>"+iNoOfpages+"</NoOfPages>"+"<DocumentType>"+strDocumentType+"</DocumentType>"+"<DocumentSize>"+strDocumentSize+"</DocumentSize>"+"</NGOAddDocument_Input>"; 
    }
//Added by Shah
    
    public static String WFUploadWorkItem_withActivityName(String strEngineName,
            String strSessionId,String strProcessDefId,String strValidationRequired,
            String strDocuments,String strAttributes,String actID,String actName, String sUserDefVarFlag)
    {
    	WFInputXml wfInputXml = new WFInputXml();
		wfInputXml.appendStartCallName("WFUploadWorkItem", "Input");
		wfInputXml.appendTagAndValue("EngineName", strEngineName);
		wfInputXml.appendTagAndValue("SessionId", strSessionId);
		wfInputXml.appendTagAndValue("ProcessDefId", strProcessDefId);
		wfInputXml.appendTagAndValue("ValidationRequired", strValidationRequired);
		// Added By Ajay Kewat, 17 June.------
		/*
		wfInputXml.appendTagAndValue("UserDefVarFlag", "Y");
		wfInputXml.appendTagAndValue("InitiateAlso", "N");
		//*/
		// --- End ---------
		wfInputXml.appendTagAndValue("Documents", strDocuments);
		wfInputXml.appendTagAndValue("UserDefVarFlag", sUserDefVarFlag);
		wfInputXml.appendTagAndValue("Attributes", strAttributes);
		wfInputXml.appendTagAndValue("InitiateFromActivityId", actID);
		wfInputXml.appendTagAndValue("InitiateFromActivityName", actName);

		wfInputXml.appendEndCallName("WFUploadWorkItem", "Input");
		return wfInputXml.toString();
    }
    
    public static String WMGetWorkItemAttributeValue(String strEngineName,
            String strSessionId, String strProcessDefinitionId, String WorkItemID,String Attribute)
    {	
    	System.out.println("Vikram");
    	WFInputXml wfInputXml = new WFInputXml();
		wfInputXml.appendStartCallName("WMGetWorkItemAttributeValue", "Input");
		wfInputXml.appendTagAndValue("EngineName", strEngineName);
		wfInputXml.appendTagAndValue("SessionID", strSessionId);
		wfInputXml.appendTagAndValue("ProcessInstanceId", strProcessDefinitionId);
		wfInputXml.appendTagAndValue("WorkItemID", WorkItemID);
		//wfInputXml.appendTagAndValue("Attribute", Attribute);
		wfInputXml.appendEndCallName("WMGetWorkItemAttributeValue", "Input");
		System.out.println("WMGetWorkItemAttributeValue is  :"+wfInputXml);
		return wfInputXml.toString();
    }
    
  public static String workItemCompleteCall(String sessionID,String cabinetName,String workitemNo,String workitemID){  
    return  "<?xml version=\"1.0\"?>" + "<WMCompleteWorkItem_Input>\n"
		+ "<Option>WMCompleteWorkItem</Option>\n" + "<EngineName>"
		+ cabinetName + "</EngineName>\n" + "<SessionId>" + sessionID
		+ "</SessionId>\n" + "<ProcessInstanceId>" + workitemNo
		+ "</ProcessInstanceId>\n" + "<WorkItemId>" + workitemID
		+ "</WorkItemId>\n" + "</WMCompleteWorkItem_Input>";
  }
/*    <? Xml Version="1.0"? >
    <WMGetWorkItemAttributeValue _Input>
    <Option>WMGetWorkItemAttributeValue </Option>
    <EngineName></EngineName>
    <SessionID></SessionID >
    <ProcessInstanceId>WF1LOANAPPLICATION</ProcessInstanceId>
    <WorkItemID>1</WorkItemID>
    <Attribute>
    <Name></Name>
    </Attribute>
    </ WMGetWorkItemAttributeValue _Input>*/

    
    
}
