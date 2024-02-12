package com.example.gatewayservise;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Predicate;

@Configuration
public class GatewayConfig {

    @Bean
    public AbstractRoutePredicateFactory<?> customRoutePredicateFactory() {
        return new CustomRoutePredicateFactory();
    }

    private static class CustomRoutePredicateFactory extends AbstractRoutePredicateFactory<CustomRoutePredicateFactory.Config> {

        public CustomRoutePredicateFactory() {
            super(Config.class);
        }



        private URI rewriteLocation(URI original) {
            // Проверяем, что перенаправление идет на ваш микросервис
            if (original.getHost().equals("http://localhost:8081/") || original.getHost().equals("http://localhost:8082/")) {
                // Заменяем порт на порт шлюза
                return original.resolve(":" + 8080); // здесь используйте порт вашего Spring Cloud Gateway
            }
            return original;
        }




        @Override
        public Predicate<ServerWebExchange> apply(Config config) {
            return null;
        }

        public static class Config {
            // Конфигурация фильтра, если требуется
        }
    }
}

