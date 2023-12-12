package com.hungnv28.core.utils;

import com.hungnv28.core.entities.UsersEntity;
import com.hungnv28.core.enums.TokenField;
import com.hungnv28.core.models.UserInfo;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static jakarta.xml.bind.DatatypeConverter.parseBase64Binary;

public class JwtTokenUtil {
    static Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static long getExpTime(int hours) {
        return (60 * 60) * hours;
    }

    public static String generateToken(UsersEntity data, boolean isRefreshToken) {
        try {
            int hours = isRefreshToken ? 48 : 24;
            long iatDate = System.currentTimeMillis();
            long expDate = (System.currentTimeMillis() / 1000 + getExpTime(hours)) * 1000;

            Map<String, Object> header = new HashMap<>();
            header.put(TokenField.header_alg.getValue(), "RS256");
            header.put(TokenField.header_typ.getValue(), "JWT");

            Map<String, Object> claim = new HashMap<>();
            claim.put(TokenField.iat.getValue(), iatDate);
            claim.put(TokenField.exp.getValue(), expDate);

            claim.put(TokenField.role.getValue(), data.getRole());
            claim.put(TokenField.email.getValue(), data.getEmail());
            claim.put(TokenField.phone.getValue(), data.getPhone());
            claim.put(TokenField.userId.getValue(), data.getUserId());
            claim.put(TokenField.userName.getValue(), data.getUsername());
            claim.put(TokenField.fullName.getValue(), data.getFullName());


            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(parseBase64Binary(getKey("keys/stock_private_key.pem", true)));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(spec);

            return Jwts.builder()
                    .setHeader(header)
                    .setClaims(claim)
                    .setSubject(data.getUsername())
                    .signWith(privateKey, SignatureAlgorithm.RS256)
                    .compact();
        } catch (Exception e) {
            logger.error("Generate Token failed", e);
            return null;
        }
    }

    public static Claims getClaims(String token) {
        if (token == null) return null;

        try {
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(parseBase64Binary(getKey("keys/stock_public_key.pem", false)));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            return Jwts.parserBuilder().setSigningKey(publicKey).build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            logger.error("JWT expired {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("Token is null, empty or only whitespace {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            logger.error("JWT is not supported", ex);
        } catch (Exception ex) {
            logger.error("Signature validation failed", ex);
        }

        return null;
    }

    public static UserInfo verifyToken(String token) {
        if (token == null) return null;

        try {
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(parseBase64Binary(getKey("keys/stock_public_key.pem", false)));
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
            logger.error("Signature validation failed", ex);
        }

        return null;
    }

    public static String getKey(String filename, boolean isPrivateKey) throws Exception {
        try (InputStream is = new ClassPathResource("/" + filename).getInputStream()) {
            String temp = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining(""));
            String pem;
            if (isPrivateKey) {
                pem = temp.replace("-----BEGIN PRIVATE KEY-----", "");
                pem = pem.replace("-----END PRIVATE KEY-----", "");
            } else {
                pem = temp.replace("-----BEGIN PUBLIC KEY-----", "");
                pem = pem.replace("-----END PUBLIC KEY-----", "");
            }
            return pem;
        }
    }
}
