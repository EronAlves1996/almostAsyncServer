package com.eron.asyncserver.handlers;

import com.eron.asyncserver.dto.NewUserData;
import com.eron.asyncserver.entities.User;
import com.eron.asyncserver.repositories.UserRepository;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class UserHandlers {

    @Autowired
    UserRepository userRepository;

    public ServerResponse createUser(ServerRequest request) throws ServletException, IOException {
        NewUserData data = request.body(NewUserData.class);
        User newUser = User.from(data);

        return ServerResponse.async(
                userRepository
                        .save(newUser)
                        .thenApply(userId -> ServerResponse.created(ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(userId)
                                .toUri()).build())
        );
    }
}
