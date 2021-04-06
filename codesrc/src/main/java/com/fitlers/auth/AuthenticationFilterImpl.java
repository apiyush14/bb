package com.fitlers.auth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.fitlers.entities.UserAuthenticationDetails;
import com.fitlers.repo.UserAuthenticationDetailsRepository;
import com.fitlers.utils.AuthenticationUtils;

@Component
public class AuthenticationFilterImpl extends OncePerRequestFilter {

	@Autowired
	UserAuthenticationDetailsRepository userAuthenticationDetailsRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String path = request.getRequestURI();
			if (path.contains("auth") || path.contains("swagger") || path.contains("api-docs") || path.contains("testMethod") || path.contains("dispatcherServlet")
					||path.contains("event-details/addEvent")
					||path.contains("event-details/uploadEventDisplayImage")
					||path.contains("event-details/getDisplayImage")
					||path.contains("log/client")) {
				filterChain.doFilter(request, response);
			} else {
				String xAuth = request.getHeader("X-AUTH");
				String userId = request.getHeader("USER_ID");
				String requestTimeStamp = request.getHeader("REQUEST_TIMESTAMP");

				if (!isValid(userId, requestTimeStamp, xAuth)) {
					throw new SecurityException();
				}

				// Long userId=getUserIdFromToken(xAuth);
				filterChain.doFilter(request, response);
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	private boolean isValid(String userId, String requestTimeStamp, String xAuth) throws NoSuchAlgorithmException {
		UserAuthenticationDetails userAuthenticationDetails = userAuthenticationDetailsRepo.findById(userId).get();
		if (Objects.isNull(userAuthenticationDetails)) {
			return false;
		}
		String secret = userAuthenticationDetails.getUserSecretKey();
		return xAuth.equals(AuthenticationUtils.generateMD5AuthenticationToken(userId, requestTimeStamp, secret));
	}

}
