package com.users.app.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.app.service.dto.UserInfoDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }


    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
            getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean isCurrentUserInRole(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
            getAuthorities(authentication).anyMatch(authority::equals);
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority);
    }

    public static UserInfoDetail getInfoCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String payload = authentication.getCredentials().toString().split("\\.")[1];
        String decodedPayload = new String(Base64.getUrlDecoder().decode(payload));
        ObjectMapper objectMapper1 = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        try {
            result = objectMapper1.readValue(decodedPayload, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Object uiClaim = result.get("ui");
        if (uiClaim instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) uiClaim;
            UserInfoDetail userInfo = new UserInfoDetail();
            userInfo.setId(map.get("id") == null ? null : Long.valueOf(map.get("id").toString()));
            userInfo.setLogin(map.get("login") == null ? null : map.get("login").toString());
            userInfo.setName(map.get("name") == null ? null : map.get("name").toString());
            userInfo.setEmail(map.get("email") == null ? null : map.get("email").toString());
            userInfo.setImageUrl(map.get("imageUrl") == null ? null : map.get("imageUrl").toString());
            userInfo.setActivated(map.get("activated") != null && Boolean.parseBoolean(map.get("activated").toString()));
            userInfo.setLangKey(map.get("langKey") == null ? null : map.get("langKey").toString());
            userInfo.setCreatedBy(map.get("createdBy") == null ? null : map.get("createdBy").toString());
            userInfo.setCreatedDate(map.get("createdDate") == null ? null : (Instant) map.get("createdDate"));
            userInfo.setLastModifiedBy(map.get("lastModifiedBy") == null ? null : map.get("lastModifiedBy").toString());
            userInfo.setLastModifiedDate(map.get("lastModifiedDate") == null ? null : (Instant) map.get("lastModifiedDate"));
            String authorities = result.get("auth").toString();
            if (authorities != null) {
                userInfo.setAuthorities(Arrays.asList(authorities.split(",")));
            }
            return userInfo;
        }
        return null;
    }
}
