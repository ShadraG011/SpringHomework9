package ru.shadrag.usersmicroservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shadrag.usersmicroservices.models.User;
import ru.shadrag.usersmicroservices.services.interfaces.UsersService;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "/all-users", method = RequestMethod.GET)
    public String indexUsers(Model model) {
        model.addAttribute("users", usersService.getAllUsers());
        return "users-views/all-users";
    }

    @RequestMapping(value = "/set-task", method = RequestMethod.GET)
    public String setTaskForUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("tasks", usersService.getAllTasks().stream().filter(it -> it.getUser() == null).toList());
        model.addAttribute("user", usersService.getUserById(id));
        return "users-views/set-task";
    }

    @RequestMapping(value = "/set-task", method = RequestMethod.POST)
    public String setTaskForUser(@RequestParam("id") Long id, @RequestParam("taskId") Long taskId) {
        User user = usersService.getUserById(id);
        user.getUserTasksList().add(usersService.getTaskById(taskId));
        usersService.updateTask(taskId, user);
        return "redirect:/users/all-users";
    }


    @RequestMapping(value = "/create-user", method = RequestMethod.GET)
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "users-views/create-user";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String createUser(@ModelAttribute User user) {
        usersService.createUser(user);
        return "redirect:/users/all-users";
    }

    @RequestMapping(value = "/delete-user", method = RequestMethod.GET)
    public String deleteUser(@RequestParam("id") Long id) {
        usersService.deleteUser(id);
        return "redirect:/users/all-users";
    }
}
