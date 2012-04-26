/**
 *
 */
package org.telokers.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.ProductDao;
import org.telokers.dao.ShoppingCartDao;
import org.telokers.model.Product;
import org.telokers.model.ShoppingCart;
import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;

/**
 * @author trung
 *
 */
public class DeleteFromCartServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 2874915245650876359L;


	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String productId = req.getParameter("productId");
		Product p = ProductDao.findById(productId);
		if (p == null) {
			resp.sendRedirect("/secured/cart?errorMsg=Product not found");
			return;
		}
		ShoppingCart cart = (ShoppingCart) req.getAttribute(MiscConstants.KEY_CART);
		User u = (User) req.getAttribute(MiscConstants.KEY_USER);
		if (cart != null) {
			if (!cart.getProductIds().contains(productId)) {
				resp.sendRedirect("/secured/cart?errorMsg=No such product in your cart");
				return;
			}
		} else {
			cart = new ShoppingCart(u.getUserId());
		}
		cart.removeProduct(productId);
		ShoppingCartDao.persist(cart);
		resp.sendRedirect("/secured/cart?infoMsg=Product removed successfully from shopping cart");
	}
}
