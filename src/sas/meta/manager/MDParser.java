package sas.meta.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sas.meta.SASOMI.ISecurity_1_1;
import com.sas.metadata.remote.AuthenticationDomain;
import com.sas.metadata.remote.CMetadata;
import com.sas.metadata.remote.Email;
import com.sas.metadata.remote.Extension;
import com.sas.metadata.remote.Group;
import com.sas.metadata.remote.IdentityGroup;
import com.sas.metadata.remote.Login;
import com.sas.metadata.remote.MdFactory;
import com.sas.metadata.remote.MdOMIUtil;
import com.sas.metadata.remote.MdOMRConnection;
import com.sas.metadata.remote.MdObjectBase;
import com.sas.metadata.remote.MdObjectStore;
import com.sas.metadata.remote.MetadataObjects;
import com.sas.metadata.remote.Person;
import com.sas.metadata.remote.ResponsibleParty;
import com.sas.metadata.remote.TextStore;
import com.sas.metadata.remote.TransformationActivity;
import com.sas.metadata.remote.TransformationStep;
import com.sas.metadata.remote.Tree;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class MDParser {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getRootLogger();
	
	private MdFactory _factory = null;
	private MdObjectStore store = null;
	private CMetadata foundation = null;
	
	public MDParser(MdFactory _factory){
		this._factory=_factory;
	}
	
	public void setUp(CMetadata foundation, MdObjectStore store){
		this.foundation=foundation;
		this.store=store;
	}
	
	public void close() {
		try {
			foundation.dispose();
			_factory.dispose();
			store.dispose();
			
		} catch (Exception e) {}
		
	}
	
	
	
	
	
	
	public void getAuthInfos() {
		if(foundation!=null) {
			try {
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType='AuthenticationDomain' " + /* " and @Name contains '" + username + */ "]\"/>";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	;
				
				
				
				
				List authList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.AUTHENTICATIONDOMAIN,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = authList.iterator();
				System.out.println(authList.size());
				
				while(transItr.hasNext()) {
					AuthenticationDomain auth = (AuthenticationDomain) transItr.next();
					
					Iterator loginItr = auth.getLogins().iterator();
					while(loginItr.hasNext()) {
						
						Login login = (Login)loginItr.next();
						Iterator assocIdsItr = login.getAssociatedIdentitys().iterator();
						
						while(assocIdsItr.hasNext()) {
							
							MdObjectBase group = (MdObjectBase)assocIdsItr.next();
							
							System.out.println(
									">>> AuthenticationDomain: " + auth.getName() 
									+ " - Login: " + login.getName()
									+ " - Object: " + group.getName()
									
									)
							;	
						}		
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void updateLoginPassword(String username, String pswd){
		if(foundation!=null){
			try{
				
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType='Login' " + /* " and @Name contains '" + username + */ "]\"/>";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	;
				
				
				
				
				List loginList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.LOGIN,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = loginList.iterator();
				System.out.println(loginList.size());
				
				while(transItr.hasNext()) {
					Login login = (Login) transItr.next();
					System.out.println(login.getUserID() + " :: " + login.getPassword());
					
					if(login.getUserID().equalsIgnoreCase(username)) {
					
						System.out.println("==> Update Pswd");
						login.setPassword(pswd);
						login.updateMetadataAll();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	

	public void getObject(String name){
		String print = "";
		if(foundation!=null){
			try{

				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType = 'CICampaign_Selection' " + "]\" />";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	
						;
				
				
				
				
				List objectList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.TRANSFORMATIONACTIVITY,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = objectList.iterator();
				System.out.println("Num. of Campaigns: " + objectList.size());
				
				// String regex = "_id:MAValue:(\\w+)";
				// Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
				// String newNote = "<Level1>:MAKey:_businessContextId:MAValue:BFDFQ0T0CFDFTDFT:MAKey:_hidden:MAType:java.lang.Boolean:MAValue:false:MAKey:_decisionCampaignType:MAValue:decisionCampaign:MAKey:_optimizeStatus:MAValue:optimizeNotReady:MAKey:_image:MAValue:null:MAKey:_totalCount:MAType:java.lang.Long:MAValue:2:MAKey:_optimizable:MAType:java.lang.Boolean:MAValue:true:MAKey:_latestApprovalEntryStatus:MAValue:2_3:MAKey:_surrogateKeyNbr:MAType:java.lang.Long:MAValue:10954:MAKey:_endDate:MAValue:null:MAKey:_attachments:MAType:java.lang.Boolean:MAValue:false:MAKey:_minBudget:MAType:java.lang.Double:MAValue:-1.0:MAKey:_name:MAValue:Enrico_prova:MAKey:_eventName:MAValue::MAKey:_description:MAValue::MAKey:_publishDate:MAType:java.util.Date:MAValue:1589210728661:MAKey:_restrictedCampaign:MAType:java.lang.Boolean:MAValue:false:MAKey:_approvalRequired:MAType:java.lang.Boolean:MAValue:true:MAKey:_owner:MAValue:sasdemo:MAKey:_markedForDeploymentVersion:MAValue:0:MAKey:_thisVersionPublished:MAType:java.lang.Boolean:MAValue:true:MAKey:_deploymentType:MAValue:null:MAKey:_lastSavedDate:MAType:java.util.Date:MAValue:1589210728497:MAKey:_code:MAValue:CAMP99999:MAKey:_maxNumOffers:MAType:java.lang.Integer:MAValue:-1:MAKey:_folder:MAType:com.sas.analytics.crm.ma.client.FolderDO:MAValue:<Level2>:MAKey:_name:MAValue:Examples:MAKey:_owner:MAValue::MAKey:_dateModified:MAValue:null:MAKey:_parentFolder:MAValue:CI\\Financial Services\\CI Business Context\\Campaigns\\Outbound:MAKey:_versionNumber:MAValue:6.6</Level2>:MAKey:_subjectIds:MAType:java.util.ArrayList:MAValue:&oblSUBJECT_ID_CUSTOMER&cbl:MAKey:_modifiedUser:MAValue:SAS Demo User:MAKey:_dateModified:MAType:java.util.Date:MAValue:1589210728497:MAKey:_maxBudget:MAType:java.lang.Double:MAValue:-1.0:MAKey:_flowId:MAValue:HEDCDDIYONC4RSPY:MAKey:_status:MAValue:Scheduled:MAKey:_parentGroupSurrogateKeyNbr:MAValue:null:MAKey:_minNumOffers:MAType:java.lang.Integer:MAValue:-1:MAKey:_lastRunDate:MAType:java.util.Date:MAValue:1589210720455:MAKey:_statusId:MAValue:3_2:MAKey:_groupsWhoCanApprove:MAType:java.util.ArrayList:MAValue:&oblCustomer Intelligence Approvers&cbl:MAKey:_importDetails:MAValue:null:MAKey:_currentReportingVersion:MAValue:1:MAKey:_deploymentStatus:MAValue:notMarkedForDeployment:MAKey:_optimizationMethod:MAValue:analytical_optimization:MAKey:_id:MAValue:FDGBW3PGRRESW3AC:MAKey:_flowType:MAValue:maFlow:MAKey:_startDate:MAType:java.util.Date:MAValue:1589209200000:MAKey:_versionNumber:MAValue:6.6</Level1>" ;
				
				
				System.out.println("name, macapaignmetadata, extension, stepName, stepTextStore");
				while(transItr.hasNext()) {
					
					print = ""; 
					TransformationActivity camp = (TransformationActivity) transItr.next();
					print += camp.getName() + "," ;
					
					
					/* STORETEXTs */
					Iterator notesItr = camp.getNotes().iterator();
					while(notesItr.hasNext()) {
						TextStore note = (TextStore) notesItr.next();
						if("MACampaignMetadata".equals(note.getName())) {
							print += "StoreMACAMPAIGN=" + note.getStoredText() + "," ;
						}
						if("MAFlowMetadata".equals(note.getName())) {
							print += "StoreMAFLOW=" + note.getStoredText() + "," ;
						}
					}
					
					/* EXTENSIONs */
					Iterator extItr = camp.getExtensions().iterator();
					while(extItr.hasNext()) {
						Extension ext = (Extension) extItr.next();
						print += ext.getName() ; 
					}
					
					/* STEPs */
					Iterator stepsItr = camp.getSteps().iterator();
					while(stepsItr.hasNext()) {
						TransformationStep step = (TransformationStep) stepsItr.next();
						print += step.getName() + "," ;
						
						Iterator stepNotes = step.getNotes().iterator();
						while(stepNotes.hasNext()) {
							TextStore stepNote = (TextStore)stepNotes.next();
							print += stepNote.getStoredText() + "," ;
							System.out.println(print);
						}
					}
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public List<Group> getGroups(String name){
		List ret = null;
		if(foundation!=null){
			ret = new ArrayList<Group>();
			try{

				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType = 'UserGroup' or @PublicType = 'Role'  ]\" />";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	
						;
				
				
				
				
				List objectList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.IDENTITYGROUP,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = objectList.iterator();
				
				while(transItr.hasNext()) {
					
					IdentityGroup g = (IdentityGroup) transItr.next();
					if(name.equalsIgnoreCase((g.getName()))) {
						ret.add(g);
					}
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return ret;
		
	}
	
	public void printPeople(){
		if(foundation!=null){
			try{
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType = 'User' ]\" />";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	
						;
				
				
				
				
				List objectList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.PERSON,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = objectList.iterator();
				
				while(transItr.hasNext()) {
					Person p = (Person) transItr.next();
					System.out.println(p.getId() + "\t==>\t" + p.getName() + "\t==>\t" + "aaatest".equalsIgnoreCase(p.getName()));
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public List<Person> getPeople(){
		List<Person> metaUsers = null;
		if(foundation!=null){
			try{
				metaUsers = new ArrayList<Person>();
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType = 'User' ]\" />";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	
						;
				
				
				
				
				List objectList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.PERSON,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = objectList.iterator();
				while(transItr.hasNext()) {
					Person p = (Person) transItr.next();
					metaUsers.add(p);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return metaUsers;
	}
	
	public void getFolder(){
		if(foundation!=null){
			try{
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType = 'User' ]\" />";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	
						;
				
				
				
				
				List objectList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.PERSON,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = objectList.iterator();
				System.out.println("Found: " + objectList.size() + " Person");
				while(transItr.hasNext()) {
					Person p = (Person) transItr.next();
					System.out.println(p.getId() + "\t==>\t" + p.getName() + "\t==>\t" + "aaatest".equalsIgnoreCase(p.getName()));
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void printTree(){
		if(foundation!=null){
			try{
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType = 'Folder' ]\" />";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	
						;
				
				
				
				
				List objectList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.TREE,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = objectList.iterator();
				System.out.println("Found: " + objectList.size() + " Person");
				while(transItr.hasNext()) {
					Tree t = (Tree) transItr.next();
					System.out.println(t.getName());
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public List<ResponsibleParty> getRespParties(String name){
		List<ResponsibleParty> ret = null;
		if(foundation!=null){
			try{
				ret = new ArrayList<ResponsibleParty>();
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@ID = 'A54EIXO5.AV000002' or @ID = 'A54EIXO5.AV000001' ]\" />";
				
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	
						;
				
				
				
				
				List objectList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.RESPONSIBLEPARTY,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = objectList.iterator();
				System.out.println("Found: " + objectList.size() + " Resp Parties");
				while(transItr.hasNext()) {
					
					ResponsibleParty p = (ResponsibleParty) transItr.next();
					ret.add(p);
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return ret;
		
	}
	

	public void deletePerson(String name){
		if(foundation!=null){
			try{
				LOGGER.info("Deleting: " + name);
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType = 'User' ]\" />";
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	
						;
				
				List objectList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.PERSON,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = objectList.iterator();
				LOGGER.info("Found: " + objectList.size() + " Person");
				while(transItr.hasNext()) {
					
					Person p = (Person) transItr.next();
					// System.out.println("--> NAME: " + p.getName());
					if(name.equalsIgnoreCase((p.getName()))) {
						System.out.println("Delete PERSON >> " + name);
						_factory.deleteMetadataObject(p);
					}
					
				}
				
				xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType = 'Login' ]\" />";
				
				
				objectList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.LOGIN,
	                    flags,
	                    sOptions
	                    );
				
				transItr = objectList.iterator();
					
				while(transItr.hasNext()) {
					Login l = (Login) transItr.next();
					if(name.equalsIgnoreCase((l.getName()))) {
						LOGGER.info("Deleting LOGIN >> " + name);
						_factory.deleteMetadataObject(l);
					}
				}
				
				store.dispose();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public AuthenticationDomain getAuthDomain(String name) {
		AuthenticationDomain authDom = null;
		if(foundation!=null){
			try{
				String xmlSelect = 
						"<XMLSELECT " +
								"Search=\"*[@PublicType = 'AuthenticationDomain' ]\" />";
				
				String sOptions = xmlSelect ; 
			
				int flags = 
						MdOMIUtil.OMI_XMLSELECT 	|  
						MdOMIUtil.OMI_ALL_SIMPLE 	| 
						MdOMIUtil.OMI_GET_METADATA	
						;
				
				List objectList = _factory.getOMIUtil().getMetadataObjectsSubset(
	                    store,
	                    foundation.getFQID(),
	                    MetadataObjects.AUTHENTICATIONDOMAIN,
	                    flags,
	                    sOptions
	                    );
				
				
				Iterator transItr = objectList.iterator();
				
				while(transItr.hasNext()) {
					
					AuthenticationDomain p = (AuthenticationDomain) transItr.next();
					if(name.equalsIgnoreCase((p.getName()))) {
						return p;
					}
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return authDom;
	}
	
	public void addPerson(String name, String displayName, String pswd, String desc, 
			String title, String authName, String domain, List<String> gNames, List<String> eMails, String loginId, int isInternal){
		if(foundation!=null){
			try{
				String reposFQID = foundation.getFQID();
				String shortReposID = reposFQID.substring(reposFQID.indexOf(".") + 1, reposFQID.length());

				Person p = (Person) _factory.createComplexMetadataObject(
						store,
                        null,
                        name,
                        MetadataObjects.PERSON,
                        shortReposID);
				
				p.setPublicType("User");
				p.setUsageVersion(1000000.0);
				p.setTitle(title);
				p.setDesc(desc);
				p.setDisplayName(displayName);
				
				if(isInternal != 1) {
					
					Login l = (Login) _factory.createComplexMetadataObject(
							store,
							null,
							"Login." + name + ".39",
							MetadataObjects.LOGIN,
							shortReposID);
					
					
					AuthenticationDomain authDom = getAuthDomain(authName);
					l.getDomains().add(authDom);
					l.setUserID(domain + "\\" + loginId );
					l.setPassword(pswd);
					
					p.getLogins().add(l);
				}
				for(String goup : gNames) {
					List<Group> groups = getGroups(goup);
					p.getIdentityGroups().addAll(groups);
				}
				for(String email : eMails) {
					Email em = (Email) _factory.createComplexMetadataObject(
							store,
							null,
							"Login." + name + ".39",
							MetadataObjects.EMAIL,
							shortReposID);
					em.setAddress(email);
					em.setName(email);
					p.getEmailAddresses().add(em);
				}
				
				p.updateMetadataAll();
				LOGGER.debug("Add user: " + p.getId() + " " + p.getName());
				
				if(isInternal == 1) {
					setInternalLogin(name, pswd);
				}
				
			}catch(Exception e){
				LOGGER.error("User: " + name);
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	public boolean setInternalLogin(String userName, String pswd) {
		boolean ret = false;
		if(_factory!=null) {
			try { 
				MdOMRConnection connection = _factory.getConnection();
				ISecurity_1_1 is = connection.MakeISecurityConnection(); 
				is.SetInternalPassword(userName, pswd);
				ret = true;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
		
		
	}
	

}
