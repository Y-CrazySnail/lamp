package com.yeem.common.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class OauthUtils {
    /**
     * get username
     * @return username
     */
    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
