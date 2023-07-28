package com.whatsapp.controller;


import com.whatsapp.entities.User;
import com.whatsapp.exception.UserException;
import com.whatsapp.request.UpdateUserRequest;
import com.whatsapp.response.ApiResponse;
import com.whatsapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("name") String name){
        List<User> users=userService.searchUser(name);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req, @RequestHeader ("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        User updateUser = userService.updateUser(user.getId(), req);
        //System.out.println("updated photo url-----> "+req.getProfile_picture()+" User Id "+user.getId());
        ApiResponse response=new ApiResponse("User updated successfully ",true);
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }
}
