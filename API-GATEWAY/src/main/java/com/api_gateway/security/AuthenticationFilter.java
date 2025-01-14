package com.api_gateway.security;

import com.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RouteValidator routeValidator;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing Authorization Header!!!!");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    //REST CALL TO AUTH SERVICE
                    //restTemplate.getForObject("http://USER-SERVICE//validate?token" + authHeader, String.class);
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e) {
                    System.out.println("Invalid Call....");
                    throw new RuntimeException("Unauthorized access to application");
                }
            }
            return chain.filter(exchange);
        }));
    }


    public static class Config {

    }
}
