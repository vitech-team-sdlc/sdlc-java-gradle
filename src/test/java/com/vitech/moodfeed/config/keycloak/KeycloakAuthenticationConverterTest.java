package com.vitech.moodfeed.config.keycloak;

import com.vitech.moodfeed.SmallTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeycloakAuthenticationConverterTest extends SmallTest {

    private final KeycloakConfig converter = new KeycloakConfig();

    @Test
    void testConvert() {
        List<String> roles = Arrays.asList("ADMIN", "USER");
        Map<String, List<String>> realmAccess = Map.of("roles", roles);
        Jwt jwt = Jwt.withTokenValue("{}")
                .header("header", "test value")
                .claim("realm_access", realmAccess)
                .build();
        AbstractAuthenticationToken authenticationToken = converter.keycloakAuthenticationConverter().convert(jwt);
        assertNotNull(authenticationToken);
        assertNotNull(authenticationToken.getAuthorities());
        assertTrue(authenticationToken.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(authenticationToken.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
