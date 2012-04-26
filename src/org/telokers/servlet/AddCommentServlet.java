/**
 *
 */
package org.telokers.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.CommentDao;
import org.telokers.dao.PaymentTransactionDao;
import org.telokers.dao.ProductDao;
import org.telokers.model.Comment;
import org.telokers.model.Comment.CommentProperty;
import org.telokers.model.Product;
import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;
import org.telokers.service.utils.Validator;

/**
 * @author trung
 *
 */
public class AddCommentServlet extends HttpServlet {

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
			resp.sendRedirect("/secured/search?errorMsg=Product not found");
			return;
		}
		String commentStr  = req.getParameter("comment");
		String rating = req.getParameter("rating");
		if (Validator.isEmpty(commentStr) || !Validator.isNumber(rating)) {
			resp.sendRedirect("/secured/search?errorMsg=Comment is empty or invalid rating value");
			return;
		}
		User u = (User) req.getAttribute(MiscConstants.KEY_USER);
		if (PaymentTransactionDao.hasTransaction(productId, u.getUserId())) {
			Comment comment = new Comment(UUID.randomUUID().toString());
			comment.setProperty(CommentProperty.userId, u.getUserId());
			comment.setProperty(CommentProperty.productId, productId);
			comment.setProperty(CommentProperty.createdDate, new Date());
			comment.setProperty(CommentProperty.comment, commentStr);
			comment.setProperty(CommentProperty.rating, Integer.parseInt(rating));
			p.addRating(Integer.parseInt(rating));
			ProductDao.persist(p);
			CommentDao.persist(comment);
			resp.sendRedirect("/secured/search?infoMsg=Comment added successfully");
		} else {
			resp.sendRedirect("/secured/search?errorMsg=You're not allowed to comment on this product");
		}
	}
}
