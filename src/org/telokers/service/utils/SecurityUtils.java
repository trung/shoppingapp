/**
 *
 */
package org.telokers.service.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

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
}
