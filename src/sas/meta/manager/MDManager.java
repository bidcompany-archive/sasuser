package sas.meta.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sas.metadata.remote.CMetadata;
import com.sas.metadata.remote.MdException;
import com.sas.metadata.remote.MdFactory;
import com.sas.metadata.remote.MdFactoryImpl;
import com.sas.metadata.remote.MdOMIUtil;
import com.sas.metadata.remote.MdOMRConnection;
import com.sas.metadata.remote.MdObjectStore;
import com.sas.metadata.remote.Person;

import csv.bean.UserBean;
import utils.props.PropsGeneral;




@SuppressWarnings("rawtypes")
public class MDManager {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getRootLogger();
	/**
	* The following statements instantiate the object factory.
	*/
	private MdFactory _factory = null;
	private MdObjectStore store = null;
	
	
	private MDParser mdchain;
	
	private PropsGeneral prop;
	
	public void close() {
		try {
			_factory.dispose();
			store.dispose();
			mdchain.close();
		}catch(Exception e) {}
	}
	
	
	/**
	 * Connection parameters
	 * This are the default pars, used for SASBAP environment
	 */
	private String serverName = "sasserver";
	private String serverPort = "8561";
	private String serverUser = "sasdemo";
	private String serverPass = "Orion123";
	

	/**
	 *  Structure Table Lineage
	 *  Used to create Lists
	 */
	
	
	
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerUser() {
		return serverUser;
	}

	public void setServerUser(String serverUser) {
		this.serverUser = serverUser;
	}

	public String getServerPass() {
		return serverPass;
	}

	public void setServerPass(String serverPass) {
		this.serverPass = serverPass;
	}

	
	/**
	* Pointing constructor
	*/
	public MDManager(){
		prop = new PropsGeneral();
		
		this.serverName = prop.getMetaServerName();
		this.serverPort = prop.getMetaServerPort();
		this.serverUser = prop.getMetaServerUser();
		this.serverPass = prop.getMetaServerPass();
		// Calls the factory’s constructor
		initializeFactory();
	}
	
	
	/**
	 * Init _factory
	 */
	private void initializeFactory(){
		try{
			//	Initializes the factory. The Boolean parameter is used to
			// determine if the application is running in a remote or local
			// environment. If the data does not need to be accessible across
			// remote JVMs, then "false" can be used, as shown here.
			_factory = new MdFactoryImpl(false);
			// Defines debug logging, but does not turn it on.
			boolean debug = false;
			if (debug){
				_factory.setDebug(false);
				_factory.setLoggingEnabled(false);
				//	Sets the output streams for logging. The logging output can be
				// directed to any OutputStream, including a file.
				_factory.getUtil().setOutputStream(System.out);
				_factory.getUtil().setLogStream(System.out);
			}
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	/**
	* The following statements define variables for SAS Metadata Server
	* connection properties, instantiate a connection factory, issue
	* the makeOMRConnection method, and check exceptions for error conditions.
	*
	*/
	public boolean connectToServer(){
		
		try{
			MdOMRConnection connection = _factory.getConnection();
			/*connection.makeISecurityConnection(serverName,
											serverPort,
											serverUser,
											serverPass);
			*/
			// This statement makes the connection to the server.
			connection.makeOMRConnection(
											serverName,
											serverPort,
											serverUser,
											serverPass
										);
			// The following statements define error handling and error
			// reporting messages.
			
		}
		catch (MdException e){
			Throwable t = e.getCause();
			if (t != null){
				String ErrorType = e.getSASMessageSeverity();
				String ErrorMsg = e.getSASMessage();
				if (ErrorType == null){
					// If there is no SAS server message, write a Java/CORBA message.
				}
				else{
					//Using the SAS Java Metadata Interface Getting Information About Repositories 29
					// If there is a message from the server:
					System.out.println(ErrorType + ": " + ErrorMsg);
				}
				if (t instanceof org.omg.CORBA.COMM_FAILURE){
					// If there is an invalid port number or host name:
					System.out.println(e.getLocalizedMessage());
				}
				else if (t instanceof org.omg.CORBA.NO_PERMISSION){
					// If there is an invalid user ID or password:
					System.out.println(e.getLocalizedMessage());
				}
			}
			else{
				// If we cannot find a nested exception, get message and print.
				System.out.println(e.getLocalizedMessage());
			}
			// If there is an error, print the entire stack trace.
			e.printStackTrace();
			return false;
		} catch (java.rmi.RemoteException e) {
			e.printStackTrace();
			return false;
		}
		// If no errors occur, then a connection is made.
		mdchain = new MDParser( _factory );
		
	return true;
	}
	

	public void updatePassword(String username, String pswd) {
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			
			mdchain.updateLoginPassword(username, pswd);
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void getUserInfos() {
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			
			mdchain.getAuthInfos();
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void getObject() {
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			
			mdchain.getObject("");
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void deletePerson(String name) {
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			
			mdchain.deletePerson(name);
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void printTree() {
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			mdchain.printTree();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void printPeople() {
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			mdchain.printPeople();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void addPerson(String name, String displayName, String pswd, String desc, String off, String authName, String domain, List<String> gNames, List<String> eMails , String loginId) {
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			
			
			mdchain.setUp(foundation, store);
			// String name, String pswd, String authName, String domain, List<String> gNames, List<String> eMails
			mdchain.addPerson(name, displayName, pswd, desc, off, authName, domain, gNames, eMails, loginId, prop.getUserIIdentity());
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	

	public void addPerson(UserBean user) {
		// USER
		String name = user.getId();
		String desc = user.getDescription();
		String title = user.getTitle();
		String displayName = user.getDisplayName();
		// LIST
		List<String> gNames = user.getGroups();
		List<String> eMails = user.getEmails();
		// LOGINS
		
		String loginid = user.getLogin().getId();
		String pswd = user.getLogin().getPswd();
		String authName = user.getLogin().getAuthDomain();
		String domain = user.getLogin().getDomain();
		
		
		mdchain.addPerson(name, displayName, pswd, desc, title, authName, domain, gNames, eMails, loginid, prop.getUserIIdentity());
	}
	
	public void addUsers(List<UserBean> users) {
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			for(UserBean user : users) {
				addPerson(user);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public List<UserBean> getPeople() {
		List<UserBean> users = new ArrayList<UserBean>();
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			// String name, String pswd, String authName, String domain, List<String> gNames, List<String> eMails
			for(Person p : mdchain.getPeople()) {
				UserBean user = new UserBean();
				user.setMetaId(p.getId());
				user.setId(p.getName());
				user.setDisplayName(p.getDisplayName());
				user.setTitle(p.getTitle());
				user.setDescription(p.getDesc());
				users.add(user);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return users;
	}
	
	
	
	
}
