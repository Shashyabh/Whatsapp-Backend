package com.whatsapp.repositories;

import com.whatsapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

    @Query("Select u from User u where u.full_name LIKE %:name%")
    List<User> searchUser(@Param("name") String name);
}
