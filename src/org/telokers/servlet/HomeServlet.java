/**
 *
 */
package org.telokers.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.PaymentTransactionDao;
import org.telokers.dao.ProductDao;
import org.telokers.dao.UserDao;
import org.telokers.model.Product;
import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;
import org.telokers.service.utils.Validator;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * @author trung
 *
 */
public class HomeServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 6437331089093059433L;

	private static final Logger logger = Logger.getLogger(HomeServlet.class.getName());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		logger.fine("Action is [" + action + "]");
		if ("create".equalsIgnoreCase(action)) {
			createProduct(req, resp);
		} else if ("save".equalsIgnoreCase(action)){
			saveProduct(req, resp);
		} else if ("delete".equalsIgnoreCase(action)){
			deleteProduct(req, resp);
		} else if ("edit".equalsIgnoreCase(action)){
			editProduct(req, resp);
		} else {
			render(req, resp);
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void editProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String productId = req.getParameter("productId");
		User u = (User) req.getAttribute(MiscConstants.KEY_USER);
		Product product = ProductDao.findById(productId);
		if (product == null) {
			req.setAttribute(MiscConstants.ERROR_MESSAGE, "Product [" + productId + "] is not found");
			render(req, resp);
			return;
		}
		if (!product.getSeller().equals(u.getUserId())) {
			req.setAttribute(MiscConstants.ERROR_MESSAGE, "You're not allowed to edit product [" + productId + "]");
			render(req, resp);
			return;
		}
		req.setAttribute(MiscConstants.IS_EDIT, true);
		req.setAttribute(MiscConstants.KEY_MY_EDIT_PRODUCT, product);
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void render(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User u = (User) req.getAttribute(MiscConstants.KEY_USER);
		req.setAttribute(MiscConstants.KEY_MY_PRODUCTS, ProductDao.findByUserId(u.getUserId()));
		req.setAttribute(MiscConstants.KEY_MY_TRANSACTIONS, PaymentTransactionDao.findByUserId(u.getUserId()));
		RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/home.jsp");
		rp.forward(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String productId = req.getParameter("productId");
		Product product = ProductDao.findById(productId);
		if (product == null) {
			req.setAttribute(MiscConstants.ERROR_MESSAGE, "Product [" + productId + "] is not found");
			render(req, resp);
			return;
		}
		ProductDao.deleteById(product);
		resp.sendRedirect("/secured/home");

	}

	/**
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void saveProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
		Map<String, BlobKey> blobs = bs.getUploadedBlobs(req);
		BlobKey bk = blobs.get("picture");

		User u = UserDao.findBySession(req.getSession().getId());
		String productId = req.getParameter("productId");
		String name = req.getParameter("name");
		String category = req.getParameter("category");
		Double price = toDouble(req.getParameter("price"));
		String comment = req.getParameter("comment");

		String errorMsg = null;

		Product p = ProductDao.findById(productId);

		if (p == null) {
			p = new Product(UUID.randomUUID().toString());
		}
		p.setProductName(name);
		p.setCategory(category);
		p.setComment(comment);
		p.setPrice(price == null ? 0f : price.doubleValue());
		p.setSeller(u.getUserId());
		if (bk != null) {
			p.setPictureBlobKey(bk.getKeyString());
		}

		if (!Validator.isAlphaNumeric(name)) {
			errorMsg = "Product name is not alpha numeric";
		} else if (!Validator.isAlphaNumeric(category)) {
			errorMsg = "Product category is not alpha numeric";
		} else if (price == null) {
			errorMsg = "Price must be a number";
		}

		if (errorMsg != null) {
			req.setAttribute(MiscConstants.KEY_USER, u);
			req.setAttribute(MiscConstants.KEY_MY_EDIT_PRODUCT, p);
			req.setAttribute(MiscConstants.ERROR_MESSAGE, errorMsg);
			RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/home.jsp");
			rp.forward(req, resp);
			return;
		}

		ProductDao.persist(p);
		resp.sendRedirect("/secured/home");
	}

	/**
	 * @param parameter
	 * @return
	 */
	private Double toDouble(String parameter) {
		if (Validator.isNumber(parameter)) {
			return Double.valueOf(parameter);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void createProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute(MiscConstants.IS_EDIT, false);
		req.setAttribute(MiscConstants.KEY_MY_EDIT_PRODUCT, new Product(UUID.randomUUID().toString()));
		getServletContext().getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
	}
}
