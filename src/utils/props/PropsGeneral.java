package utils.props;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class PropsGeneral implements Props{
	
	private final String _PROPS_FOLDER = "props";
	private final String _PROPS_CONFIG_NAME = "config.properties";
	private static final Logger LOGGER = Logger.getRootLogger();
	
	private boolean configFound;
	
	private Properties prop = new Properties();
	
	private String csvPath;
	private int csvIdPos;
	private int csvSurnamePos;
	private int csvNamePos;
	private int csvTitlePos;
	private int csvDescrPos;
	private int csvMailsPos;
	private int csvGroupsPos;
	private int csvDomainPos;
	private int csvAuthDomainPos;
	private int csvPswdPos;
	
	
	private String metaServerName;
	private String metaServerPort;
	private String metaServerUser;
	private String metaServerPass;


	

	public void loadProps(){
		/**
		 * Load Project Properties
		 */
		
		InputStream input = null;
		try{
			input = new FileInputStream(_PROPS_FOLDER + "/" + _PROPS_CONFIG_NAME);
			prop.load(input);
			
			
			csvPath  = prop.getProperty("engine.csv.path");

			csvIdPos = Integer.valueOf(prop.getProperty("csv.id.pos"));
			csvSurnamePos = Integer.valueOf(prop.getProperty("csv.surname.pos"));
			csvNamePos = Integer.valueOf(prop.getProperty("csv.name.pos"));
			csvTitlePos = Integer.valueOf(prop.getProperty("csv.title.pos"));
			csvDescrPos = Integer.valueOf(prop.getProperty("csv.description.pos"));
			csvMailsPos = Integer.valueOf(prop.getProperty("csv.mails.pos"));
			csvGroupsPos = Integer.valueOf(prop.getProperty("csv.groups.pos"));
			csvDomainPos = Integer.valueOf(prop.getProperty("csv.domain.pos"));
			csvAuthDomainPos = Integer.valueOf(prop.getProperty("csv.authdomain.pos"));
			csvPswdPos = Integer.valueOf(prop.getProperty("csv.pswd.pos"));
			
			
			metaServerName = prop.getProperty("meta.env.addr");
			metaServerPort = prop.getProperty("meta.env.port");
			metaServerUser = prop.getProperty("meta.user.name");
			metaServerPass = prop.getProperty("meta.user.pwd");
			
			
			configFound = true;
			
		}catch(Exception e){
			
			
			System.err.println("SAS Metadata Lineage use a <config.properties> file for all settings.");
			System.err.println("Please, provide a proper <config.properties> file.");
			
			configFound = false;
		}
	}
	

	public void printProps(){
		String print = "";
		
		print += "------------ <CONNECTION DETAILS> ------------------" ;
		print += "\n" + "Metadata Server: " + metaServerName + ":" + metaServerPort;
		print += "\n" + "Meta Credential: " + metaServerUser + "::Base64(" + Base64.getEncoder().encode(metaServerPass.getBytes()) + ")";
		print += "\n" + "CSV Path: " + csvPath ; 
		print += "\n" + "------------ </CONNECTION DETAILS> ------------------" ;
		
		LOGGER.info(print);
	}
	
	public PropsGeneral (){
		loadProps();
	}


	public String getMetaServerName() {
		return metaServerName;
	}
	

	public void setMetaServerName(String metaServerName) {
		this.metaServerName = metaServerName;
	}
	

	public String getMetaServerPort() {
		return metaServerPort;
	}
	

	public void setMetaServerPort(String metaServerPort) {
		this.metaServerPort = metaServerPort;
	}
	

	public String getMetaServerUser() {
		return metaServerUser;
	}
	

	public void setMetaServerUser(String metaServerUser) {
		this.metaServerUser = metaServerUser;
	}
	

	public String getMetaServerPass() {
		return metaServerPass;
	}
	

	public void setMetaServerPass(String metaServerPass) {
		this.metaServerPass = metaServerPass;
	}



	public boolean isConfigFound() {
		return configFound;
	}

	public void setConfigFound(boolean configFound) {
		this.configFound = configFound;
	}


	public String getCsvPath() {
		return csvPath;
	}


	public void setCsvPath(String csvPath) {
		this.csvPath = csvPath;
	}


	public int getCsvIdPos() {
		return csvIdPos;
	}


	public void setCsvIdPos(int csvIdPos) {
		this.csvIdPos = csvIdPos;
	}


	public int getCsvSurnamePos() {
		return csvSurnamePos;
	}


	public void setCsvSurnamePos(int csvSurnamePos) {
		this.csvSurnamePos = csvSurnamePos;
	}


	public int getCsvNamePos() {
		return csvNamePos;
	}


	public void setCsvNamePos(int csvNamePos) {
		this.csvNamePos = csvNamePos;
	}


	public int getCsvTitlePos() {
		return csvTitlePos;
	}


	public void setCsvTitlePos(int csvTitlePos) {
		this.csvTitlePos = csvTitlePos;
	}


	public int getCsvDescrPos() {
		return csvDescrPos;
	}


	public void setCsvDescrPos(int csvDescrPos) {
		this.csvDescrPos = csvDescrPos;
	}


	public int getCsvMailsPos() {
		return csvMailsPos;
	}


	public void setCsvMailsPos(int csvMailsPos) {
		this.csvMailsPos = csvMailsPos;
	}


	public int getCsvGroupsPos() {
		return csvGroupsPos;
	}


	public void setCsvGroupsPos(int csvGroupsPos) {
		this.csvGroupsPos = csvGroupsPos;
	}


	public int getCsvDomainPos() {
		return csvDomainPos;
	}


	public void setCsvDomainPos(int csvDomainPos) {
		this.csvDomainPos = csvDomainPos;
	}


	public int getCsvAuthDomainPos() {
		return csvAuthDomainPos;
	}


	public void setCsvAuthDomainPos(int csvAuthDomainPos) {
		this.csvAuthDomainPos = csvAuthDomainPos;
	}


	public int getCsvPswdPos() {
		return csvPswdPos;
	}


	public void setCsvPswdPos(int csvPswdPos) {
		this.csvPswdPos = csvPswdPos;
	}

	
	
	
	
	
}
