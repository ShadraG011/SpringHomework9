package ru.shadrag.taskmicroservice.services.interfaces;


import ru.shadrag.taskmicroservice.DAO.UserDao;
import ru.shadrag.taskmicroservice.models.Task;

import java.util.List;

public interface TasksService {
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task task, UserDao user);
    void deleteTask(Long id);

    List<UserDao> getUsers();

    UserDao getUserById(Long userId);
}
