package com.hungnv28.core.utils;

import com.hungnv28.core.enums.TokenField;
import com.hungnv28.core.models.TokenData;
import com.hungnv28.core.models.UserInfo;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static jakarta.xml.bind.DatatypeConverter.parseBase64Binary;

@Component
public class JwtTokenUtil {
    static Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    public static String generateToken(TokenData data) throws Exception {
        long iatDate = System.currentTimeMillis();
        long expDate = System.currentTimeMillis() / 1000 + 3600 * 24;

        Map<String, Object> claim = new HashMap<>();
        claim.put(TokenField.iat.getValue(), iatDate);
        claim.put(TokenField.exp.getValue(), expDate);

        claim.put(TokenField.email.getValue(), data.getEmail());
        claim.put(TokenField.phone.getValue(), data.getEmail());
        claim.put(TokenField.userId.getValue(), data.getEmail());
        claim.put(TokenField.userName.getValue(), data.getEmail());
        claim.put(TokenField.fullName.getValue(), data.getEmail());

        Map<String, Object> header = new HashMap<>();
        header.put(TokenField.header_alg.toString(), "RS256");
        header.put(TokenField.header_typ.toString(), "JWT");

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(parseBase64Binary(getKey("keys/stock_private.pem", true)));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = factory.generatePrivate(spec);

        return Jwts.builder()
                .setHeader(header)
                .setClaims(claim)
                .setSubject(data.getUserName())
                .setIssuedAt(new Date(iatDate))
                .setExpiration(new Date(expDate))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    public static UserInfo verifyToken(String token) {
        if (token == null) return null;

        try {
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(parseBase64Binary(getKey("keys/stock_private.pem", false)));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey bobPubKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
            JWSVerifier verifier = new RSASSAVerifier(bobPubKey);

            SignedJWT signedJWT = SignedJWT.parse(token);
            if (signedJWT.verify(verifier)) {
                Map<String, Object> objInfo = signedJWT.getPayload().toJSONObject();
                UserInfo userInfo = new UserInfo(objInfo);
                if (userInfo.verify()) {
                    return userInfo;
                }
            }
        } catch (ExpiredJwtException ex) {
            logger.error("JWT expired {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("Token is null, empty or only whitespace {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            logger.error("JWT is not supported", ex);
        } catch (Exception ex) {
            logger.error("Signature validation failed");
        }

        return null;
    }

    public static String getKey(String filename, boolean isPrivateKey) throws Exception {
        try (InputStream is = new ClassPathResource("/" + filename).getInputStream()) {
            String temp = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining(""));
            String pem;
            if (isPrivateKey) {
                pem = temp.replace("-----BEGIN RSA PRIVATE KEY-----", "");
                pem = pem.replace("-----END RSA PRIVATE KEY-----", "");
            } else {
                pem = temp.replace("-----BEGIN PUBLIC KEY-----", "");
                pem = pem.replace("-----END PUBLIC KEY-----", "");
            }
            return pem;
        }
    }
}
