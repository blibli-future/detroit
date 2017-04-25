package com.blibli.future.detroit.util;

import com.blibli.future.detroit.model.enums.UserType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class TestHelper {
    public static void loginAsAgent() {
        SecurityContextHolder.getContext().setAuthentication(
            new TestingAuthenticationToken("Agent", "secret",
                AuthorityUtils.createAuthorityList(UserType.AGENT.toString()))
        );
    }

    public static void logout() {
        SecurityContextHolder.clearContext();
    }
}
