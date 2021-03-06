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
import org.telokers.model.ShoppingCart;
import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;

/**
 * @author trung
 *
 */
public class CartServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 6428022292352162972L;

	private static final Logger logger = Logger.getLogger(CartServlet.class.getName());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ShoppingCart cart = (ShoppingCart) req.getAttribute(MiscConstants.KEY_CART);
		User u = (User) req.getAttribute(MiscConstants.KEY_USER);
		if (cart == null) {
			cart = new ShoppingCart(u.getUserId());
		}
		List<Product> list = new ArrayList<Product>();
		List<String> productIds = cart.getProductIds();
		for (String pid : productIds) {
			Product p = ProductDao.findById(pid);
			list.add(p);
		}
		req.setAttribute(MiscConstants.KEY_ALL_PRODUCTS, list);
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
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
