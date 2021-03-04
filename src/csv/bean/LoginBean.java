package csv.bean;

public class LoginBean {

	private String id;
	private String domain;
	private String authDomain;
	private String username;
	private String pswd;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String domain, String id) {
		this.username = id + "@" + domain;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getAuthDomain() {
		return authDomain;
	}
	public void setAuthDomain(String authDomain) {
		this.authDomain = authDomain;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	@Override
	public String toString() {
		String print = "";
		print += "{ login: " ;
		print += "{ id:" + id + ", domain: " + domain + ", authDomain: " + authDomain + ", pswd: " + pswd + "}";
		return print;
	}
	
	
}
