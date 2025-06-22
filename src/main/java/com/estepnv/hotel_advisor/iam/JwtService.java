package com.estepnv.hotel_advisor.iam;

import com.estepnv.hotel_advisor.exceptions.RecordNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;

    public JwtService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtEncoder jwtEncoder
        ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public String createToken(CreateTokenRequest request) throws RecordNotFoundException {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RecordNotFoundException("User", request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserPasswordDoesNotMatchException();
        }

        var now = Instant.now();

        String scpClaim = user.getRoles().stream()
                .map(r -> r.getName())
                .reduce("", (acc, roleName) -> "%s %s".formatted(acc, roleName));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("hotel_advisor")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(36000L))
                .claim("authorities", scpClaim)
                .subject(user.getId().toString())
                .build();

        JwsHeader header = JwsHeader.with(new JwsAlgorithm() {
            @Override
            public String getName() {
                return "HS256";
            }
        }).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

}
