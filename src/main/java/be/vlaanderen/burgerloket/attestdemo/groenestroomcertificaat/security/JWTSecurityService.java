package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.security;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import java.util.List;

@CommonsLog
@Service
public class JWTSecurityService
//        extends WebSecurityConfigurerAdapter
{
    private static final String CLIENTID_BURGERPROFIEL = "80689076-8c4a-4bef-abc4-82805e17988d";
//    private static final String ISSUE_URI = "https://authenticatie-ti.vlaanderen.be/op";
    private static final String ISSUE_URI = "https://beta.openid.burgerprofiel.dev-vlaanderen.be/op";

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(ISSUE_URI);
        OAuth2TokenValidator<Jwt> audienceValidator = new JwtClaimValidator<List<String>>(JwtClaimNames.AUD, aud ->
                aud.contains(CLIENTID_BURGERPROFIEL));
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(ISSUE_URI);
        jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator));
        return jwtDecoder;
    }
}
