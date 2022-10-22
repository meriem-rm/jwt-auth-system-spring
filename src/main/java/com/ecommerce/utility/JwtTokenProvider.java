package com.ecommerce.utility;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ecommerce.constant.SecurityConstant;

@Service
public class JwtTokenProvider {
	@Value("${jwt.secret}")
	private String secret; 
	public String generateToken(UserDetails  userDetails) {
		// take that userPrincipal and get all the (claims) mean autorities all the information 
		String[] claims = getClaimsFromuser(userDetails); 
		System.out.println("claims" + claims);
		return JWT.create()
				// name of the company
				.withIssuer(SecurityConstant.GET_E_COMMERCE)
				.withAudience(SecurityConstant.GET_E_COMMERCE)
				// date when token was created 
				.withIssuedAt(new Date())
				.withSubject(userDetails.getUsername())
				.withArrayClaim(SecurityConstant.AUTHORITIES, claims)
				.withExpiresAt(new Date(System.currentTimeMillis() + 4 * 60 * 1000))
				//.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(secret));
				//.sign(Algorithm.HMAC512(secret.getBytes()));
	} 
	public String generateRefreshToken(UserDetails  userDetails) {
		// take that userPrincipal and get all the (claims) mean autorities all the information 
//		String[] claims = getClaimsFromuser(userDetails); 
//		System.out.println("claims" + claims);
		return JWT.create()
				// name of the company
				.withIssuer(SecurityConstant.GET_E_COMMERCE)
				//.withAudience(SecurityConstant.GET_E_COMMERCE)
				// date when token was created 
				//.withIssuedAt(new Date())
				.withSubject(userDetails.getUsername())
				//.withArrayClaim(SecurityConstant.AUTHORITIES, claims)
				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(secret));
				//.sign(Algorithm.HMAC512(secret.getBytes()));
	} 
	private String[] getClaimsFromuser(UserDetails  user) {
		System.out.println("user Authorities" + user.getAuthorities());
		return user.getAuthorities().stream() 
				.map(GrantedAuthority::getAuthority)
				.toArray(String[]::new);	
	} 
	private JWTVerifier getJwtVerifie() {
		JWTVerifier verifier;
        Algorithm algorithm = Algorithm.HMAC512(secret);
		try {
			verifier= JWT.require(algorithm)
					.withIssuer(SecurityConstant.GET_E_COMMERCE)
					.build(); //Reusable verifier instance
		} catch (JWTVerificationException e) {
		 throw new JWTVerificationException("TOKEN_CANNOT_BE_VERIFIED");
		}
		return verifier;
	} 
	// - 5 
			public String getSubject(String token) {
				JWTVerifier verifier = getJwtVerifie();
	            return verifier.verify(token).getSubject();
			} 
			private boolean isTokenExpired(JWTVerifier verifier, String token) {
				Date exprition = verifier.verify(token).getExpiresAt();
				return exprition.before(new Date());
			}
			// - 4 
			public boolean isTokenValid(String userName, String token) {
				JWTVerifier verifier = getJwtVerifie();
				return userName != "" && !isTokenExpired(verifier, token);
			}

}
