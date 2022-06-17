package com.javaabuser.todolist.services;

import com.javaabuser.todolist.models.User;
import com.javaabuser.todolist.repositories.UsersRepository;
import com.javaabuser.todolist.security.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByName(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return new com.javaabuser.todolist.security.UserDetails(user.get());
    }
}
