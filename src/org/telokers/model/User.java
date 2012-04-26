/**
 *
 */
package org.telokers.model;



import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.telokers.service.utils.MiscConstants;
import org.telokers.service.utils.MiscUtils;
import org.telokers.service.utils.RSA;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


/**
 * @author trung
 *
 */

public class User extends AbstractModel{

	/**
	 *
	 */
	private static final long serialVersionUID = -7610355287291683349L;

	public static enum UserProperty {
		name,
		email,
		userId,
		password,
		sessionId,
		lastLogin,
		role,
		status,
		cardNo,
		cardType,
		skusHashReference,
		cardHolderName,
		expDate,
		csrfToken,
		lastModifiedOfStatus,
		suspensionStart,
		suspensionEnd,
		remarks,
		rating,
		countRating
	}

	public User(String userId) {
		super(userId);
		entity = new Entity(getKey(userId));
		setUserId(userId);
		setRole(MiscConstants.ROLE_USER);
		setStatus(MiscConstants.STATUS_SUSPEND);
	}

	/**
	 * @param b
	 */
	public void setStatus(String status) {
		setProperty(UserProperty.status, status);
	}

	public boolean isActive() {
		return MiscConstants.STATUS_APPROVED.equals(getStatus());
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return (String) getProperty(UserProperty.status);
	}

	/**
	 * @param roleUser
	 */
	public void setRole(String role) {
		setProperty(UserProperty.role, role);
	}

	public User(Entity entity) throws Exception{
		super(entity);
	}

	public void setPassword(String password) {
		setProperty(UserProperty.password, password);
	}

	public String getPassword() {
		return (String) getProperty(UserProperty.password);
	}

	public void setUserSessionId(String sid) {
		setProperty(UserProperty.sessionId, sid);
	}

	public void getUserSessionId(String sid) {
		setProperty(UserProperty.sessionId, sid);
	}

	public String getName() {
		return (String) getProperty(UserProperty.name);
	}

	public void setName(String name) {
		setProperty(UserProperty.name, name);
	}

	public String getUserId() {
		return (String) getProperty(UserProperty.userId);
	}

	private void setUserId(String userId) {
		setProperty(UserProperty.userId, userId);
	}

	public String getEmail() {
		return (String) getProperty(UserProperty.email);
	}

	public void setEmail(String email) {
		setProperty(UserProperty.email, email);
	}

	@Override
	public Entity getEntity(){
		return this.entity;
	}

	/**
	 * @param userId
	 * @return
	 */
	public static Key getKey(String userId) {
		return KeyFactory.createKey(getKind(), userId);
	}

	/**
	 * @return
	 */
	public static String getKind() {
		return User.class.getSimpleName();
	}

	/**
	 * @param date
	 */
	public void setLastLogin(Date date) {
		setProperty(UserProperty.lastLogin, date);
	}

	public boolean isAdmin() {
		return MiscConstants.ROLE_ADMIN.equalsIgnoreCase(getRole());
	}

	/**
	 * @return
	 */
	public String getRole() {
		return (String)getProperty(UserProperty.role);
	}

	/**
	 * @param string
	 * @return
	 */
	public boolean hasExistingSessionId() {
		String sid = (String) getProperty(UserProperty.sessionId);
		return sid != null && sid.length() > 0;
	}

	/**
	 * @return
	 */
	public String getSessionId() {
		return (String) getProperty(UserProperty.sessionId);
	}

	public String getCardHolderName() {
		return (String) getProperty(UserProperty.cardHolderName);
	}

	public void setCardHolderName(String cardHolderName) {
		setProperty(UserProperty.cardHolderName, cardHolderName);
	}

	public String getCardNo() {
		return (String) getProperty(UserProperty.cardNo);
	}

	public void setCardNo(String cardNo) {
		setProperty(UserProperty.cardNo, cardNo);
	}

	public String getCardType() {
		return (String) getProperty(UserProperty.cardType);
	}

	public void setCardType(String cardType) {
		setProperty(UserProperty.cardType, cardType);
	}

	public String getCardExpDate() {
		return (String) getProperty(UserProperty.expDate);
	}

	public void setCardExpDate(String expDate) {
		setProperty(UserProperty.expDate, expDate);
	}

	/**
	 * @return
	 */
	public Date getLastLogin() {
		return (Date)getProperty(UserProperty.lastLogin);
	}

	public void createCSRFToken() {
		setProperty(UserProperty.csrfToken, UUID.randomUUID().toString());
	}

	public String getCSRFToken() {
		return (String) getProperty(UserProperty.csrfToken);
	}

	public void setLastModifiedOfStatus(Date d) {
		setProperty(UserProperty.lastModifiedOfStatus, d);
	}

	public Date getLastModifiedOfstatus() {
		return (Date) getProperty(UserProperty.lastModifiedOfStatus);
	}

	public void setSuspensionPeriod(Date start, Date end) {
		setProperty(UserProperty.suspensionStart, start);
		setProperty(UserProperty.suspensionEnd, end);
	}

	public Date getSuspensionStart() {
		return (Date) getProperty(UserProperty.suspensionStart);
	}

	public Date getSuspensionEnd() {
		return (Date) getProperty(UserProperty.suspensionEnd);
	}

	public void setRemarks(String remarks) {
		setProperty(UserProperty.remarks, remarks);
	}

	public String getRemarks() {
		return (String) getProperty(UserProperty.remarks);
	}

	public String getSuspensionStartString() {
		Date d = getSuspensionStart();
		if (d != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(d);
		} else {
			return "";
		}
	}

	public String getSuspensionEndString() {
		Date d = getSuspensionEnd();
		if (d != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(d);
		} else {
			return "";
		}
	}

	public String getLastModifiedOfStatusString() {
		Date d = getLastModifiedOfstatus();
		if (d == null) {
			return  "";
		}
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(d);
	}

	public void setEncryptKeyString(String encryptKey) {
		setProperty(UserProperty.skusHashReference, encryptKey);
	}

	public String getEncryptKeyString() {
		return (String) getProperty(UserProperty.skusHashReference);
	}

	public void addRating(int r) {
		int rating = getRating();
		int count = getCountRating();
		rating = (rating * count + r) / (count + 1);
		setProperty(UserProperty.rating, rating);
		setProperty(UserProperty.countRating, count + 1);
	}

	public int getRating() {
		Integer i = (Integer) getProperty(UserProperty.rating);
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}

	public int getCountRating() {
		Integer i = (Integer) getProperty(UserProperty.countRating);
		if (i == null) {
			return 0;
		}
		return i.intValue();
	}
	
	public String getDecryptedCreditCardNo() {
		String encryptedCreditCardNo = this.getCardNo();
		String decryptedCreditCardNo = "";
		if(!MiscUtils.isNullorBlank(encryptedCreditCardNo)){
			RSA rsa = new RSA();
			String encryptKeyString = this.getEncryptKeyString();
			BigInteger creditcardNo = rsa.decrypt(new BigInteger(encryptedCreditCardNo), encryptKeyString);
			if (creditcardNo != null){
				decryptedCreditCardNo = creditcardNo.toString();
			}
		}
		return decryptedCreditCardNo;
	}

}
