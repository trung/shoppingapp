
/**
 @author tommyquangqthinh
*/

package org.telokers.servlet.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.service.utils.JSONUtils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;


public class LoginServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		String user = req.getParameter("username");
		String password = req.getParameter("password");
		
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
				jsonMap.put("error", "wrong password");
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
			jsonMap.put("error", "no user");
			out.write(JSONUtils.toJSON(jsonMap).toString());	
		}
	}
}

