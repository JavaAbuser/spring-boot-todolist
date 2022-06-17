package com.javaabuser.todolist.services;

import com.javaabuser.todolist.models.Task;
import com.javaabuser.todolist.repositories.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TasksService {
    private final TasksRepository tasksRepository;

    @Autowired
    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Transactional
    public void deleteById(int id){
        tasksRepository.deleteById(id);
    }

    @Transactional
    public void save(Task task){
        tasksRepository.save(task);
    }
}
