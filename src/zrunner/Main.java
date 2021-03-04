package zrunner;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import csv.bean.UserBean;
import csv.manager.CSVManager;
import sas.meta.manager.MDManager;
import users.manager.PeopleManager;
import utils.props.PropsGeneral;

public class Main {

	private final String _PROPS_FOLDER = "props";
	private final String _LOG4J_FILE_NAME = "log4j.properties";
	private Properties p4j = new Properties();
	private MDManager md;
	private CSVManager csv;
	private PeopleManager pm;
	private PropsGeneral prop;

	Logger logger = Logger.getLogger(this.getClass());
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	
	private boolean loadProps(){
		boolean ret = true;
		/**
		 * Load Log4J Properties
		 */
		BasicConfigurator.configure();
		try {
			p4j.load(new FileInputStream(_PROPS_FOLDER + "/" + _LOG4J_FILE_NAME));
		} catch (Exception e) {
			ret = false;
		} 
		PropertyConfigurator.configure(p4j);
		/**
		 * Load Config Properties
		 */
		try {
			prop = new PropsGeneral();
			if(!prop.isConfigFound())
				ret = false;
		}catch(Exception e) {
			ret = false;
		}
		return ret;
	}
	
	private boolean initObjs(){
		loadProps();
		boolean ret = true;
		try {
			md = new MDManager();
			csv = new CSVManager();
			pm = new PeopleManager();
			
		}catch(Exception e) {
			e.printStackTrace();
			ret = false;
		}
		return ret;
	}
	
	public void run() {
		Timestamp started = new Timestamp(System.currentTimeMillis());
		/** Load Properties */
		if(loadProps()) {
			logger.info("Started at: " + sdf.format(started));
			/** Connect 2 MetadataServer and Setup XML */
			@SuppressWarnings("unused")
			boolean initObjs = initObjs();
			if(md.connectToServer()) {
				/** Extract Meta List and diff with CSV users */
				List<UserBean> deltaUser = pm.getDiff(csv.getPeople(), md.getPeople());
				/** Add new Users if any */
				if(deltaUser!=null && deltaUser.size()>0) {
					md.addUsers(deltaUser);
					for(UserBean us : deltaUser)
						logger.info("New User: " + us);
				}
				else
					logger.info("No new Users!");
				/** Print Updated list */
				for(UserBean u : md.getPeople())
					logger.debug(u);
			}
		}
		Timestamp finished = new Timestamp(System.currentTimeMillis());
		Long delta = (finished.getTime() - started.getTime());
		logger.info("Execution took: " + delta + "ms");
		logger.info("END at : " + sdf.format(finished));
	}
	
	public void readUsers() {
		csv.printUsers();
	}
	
	public void getUsers() {
		for(UserBean u : md.getPeople()) {
			logger.info(u);
		}
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		m.run();
	}

}
