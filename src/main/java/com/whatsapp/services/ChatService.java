package com.whatsapp.services;

import com.whatsapp.entities.Chat;
import com.whatsapp.entities.User;
import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.UserException;
import com.whatsapp.request.GroupChatRequest;

import java.util.List;

public interface ChatService {

    Chat CreateChat(User reqUser, Integer userId2) throws UserException;
    Chat  findChatById(Integer chatId) throws ChatException;
    List<Chat> findChatByUserId(Integer userId) throws UserException;
    Chat createGroup(GroupChatRequest req, User reqUser) throws UserException;
    Chat addUserToGroup(Integer userId,Integer chatId,User reqUser) throws UserException, ChatException;
    Chat renameGroup(Integer chatId, String groupName,User reqUser) throws UserException,ChatException;
    Chat removeFromGroup(Integer chatId,Integer userId,User reqUser) throws UserException,ChatException;
    void deleteChat(Integer chatId,Integer userId) throws UserException,ChatException;
}
