package com.eron.asyncserver.repositories;

import com.eron.asyncserver.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
