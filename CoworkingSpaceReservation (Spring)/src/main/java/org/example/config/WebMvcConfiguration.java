package org.example.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor = registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("currentUser") == null) {
                    response.sendRedirect("/login");
                    return false;
                }
                return true;
            }
        });

        interceptor.addPathPatterns("/dashboard", "/user/reservations", "/admin/reservations", "user/spaces", "/admin/spaces",
                        "/admin/reservations/all", "/user/reservations/list", "/user/reservations/add-reservation-form", "/user/reservations/remove-form",
                        "/admin/spaces/add-spaces-form", "/admin/spaces/remove-spaces-form", "/user/spaces/available", "/user/spaces/available-list")
                .excludePathPatterns("/login");
    }
}
