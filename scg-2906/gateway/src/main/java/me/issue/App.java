package me.issue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("json-grpc", r -> r.path("/json/go").filters(f -> {
                    String protoDescriptor = "classpath:desc/descriptor.pb";
                    String protoFile = "classpath:proto/go.proto";
                    String service = "GoService";
                    String method = "goWhere";
                    return f.jsonToGRPC(protoDescriptor, protoFile, service, method);
                }).uri("http://localhost:9000"))
                .route("test", r -> r.path("/test/test").uri("http://www.baidu.com"))
                .build();
    }

}
