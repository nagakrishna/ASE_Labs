
public class ReplaceSpaces {
		
	public String getText(String text){
		return (text.replace(" ", "%20"));
	}
	
	public Boolean verifyPlaceNotANumber(String place){
		final char c = place.charAt(0);
	    return (c >= '0' && c <= '9');
	}

	

}
