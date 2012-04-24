
/**
 @author tommyquangqthinh
*/

package org.telokers.servlet.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.telokers.model.User;
import org.telokers.model.dao.JpaUserDao;
import org.telokers.service.utils.JSONUtils;
import org.telokers.service.utils.MiscConstants;

import com.google.appengine.api.datastore.Entity;


public class LoginServlet extends HttpServlet{
	
	private final JpaUserDao userDao = JpaUserDao.instance();
	
	/*protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		String user = req.getParameter("username");
		String password = req.getParameter("password");
		String errorMsg = "Invalid user/password";
		
		Query q = new Query("Customer");
		q.addFilter("username", Query.FilterOperator.EQUAL, user);
		PreparedQuery pq = datastore.prepare(q);
		
		Iterator<Entity> entityIterator = pq.asIterable().iterator();
		
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		
		
		if(entityIterator.hasNext()){
			if(password.equals(entityIterator.next().getProperty("password"))){
				Map<String, String> jsonMap = new HashMap<String, String>(); 
				jsonMap.put("success", Integer.toString(1));
				out.write(JSONUtils.toJSON(jsonMap).toString());
			}
			else{
				Map<String, String> jsonMap = new HashMap<String, String>(); 
				jsonMap.put("success", Integer.toString(0));
				jsonMap.put("error", errorMsg);
				out.write(JSONUtils.toJSON(jsonMap).toString());
			}
		}
		else {
			Entity customer = new Entity("Customer");
			customer.setProperty("username", user);
			customer.setProperty("password", password);
			datastore.put(customer);
			
			Map<String, String> jsonMap = new HashMap<String, String>(); 
			jsonMap.put("success", Integer.toString(0));
			jsonMap.put("error", errorMsg);
			out.write(JSONUtils.toJSON(jsonMap).toString());	
		}
	}*/
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		String errorMsg = "Invalid user/password";

		User user = userDao.findByUserName(userName);
		HttpSession session = req.getSession(true);
		
		try {
			if(user.getPassword().equals(password)){
				Map<String, String> jsonMap = new HashMap<String, String>(); 
				jsonMap.put("success", Integer.toString(1));
				out.write(JSONUtils.toJSON(jsonMap).toString());
				String key = UUID.randomUUID().toString();
				user.setUserKey(key);
				userDao.persistUser(user);
				session.setAttribute(MiscConstants.user_session_key, key);
			}
			else {
				Map<String, String> jsonMap = new HashMap<String, String>(); 
				jsonMap.put("success", Integer.toString(0));
				jsonMap.put("error", errorMsg);
				out.write(JSONUtils.toJSON(jsonMap).toString());
			}
		} finally {
			out.close();
		}
	}
}

