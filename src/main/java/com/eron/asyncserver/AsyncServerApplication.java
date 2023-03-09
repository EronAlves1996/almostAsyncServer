package com.eron.asyncserver;

import com.eron.asyncserver.handlers.UserHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@SpringBootApplication
public class AsyncServerApplication {

	@Autowired
	UserHandlers userHandlers;

	public static void main(String[] args) {
		SpringApplication.run(AsyncServerApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> routerFunctions(){
		return RouterFunctions.route()
				.path("/users", r ->r
						.path("/{id}", p -> p
										.GET(userHandlers::getUser)
										.PUT(userHandlers::updateUser)
										.DELETE(userHandlers::deleteUser)
								.build())
								.POST(userHandlers::createUser)
								.GET(userHandlers::getAllUsers)
						)
				.build();
	}

}
