
/**
 @author tommyquangqthinh
*/

package org.telokers.service.utils;

public class MiscUtils {
	
	public static boolean isNullorBlank(String input){
		if (input == null || input.length() == 0){
			return true;
		}
		else return false;
	}
	
	public static String blankifyString(String input){
		if(isNullorBlank(input)){
			input = "";
		}
		return input;
	}
	
	public static class ErrorMessageHolder {
		public String userIdErrorMsg = "";
		public String nameErrorMsg = "";
		public String emailErrorMsg = "";
		public String passwordErrorMsg = "";
		public String cardHolderNameErrorMsg = "";
		public String typeOfCardErrorMsg = "";
		public String cardNumberErrorMsg = "";
		public String expiryDateErrorMsg = "";
	}
	
}

