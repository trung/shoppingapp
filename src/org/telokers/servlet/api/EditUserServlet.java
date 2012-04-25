
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
import org.telokers.service.utils.MiscConstants;
import org.telokers.service.utils.SecurityUtils;
import org.telokers.service.utils.Validator;

public class EditUserServlet extends HttpServlet{
	private static final Logger logger = Logger.getLogger(EditUserServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	
	private String userIdErrorMsg = "";
	private String nameErrorMsg = "";
	private String emailErrorMsg = "";
	private String passwordErrorMsg = "";
	private String cardHolderNameErrorMsg = "";
	private String typeOfCardErrorMsg = "";
	private String cardNumberErrorMsg = "";
	private String expiryDateErrorMsg = "";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//Re-initialize all error messages
		userIdErrorMsg = "";
		nameErrorMsg = "";
		emailErrorMsg = "";
		passwordErrorMsg = "";
		cardHolderNameErrorMsg = "";
		typeOfCardErrorMsg = "";
		cardNumberErrorMsg = "";
		expiryDateErrorMsg = "";
		// TODO Auto-generated method stub
		
		boolean proceed = false;
		
		String userId = req.getParameter(MiscConstants.USER_ID);
		String name = req.getParameter(MiscConstants.USER_NAME);
		String email = req.getParameter(MiscConstants.EMAIL);
		String password = req.getParameter(MiscConstants.PASSWORD);
		
		String cardHolderName = req.getParameter(MiscConstants.CARD_HOLDER_NAME);
		String cardType = req.getParameter(MiscConstants.CARD_TYPE);
		String cardNo = req.getParameter(MiscConstants.CARD_NUMBER);
		String expMonth = req.getParameter(MiscConstants.EXP_MONTH);
		String expYear = req.getParameter(MiscConstants.EXP_YEAR);	
		
		User user = new User(userId);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.setCardHolderName(cardHolderName);
		user.setCardType(cardType);
		user.setCardNo(cardNo);
		user.setCardExpDate(expMonth + expYear);
		
		proceed = validateUser(user);
		
		if(proceed){
			//Persisting user
			user.setPassword(SecurityUtils.hashPassword(user.getPassword()));
			UserDao.persistUser(user);
			logger.log(Level.FINE, "User created succesfully");
		}
		else {
			req.setAttribute(MiscConstants.KEY_CARD_HOLDER_NAME_ERROR_MSG, this.cardHolderNameErrorMsg);
			req.setAttribute(MiscConstants.KEY_CARD_NUMBER_ERROR_MSG, this.cardNumberErrorMsg);
			req.setAttribute(MiscConstants.KEY_EMAIL_ERROR_MSG, this.emailErrorMsg);
			req.setAttribute(MiscConstants.KEY_EXPIRY_DATE_ERROR_MSG, this.expiryDateErrorMsg);
			req.setAttribute(MiscConstants.KEY_NAME_ERROR_MSG, this.nameErrorMsg);
			req.setAttribute(MiscConstants.KEY_PASSWORD_ERROR_MSG, this.passwordErrorMsg);
			req.setAttribute(MiscConstants.KEY_TYPE_OF_CARD_ERROR_MSG, this.typeOfCardErrorMsg);
			req.setAttribute(MiscConstants.KEY_USER_ID_ERROR_MSG, this.userIdErrorMsg);
			
			RequestDispatcher rp = getServletContext().getRequestDispatcher("/createUser.jsp");
			rp.forward(req, resp);
		}
		
	}
	
	private boolean validateUser (User user) {
		boolean proceed = true;
		
		if (Validator.isEmpty(user.getUserId())){
			userIdErrorMsg = "Empty User ID";
			proceed = false;
		}
		if (Validator.isEmpty(user.getName())){
			nameErrorMsg = "Empty name";
			proceed = false;
		}
		if (Validator.isEmpty(user.getEmail())){
			emailErrorMsg = "Empty name";
			proceed = false;
		}
		if (!Validator.isEmail(user.getEmail())){
			emailErrorMsg = "Wrong email format";
			proceed = false;
		}
		if(Validator.isEmpty(user.getPassword())){
			passwordErrorMsg = "Emty password";
			proceed = false;
		}
		else {
			int pwStatus = Validator.isValidPW(user.getUserId(), user.getPassword());
			switch (pwStatus) {
				case 1: passwordErrorMsg = "Password is the same as User"; proceed = false; break;
				case 2: passwordErrorMsg = "Password length < 8"; proceed = false; break;
				case 3: passwordErrorMsg = "Password must be alphanumeric"; proceed = false; break;
				case 0: proceed = true; break;
			}
		}
		if(Validator.isEmpty(user.getCardHolderName())){
			cardNumberErrorMsg = "Empty card holder name";
			proceed = false;
		}
		if(Validator.isEmpty(user.getCardNo()) || !Validator.isCreditCardNumber(user.getCardNo())){
			cardNumberErrorMsg = "Empty or wrong format card number";
			proceed = false;
		}
		if(!Validator.isValidCCType(user.getCardType())){
			typeOfCardErrorMsg = "Invalid card type";
			proceed = false;
		}
		if(!Validator.isCCExpirationDate(user.getCardExpDate())){
			expiryDateErrorMsg = "Invalid expiry date";
			proceed = false;
		}
		return proceed;
	}
	
}

