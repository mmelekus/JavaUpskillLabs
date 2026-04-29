package com.example.bankbff.controller;

import com.example.bankbff.dto.UserInfoDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints related to the authenticated user.
 *
 * The SPA calls /api/me on load to determine whether the user is logged in
 * and to display their name in the header. If no user is authenticated,
 * Spring Security returns 401 before this controller is invoked.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/me")
    public UserInfoDto me(@AuthenticationPrincipal OidcUser user) {
        // TODO 7.1: Build and return a UserInfoDto from the OidcUser.
        //
        // The OidcUser exposes the ID token claims:
        //   user.getSubject()           -- the unique user ID (sub claim)
        //   user.getPreferredUsername() -- typically the username
        //   user.getFullName()          -- the user's display name
        //
        // For the roles list, look up the "roles" claim:
        //   user.getClaimAsStringList("roles")
        //
        // Hints:
        //   - For username, prefer getPreferredUsername(); fall back to getSubject() if null.
        //   - For name, prefer getFullName(); fall back to username if null.
        //   - If the roles claim is null, use List.of().
        String username = user.getPreferredUsername();
        if (username == null) {
            username = user.getSubject();
        }

        String name = user.getFullName();
        if (name == null) {
            name = username;
        }

        List<String> roles = user.getClaimAsStringList("roles");
        if (roles == null) {
            roles = List.of();
        }

        return new UserInfoDto(username, name, roles);
    }
}