package org.telokers.service.utils;

public class Validator {
	// if empty return true 
	// will be called to check userid, password or manditory fields
	public static boolean isEmpty(String input)
	{
		if ((input ==null)|| input.matches("^\\s*$")) 
		{
			return true;
		}
		else 
		{
			return false;
		
		}
	}
	
	public static boolean isNumber(String input)
	{
		return input.matches("-?\\d+(.\\d+)?");
	}
	
	// check CC expiration date 
	// input MMYYYY   e.g. 052013
	public static boolean isCCExpirationDate(String input)
	{	
		return input.matches("0[1-9]|1[0-2]20[1-9][0-9]");
	}
	public static boolean isAlphabet(String input)
	{
		return input.matches("^[a-zA-Z]$");		
	}
	public static boolean isEmail(String input)
	{
		return input.matches("^[a-z|0-9|A-Z]*([_][a-z|0-9|A-Z]+)*([.][a-z|0-9|A-Z]+)*([.][a-z|0-9|A-Z]+)*(([_][a-z|0-9|A-Z]+)*)?@[a-z][a-z|0-9|A-Z]*\\.([a-z][a-z|0-9|A-Z]*(\\.[a-z][a-z|0-9|A-Z]*)?)$");
		 	
	} 	
	// credit card format 4444-4444-4444-4444x`
	public static boolean isCreditCardNumber(String input)
	{
		return input.matches("^((4\\d{3})|(5[1-5]\\d{2})|(6011)|(7\\d{3}))-?\\d{4}-?\\d{4}-?\\d{4}|3[4,7]\\s\\d{13}$");
	}
	// only accept VISA and MASTER
	public static boolean isValidCCType(String input)
	{
		if (input.equals("VISA") || input.equals("MASTER"))
		{return true;
		}
		else return false;
	}
	
	//call when user register new account & pw 
	//check is password the same as user   - if the same return 1
	//is password length more than 8       - if password length less than 8 return 2  
	//is password in alphenumeric format   - if password is not alphanumeric return 3
	//										 else return 0
	
	public static int isValidPW(String username, String password)
	{
		if ( password.equals(username)) return 1;
	 
		if (password.length() <8)	return 2;
			
		if (!password.matches("^[a-zA-Z]+$") || !password.matches("^[0-9]+$")) return 3;
		
		return 0;
		
	}
	// to validate the extension of the image file 
	public static boolean isImageFile(String filename)
	{
		if (filename.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))+$)"))
			return true;
		else return false;
			
		
	}
	
	public static boolean isValidPrice(String productPrice)
	{
		return isNumber(productPrice);
		
	}
	
	//restrict Product name, catergory, seller comments to alphanumeric and space ? !
	// this is in case the output encoding not working
	
	public static boolean isAlphaNumeric(String input)
	{
		if (input.matches("^\\w\\s''-'@\""))
			return true;
		else return false;
	}
}

