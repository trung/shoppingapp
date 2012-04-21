/**
 *
 */
package org.telokers.servlet.security;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.telokers.exception.LoginException;
import org.telokers.exception.SecurityException;

/**
 * Handle all incoming requests
 *
 * @author trung
 *
 */
public class SecurityFilter implements Filter {

	private static final Logger logger = Logger.getLogger(SecurityFilter.class.getName());

	/**
	 *
	 */
	private static final long serialVersionUID = 7300017349301631680L;

	private ServletContext servletContext;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getRequestURI();
		try {
			boolean isLocal = (uri.startsWith("/_ah/") && "127.0.0.1".equals(httpRequest.getRemoteHost()));
			if (!isLocal) {
				if (uri.startsWith("/secured/")) {
					// only when it's secured, we will apply authentication/authorization
					String sessionId = httpRequest.getSession().getId();
					logger.log(Level.FINE, "SecurityFilter.doFilter() - SessionID: [" + sessionId + "]");
					logger.log(Level.FINE, UUID.randomUUID().toString());
					checkAuthenticationAndAuthorization(request, response);
				}
			}
			chain.doFilter(request, response);
		} catch (LoginException le) {
			logger.log(Level.FINE, "Login required!");
			if (uri.startsWith("/secured/api/")) {
				// return 401 if this is an RESTful call
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please login to continue");
			} else {
				RequestDispatcher rp = servletContext.getRequestDispatcher("/login.jsp");
				rp.forward(request, response);
			}
		} catch (SecurityException se) {
			logger.log(Level.FINE, "Security error");
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, se.getMessage());
		}
	}

	/**
	 * Perform authentication and authorization check here
	 * @param request
	 * @param response
	 */
	private void checkAuthenticationAndAuthorization(ServletRequest request,
			ServletResponse response) throws LoginException, SecurityException {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
	}
}
