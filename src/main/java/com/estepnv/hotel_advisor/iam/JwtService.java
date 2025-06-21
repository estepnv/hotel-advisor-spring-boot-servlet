package com.estepnv.hotel_advisor.iam;

import com.estepnv.hotel_advisor.exceptions.RecordNotFoundException;
import com.estepnv.hotel_advisor.users.User;
import com.estepnv.hotel_advisor.users.UserRepository;
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
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RecordNotFoundException(User.class, request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserPasswordDoesNotMatchException();
        }

        var now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("hotel_advisor")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(36000L))
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
