package users.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import csv.bean.UserBean;


public class PeopleManager {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getRootLogger();
	
	public List<UserBean> getDiff(List<UserBean> lsrc, List<UserBean> ltgt){
		List<UserBean> ret = new ArrayList<UserBean>();
		ret = lsrc;
		ret.removeAll(ltgt);
		return ret;
	}
	
	
}
