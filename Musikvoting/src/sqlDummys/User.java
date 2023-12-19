package sqlDummys;

public class User {

	public String username = "";
	public boolean gastgeber = false;

	public User(String username, boolean gastgeber){
		this.username = username;
		this.gastgeber = gastgeber;
	}

	public String getUsername(){
		return username;
	}
}
