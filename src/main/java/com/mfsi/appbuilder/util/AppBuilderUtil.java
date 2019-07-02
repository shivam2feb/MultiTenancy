package com.mfsi.appbuilder.util;

import com.mfsi.appbuilder.dto.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppBuilderUtil {

    public static String getLoggedInUsername() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    public static String getLoggedInUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUserId();
    }
}
