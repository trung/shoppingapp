
/**
 @author tommyquangqthinh
*/

package org.telokers.servlet.api;

import java.io.IOException;
import java.math.BigInteger;
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
import org.telokers.service.utils.MiscUtils;
import org.telokers.service.utils.MiscUtils.ErrorMessageHolder;
import org.telokers.service.utils.RSA;
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

		boolean outerproceed = true;
		boolean innerproceed = false;

		boolean isEdit = true;

		String operation = MiscUtils.blankifyString(req.getParameter("action"));



		//If "create"
		if( operation != null && operation.equals("CreateUser") ){
			isEdit = false;
		}

		String userId = "";

		if(!isEdit){
			userId = MiscUtils.blankifyString(req.getParameter(MiscConstants.USER_ID));
		}

		if (!isEdit && UserDao.findbyUserId(userId) != null){
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

		if (!isEdit && Validator.isEmpty(userId)) {
			errorMessageHolder.userIdErrorMsg = "Empty user Id";
			outerproceed = false;
		}

		User user = new User("1234");
		if(isEdit){
			user = (User) req.getAttribute(MiscConstants.KEY_USER);
		}
		else {
			if(!MiscUtils.isNullorBlank(userId)){
				user = new User(userId);
			}
		}
		user.setEmail(email);
		user.setName(name);
		user.setCardHolderName(cardHolderName);
		user.setCardType(cardType);
		user.setCardNo(cardNo);
		user.setCardExpDate(simpleExpDate);

		if(isEdit){
			user.setStatus(MiscConstants.STATUS_APPROVED);
		}


		if(isEdit){
			if (!MiscUtils.isNullorBlank(password)){
				user.setPassword(SecurityUtils.hashPassword(password));
			}
		}
		else {
			if(Validator.isEmpty(password)){
				errorMessageHolder.passwordErrorMsg = "Empty password";
				outerproceed = false;
			}
			else {
				int pwStatus = Validator.isValidPW(user.getUserId(), password);
				switch (pwStatus) {
					case 1: errorMessageHolder.passwordErrorMsg = "Password is the same as User"; outerproceed = false; break;
					case 2: errorMessageHolder.passwordErrorMsg = "Password length < 8"; outerproceed = false; break;
					case 3: errorMessageHolder.passwordErrorMsg = "Password must be alphanumeric"; outerproceed = false; break;
				}
				user.setPassword(SecurityUtils.hashPassword(password));
			}
		}


		innerproceed = validateUser(user, errorMessageHolder);

		if(outerproceed && innerproceed){
			//Pre approve user in edit mode
			UserDao.persistUser(user);
			logger.log(Level.FINE, "User created succesfully");

			//RequestDispatcher rp = null;
			if(isEdit){
				resp.sendRedirect("/secured/home?" + MiscConstants.INFO_MESSAGE +"=user profile edited successfully");
				//resp.sendRedirect("/secured/home");
			}
			else {
				resp.sendRedirect("/login?" + MiscConstants.INFO_MESSAGE +"=User profile created successfully. Please wait until admin has approved your account");

				/*req.setAttribute(MiscConstants.ERROR_MESSAGE, "User created successfully. Please login");
				rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp");
				rp.forward(req, resp);*/
			}
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

			if(isEdit){
				rp = getServletContext().getRequestDispatcher("/WEB-INF/jsp/editUser.jsp");
			}
			else {
				rp = getServletContext().getRequestDispatcher("/createUser.jsp");
			}
			rp.forward(req, resp);
		}

	}

	private boolean validateUser (User user, ErrorMessageHolder errorMsgHolder) {
		boolean proceed = true;

		if (Validator.isEmpty(user.getUserId()) || "1234".equalsIgnoreCase(user.getUserId())){
			errorMsgHolder.userIdErrorMsg = "Empty User ID";
			proceed = false;
		}
		if (!Validator.isAlphabet(user.getUserId())){
			errorMsgHolder.userIdErrorMsg = "User ID must be alphabet";
			proceed = false;
		}
		if (Validator.isEmpty(user.getName())){
			errorMsgHolder.nameErrorMsg = "Empty name";
			proceed = false;
		}
		if (!Validator.isAlphabet(user.getName())){
			errorMsgHolder.nameErrorMsg = "Name must be alphabet";
			proceed = false;
		}
		if (Validator.isEmpty(user.getEmail())){
			errorMsgHolder.emailErrorMsg = "Empty email";
			proceed = false;
		}
		if (!Validator.isEmail(user.getEmail())){
			errorMsgHolder.emailErrorMsg = "Wrong email format";
			proceed = false;
		}
		if(Validator.isEmpty(user.getCardHolderName())){
			errorMsgHolder.cardHolderNameErrorMsg = "Empty card holder name";
			proceed = false;
		}
		if(Validator.isEmpty(user.getCardNo()) || !Validator.isCreditCardNumber(user.getCardNo())){
			errorMsgHolder.cardNumberErrorMsg = "Empty or wrong format card number";
			proceed = false;
		}
		else {
			RSA rsa = new RSA();
			String encryptedCardNo = rsa.encrypt(new BigInteger(user.getCardNo())).toString();
			user.setCardNo(encryptedCardNo);
			user.setEncryptKeyString(rsa.getComboString());
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

