/**
 *
 */
package org.telokers.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * @author trung
 *
 */
public class PictureServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = -735827395809054426L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String key = req.getParameter("key");
		if (key == null || key.length() == 0) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		resp.setContentType("image/png");
		BlobKey bk = new BlobKey(key);
		BlobstoreServiceFactory.getBlobstoreService().serve(bk, resp);
	}
}
