package com.whatsapp.services.impl;

import com.whatsapp.config.TokenProvider;
import com.whatsapp.entities.User;
import com.whatsapp.exception.UserException;
import com.whatsapp.repositories.UserRepository;
import com.whatsapp.request.UpdateUserRequest;
import com.whatsapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String email=tokenProvider.getEmailFromToken(jwt);
        User user=userRepository.findByEmail(email);
        if (user==null){
            throw new UserException("User has entered null email "+email);
        }
        return user;
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
        User user=findUserById(userId);
//        if(user.getFull_name()!=null){
//            user.setFull_name(req.getFull_name());
//        }
        user.setFull_name(req.getFull_name());
//        if(user.getProfile_picture()!=null){
//            user.setProfile_picture(req.getProfile_picture());
//        }
        user.setProfile_picture(req.getProfile_picture());
        User user1 = userRepository.save(user);
        //System.out.println(user1.getProfile_picture());
        return user1;
    }

    @Override
    public User findUserById(Integer id) throws UserException {

        return this.userRepository.findById(id).orElseThrow(()->new UserException("User Not found with this "+id));
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> user=userRepository.searchUser(query);
        return user;
    }
}
