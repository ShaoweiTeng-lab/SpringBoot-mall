package utils;


import io.jsonwebtoken.*;

import org.springframework.stereotype.Component;

import javax.security.auth.Subject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(5);
    /**
     * JWT SECRET KEY
     */
    private static final String SECRET = "57c7cb6133e92eff8e514eb3e50aec9284741faa7b097dd5dca8dbaffb8a80d7";


    /**
     * 簽發JWT
     */
    public String generateToken(Map<String, String> userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put( "userName", userDetails.get("userName") );
        return Jwts.builder()
                .setClaims( claims )
                .setExpiration( new Date( Instant.now().toEpochMilli() + EXPIRATION_TIME  ) )//設定過期
                .signWith(SignatureAlgorithm.HS256, SECRET )//設定簽章
                .compact();//返回的 JwtBuilder 物件的一個方法。
    }
    /**
     * 驗證JWT
     */
    public Claims validateToken(String token) {
        System.out.println(token);
        try {
           return Jwts.parser()
                    .setSigningKey( SECRET )
                    .parseClaimsJws( token )
                    .getBody();

        } catch (SignatureException e) {
            //throw new SignatureException("Invalid JWT signature.");
        }
        catch (MalformedJwtException e) {
           // throw new MalformedJwtException("Invalid JWT token.");
        }
        catch (ExpiredJwtException e) {
           // throw e;
        }
        catch (UnsupportedJwtException e) {
           // throw new UnsupportedJwtException("Unsupported JWT token");
        }
        catch (IllegalArgumentException e) {
          //  throw new IllegalArgumentException("JWT token compact of handler are invalid");
        }
        return null;
    }

    public  String getUserName(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET) // 得到Claims 資訊
                .parseClaimsJws(jwt)
                .getBody();
        return  claims.get("userName",String.class);
    }

    public String createJwt(  String sub){
        return Jwts.builder()
                .setSubject(sub)
                .setExpiration( new Date( Instant.now().toEpochMilli() + EXPIRATION_TIME  ) )//設定過期
                .signWith(SignatureAlgorithm.HS256, SECRET )//設定簽章
                .compact();//返回的 JwtBuilder 物件的一個方法。
    }
}
