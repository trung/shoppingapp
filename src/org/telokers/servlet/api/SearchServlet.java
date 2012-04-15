/**
 *
 */
package org.telokers.servlet.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.service.utils.SecurityUtils;

/**
 *
 * Perfomr AJAX search, support POST only
 * Accept param: query, return JSON search results
 *
 * @author trung
 *
 */
public class SearchServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = -6290174023462688988L;


	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String inputQuery = req.getParameter("query");
		String query = SecurityUtils.encode(inputQuery);
		if (query == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

	}

}
