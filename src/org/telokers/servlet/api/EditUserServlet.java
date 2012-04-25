
/**
 @author tommyquangqthinh
*/

package org.telokers.servlet.api;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/editUser.jsp");
		rp.forward(req, resp);
	}
}

