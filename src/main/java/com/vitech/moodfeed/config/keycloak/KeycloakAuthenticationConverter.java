package com.vitech.moodfeed.config.keycloak;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class KeycloakAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@Nonnull Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        return ((List<String>) realmAccess.get("roles")).stream()
                .map(roleName -> "ROLE_" + roleName) // prefix to map to a Spring Security "role"
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
