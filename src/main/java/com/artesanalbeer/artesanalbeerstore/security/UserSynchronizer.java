package com.artesanalbeer.artesanalbeerstore.security;

import com.artesanalbeer.artesanalbeerstore.entities.User;
import com.artesanalbeer.artesanalbeerstore.reposotory.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSynchronizer {
    private final UserRepository userRepository;

    public void synchronizeUser(JwtAuthenticationToken jwtAuthenticationToken) {
        Jwt jwt = jwtAuthenticationToken.getToken();
        Map<String, Object> claims = jwt.getClaims();
        User user = this.mapClaimsToUser(claims);
        userRepository.save(user);
    }


    public User mapClaimsToUser(Map<String, Object> claims) {

        User user = new User();

        if (claims.containsKey("email")) {
            user.setEmail((String) claims.get("email"));
        }

        if (claims.containsKey("sub")) {
            user.setId((UUID) claims.get("sub"));
        }
        if (claims.containsKey("given_name")) {
            user.setFirstName(claims.get("given_name").toString());
        } else if (claims.containsKey("nickname")) {
            user.setFirstName(claims.get("nickname").toString());
        }
        if (claims.containsKey("family_name")) {
            user.setLastName((String) claims.get("family_name"));
        }
        return user;
    }

}
