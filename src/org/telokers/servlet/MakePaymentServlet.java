/**
 *
 */
package org.telokers.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.dao.PaymentTransactionDao;
import org.telokers.dao.ProductDao;
import org.telokers.dao.ShoppingCartDao;
import org.telokers.model.PaymentTransaction;
import org.telokers.model.Product;
import org.telokers.model.ShoppingCart;
import org.telokers.model.User;
import org.telokers.service.utils.MiscConstants;
import org.telokers.service.utils.Validator;

/**
 * @author trung
 *
 */
public class MakePaymentServlet extends HttpServlet {

	private static final String PAYMENT_GATEWAY = "http://securecode.syscan.org/pay.php";

	/**
	 *
	 */
	private static final long serialVersionUID = 2874915245650876359L;

	private static final Logger logger = Logger.getLogger(MakePaymentServlet.class.getName());


	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ShoppingCart cart = (ShoppingCart) req.getAttribute(MiscConstants.KEY_CART);
		User u = (User) req.getAttribute(MiscConstants.KEY_USER);
		if (cart == null || cart.countProducts() == 0) {
			resp.sendRedirect("/secured/home?errorMsg=Your cart is empty");
			return;
		}
		if (Validator.isEmpty(u.getCardHolderName()) || Validator.isEmpty(u.getCardNo()) || Validator.isEmpty(u.getCardExpDate()) || Validator.isEmpty(u.getCardType())) {
			resp.sendRedirect("/secured/cart?errorMsg=You don't have any credit card information in order to purchase");
			return;
		}
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		double totalPrice = 0f;
		for (String pid : cart.getProductIds()) {
			Product p = ProductDao.findById(pid);
			totalPrice += p.getPrice();
		}
		Date transactionDate = new Date();
		params.put("amount", totalPrice);
		params.put("cnum", u.getDecryptedCreditCardNo());
		params.put("expm", u.getCardExpDate().substring(0, 2));
		params.put("expy", u.getCardExpDate().substring(2));
		params.put("mechid", MiscConstants.TEAM_NAME);
		params.put("name", u.getCardHolderName());
		params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transactionDate));
		params.put("type", "visa".equalsIgnoreCase(u.getCardType()) ? u.getCardType() : u.getCardType() + "card");

		String hashStr = buildHashString(params);
		logger.fine("Hash = " + hashStr);
		params.put("hash", hashStr);

		String paramStr = buildParamString(params);


		String response = makePayment(paramStr);

		logger.fine("Response = " + response);

		int result = parseResult(response);
		String refId = parseRefId(response);
		String info = parseInfo(response);

		PaymentTransaction pt = new PaymentTransaction(UUID.randomUUID().toString());
		pt.setProductIds(cart.getProductIds());
		pt.setUserId(u.getUserId());
		pt.setTimestamp(transactionDate);

		switch (result) {
		case 0:
			pt.setStatus("Success");
			pt.setRefId(refId);
			break;
		case 1:
			pt.setStatus("Failed");
			break;
		case 2:
			pt.setStatus(info);
			break;
		default:
			resp.sendRedirect("/secured/cart?errorMsg=Sorry, we're unable to proceed with the payment. Please try again later");
			return;
		}

		cart = new ShoppingCart(u.getUserId());
		ShoppingCartDao.persist(cart);

		PaymentTransactionDao.persis(pt);

		resp.sendRedirect("/secured/cart?infoMsg=Payment made successfully");
	}


	/**
	 * @param response
	 * @return
	 */
	private String parseInfo(String response) {
		Pattern p = Pattern.compile("\"info\":\"([^\\\"]+)\"");
		java.util.regex.Matcher m = p.matcher(response);
		if (m.find()) {
			return m.group(1);
		} else {
			return "";
		}
	}


	/**
	 * @param response
	 * @return
	 */
	private String parseRefId(String response) {
		Pattern p = Pattern.compile("\"refid\":\"([^\\\"]+)\"");
		java.util.regex.Matcher m = p.matcher(response);
		if (m.find()) {
			return m.group(1);
		} else {
			return "";
		}
	}


	/**
	 * @param response
	 * @return
	 */
	private int parseResult(String response) {
		Pattern p = Pattern.compile("result\":\"([^\\\"]+)\"");
		java.util.regex.Matcher m = p.matcher(response);
		if (m.find()) {
			return Integer.parseInt(m.group(1));
		} else {
			return -1;
		}
	}


	/**
	 * @param paramStr
	 * @return
	 */
	private String makePayment(String paramStr) {
		try {
            URL url = new URL(PAYMENT_GATEWAY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(paramStr);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer buf = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                buf.append(line);
            }
            wr.close();
            rd.close();
            return buf.toString();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to make payment due to ", e);
            return "";
        }
	}


	/**
	 * @param params
	 * @return
	 */
	private String buildHashString(Map<String, Object> params) {
		List<String> list = new ArrayList<String>(params.keySet());
		StringBuffer buf = new StringBuffer();
		Collections.sort(list);
		for (String key : list) {
			buf.append(key).append(params.get(key));
		}

		return sStringToHMACMD5(buf.toString(), MiscConstants.SHARE_SECRET);
	}

	public static String sStringToHMACMD5(String s, String keyString) {
        String sEncodedString = null;
        try
        {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(key);

            byte[] bytes = mac.doFinal(s.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();

            for (int i=0; i<bytes.length; i++) {
                String hex = Integer.toHexString(0xFF &  bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            sEncodedString = hash.toString();
        }
        catch (UnsupportedEncodingException e) {}
        catch(InvalidKeyException e){}
        catch (NoSuchAlgorithmException e) {}
        return sEncodedString ;
    }

	/**
	 * @param params
	 * @return
	 */
	private String buildParamString(Map<String, Object> params) {
		StringBuffer buf = new StringBuffer();
		for (String key : params.keySet()) {
			buf.append(key).append("=").append(params.get(key)).append("&");
		}
		if (buf.length() > 0) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}
}
