
/**
 @author tommyquangqthinh
*/

package org.telokers.servlet.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.UserDao;
import org.telokers.model.User;
import org.telokers.model.User.UserProperty;
import org.telokers.service.utils.MiscConstants;
import org.telokers.service.utils.MiscUtils;
import org.telokers.service.utils.MiscUtils.ErrorMessageHolder;
import org.telokers.service.utils.SecurityUtils;
import org.telokers.service.utils.Validator;

public class CreateEditUserServlet extends HttpServlet{
	private static final Logger logger = Logger.getLogger(CreateEditUserServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//Re-initialize all error messagespera
		ErrorMessageHolder errorMessageHolder = new ErrorMessageHolder();
		
		boolean proceed = false;
		
		String operation = MiscUtils.blankifyString(req.getParameter("submit"));
		String userId = MiscUtils.blankifyString(req.getParameter(MiscConstants.USER_ID));
		
		if( (operation != null && operation.equals("Create"))
		    && (UserDao.findbyUserId(userId) != null) ){
			
			req.setAttribute(MiscConstants.ERROR_MESSAGE, "User has already existed");
			RequestDispatcher rp = getServletContext().getRequestDispatcher("/createUser.jsp");
			rp.forward(req, resp);
		}
		
		String name = req.getParameter(MiscConstants.USER_NAME);
		String email = req.getParameter(MiscConstants.EMAIL);
		String password = req.getParameter(MiscConstants.PASSWORD);
		String cardHolderName = req.getParameter(MiscConstants.CARD_HOLDER_NAME);
		String cardType = req.getParameter(MiscConstants.CARD_TYPE);
		String cardNo = req.getParameter(MiscConstants.CARD_NUMBER);
		/*String expMonth = req.getParameter(MiscConstants.EXP_MONTH);
		String expYear = req.getParameter(MiscConstants.EXP_YEAR);*/
		String simpleExpDate = req.getParameter(MiscConstants.SIMPLE_EXP_DATE);
		
		User user = new User(userId);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setCardHolderName(cardHolderName);
		user.setCardType(cardType);
		user.setCardNo(cardNo);
		user.setCardExpDate(simpleExpDate);
//		user.setCardExpDate(expMonth + expYear);
		
		proceed = validateUser(user, errorMessageHolder);
		
		if(proceed){
			
			if( ! ((operation != null) && operation.equals("Create")) ){
				user.setStatus(MiscConstants.STATUS_APPROVED);
			}
			
			//Persisting user
			user.setPassword(SecurityUtils.hashPassword(user.getPassword()));
			UserDao.persistUser(user);
			logger.log(Level.FINE, "User created succesfully");
			req.setAttribute(MiscConstants.ERROR_MESSAGE, "User created successfully. Please login");
			RequestDispatcher rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			rp.forward(req, resp);
		}
		else {
			req.setAttribute(MiscConstants.KEY_CARD_HOLDER_NAME_ERROR_MSG, errorMessageHolder.cardHolderNameErrorMsg);
			req.setAttribute(MiscConstants.KEY_CARD_NUMBER_ERROR_MSG, errorMessageHolder.cardNumberErrorMsg);
			req.setAttribute(MiscConstants.KEY_EMAIL_ERROR_MSG, errorMessageHolder.emailErrorMsg);
			req.setAttribute(MiscConstants.KEY_EXPIRY_DATE_ERROR_MSG, errorMessageHolder.expiryDateErrorMsg);
			req.setAttribute(MiscConstants.KEY_NAME_ERROR_MSG, errorMessageHolder.nameErrorMsg);
			req.setAttribute(MiscConstants.KEY_PASSWORD_ERROR_MSG, errorMessageHolder.passwordErrorMsg);
			req.setAttribute(MiscConstants.KEY_TYPE_OF_CARD_ERROR_MSG, errorMessageHolder.typeOfCardErrorMsg);
			req.setAttribute(MiscConstants.KEY_USER_ID_ERROR_MSG, errorMessageHolder.userIdErrorMsg);
			
			RequestDispatcher rp = null;
			
			if( (operation != null) && operation.equals("Create") ){
				rp = getServletContext().getRequestDispatcher("/createUser.jsp");
			}
			else {
				rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/editUser.jsp");
			}
			rp.forward(req, resp);
		}
		
	}
	
	private boolean validateUser (User user, ErrorMessageHolder errorMsgHolder) {
		boolean proceed = true;
		
		if (Validator.isEmpty(user.getUserId())){
			errorMsgHolder.userIdErrorMsg = "Empty User ID";
			proceed = false;
		}
		if (Validator.isEmpty(user.getName())){
			errorMsgHolder.nameErrorMsg = "Empty name";
			proceed = false;
		}
		if (Validator.isEmpty(user.getEmail())){
			errorMsgHolder.emailErrorMsg = "Empty name";
			proceed = false;
		}
		if (!Validator.isEmail(user.getEmail())){
			errorMsgHolder.emailErrorMsg = "Wrong email format";
			proceed = false;
		}
		if(Validator.isEmpty(user.getPassword())){
			errorMsgHolder.passwordErrorMsg = "Empty password";
			proceed = false;
		}
		else {
			int pwStatus = Validator.isValidPW(user.getUserId(), user.getPassword());
			switch (pwStatus) {
				case 1: errorMsgHolder.passwordErrorMsg = "Password is the same as User"; proceed = false; break;
				case 2: errorMsgHolder.passwordErrorMsg = "Password length < 8"; proceed = false; break;
				case 3: errorMsgHolder.passwordErrorMsg = "Password must be alphanumeric"; proceed = false; break;
				case 0: proceed = true; break;
			}
		}
		if(Validator.isEmpty(user.getCardHolderName())){
			errorMsgHolder.cardNumberErrorMsg = "Empty card holder name";
			proceed = false;
		}
		if(Validator.isEmpty(user.getCardNo()) || !Validator.isCreditCardNumber(user.getCardNo())){
			errorMsgHolder.cardNumberErrorMsg = "Empty or wrong format card number";
			proceed = false;
		}
		if(!Validator.isValidCCType(user.getCardType())){
			errorMsgHolder.typeOfCardErrorMsg = "Invalid card type";
			proceed = false;
		}
		if(!Validator.isCCExpirationDate(user.getCardExpDate())){
			errorMsgHolder.expiryDateErrorMsg = "Invalid expiry date";
			proceed = false;
		}
		return proceed;
	}
	
}

