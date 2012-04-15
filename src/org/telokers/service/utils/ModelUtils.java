/**
 *
 */
package org.telokers.service.utils;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author trung
 *
 */
public class ModelUtils {
	public static Collection<String> decompose(String str) {
		String[] strings = str.split("\\s");
		return Arrays.asList(strings);
	}
}
