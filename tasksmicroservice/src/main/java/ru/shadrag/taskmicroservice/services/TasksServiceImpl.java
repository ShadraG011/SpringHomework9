package ru.shadrag.taskmicroservice.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shadrag.taskmicroservice.DAO.UserDao;
import ru.shadrag.taskmicroservice.models.Task;
import ru.shadrag.taskmicroservice.repositories.TasksRepository;
import ru.shadrag.taskmicroservice.services.interfaces.TasksService;

import java.util.List;

@Service
public class TasksServiceImpl implements TasksService {

    private final TasksRepository repository;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public TasksServiceImpl(TasksRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Task createTask(Task task) {
        return repository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task, UserDao user) {
        Task oldTask = getTaskById(id);
        oldTask.setTitle(task.getTitle());
        oldTask.setDescription(task.getDescription());
        if (user != null)
            oldTask.setUser(user);
        return repository.save(oldTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task deletedTask = getTaskById(id);
        repository.delete(deletedTask);
    }

    @Override
    public List<UserDao> getUsers() {
        return entityManager.createQuery("select u from UserDao u", UserDao.class).getResultList();
    }

    @Override
    public UserDao getUserById(Long userId) {
        return entityManager.find(UserDao.class, userId);
    }


}
