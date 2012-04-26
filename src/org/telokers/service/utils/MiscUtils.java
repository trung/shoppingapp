
/**
 @author tommyquangqthinh
*/

package org.telokers.service.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MiscUtils {

	private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

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

	/**
	 * @param d
	 * @return
	 */
	public static String formatDateTime(Date d) {
		if (d == null) {
			return  "";
		}
		return new SimpleDateFormat(DATE_TIME_FORMAT).format(d);
	}

	public static String formatPrice(double d) {
		return String.valueOf(new DecimalFormat("####00.##").format(d));
	}
}

