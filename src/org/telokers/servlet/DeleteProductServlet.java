/**
 *
 */
package org.telokers.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.ProductDao;
import org.telokers.model.Product;
import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;

/**
 * @author trung
 *
 */
public class DeleteProductServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 2874915245650876359L;

	private static final Logger logger = Logger.getLogger(DeleteProductServlet.class.getName());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String productId = req.getParameter("productId");
		logger.fine("Deleting product [" + productId + "]");
		Product p = ProductDao.findById(productId);
		if (p == null) {
			resp.sendRedirect("/secured/search?errorMsg=Product not found");
			return;
		}
		User u = (User) req.getAttribute(MiscConstants.KEY_USER);

		if (p.getSeller().equals(u.getUserId()) || u.isAdmin()) {
			ProductDao.deleteById(p);
			resp.sendRedirect("/secured/search?infoMsg=Deleted successfully");
		} else {
			resp.sendRedirect("/secured/search?errorMsg=You're not allowed to delete the product");
			return;
		}
	}
}
