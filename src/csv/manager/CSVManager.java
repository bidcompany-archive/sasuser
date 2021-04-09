package csv.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import csv.bean.UserBean;
import utils.props.PropsGeneral;

public class CSVManager {
	
	
	private PropsGeneral prop;
	private static final Logger LOGGER = Logger.getRootLogger();
	
	private final int _NUM_OF_FIELDS = 11;
	
	private List<UserBean> users;
	
	private boolean checkFields(String str) {
		return str.split(";").length == _NUM_OF_FIELDS;
	}
	
	private UserBean getBeanFromLine(String content) {
		
		UserBean user = null; 
		
		try {
			String line[] = content.split(";");
			user = new UserBean();
			user.setId(line[prop.getCsvIdPos()]);
			user.setDisplayName(line[prop.getCsvSurnamePos()], line[prop.getCsvNamePos()]);
			user.setTitle(line[prop.getCsvTitlePos()]);
			user.setDescription(line[prop.getCsvDescrPos()]);
			user.setEmail(line[prop.getCsvMailsPos()]);
			user.setGroups(line[prop.getCsvGroupsPos()]);
			user.setLogin(
					line[prop.getCsvLoginIdPos()], 
					line[prop.getCsvDomainPos()], 
					line[prop.getCsvPswdPos()], 
					line[prop.getCsvAuthDomainPos()]
			);
			
			return user;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean readCSV() {
		users = new ArrayList<UserBean>();
		try {
			LOGGER.debug("Reading file users [ " + prop.getCsvPath() + "]");
			BufferedReader br = new BufferedReader(new FileReader(prop.getCsvPath()));
	        String content = br.readLine();
	        if(content.contains("cognome")) { // SKIP HEADER
	        	LOGGER.debug("Header: [" + content + "]");
	        	content = br.readLine();
	        }
	        while(content!=null) {
	        	if(checkFields(content)) {
	        		users.add(getBeanFromLine(content));
	        	}
	        	content = br.readLine();
	        }
	        br.close();
			return true;
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}
	
	public void printUsers() {
		for(UserBean u : users) {
			LOGGER.info(u);
		}
	}
	
	public List<UserBean> getPeople(){
		return users;
	}
	
	public CSVManager() {
		prop = new PropsGeneral();
		readCSV();
	}
	
	
	

}
