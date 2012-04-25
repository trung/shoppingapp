/**
 *
 */
package org.telokers.service.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author trung
 *
 */
public class RequestUtils {
	public static String getAttribute(HttpServletRequest request, String name) {
		String v = (String) request.getAttribute(name);
		if (v != null) {
			v = HTMLEncode.encode(v.trim());
		} else {
			v = "";
		}
		return v;
	}
}
