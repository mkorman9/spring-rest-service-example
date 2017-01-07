package com.github.mkorman9.web.filter;

import com.google.common.base.Strings;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SampleFilter implements Filter {
    private static final boolean AUTH_ENABLED = false;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String securityToken = httpRequest.getHeader("X-Security-Token");

        if (AUTH_ENABLED && !isTokenValid(securityToken)) {
            setUnauthorizedStatus(httpResponse);
        }
        else {
            httpRequest.setAttribute("user", "authenticatedUser");
            continueProcessing(servletResponse, filterChain, httpRequest);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    private boolean isTokenValid(String securityToken) {
        return !Strings.isNullOrEmpty(securityToken) && securityToken.equals("token");
    }

    private void setUnauthorizedStatus(HttpServletResponse httpResponse) {
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void continueProcessing(ServletResponse servletResponse, FilterChain filterChain, HttpServletRequest httpRequest) throws IOException, ServletException {
        filterChain.doFilter(httpRequest, servletResponse);
    }
}
