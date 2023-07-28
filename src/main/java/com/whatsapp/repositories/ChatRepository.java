package com.whatsapp.repositories;

import com.whatsapp.entities.Chat;
import com.whatsapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Integer> {

    //Checking wheather both user is present inside any chat
    @Query("SELECT c FROM Chat c WHERE c.isGroup = false AND :user2 MEMBER OF c.users AND :reqUser MEMBER OF c.users")
    Chat findSingleChatByUserIds(@Param("user2") User user2, @Param("reqUser") User reqUser);


    //@Query("Select c from Chat c join c.users u where u.id=:userId")
    @Query("SELECT c FROM Chat c join c.users u WHERE u.id=:userId or c.createdBy.id = :userId")
    List<Chat> findChatByUserId(@Param("userId") Integer userId);
}
