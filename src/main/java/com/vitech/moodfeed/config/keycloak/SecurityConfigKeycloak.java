package com.vitech.moodfeed.config.keycloak;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

@Configuration
@ConditionalOnProperty(name = "security.provider", havingValue = "keycloak")
public class SecurityConfigKeycloak {

    @Bean
    Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        return new KeycloakRoleConverter();
    }

}
