package security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private String secret = "jwtTokenKey";

    //retrieve userName from jwt token
    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);

    }
    //retrieve expiration from jwt token
    public Date getExpirationDataFromToken(String token){
        return  getClaimFromToken(token,Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token .we will need the secret key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }
    //check if the token has expired
    private  Boolean isTokenExpired(String token){
        final  Date expiration=getExpirationDataFromToken(token);
        return expiration.before(new Date());
    }
    //generate token for user
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }
    //while creating the token
    //1.Define claims of the token, like Issuer,Expiration,Subject and the ID
    //2.Sign the JwT using the HS512 algorithm and secret key
    //3.According to Jws compact Serialization*

    // compaction of the JWT to a URL-safe string.
    private  String doGenerateToken(Map<String,Object>claims,String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY*100))
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }
//validate token
    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName=getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));

    }
}
