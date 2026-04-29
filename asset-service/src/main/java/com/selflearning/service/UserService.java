package com.selflearning.service;
import com.selflearning.dto.*;
import com.selflearning.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    public User register(String username, String rawPassword, String role);
    public List<UserResponseDto> getAllUsers();
}
