/**
 *
 */
package org.telokers.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.ProductDao;
import org.telokers.model.Product;
import org.telokers.service.utils.MiscConstants;

/**
 * @author trung
 *
 */
public class SearchServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 953151616169059805L;


	private static Logger logger = Logger.getLogger(SearchServlet.class.getName());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String q = req.getParameter("q");
		List<Product> list = new ArrayList<Product>();
		if (q == null || q.length() == 0) {
			list = ProductDao.findAll();
		} else {
			Product p = ProductDao.findById(q);
			if (p != null) { // just display the details
				req.setAttribute(MiscConstants.KEY_MY_EDIT_PRODUCT, p);
			} else {
				list = ProductDao.findByKeyword(q);
			}
		}
		if (list == null) {
			list = new ArrayList<Product>();
		}
		req.setAttribute(MiscConstants.KEY_ALL_PRODUCTS, list);
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/search.jsp").forward(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
