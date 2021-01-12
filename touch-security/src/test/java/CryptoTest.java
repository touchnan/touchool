import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on 2021/1/6.
 */
public class CryptoTest {
    public static void main(String[] args) {
//        Encoder encoder = new PKCS5S2Encoder();
//        String raw = "123456";
//        String encode = encoder.encode(raw);
//        System.err.println("encode:"+encode);
//        System.err.println("canencode:"+encoder.canDecode(encode));
//        System.err.println("valid:"+encoder.isValid(raw, encode));

        testJJwt();

    }

    private static void testJJwt() {
        String SECRET = "woshiyigeren";

        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        //有10天有效期
        nowTime.add(Calendar.DATE, 10);
        Date expiresDate = nowTime.getTime();

        Claims claims = Jwts.claims();
        claims.put("name","hcq");
        claims.put("uid", "007007");
        claims.setAudience("cy");
        claims.setIssuer("cy");


//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.RS256);
//        secretKey.
        String token = Jwts.builder().setSubject("h").setClaims(claims).signWith(secretKey).setExpiration(expiresDate).compact();
        System.out.println("token = " + token);

        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

        String signature = jws.getSignature();
        Map<String, String> header = jws.getHeader();
        System.out.println("header = " + header);
        Claims Claims = jws.getBody();
        System.out.println("Claims = " + Claims);
    }

    private void testJwt() {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, 10);
        Date expiresDate = nowTime.getTime();


        String SECRET = "woshiyigeren";

        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        String token = JWT.create().withHeader(header)
                .withAudience("what", "a", "b")
                .withClaim("uid", "007007")
                .withClaim("nickname", "hcq")
//                .withIssuedAt(iatDate)
//                .withExpiresAt(expiresDate)
                .sign(Algorithm.HMAC256(SECRET));

        System.out.println(token);

        JWTVerifier build = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT verify = build.verify(token);
        System.out.println("verify = " + verify);

        Map<String, Claim> claims = verify.getClaims();
        System.out.println("claims = " + claims);

        System.out.println("audience = " + verify.getAudience());
        Claim uid = claims.get("uid");
        System.out.println("uid = " + uid.asString());
        System.out.println("nickname = " + claims.get("nickname").asString());
        System.out.println("aud = " + claims.get("aud").asString());

    }
}
