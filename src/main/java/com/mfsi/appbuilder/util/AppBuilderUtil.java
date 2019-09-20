package com.mfsi.appbuilder.util;

import com.mfsi.appbuilder.dto.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppBuilderUtil {

    public static String getLoggedInUsername() {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    public static String getLoggedInUserId() {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUserId();
    }
}
