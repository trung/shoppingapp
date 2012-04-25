/**
 *
 */
package org.telokers.service.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.spec.KeySpec;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Encoding, decoding, sql injection resolver ... all here
 *
 * @author trung
 *
 */
public class SecurityUtils {
	private static final Logger logger = Logger.getLogger(SecurityUtils.class.getName());

	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * Encode string by using {@link URLEncoder}
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		if (str == null) {
			return null;
		}
 		try {
			return URLEncoder.encode(str, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, "Unable to encode string due to ", e);
			return null;
		}
	}
	
	public static String hashPassword(String password){
		byte[] salt = new byte[16];
		byte[] hash = null;
		Random random = new Random(); 
		//random.nextBytes(salt);
		//KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 2048, 160);
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 2048, 160);
		SecretKeyFactory f = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		}
		catch (Exception e){
			logger.log(Level.SEVERE, e.getMessage());
		}
		
		return (new BigInteger(1, hash).toString(16)).toString();
	}
	
	public static void main(String[] args){
		System.out.println(hashPassword("abc123"));
	}
}
