/**
 *
 */
package org.telokers.servlet.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.service.utils.JSONUtils;

/**
 * @author trung
 *
 */
public class ListItemsServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = -4790244493902796042L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Map<String, Object> ret = new HashMap<String, Object>();

		resp.setContentType("application/json");
		ret.put("success", "1");

		Map<String, Object> data = new HashMap<String, Object>();
		ret.put("data", data);

		data.put("columns", Arrays.asList("ID", "Description", "Price"));
		List<List<?>> values = new ArrayList<List<?>>();
		values.add(Arrays.asList("1", "Dell Poweredge 2450 P3 866MHz 512MB RAM 2x9GB HD", "50"));
		values.add(Arrays.asList("2", "WRANGLER Men's DODGE RAM RODEO LOGO Shirt - L", "60"));
		values.add(Arrays.asList("3", "Tshirt: Bite Me Ford & Chevy Boy Real Men Drive Dodges", "18"));
		values.add(Arrays.asList("4", "NEW DOLCE & GABBANA BLACK DENIM RAM LEATHER JEANS ", "600"));
		values.add(Arrays.asList("5", "3 GB kit laptop Ram Memory PC3 DDR3 SODIMM", "8"));
		values.add(Arrays.asList("6", "Apple 27 iMac i7 3.4 32GB Ram 2TB & 256 SSD 2gb", "4000"));
		values.add(Arrays.asList("7", "MOTOR TREND Magazine 1993 Complete Year Lot of (12) ", "20"));
		values.add(Arrays.asList("8", "NEW Toshiba C675D-S7109 4GB RAM 320GB HD SuperMulti Web", "400"));
		values.add(Arrays.asList("9", "400g.Angora Ram 40% Mohair Knitting Yarn", "13"));
		data.put("values", values);

		resp.getWriter().write(JSONUtils.toJSON(ret));
	}
}
