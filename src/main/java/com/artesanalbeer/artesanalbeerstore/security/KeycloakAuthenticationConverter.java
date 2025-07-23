package com.artesanalbeer.artesanalbeerstore.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class KeycloakAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        return new JwtAuthenticationToken(
                source,
                Stream.concat(
                                new JwtGrantedAuthoritiesConverter().convert(source).stream(),
                                extractResourceRoles(source).stream())
                        .collect(Collectors.toSet()));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(@NonNull Jwt source) {
        Object resourceAccessClaim = source.getClaim("resource_access");
        if (!(resourceAccessClaim instanceof Map<?, ?> resourceAccess)) {
            return Collections.emptySet();
        }

        Object accountAccess = resourceAccess.get("account");
        if (!(accountAccess instanceof Map<?, ?> accountMap)) {
            return Collections.emptySet();
        }

        Object rolesObject = accountMap.get("roles");
        if (!(rolesObject instanceof List<?> rolesList)) {
            return Collections.emptySet();
        }

        return rolesList.stream()
                .filter(role -> role instanceof String)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + ((String) role).replace("-", "_")))
                .collect(Collectors.toSet());
    }


}
