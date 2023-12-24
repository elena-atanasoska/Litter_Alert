package com.example.litteralert.service.impl;

import com.example.litteralert.model.enums.Role;
import com.example.litteralert.model.User;
import com.example.litteralert.model.exceptions.InvalidArgumentsException;
import com.example.litteralert.model.exceptions.PasswordsDoNotMatchException;
import com.example.litteralert.model.exceptions.UsernameAlreadyExistsException;
import com.example.litteralert.repository.UserRepository;
import com.example.litteralert.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, String phoneNumber) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidArgumentsException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = new User(username,passwordEncoder.encode(password),name,surname,Role.ROLE_USER, phoneNumber);
        return userRepository.save(user);
    }
}
