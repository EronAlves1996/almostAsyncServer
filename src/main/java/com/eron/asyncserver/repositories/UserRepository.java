package com.eron.asyncserver.repositories;

import com.eron.asyncserver.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Async
    CompletableFuture<Long> save(User user);

}
