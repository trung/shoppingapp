/**
 *
 */
package org.telokers.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.UserDao;
import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;

/**
 *
 * @author trung
 *
 */
public class AdminServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = -5826144668754372828L;

	private static final Logger logger = Logger.getLogger(AdminServlet.class.getName());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("edit".equalsIgnoreCase(action)) {
			String userId = req.getParameter("userId");
			logger.fine("Editting user [" + userId + "]");
			// edit user
			User u = UserDao.findbyUserId(userId);
			if (u == null) {
				req.setAttribute(MiscConstants.ERROR_MESSAGE, "User [" + userId + "] not found");
				render(req, resp);
				return;
			} else {
				req.setAttribute(MiscConstants.KEY_EDIT_USER, u);
				RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
				rp.forward(req, resp);
				return;
			}
		} else if ("save".equalsIgnoreCase(action)) {
			String userId = req.getParameter("userId");
			String accountType = req.getParameter("accountType");
			String status = req.getParameter("status");
			Date suspensionStart = toDate(req.getParameter("suspensionStart"));
			Date suspensionEnd = toDate(req.getParameter("suspensionEnd"));
			String remarks = req.getParameter("remarks");

			User u = UserDao.findbyUserId(userId);
			String errorMsg = null;
			if (u == null) {
				errorMsg = "User [" + userId + "] not found";
			} else if (invalidAccountType(accountType)) {
				errorMsg = "Account type is invalid";
			} else if (isInvalidStatus(status)) {
				errorMsg = "Status is invalid";
			} else if (status.equalsIgnoreCase(MiscConstants.STATUS_SUSPEND) && invalidSuspensionPeriod(suspensionStart, suspensionEnd)) {
				errorMsg = "Suspension period is invalid";
			}

			if (errorMsg != null) {
				req.setAttribute(MiscConstants.KEY_EDIT_USER, u);
				req.setAttribute(MiscConstants.ERROR_MESSAGE, errorMsg);
				RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
				rp.forward(req, resp);
				return;
			}

			u.setSuspensionPeriod(suspensionStart, suspensionEnd);
			u.setRole(accountType);
			if (!status.equals(u.getStatus())) {
				u.setStatus(status);
				u.setLastModifiedOfStatus(new Date());
			}
			if (MiscConstants.STATUS_APPROVED.equals(status)) {
				u.setSuspensionPeriod(null, null);
			}
			u.setRemarks(remarks);
			UserDao.persistUser(u);
			resp.sendRedirect("/secured/admin");
		} else {
			render(req, resp);
		}
	}

	/**
	 * @param suspensionStart
	 * @param suspensionEnd
	 * @return
	 */
	private boolean invalidSuspensionPeriod(Date suspensionStart,
			Date suspensionEnd) {
		return suspensionStart == null || suspensionEnd == null || suspensionStart.getTime() > suspensionEnd.getTime();
	}

	/**
	 * @param status
	 * @return
	 */
	private boolean isInvalidStatus(String status) {
		if (status == null) {
			return true;
		}
		for (String r : MiscConstants.STATUSES) {
			if (r.equals(status)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param accountType
	 * @return
	 */
	private boolean invalidAccountType(String accountType) {
		if (accountType == null) {
			return true;
		}
		for (String r : MiscConstants.ROLES) {
			if (accountType.equals(r)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param parameter
	 * @return
	 */
	private Date toDate(String parameter) {
		if (parameter == null) {
			return null;
		} else {
			try {
				return new SimpleDateFormat("dd/MM/yyyy").parse(parameter);
			} catch (Exception e) {
				return null;
			}
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
	private void render(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> users = UserDao.findAll();
		req.setAttribute(MiscConstants.KEY_USERS, users);
		RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
		rp.forward(req, resp);
	}

}
