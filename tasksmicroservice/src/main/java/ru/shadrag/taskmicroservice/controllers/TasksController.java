package ru.shadrag.taskmicroservice.controllers;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shadrag.taskmicroservice.DAO.UserDao;
import ru.shadrag.taskmicroservice.components.Status;
import ru.shadrag.taskmicroservice.models.Task;
import ru.shadrag.taskmicroservice.services.interfaces.TasksService;


import java.util.Date;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    private boolean onIndexPage;
    private final TasksService tasksService;


    @Autowired
    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @RequestMapping(value = "/all-tasks", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("buttonStat", Status.ALL_TASKS);
        model.addAttribute("tasks", tasksService.getAllTasks());
        onIndexPage = true;
        return "tasks-views/all-tasks";
    }

    @RequestMapping(value = "/all-tasks/{id}/{taskStatus}", method = RequestMethod.GET)
    public String updateStatus(@PathVariable("taskStatus") Status status, @RequestParam("sortStatus") Status sortStatus, @PathVariable("id") Long id) {
        Task task = tasksService.getTaskById(id);
        task.setStatus(status.getDescription());
        task.setDate(new Date());
        tasksService.updateTask(task.getId(), task, task.getUser());

        if (onIndexPage)
            return "redirect:/tasks/all-tasks";
        else
            return "redirect:/tasks/sort-tasks/" + sortStatus;
    }

    @RequestMapping(value = "/update-task/{id}", method = RequestMethod.GET)
    public String updateTask(@PathVariable("id") Long id, @RequestParam(value = "userId", defaultValue = "-1") Long userId, Model model) {
        Task tmpTask = tasksService.getTaskById(id);
        UserDao tmpUser = tasksService.getUserById(userId);
        if (tmpUser != null)
            tmpTask.setUser(tmpUser);
        model.addAttribute("userId", userId);
        model.addAttribute("task", tmpTask);
        model.addAttribute("users", tasksService.getUsers());
        return "tasks-views/update-task";
    }

    @RequestMapping(value = "/update-task/{id}", method = RequestMethod.POST)
    public String updateTask(@ModelAttribute("task") Task task, @PathVariable("id") Long id, @RequestParam(value = "userId") Long userId) {
        tasksService.updateTask(id, task, tasksService.getUserById(userId));
        if (onIndexPage)
            return "redirect:/tasks/all-tasks";
        else
            return "redirect:/tasks/sort-tasks/" + tasksService.getTaskById(id).getStatus();
    }


    @RequestMapping(value = "/create-task", method = RequestMethod.GET)
    public String createTask(Model model) {
        model.addAttribute("task", new Task());
        return "tasks-views/create-task";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String createTask(@ModelAttribute Task task) {
        tasksService.createTask(task);
        return "redirect:/tasks/all-tasks";
    }

    @RequestMapping(value = "/delete-task/{id}", method = RequestMethod.GET)
    public String deleteTask(@PathVariable("id") Long id) {
        tasksService.deleteTask(id);
        return "redirect:/tasks/all-tasks";
    }

    @RequestMapping(value = "/sort-tasks/{sortStatus}", method = RequestMethod.GET)
    public String sortTaskByStatus(@PathVariable("sortStatus") Status sortStatus, Model model) {
        onIndexPage = false;
        model.addAttribute("buttonStat", sortStatus);
        model.addAttribute("tasks", tasksService.getAllTasks().stream().filter(it -> it.getStatus().equals(sortStatus.getDescription())));
        return "tasks-views/all-tasks";
    }

}
