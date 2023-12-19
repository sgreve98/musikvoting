package sqlDummys;

public class Titel {

	String titel = "";
	String interpret = "";
	String genre = "";

	public Titel(String titel, String interpret, String genre){
		this.titel = titel;
		this.interpret = interpret;
		this.genre = genre;
	}

	public String getTitel(){
		return titel;
	}

}
