package csv.bean;

import java.util.ArrayList;
import java.util.List;

public class UserBean {
	private String id;
	private String displayName;
	private String title;
	private String description;
	private List<String> emails;
	private List<String> groups;
	private List<String> roles;
	private LoginBean login;
	private String metaId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String surname, String name) {
		this.displayName = surname + " " + name;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getEmails() {
		return emails;
	}
	public void setEmail(String lst) {
		String[] vett = lst.split(",");
		this.emails = new ArrayList<String>();
		for(String em : vett) {
			this.emails.add(em);
		}
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	public List<String> getGroups() {
		return groups;
	}
	public void setGroups(String lst) {
		String[] vett = lst.split(",");
		this.groups = new ArrayList<String>();
		for(String em : vett) {
			this.groups.add(em);
		}
	}
	public List<String> getRoles(){
		return roles;
	}
	public void setRoles(String lst) {
		String[] vett = lst.split(",");
		this.roles = new ArrayList<String>();
		for(String rl : vett) {
			this.roles.add(rl);
		}
	}
	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
	
	
	public String getMetaId() {
		return metaId;
	}
	public void setMetaId(String metaId) {
		this.metaId = metaId;
	}
	public LoginBean getLogin() {
		return login;
	}
	public void setLogin(String id, String domain, String pswd, String authDomain) {
		login = new LoginBean();
		login.setId(id);
		login.setUsername(domain, id);
		login.setPswd(pswd);
		login.setAuthDomain(authDomain);
		login.setDomain(domain);
	}
	public void setLogin(LoginBean login) {
		this.login = login;
	}
	@Override
	public String toString() {
		String print = "";
		print += "{ user: " ; 
		print += "{ " ; 
		if(metaId!=null && !"".equals(metaId))
			print += "uri: " + metaId + ", " ;
		print += "id:" + id + ", displayName:" + displayName + ", title:" + title 
				+ ", description:" + description + ", username: " + login.getUsername(); 
		print += "}";
		print += "}" ; 
		return print;
	}
	
	// UserBean sono uguali quando hanno lo stesso nome
	@Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof UserBean)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        UserBean c = (UserBean) o; 
          
        // Compare the data members and return accordingly  
        return getId().equalsIgnoreCase(c.getId()); 
    } 
	
	

}
