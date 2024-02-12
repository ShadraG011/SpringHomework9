package ru.shadrag.usersmicroservices.services.interfaces;



import ru.shadrag.usersmicroservices.DAO.TaskDao;
import ru.shadrag.usersmicroservices.models.User;

import java.util.List;

public interface UsersService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
    void deleteUser(Long id);

    void updateTask(Long taskId, User user);

     List<TaskDao> getAllTasks();

    TaskDao getTaskById(Long taskId);
}
