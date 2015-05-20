package com.bgasparotto.archproject.web.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter that disables page caching for protected resources, to avoid a page to
 * be retrieved from the web browser cache instead of from the server. <br>
 * This way, the user won't be able to see protected resources after logged out
 * or had its session expired.
 * 
 * @author Bruno Gasparotto
 *
 */
public class NoCacheProtectedPagesFilter implements Filter {

	@Override
	public void destroy() {
		// Nothing to implement
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		/* Set headers to avoid caching the page. */
		httpResponse.setHeader("Cache-Control",
				"no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache");
		httpResponse.setDateHeader("Expires", 0);

		/* Process the request. */
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// Nothing to implement
	}
}