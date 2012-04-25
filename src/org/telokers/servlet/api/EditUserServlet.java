
/**
 @author tommyquangqthinh
*/

package org.telokers.servlet.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;
import org.telokers.service.utils.Validator;

public class EditUserServlet extends HttpServlet{
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
		// TODO Auto-generated method stub
		
		String userId = req.getParameter(MiscConstants.USER_ID);
		String name = req.getParameter(MiscConstants.USER_NAME);
		String email = req.getParameter(MiscConstants.EMAIL);
		String emailErrorMsg = null;
		
		
		String password = req.getParameter(MiscConstants.PASSWORD);
		
		User user = new User(userId);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		user.set
		
		
		String cardHolderName = req.getParameter(MiscConstants.CARD_HOLDER_NAME);
		String cardType = req.getParameter(MiscConstants.CARD_TYPE);
		String cardNo = req.getParameter(MiscConstants.CARD_NUMBER);
		String expMonth = req.getParameter(MiscConstants.EXP_YEAR);
		String expYear = req.getParameter(MiscConstants.EXP_YEAR);	
		
	}
	
	private boolean validateUser (User user) {
		boolean proceed = false;
		
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
		if (Validator.isEmail(user.getEmail())){
			emailErrorMsg = "Wrong email format";
			proceed = false;
		}
		
		
		return false;
	}
	
	
}

