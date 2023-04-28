package com.canerzin.notes.service.service;

import com.canerzin.notes.service.dto.UserDto;
import com.canerzin.notes.service.model.User;
import com.canerzin.notes.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public User createUser(UserDto userDto) throws Exception {
        var optional = userRepository.findById(userDto.getUsername());
        if (optional.isPresent()) {
            throw new Exception("User already exist ");
        }
        return userRepository.save(new User(userDto.getUsername(),userDto.getPassword()));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUserName(String username) throws Exception {
        var optional = userRepository.findById(username);
        if (optional.isEmpty()) {
            throw new Exception("User Not Found");
        }
        return optional.get();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public User updateUser(String username, UserDto userDto) throws Exception {
        var optional = userRepository.findById(username);
        if (optional.isEmpty()) {
            throw new Exception("User Not Found");
        }
        var user = optional.get();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optional = userRepository.findById(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found " + username);
        }
        var user = optional.get();
        Set<SimpleGrantedAuthority> authoritySet = new HashSet<>();
        authoritySet.add(new SimpleGrantedAuthority("read"));
        return org.springframework.security.core.userdetails.User.builder().passwordEncoder(s-> {return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword());}).username(user.getUsername()).password(user.getPassword()).authorities("user").build();
    }
}
