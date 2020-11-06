package com.vitech.moodfeed.config.keycloak;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
@ConditionalOnProperty(name = "security.provider", havingValue = "keycloak")
public class KeycloakConfig {

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> keycloakAuthenticationConverter() {
        return new KeycloakAuthenticationConverter();
    }

}
