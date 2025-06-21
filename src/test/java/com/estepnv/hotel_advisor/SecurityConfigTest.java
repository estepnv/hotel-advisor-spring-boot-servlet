package com.estepnv.hotel_advisor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

import javax.crypto.SecretKey;
import java.time.Instant;

@SpringBootTest
@TestPropertySource(properties = {
		"jwt.secret=test-secret-1234567890-ABCDEFGHIJKLMNOPQRSTUVWXYZ"
})
class SecurityConfigTest {

	@Autowired
	private SecretKey jwtSecretKey;

	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private JwtDecoder jwtDecoder;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Test
	void testJwtSecretKeyGeneration() {
		assertNotNull(jwtSecretKey, "SecretKey should not be null");
		assertEquals("HmacSHA256", jwtSecretKey.getAlgorithm(), "Algorithm should be HmacSHA256");
		assertEquals(32, jwtSecretKey.getEncoded().length, "Key should be 256 bits (32 bytes)");
	}

	@Test
	public void testJwtEncodingAndDecoding() {
		try {
			// 1. Create claims
			Instant now = Instant.now();
			JwtClaimsSet claims = JwtClaimsSet.builder()
					.issuer("test-issuer")
					.subject("test-subject")
					.issuedAt(now)
					.expiresAt(now.plusSeconds(3600))
					.build();

			// 2. Encode
			String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
			assertNotNull(token, "Token should not be null");
			assertTrue(token.split("\\.").length == 3, "Token should have 3 parts");

			// 3. Decode
			Jwt decoded = jwtDecoder.decode(token);
			assertEquals("test-issuer", decoded.getIssuer().toString());
			assertEquals("test-subject", decoded.getSubject());

		} catch (Exception e) {
			fail("JWT encoding/decoding failed: " + e.getMessage());
		}
	}
}

