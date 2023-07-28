package com.whatsapp.services;

import com.whatsapp.entities.User;
import com.whatsapp.exception.UserException;
import com.whatsapp.request.UpdateUserRequest;

import java.util.List;

public interface UserService {

    User findUserProfile(String jwt) throws UserException;

    User updateUser(Integer userId, UpdateUserRequest req) throws UserException;

    User findUserById(Integer id) throws UserException;

    List<User> searchUser(String query);
}
