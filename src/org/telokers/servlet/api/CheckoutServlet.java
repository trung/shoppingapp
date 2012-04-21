/**
 *
 */
package org.telokers.servlet.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.service.utils.JSONUtils;

/**
 * @author trung
 *
 */
public class CheckoutServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = -4188326214473736476L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String items = req.getParameter("items");
		if (items == null) {
			resp.sendError(HttpServletResponse.SC_NO_CONTENT, "Items param expected");
		}
		String[] itemIds = items.split(",");
		if (itemIds == null || itemIds.length == 0) {
			resp.sendError(HttpServletResponse.SC_NO_CONTENT, "Item id(s) expected");
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		for (String id : itemIds) {

		}
		ret.put("success", "1");
		resp.setContentType("application/json");

		resp.getWriter().write(JSONUtils.toJSON(ret));
	}
}
