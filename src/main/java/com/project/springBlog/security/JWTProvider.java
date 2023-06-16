package com.project.springBlog.security;

import com.project.springBlog.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.security.Key;

@Service
public class JWTProvider {

    private Key secretKey;

    @PostConstruct // after JWTProvider bean is constructed, this will do all the initialisations
    public void init() {
        // generate key only one time and assign to every JWT, rather than creating a new everytime generate token is called.
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.ES512);
    }

    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder().setSubject(principal.getUserName()).signWith(secretKey).compact();
    }
    public boolean validateToken(String jwt){
        Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(jwt);
        return true;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody();
        return claims.getSubject();
    }
}
/*
     The generateToken method takes an Authentication object as a parameter.
     The Authentication object represents the authenticated user.

    Within the method, the authentication.getPrincipal() method is called to obtain the principal object representing the authenticated user.
     In this case, it is assumed that the principal object is of type User.

    The next line constructs a JWT using the JJWT library.
    The Jwts.builder() method is used to create a new JWT builder instance.

    setSubject(principal.getUserName()) sets the subject of the JWT to the username of the authenticated user.
    It extracts the username from the principal object obtained from the Authentication object.

    signWith(Keys.secretKeyFor(SignatureAlgorithm.ES512)) signs the JWT with a secret key.
    In this example, Keys.secretKeyFor(SignatureAlgorithm.ES512) is used to generate a secret key for signing. It uses the ECDSA (Elliptic Curve Digital Signature Algorithm) with the ES512 algorithm.

    Finally, compact() is called to build the JWT and return it as a string.

*/