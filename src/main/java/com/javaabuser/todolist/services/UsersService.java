package com.javaabuser.todolist.services;

import com.javaabuser.todolist.models.User;
import com.javaabuser.todolist.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<User> findById(int id){
        return usersRepository.findById(id);
    }

    @Transactional
    public void save(User user){
        usersRepository.save(user);
    }
}
