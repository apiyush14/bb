package com.fitlers.auth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("#{'${whitelistedPaths}'.split(',')}")
	private List<String> listOfWhitelistedPaths;
	
	public static final Logger logger = LoggerFactory.getLogger(AuthenticationFilterImpl.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String path = request.getRequestURI();
			Optional<String> isWhitelistedPath = listOfWhitelistedPaths.stream()
					.filter(whitelistedPath -> path.contains(whitelistedPath)).findFirst();
			if (isWhitelistedPath.isPresent()) {
				filterChain.doFilter(request, response);
			} else {
				String xAuth = request.getHeader("X-AUTH");
				String userId = request.getHeader("USER_ID");
				String requestTimeStamp = request.getHeader("REQUEST_TIMESTAMP");
				if (!isValid(userId, requestTimeStamp, xAuth)) {
					logger.error("Authentication Filter failed for user : " + userId + " for URL : " + path);
					throw new SecurityException();
				}
				filterChain.doFilter(request, response);
			}
		} catch (Exception e) {
			logger.error("Authentication Filter failed");
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
