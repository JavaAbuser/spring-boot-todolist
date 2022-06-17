package com.javaabuser.todolist.controllers;

import com.javaabuser.todolist.models.Task;
import com.javaabuser.todolist.models.User;
import com.javaabuser.todolist.security.UserDetails;
import com.javaabuser.todolist.services.TasksService;
import com.javaabuser.todolist.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final UsersService usersService;
    private final TasksService tasksService;

    @Autowired
    public TaskController(UsersService usersService, TasksService tasksService) {
        this.usersService = usersService;
        this.tasksService = tasksService;
    }

    @GetMapping()
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int userId = userDetails.getUser().getId();
        Optional<User> user = usersService.findById(userId);
        user.ifPresent(value -> {
            model.addAttribute("tasks", value.getTasks());
            model.addAttribute("userName", user.get().getName());
        });
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        model.addAttribute("currentDate", dateFormat.format(new Date()));
        return "tasks";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        tasksService.deleteById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/add")
    public String addPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int userId = userDetails.getUser().getId();
        Optional<User> user = usersService.findById(userId);
        Task task = new Task();
        user.ifPresent(value -> {
            model.addAttribute("tasks", value.getTasks());
            task.setUser(user.get());
            model.addAttribute("userName", user.get().getName());
        });
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        model.addAttribute("currentDate", dateFormat.format(new Date()));
        model.addAttribute("taskToBeAdded", task);
        model.addAttribute("localDate", LocalDate.of(2022, 1, 1));
        return "tasks";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("taskToBeAdded") Task task){
        tasksService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/search")
    public String searchByDay(@ModelAttribute("localDate") LocalDate date){
        System.out.println(date.getDayOfWeek());
        return "tasks";
    }
}
