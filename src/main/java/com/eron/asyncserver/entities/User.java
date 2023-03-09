package com.eron.asyncserver.entities;

import com.eron.asyncserver.dto.NewUserData;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "app_user")
public class User {

    @Id
    @GeneratedValue
    public int id;
    public String firstName;
    public String lastName;
    public int age;

    public static User from(NewUserData data){
        User user = new User();
        user.age = data.age();
        user.firstName = data.firstName();
        user.lastName = data.lastName();
        return user;
    }

}
