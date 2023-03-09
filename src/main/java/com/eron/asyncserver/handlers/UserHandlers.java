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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class UserHandlers {

    @Autowired
    UserRepository userRepository;

    public ServerResponse createUser(ServerRequest request) throws ServletException, IOException {
        NewUserData data = request.body(NewUserData.class);
        User newUser = User.from(data);
        UriComponentsBuilder builder = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}");

        return ServerResponse.async(CompletableFuture.supplyAsync(()->{
            User userSaved = userRepository.save(newUser);
            return ServerResponse.created(builder
                    .buildAndExpand(userSaved.id)
                    .toUri())
                    .build();
        })
        );
    }

    public ServerResponse getAllUsers(ServerRequest request) {
        return ServerResponse
                .async(CompletableFuture
                        .supplyAsync(()-> ServerResponse
                                .ok()
                                .body(userRepository
                                        .findAll())));
    }

    public ServerResponse getUser(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse
                .async(CompletableFuture
                        .supplyAsync(()->ServerResponse
                                .ok()
                                .body(userRepository
                                        .findById(Integer
                                                .valueOf(id))
                                        .get()))
                );
    }

    public ServerResponse updateUser(ServerRequest request) throws ServletException, IOException {
        User user = request.body(User.class);
        int id = Integer.valueOf(request.pathVariable("id"));
        if(user.id != id) throw new RuntimeException("Id's are not equals");

        return ServerResponse
                .async(CompletableFuture
                        .supplyAsync(()->{
                            boolean isExists = userRepository.existsById(id);

                            if(!isExists) return ServerResponse
                                    .status(404)
                                    .body(new Object(){public String message="User don't exists";});

                            userRepository.save(user);
                            return ServerResponse.ok().build();
        }));
    }

    public ServerResponse deleteUser(ServerRequest request) {
        String id = request.pathVariable("id");
        userRepository.deleteById(Integer.valueOf(id));

        return ServerResponse
                .async(CompletableFuture
                        .supplyAsync(()-> ServerResponse
                                .ok()
                                .build()));
    }
}
