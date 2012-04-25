/**
 *
 */
package org.telokers.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author trung
 *
 */
public class HomeServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 6437331089093059433L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/home.jsp");
		rp.forward(req, resp);
	}
}
