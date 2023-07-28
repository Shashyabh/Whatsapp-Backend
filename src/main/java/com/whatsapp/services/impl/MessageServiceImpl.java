package com.whatsapp.services.impl;

import com.whatsapp.entities.Chat;
import com.whatsapp.entities.Message;
import com.whatsapp.entities.User;
import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.MessageException;
import com.whatsapp.exception.UserException;
import com.whatsapp.repositories.MessageRepository;
import com.whatsapp.request.SendMessageRequest;
import com.whatsapp.services.ChatService;
import com.whatsapp.services.MessageService;
import com.whatsapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Override
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {

        User user = userService.findUserById(req.getUserId());
        Chat chat = chatService.findChatById(req.getChatId());

        Message message=new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimestamp(LocalDateTime.now());
        message=messageRepository.save(message);
        return message;
    }

    @Override
    public List<Message> getChatMessages(Integer chatId,User reqUser) throws ChatException, UserException {

        Chat chat = chatService.findChatById(chatId);

        //If loggedIn user is not present in chat
//        if(!chat.getUsers().contains(reqUser)){
//            throw new UserException("You are not related to this chat"+chat.getId());
//        }
        List<Message> messages = messageRepository.findByChatId(chat.getId());
        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Optional<Message> optional = messageRepository.findById(messageId);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new MessageException("Message not found with this Id");
    }

    @Override
    public void deleteMessage(Integer messageId,User reqUser) throws MessageException, UserException {
        Optional<Message> optional = messageRepository.findById(messageId);

        Message message=optional.get();
        //If loggedIn user is not present in chat
        if(message.getUser().getId().equals(reqUser)){
            messageRepository.deleteById(messageId);
        }
        throw new UserException("You can't deleted other User message"+reqUser.getFull_name());
    }
}
