package com.github.skanukov.springify.apps.web.interceptors;

import com.github.skanukov.springify.apps.web.facades.AuthFacade;
import com.github.skanukov.springify.apps.web.viewmodels.AuthViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthModelViewInterceptor extends HandlerInterceptorAdapter {

    private final AuthFacade authFacade;

    @Autowired
    public AuthModelViewInterceptor(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        AuthViewModel authViewModel = new AuthViewModel();
        authViewModel.isSignedIn = authFacade.isAuthenticated();

        if (authViewModel.isSignedIn) {
            authViewModel.user = authFacade.getAuthentication();
        }

        modelAndView.addObject("auth", authViewModel);
    }
}