package com.whatsapp.services.impl;

import com.whatsapp.entities.Chat;
import com.whatsapp.entities.User;
import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.UserException;
import com.whatsapp.repositories.ChatRepository;
import com.whatsapp.request.GroupChatRequest;
import com.whatsapp.services.ChatService;
import com.whatsapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;


    @Override
    public Chat CreateChat(User reqUser, Integer userId2) throws UserException {
        User user2 = userService.findUserById(userId2);

        Chat isChatExist=chatRepository.findSingleChatByUserIds(user2,reqUser);
        if(isChatExist!=null){
            return isChatExist;
        }

        Chat chat=new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user2);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);

        chat=chatRepository.save(chat);
        return chat;
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isPresent()){
            return chat.get();
        }
        throw new ChatException("Chat not found with Id"+chatId);
    }

    @Override
    public List<Chat> findChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        List<Chat> chats = chatRepository.findChatByUserId(user.getId());
        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {

        Chat group=new Chat();
        group.setGroup(true);
        group.setChat_image(req.getChat_image());
        group.setChat_name(req.getChat_name());
        group.setCreatedBy(reqUser);
        group.getAdmin().add(reqUser);

        for (Integer userId:req.getUserIds()){
            User user=userService.findUserById(userId);
            group.getUsers().add(user);
        }
        Chat groupChat = chatRepository.save(group);
        return groupChat;
    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        if(optionalChat.isPresent()){
            Chat chat=optionalChat.get();
            if(chat.getAdmin().contains(reqUser)){
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            }
            else {
                throw new UserException("You are not admin ");
            }
        }
        throw new ChatException("Chat not found with Id "+chatId);
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws UserException, ChatException {

        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if(optionalChat.isPresent()){
            Chat chat = optionalChat.get();
            if(chat.getUsers().contains(reqUser)){
                chat.setChat_name(groupName);
                return chatRepository.save(chat);
            }
            throw new UserException("You are not member of this group");
        }
        throw new ChatException("Chat not found with this id"+chatId);
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {

        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        if(optionalChat.isPresent()){
            Chat chat = optionalChat.get();

            //User is Admin -> Remove itself and Users
            if(chat.getUsers().contains(reqUser)){
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            }
            //User is Normal User ->>Remove itself
            else if(chat.getUsers().contains(reqUser)){
                if(user.getId().equals(reqUser.getId())){
                    chat.getUsers().remove(user);
                    return chatRepository.save(chat);
                }
            }
            throw new UserException("You are not admin");
        }
        throw new ChatException("Chat not found with this id"+chatId);
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws UserException, ChatException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if (optionalChat.isPresent()){
            Chat chat=optionalChat.get();
            chatRepository.deleteById(chat.getId());
        }
    }
}
