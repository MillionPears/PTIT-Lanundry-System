package com.laundry.apigateway.config;

import com.laundry.apigateway.Constants;
import com.laundry.apigateway.filter.JwtRoleAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;



import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    private final JwtRoleAuthenticationFilter jwtRoleAuthenticationFilter;

    private GatewayFilter applyJwtAuthentication(List<String> allowedRoles) {
        return (exchange, chain) -> jwtRoleAuthenticationFilter.filter(exchange, chain)
                .doFinally(signalType -> processRoles(exchange, chain, allowedRoles));
    }


    private void processRoles(ServerWebExchange exchange, GatewayFilterChain chain, List<String> allowedRoles) {
        List<String> roles = exchange.getAttribute("roles");
        if (roles != null && !Collections.disjoint(roles, allowedRoles)) {
            chain.filter(exchange); // Continue with the chain if roles contain any of the allowedRoles
        } else {
            jwtRoleAuthenticationFilter.onError(exchange); // Handle unauthorized access
        }
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        List<String> allowedRoles = Arrays.asList("ADMIN", "KH","NV"); // Example of allowed roles

        return builder.routes()
                // Customer route
                .route("order-service", r->r.path(
                                Constants.ORDER_PREFIX+"/create",
                                Constants.ORDER_PREFIX+"/update/{id}/deleverytype/{type}"
                        ).and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(jwtRoleAuthenticationFilter))
                        .filters(f -> f.filter(applyJwtAuthentication(
                                allowedRoles.stream()
                                        .filter(role -> role.equals("KH"))
                                        .collect(Collectors.toList())
                        )))
                        .uri("lb://order-service"))
                .route("customer-service", r -> r.path(


                                Constants.CUSTOMER_PREFIX+"/getbyusername/{username}",
                                Constants.CUSTOMER_PREFIX+"/update/{customerId}")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(
                                allowedRoles.stream()
                                        .filter(role -> role.equals("KH"))
                                        .collect(Collectors.toList())
                        )))
                        .uri("lb://customer-service"))
                .route("user-service", r -> r.path(
                                Constants.USER_PREFIX+"/register")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)

                        .uri("lb://user-service"))
                // Staff route
//                .route("staff-service", r -> r.path(
//                                Constants.STAFF_PREFIX+"/update/{id}")
//                        .and()
//                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
//                        .filters(f -> f.filter(applyJwtAuthentication(
//                                allowedRoles.stream()
//                                        .filter(role -> role.equals("NV"))
//                                        .collect(Collectors.toList())
//                        )))
//                        .uri("lb://staff-service"))
                // Admin route
                .route("promotion-service", r -> r.path(
                                Constants.PROMOTION_PREFIX+"create",
                                Constants.PROMOTION_PREFIX+"/update/{id}",
                                Constants.PROMOTION_PREFIX+"/delete/{id}",
                                Constants.PROMOTION_PREFIX+"/list/{status}",
                                Constants.PROMOTION_PREFIX+"/getall",
                                Constants.PROMOTION_PREFIX+"/update/status/{status}/byid/{id}",
                                Constants.PROMOTION_PREFIX+"/{id}")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(
                                allowedRoles.stream()
                                        .filter(role -> role.equals("ADMIN"))
                                        .collect(Collectors.toList())
                        )))
                        .uri("lb://promotion-service"))
                .route("service-service", r -> r.path(
                                Constants.SERVICE_PREFIX+"/create",
                                Constants.SERVICE_PREFIX+"/update/{serviceId}",
                                Constants.SERVICE_PREFIX+"/update/promotionid/{promotionId}",
                                Constants.SERVICE_PREFIX+"/update/promotionid/{promotionid}/forservices",
                                Constants.SERVICE_PREFIX+"/getall/bypromotionid/{promotionid}",
                                Constants.SERVICE_PREFIX+"/getall")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(
                                allowedRoles.stream()
                                        .filter(role -> role.equals("ADMIN"))
                                        .collect(Collectors.toList())
                        )))
                        .uri("lb://service-service"))
                .route("customer-service", r -> r.path(
                                Constants.RANKING_PREFIX+"/create",
                                Constants.SERVICE_PREFIX+"/update/{serviceId}")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(
                                allowedRoles.stream()
                                        .filter(role -> role.equals("ADMIN"))
                                        .collect(Collectors.toList())
                        )))
                        .uri("lb://customer-service"))
                .route("staff-service", r -> r.path(
                                Constants.STAFF_PREFIX+"/create",
                                Constants.STAFF_PREFIX+"/list/all",
                                Constants.SCHEDULE_PREFIX+"/create",

                                Constants.SCHEDULE_PREFIX+"/delete/{id}",
                                Constants.STAFF_PREFIX+"/byId/{id}")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(
                                allowedRoles.stream()
                                        .filter(role -> role.equals("ADMIN"))
                                        .collect(Collectors.toList())
                        )))
                        .uri("lb://staff-service"))
                .route("warehouse-service", r -> r.path(
                                Constants.GOODS_PREFIX+"/create",
                                Constants.GOODS_PREFIX+"/update/{id}"
                                )
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(
                                allowedRoles.stream()
                                        .filter(role -> role.equals("ADMIN"))
                                        .collect(Collectors.toList())
                        )))
                        .uri("lb://warehouse-service"))
                // Admin $ Staff route
                .route("customer-service", r -> r.path(
                                Constants.CUSTOMER_PREFIX+"/list/all",
                                Constants.CUSTOMER_PREFIX+"/list/byName",
                                Constants.CUSTOMER_PREFIX+"/list/byrank/{rankId}",
                                Constants.CUSTOMER_PREFIX+"/getbyid/{id}")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(
                                allowedRoles.stream()
                                        .filter(role -> role.equals("ADMIN")||role.equals("NV"))
                                        .collect(Collectors.toList())
                        )))
                        .uri("lb://customer-service"))
                .route("order-service", r -> r.path(
                                Constants.ORDER_PREFIX+"/update/{id}/orderstatus/{status}",
                                Constants.ORDER_PREFIX+"/update/{id}/deliverystatus/{status}",
                                Constants.ORDERDETAIL_PREFIX+"/create",
                                Constants.INVOICE_PREFIX+"/update/status/{id}",
                                Constants.ORDER_PREFIX+"/getall/bystatus/{status}",
                                Constants.ORDER_PREFIX+"/getall",
                                Constants.ORDER_PREFIX+"/list/shipment",
                                Constants.INVOICE_PREFIX+"/getall")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(
                                allowedRoles.stream()
                                        .filter(role -> role.equals("ADMIN")||role.equals("NV"))
                                        .collect(Collectors.toList())
                        )))
                        .uri("lb://order-service"))
                .route("staff-service", r -> r.path(
                        Constants.SCHEDULE_PREFIX+"/list/all",
                                Constants.STAFF_PREFIX+"/update/{id}",
                                Constants.STAFF_PREFIX+"/getbyusername/{username}"
                                )
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(
                                allowedRoles.stream()
                                        .filter(role -> role.equals("ADMIN")||role.equals("NV"))
                                        .collect(Collectors.toList())
                        )))
                        .uri("lb://staff-service"))
                .route("warehouse-service", r -> r.path(
                                Constants.GOODS_PREFIX+"/getall",
                                Constants.PURCHASE_PREFIX+"/create",
                                Constants.GOODS_PREFIX+"/decrease/{id}",
                                Constants.GOODS_PREFIX+"/get/byid/{id}",
                                Constants.PURCHASE_PREFIX+"/getall",
                                Constants.PURCHASEDETAIL_PREFIX+"/create/bystaffid/{staffid}",
                                Constants.PURCHASEDETAIL_PREFIX+"/getall/bypurchaseid/{id}"
                        )
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(
                                allowedRoles.stream()
                                        .filter(role -> role.equals("ADMIN")||role.equals("NV"))
                                        .collect(Collectors.toList())
                        )))
                        .uri("lb://warehouse-service"))
                // ALL route
                .route("promotion-service", r -> r.path(
                                Constants.PROMOTION_PREFIX+"/{id}")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://promotion-service"))
                .route("service-service", r -> r.path(
                                Constants.SERVICE_PREFIX+"/byId/{id}",
                                Constants.SERVICE_PREFIX+"/getall/bystatus/{status}")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://service-service"))
                .route("user-service", r -> r.path(
                                Constants.USER_PREFIX+"/login",
                                Constants.USER_PREFIX+"/getroleid/byusername/{username}",
                                Constants.USER_PREFIX+"/getbyusername/{username}")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://user-service"))

                .route("order-service", r -> r.path(

                                Constants.ORDERDETAIL_PREFIX+"/getdetail/byorderid/{orderid}",
                                Constants.ORDER_PREFIX+"/getall/bycustomerid/{customerid}",
                                Constants.ORDER_PREFIX+"/delete/{id}")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        .filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://order-service"))
                .route("customer-service", r -> r.path(
                                Constants.CUSTOMER_PREFIX+"/create")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
//                        .filters(f -> f.filter(applyJwtAuthentication(
//                                allowedRoles.stream()
//                                        .filter(role -> role.equals("ADMIN")||role.equals("NV"))
//                                        .collect(Collectors.toList())
//                        )))
                        .uri("lb://customer-service"))
                .build();
    }
}
