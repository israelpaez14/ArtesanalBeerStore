package com.artesanalbeer.artesanalbeerstore.config;

import com.artesanalbeer.artesanalbeerstore.entities.User;
import com.artesanalbeer.artesanalbeerstore.reposotory.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component("auditorProvider")
@RequiredArgsConstructor
public class AuditorAwareImp implements AuditorAware<User> {
    private final UserRepository userRepository;

    @Override
    @NonNull
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof String)) {
            return Optional.empty();
        }

        if (!authentication.isAuthenticated()) {
            return Optional.empty();
        }
        try {
            UUID userId = UUID.fromString(authentication.getName());
            return userRepository.findById(userId);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

    }
}
