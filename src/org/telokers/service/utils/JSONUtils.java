/**
 *
 */
package org.telokers.service.utils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.telokers.model.response.JSONFriendly;

/**
 * @author trung
 *
 */
public class JSONUtils {
	private static String toJSON(Map<?, ?> map) {
		StringBuffer buf = new StringBuffer();
		buf.append("{");
		if (map == null) {
			return "{}";
		}
		int i = 0;
		for (Object key : map.keySet()) {
			i++;
			if (i > 1) {
				buf.append(",");
			}
			buf.append("\"").append(key).append("\":");
			Object value = map.get(key);
			buf.append(toJSON(value));
		}
		buf.append("}");
		return buf.toString();
	}

	/**
	 * @param value
	 */
	public static String toJSON(Object value) {
		StringBuffer buf = new StringBuffer();
		if (value == null) {
			buf.append("null");
		} else if (value instanceof Collection<?>) {
			buf.append(toJSON((Collection<?>) value));
		} else if (value instanceof Map<?, ?>) {
			buf.append(toJSON((Map<?, ?>) value));
		} else if (value instanceof String) {
			buf.append("\"").append(value).append("\"");
		} else if (value instanceof Date) {
			buf.append("\"").append(value).append("\"");
		} else if (value instanceof JSONFriendly) {
			buf.append(((JSONFriendly) value).toJSONString());
		} else if (value instanceof Number) {
			buf.append(value);
		} else {
			buf.append("\"").append(value).append("\"");
		}
		return buf.toString();
	}

	/**
	 * @param value
	 * @return
	 */
	private static String toJSON(Collection<?> value) {
		StringBuffer buf = new StringBuffer();
		buf.append("[");
		int i = 0;
		for (Object o : value) {
			i++;
			if (i > 1) {
				buf.append(",");
			}
			buf.append(toJSON(o));
		}
		buf.append("]");
		return buf.toString();
	}
}
